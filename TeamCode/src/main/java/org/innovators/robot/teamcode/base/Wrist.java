package org.innovators.robot.teamcode.base;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.innovators.robot.teamcode.util.Constants.SERVO_POWER_DOWN;
import static org.innovators.robot.teamcode.util.Constants.SERVO_POWER_MID;
import static org.innovators.robot.teamcode.util.Constants.SERVO_POWER_UP;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Wrist {
    public Servo wristTorqueServo = null;

    public Wrist(HardwareMap hwMap) {
        wristTorqueServo = hwMap.get(Servo.class, "wrist_torque_servo");

        wristTorqueServo.setPosition(SERVO_POWER_MID);
    }

    public void control(Gamepad gamepad1) {
        if (gamepad1.a) {
            // Move arm to pick up position
            wristTorqueServo.setPosition(SERVO_POWER_UP);
        } else if (gamepad1.b) {
            // Move arm to drop position
            wristTorqueServo.setPosition(SERVO_POWER_DOWN);
        } else {
            wristTorqueServo.setPosition(SERVO_POWER_MID);
        }

        telemetry.addData("Wrist Servo Position", wristTorqueServo.getPosition());
    }
}
