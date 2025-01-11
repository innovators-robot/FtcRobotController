package org.innovators.robot.teamcode.teleop;

import static org.innovators.robot.teamcode.util.Constants.HardwareConfig.MANUAL_VIPER_ARM_WRIST_MECANUM;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.innovators.robot.teamcode.hardware.RobotHardware;

@TeleOp(name = "ManualV2", group = "TeleOp")
public class ManualV2 extends LinearOpMode {
    private final RobotHardware robot = new RobotHardware();
    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, MANUAL_VIPER_ARM_WRIST_MECANUM);

        // Reset the runtime
        runtime.reset();

        waitForStart();

        while (opModeIsActive()) {
            robot.control(MANUAL_VIPER_ARM_WRIST_MECANUM, gamepad1, opModeIsActive());

            // Telemetry for debugging
            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.update();
        }
    }
}
