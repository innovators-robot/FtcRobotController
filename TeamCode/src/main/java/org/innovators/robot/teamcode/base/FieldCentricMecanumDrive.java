package org.innovators.robot.teamcode.base;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class FieldCentricMecanumDrive {
    private DcMotor frontLeftDriveMotor = null;
    private DcMotor frontRightDriveMotor = null;
    private DcMotor backLeftDriveMotor = null;
    private DcMotor backRightDriveMotor = null;
    private BNO055IMU imu;
    private Orientation angles;

    public FieldCentricMecanumDrive(HardwareMap hwMap) {
        frontLeftDriveMotor = hwMap.get(DcMotor.class, "front_left_drive_motor");
        frontRightDriveMotor = hwMap.get(DcMotor.class, "front_right_drive_motor");
        backLeftDriveMotor = hwMap.get(DcMotor.class, "back_left_drive_motor");
        backRightDriveMotor = hwMap.get(DcMotor.class, "back_right_drive_motor");

        frontLeftDriveMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftDriveMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightDriveMotor.setDirection(DcMotor.Direction.REVERSE);

        imu = hwMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);
    }

    public void control(Gamepad gamepad1) {
        // Get joystick values
        double drive = -gamepad1.left_stick_y; // Inverted Y axis
        double strafe = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        // Get robot orientation
        angles = imu.getAngularOrientation();
        double botHeading = -angles.firstAngle;

        // Calculate field-centric values
        double rotX = strafe * Math.cos(botHeading) - drive * Math.sin(botHeading);
        double rotY = strafe * Math.sin(botHeading) + drive * Math.cos(botHeading);

        // Calculate motor powers
        double frontLeftPower = rotY + rotX + rotate;
        double frontRightPower = rotY - rotX - rotate;
        double backLeftPower = rotY - rotX + rotate;
        double backRightPower = rotY + rotX - rotate;

        // Set motor powers
        frontLeftDriveMotor.setPower(Range.clip(frontLeftPower, -1.0, 1.0));
        frontRightDriveMotor.setPower(Range.clip(frontRightPower, -1.0, 1.0));
        backLeftDriveMotor.setPower(Range.clip(backLeftPower, -1.0, 1.0));
        backRightDriveMotor.setPower(Range.clip(backRightPower, -1.0, 1.0));

        // Telemetry for debugging
        telemetry.addData("Front Left Power", frontLeftPower);
        telemetry.addData("Front Right Power", frontRightPower);
        telemetry.addData("Back Left Power", backLeftPower);
        telemetry.addData("Back Right Power", backRightPower);
        telemetry.addData("Heading", botHeading);
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
