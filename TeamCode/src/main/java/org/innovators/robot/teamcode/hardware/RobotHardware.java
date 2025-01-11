package org.innovators.robot.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.innovators.robot.teamcode.base.Arm;
import org.innovators.robot.teamcode.base.Claw;
import org.innovators.robot.teamcode.base.FieldCentricMecanumDrive;
import org.innovators.robot.teamcode.base.ViperSlide;
import org.innovators.robot.teamcode.base.Wrist;
import org.innovators.robot.teamcode.base.GeckoWheel;
import org.innovators.robot.teamcode.base.RhinoDrive;
import org.innovators.robot.teamcode.base.RobotCentricMecanumDrive;
import org.innovators.robot.teamcode.util.Constants;
import org.innovators.robot.teamcode.util.Constants.HardwareConfig;

public class RobotHardware {
    private HardwareMap hwMap;
    public Arm arm;
    public Claw claw;
    public FieldCentricMecanumDrive fieldCentricMecanumDrive;
    public GeckoWheel geckoWheel;
    public RhinoDrive rhinoDrive;
    public RobotCentricMecanumDrive robotCentricMecanumDrive;
    public ViperSlide viperSlide;
    public Wrist wrist;

    public RobotHardware() {
        hwMap = null;
        arm = null;
        claw = null;
        fieldCentricMecanumDrive = null;
        geckoWheel = null;
        rhinoDrive = null;
        robotCentricMecanumDrive = null;
        viperSlide = null;
        wrist = null;
    }

    public void init(HardwareMap ahwMap, HardwareConfig hwConfig) {
        hwMap = ahwMap;

        switch (hwConfig) {
          case AUTO_VIPER_ARM_WRIST_GECKO:
          case MANUAL_VIPER_ARM_WRIST_GECKO:
                arm = new Arm(ahwMap);
                geckoWheel = new GeckoWheel(ahwMap);
                rhinoDrive = new RhinoDrive(ahwMap);
                viperSlide = new ViperSlide(ahwMap);
                wrist = new Wrist(ahwMap);
          break;

          case AUTO_VIPER_ARM_WRIST_MECANUM:
          case MANUAL_VIPER_ARM_WRIST_MECANUM:
                arm = new Arm(ahwMap);
                claw = new Claw(ahwMap);
                viperSlide = new ViperSlide(ahwMap);
                wrist = new Wrist(ahwMap);
                robotCentricMecanumDrive = new RobotCentricMecanumDrive(ahwMap);
          break;

          default:
                arm = new Arm(ahwMap);
                claw = new Claw(ahwMap);
                fieldCentricMecanumDrive = new FieldCentricMecanumDrive(ahwMap);
                geckoWheel = new GeckoWheel(ahwMap);
                rhinoDrive = new RhinoDrive(ahwMap);
                robotCentricMecanumDrive = new RobotCentricMecanumDrive(ahwMap);
                viperSlide = new ViperSlide(ahwMap);
                wrist = new Wrist(ahwMap);
        }
    }

    public void control(HardwareConfig hwConfig, Gamepad gamepad1, boolean opModeIsActive) {

        switch (hwConfig) {
            case AUTO_VIPER_ARM_WRIST_GECKO:
                // Step 1: Drive forward to the block
                rhinoDrive.driveForward(Constants.DRIVE_SPEED, 1000); // Drive forward at 50% power for 1000 milliseconds

                // Step 2: Pick up the block
                geckoWheel.pickUpBlock();

                // Step 3: Drive to the basket
                rhinoDrive.driveForward(Constants.DRIVE_SPEED, 1000); // Adjust the distance as needed

                // Step 4: Drop the block in the basket
                geckoWheel.dropBlock();

                // Step 5: Back away from the basket
                rhinoDrive.driveBackward(Constants.DRIVE_SPEED, 500); // Drive backward at 50% power for 500 milliseconds
            break;

            case MANUAL_VIPER_ARM_WRIST_GECKO:
                arm.control(gamepad1);
                geckoWheel.control(gamepad1);
                rhinoDrive.control(gamepad1);
                viperSlide.control(gamepad1, opModeIsActive);
                wrist.control(gamepad1);
            break;

            case AUTO_VIPER_ARM_WRIST_MECANUM:
                // Step 1: Drive forward to the block
                robotCentricMecanumDrive.driveForward(Constants.DRIVE_SPEED, 1000); // Drive forward at 50% power for 1000 milliseconds

                // Step 2: Extend the viper slide for pick up
                viperSlide.moveToPosition(Constants.SLIDE_POSITION_PICKUP, opModeIsActive); // Move to top position

                // Step 3: Pick up the block
                claw.pickUpBlock();

                // Step 4: Retract viper slide for drive
                viperSlide.moveToPosition(Constants.SLIDE_POSITION_BOTTOM, opModeIsActive); // Move to top position

                // Step 5: Drive to the basket
                robotCentricMecanumDrive.driveForward(Constants.DRIVE_SPEED, 1000); // Adjust the distance as needed

                // Step 6: Extend Viper slide here to drop the block
                viperSlide.moveToPosition(Constants.SLIDE_POSITION_TOP, opModeIsActive); // Move to top position

                // Step 7: Drop the block in the basket
                claw.dropBlock();

                // Step 9: Retract viper slide for drive
                viperSlide.moveToPosition(Constants.SLIDE_POSITION_BOTTOM, opModeIsActive); // Move to top position

                // Step 10: Back away from the basket
                robotCentricMecanumDrive.driveBackward(Constants.DRIVE_SPEED, 500); // Drive backward at 50% power for 500 milliseconds
            break;

            case MANUAL_VIPER_ARM_WRIST_MECANUM:
                arm.control(gamepad1);
                claw.control(gamepad1);
                robotCentricMecanumDrive.control(gamepad1);
                viperSlide.control(gamepad1, opModeIsActive);
                wrist.control(gamepad1);
            break;

            default:
                arm.control(gamepad1);
                claw.control(gamepad1);
                fieldCentricMecanumDrive.control(gamepad1);
                geckoWheel.control(gamepad1);
                rhinoDrive.control(gamepad1);
                robotCentricMecanumDrive.control(gamepad1);
                viperSlide.control(gamepad1, opModeIsActive);
                wrist.control(gamepad1);
        }
    }
}

