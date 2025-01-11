package org.innovators.robot.teamcode.base;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    public DcMotor armMotor = null;

    public Arm(HardwareMap hwMap) {
        armMotor = hwMap.get(DcMotor.class, "arm_motor");

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void control(Gamepad gamepad1) {
        double armPower = gamepad1.right_trigger - gamepad1.left_trigger;
        armMotor.setPower(armPower);

        telemetry.addData("Arm Motor Power", armPower);
        telemetry.addData("Arm Motor Position", armMotor.getCurrentPosition());
    }
}
