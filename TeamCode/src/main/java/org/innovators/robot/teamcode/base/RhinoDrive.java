package org.innovators.robot.teamcode.base;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.innovators.robot.teamcode.util.Constants;

public class RhinoDrive {
    public DcMotor leftDriveMotor = null;
    public DcMotor rightDriveMotor = null;

    public RhinoDrive(HardwareMap hwMap) {
        leftDriveMotor = hwMap.get(DcMotor.class, "left_drive_motor");
        rightDriveMotor = hwMap.get(DcMotor.class, "right_drive_motor");

        leftDriveMotor.setDirection(DcMotor.Direction.FORWARD);
        rightDriveMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void control(Gamepad gamepad1) {
        double drive = -gamepad1.left_stick_y * Constants.DRIVE_SPEED;
        double turn  =  gamepad1.right_stick_x * Constants.TURN_SPEED;
        double leftDrivePower = drive + turn;
        double rightDrivePower = drive - turn;

        leftDriveMotor.setPower(leftDrivePower);
        rightDriveMotor.setPower(rightDrivePower);

        telemetry.addData("Left Drive Motor Power", leftDrivePower);
        telemetry.addData("Right Drive Motor Power", rightDrivePower);
    }

    public void driveForward(double power, long time) {
        leftDriveMotor.setPower(power);
        rightDriveMotor.setPower(power);
        sleep(time);
        stopDriving();
    }

    public void driveBackward(double power, long time) {
        leftDriveMotor.setPower(-power);
        rightDriveMotor.setPower(-power);
        sleep(time);
        stopDriving();
    }

    public void stopDriving() {
        leftDriveMotor.setPower(0);
        rightDriveMotor.setPower(0);
    }

    private final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
