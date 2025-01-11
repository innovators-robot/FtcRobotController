package org.innovators.robot.teamcode.base;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.innovators.robot.teamcode.util.Constants.SERVO_POWER_DOWN;
import static org.innovators.robot.teamcode.util.Constants.SERVO_POWER_UP;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class GeckoWheel {
    public Servo geckoWheelSpeedServo = null;

    public GeckoWheel(HardwareMap hwMap) {
        geckoWheelSpeedServo = hwMap.get(Servo.class, "gecko_wheel_speed_servo");

        geckoWheelSpeedServo.setPosition(SERVO_POWER_DOWN);
    }

    public void control(Gamepad gamepad1) {
        if (gamepad1.right_bumper) {
            geckoWheelSpeedServo.setPosition(SERVO_POWER_DOWN);
        } else if (gamepad1.left_bumper) {
            geckoWheelSpeedServo.setPosition(SERVO_POWER_UP);
        } else {
            geckoWheelSpeedServo.setPosition(SERVO_POWER_DOWN);
        }

        telemetry.addData("Gecko Wheel Servo Position", geckoWheelSpeedServo.getPosition());
    }

    public void pickUpBlock() {
        geckoWheelSpeedServo.setPosition(SERVO_POWER_UP); // Adjust the position as needed to pick up the block
        sleep(500); // Wait for the servo to move
    }

    public void dropBlock() {
        geckoWheelSpeedServo.setPosition(SERVO_POWER_DOWN); // Adjust the position as needed to drop the block
        sleep(500); // Wait for the servo to move
    }

    private final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
