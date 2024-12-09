package org.innovators.robot.teamcode.hardware;
import static org.innovators.robot.teamcode.util.Constants.SERVO_POWER_DOWN;
import static org.innovators.robot.teamcode.util.Constants.SERVO_POWER_MID;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotHardware {
    public DcMotor leftDriveMotor = null;
    public DcMotor rightDriveMotor = null;
    public DcMotor armExtensionMotor = null;
    public Servo armWristTorqueServo = null;
    public Servo geckoWheelSpeedServo = null;
    HardwareMap hwMap = null;

    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;

        // Two motor wheel driving
        leftDriveMotor = hwMap.get(DcMotor.class, "left_drive_motor");
        rightDriveMotor = hwMap.get(DcMotor.class, "right_drive_motor");
        /* Replace code above with this code for Mecanum Wheels. Change the variables if needed.
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight"); */

        leftDriveMotor.setDirection(DcMotor.Direction.FORWARD);
        rightDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        /* Replace code above with this code for Mecanum Wheels. Change the variables if needed.
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD); */

        // armExtension wrist and gecko wheel
        armExtensionMotor = hwMap.get(DcMotor.class, "arm_extension_motor");
        armWristTorqueServo = hwMap.get(Servo.class, "arm_wrist_torque_servo");
        geckoWheelSpeedServo = hwMap.get(Servo.class, "gecko_wheel_speed_servo");

        armExtensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armWristTorqueServo.setPosition(SERVO_POWER_MID);
        geckoWheelSpeedServo.setPosition(SERVO_POWER_DOWN);
    }
}

