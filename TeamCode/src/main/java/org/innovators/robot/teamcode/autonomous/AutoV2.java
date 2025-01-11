package org.innovators.robot.teamcode.autonomous;
import static org.innovators.robot.teamcode.util.Constants.HardwareConfig.AUTO_VIPER_ARM_WRIST_MECANUM;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.innovators.robot.teamcode.hardware.RobotHardware;

@Autonomous(name="AutoV2", group="Autonomous")
public class AutoV2 extends LinearOpMode {
    protected RobotHardware robot = new RobotHardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, AUTO_VIPER_ARM_WRIST_MECANUM);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        robot.control(AUTO_VIPER_ARM_WRIST_MECANUM, gamepad1, opModeIsActive());
    }
}
