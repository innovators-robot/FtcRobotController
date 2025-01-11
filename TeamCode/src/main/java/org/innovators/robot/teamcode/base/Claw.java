package org.innovators.robot.teamcode.base;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.innovators.robot.teamcode.util.Constants.SERVO_POWER_DOWN;
import static org.innovators.robot.teamcode.util.Constants.SERVO_POWER_UP;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    public Servo claw = null;

    public Claw(HardwareMap hwMap) {
        claw  = hwMap.get(Servo.class, "claw_servo");

        claw.setPosition(SERVO_POWER_DOWN);
    }

    public void control(Gamepad gamepad1) {
        if (gamepad1.right_bumper) {
            claw.setPosition(SERVO_POWER_DOWN);
        } else if (gamepad1.left_bumper) {
            claw.setPosition(SERVO_POWER_UP);
        } else {
            claw.setPosition(SERVO_POWER_DOWN);
        }

        telemetry.addData("Arm Wrist Servo Position", claw.getPosition());
    }

    public void pickUpBlock() {
        claw.setPosition(SERVO_POWER_UP); // Adjust the position as needed to pick up the block
        sleep(500); // Wait for the servo to move
    }

    public void dropBlock() {
        claw.setPosition(SERVO_POWER_DOWN); // Adjust the position as needed to drop the block
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
