package org.innovators.robot.teamcode.base;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotCentricMecanumDrive {
    public DcMotor frontLeftDriveMotor = null;
    public DcMotor frontRightDriveMotor = null;
    public DcMotor backLeftDriveMotor = null;
    public DcMotor backRightDriveMotor = null;

    public RobotCentricMecanumDrive(HardwareMap hwMap) {
        frontLeftDriveMotor = hwMap.get(DcMotor.class, "front_left_drive_motor");
        frontRightDriveMotor = hwMap.get(DcMotor.class, "front_right_drive_motor");
        backLeftDriveMotor = hwMap.get(DcMotor.class, "back_left_drive_motor");
        backRightDriveMotor = hwMap.get(DcMotor.class, "back_right_drive_motor");

        frontLeftDriveMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftDriveMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightDriveMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void control(Gamepad gamepad1) {
        double drive = -gamepad1.left_stick_y; // Forward/backward
        double strafe = gamepad1.left_stick_x; // Left/right
        double rotate = gamepad1.right_stick_x; // Rotation

        double frontLeftPower = drive + strafe + rotate;
        double frontRightPower = drive - strafe - rotate;
        double backLeftPower = drive - strafe + rotate;
        double backRightPower = drive + strafe - rotate;

        frontLeftDriveMotor.setPower(frontLeftPower);
        frontRightDriveMotor.setPower(frontRightPower);
        backLeftDriveMotor.setPower(backLeftPower);
        backRightDriveMotor.setPower(backRightPower);

        telemetry.addData("Front Left Power", frontLeftPower);
        telemetry.addData("Front Right Power", frontRightPower);
        telemetry.addData("Back Left Power", backLeftPower);
        telemetry.addData("Back Right Power", backRightPower);
    }
    public void driveForward(double power, long time) {
        frontLeftDriveMotor.setPower(power);
        frontRightDriveMotor.setPower(power);
        backLeftDriveMotor.setPower(power);
        backRightDriveMotor.setPower(power);
        sleep(time);
        stopDriving();
    }

    public void driveBackward(double power, long time) {
        frontLeftDriveMotor.setPower(-power);
        frontRightDriveMotor.setPower(-power);
        backLeftDriveMotor.setPower(-power);
        backRightDriveMotor.setPower(-power);
        sleep(time);
        stopDriving();
    }

    public void stopDriving() {
        frontLeftDriveMotor.setPower(0);
        frontRightDriveMotor.setPower(0);
        backLeftDriveMotor.setPower(0);
        backRightDriveMotor.setPower(0);
    }

    private final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
