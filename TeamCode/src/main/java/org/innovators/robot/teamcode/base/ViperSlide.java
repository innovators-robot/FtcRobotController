package org.innovators.robot.teamcode.base;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.innovators.robot.teamcode.util.Constants;

public class ViperSlide {
    private DcMotorEx viperSlideMotor;

    public ViperSlide(HardwareMap hwMap) {
        viperSlideMotor = hwMap.get(DcMotorEx.class, "viper_slide_motor");

        // Set the motor to run using its encoder
        viperSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

//        /* Most skid-steer/differential drive robots require reversing one motor to drive forward.
//        for this robot, we reverse the right motor.*/
//        viperSlide.setDirection(DcMotor.Direction.REVERSE);
//
//        /* Setting zeroPowerBehavior to BRAKE enables a "brake mode". This causes the motor to slow down
//        much faster when it is coasting. This creates a much more controllable drivetrain. As the robot
//        stops much quicker. */
//        viperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //Added for ViperSlide
//
//        /*This sets the maximum current that the control hub will apply to the arm before throwing a flag */
//        ((DcMotorEx) viperSlide).setCurrentAlert(5, CurrentUnit.AMPS);
//
//        // Configure motor for ViperSlide
//        viperSlide.setTargetPosition(0);
//        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        viperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        viperSlide.setPower(0);
//        viperSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void control(Gamepad gamepad1, boolean opModeIsActive) {
        // Control the slide with gamepad buttons
        if (gamepad1.dpad_up) {
            moveToPosition(Constants.SLIDE_POSITION_TOP, opModeIsActive); // Move to top position
        } else if (gamepad1.dpad_down) {
            moveToPosition(Constants.SLIDE_POSITION_BOTTOM, opModeIsActive); // Move to bottom position
        } else if (gamepad1.dpad_left) {
            viperSlideMotor.setPower(0.5); // Move up manually
        } else if (gamepad1.dpad_right) {
            viperSlideMotor.setPower(-0.5); // Move down manually
        } else {
            viperSlideMotor.setPower(0); // Stop
        }

        telemetry.addData("Slide Position", viperSlideMotor.getCurrentPosition());
    }

    public void moveToPosition(int position, boolean opModeIsActive) {
        int targetPosition = position + Constants.FUDGE_FACTOR;
        viperSlideMotor.setTargetPosition(targetPosition);
        viperSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperSlideMotor.setPower(1.0);

        while (opModeIsActive && viperSlideMotor.isBusy()) {
            telemetry.addData("Moving to Position", targetPosition);
            telemetry.addData("Current Position", viperSlideMotor.getCurrentPosition());
            telemetry.update();
        }

        viperSlideMotor.setPower(0);
        viperSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
