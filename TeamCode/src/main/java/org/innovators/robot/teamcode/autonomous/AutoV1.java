package org.innovators.robot.teamcode.autonomous;
import static org.innovators.robot.teamcode.util.Constants.HardwareConfig.AUTO_VIPER_ARM_WRIST_GECKO;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.innovators.robot.teamcode.hardware.RobotHardware;


@Autonomous(name="AutoV1", group="Autonomous")
public class AutoV1 extends LinearOpMode {
    protected RobotHardware robot = new RobotHardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, AUTO_VIPER_ARM_WRIST_GECKO);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        robot.control(AUTO_VIPER_ARM_WRIST_GECKO, gamepad1, opModeIsActive());
    }
}
