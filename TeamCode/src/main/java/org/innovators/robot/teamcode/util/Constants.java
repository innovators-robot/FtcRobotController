package org.innovators.robot.teamcode.util;
public class Constants {
    public static final double DRIVE_SPEED = 0.5;
    public static final double TURN_SPEED = 0.3;
    public static final double SERVO_POWER_UP = 1.0;
    public static final double SERVO_POWER_MID = 0.5;
    public static final double SERVO_POWER_DOWN = 0.0;

    public static final int SLIDE_POSITION_TOP = 1000; // Example position value for top
    public static final int SLIDE_POSITION_BOTTOM = 0; // Example position value for bottom
    public static final int SLIDE_POSITION_PICKUP = 20; // Example position value for top

    public static final int FUDGE_FACTOR = 10; // Example fudge factor value

    public enum HardwareConfig {
        AUTO_VIPER_ARM_WRIST_GECKO,
        AUTO_VIPER_ARM_WRIST_MECANUM,
        MANUAL_VIPER_ARM_WRIST_GECKO,
        MANUAL_VIPER_ARM_WRIST_MECANUM
    }
}

