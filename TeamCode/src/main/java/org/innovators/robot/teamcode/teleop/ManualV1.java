package org.innovators.robot.teamcode.teleop;

import static org.innovators.robot.teamcode.util.Constants.HardwareConfig.MANUAL_VIPER_ARM_WRIST_GECKO;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.innovators.robot.teamcode.hardware.RobotHardware;

@TeleOp(name = "ManualV1", group = "TeleOp")
public class ManualV1 extends LinearOpMode {
  private final RobotHardware robot = new RobotHardware();
  private final ElapsedTime runtime = new ElapsedTime();

  @Override
  public void runOpMode() {
      robot.init(hardwareMap, MANUAL_VIPER_ARM_WRIST_GECKO);

      // Reset the runtime
      runtime.reset();

      waitForStart();

      while (opModeIsActive()) {
          robot.control(MANUAL_VIPER_ARM_WRIST_GECKO, gamepad1, opModeIsActive());

          // Telemetry for debugging
          telemetry.addData("Status", "Run Time: " + runtime);
          telemetry.update();
      }
  }
}
