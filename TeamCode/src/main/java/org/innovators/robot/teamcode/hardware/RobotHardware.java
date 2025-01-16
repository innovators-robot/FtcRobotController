package org.innovators.robot.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class RobotHardware {
    public DcMotor leftDriveMotor = null;
    public DcMotor rightDriveMotor = null;
    public DcMotor armExtensionMotor = null;
    public Servo armWristTorqueServo = null;
    public Servo geckoWheelSpeedServo = null;
    HardwareMap hardwareMap = null;


    //public DcMotor  rightDrive  = null; //the right drivetrain motor
    public DcMotor  armMotor    = null; //the arm motor

    public Servo    wrist       = null; //the wrist servo
    public Servo    claw       = null; //the wrist servo
    public Servo    finger       = null; //the finger servo

    private DcMotor viperSlide = null; //Added for ViperSlide


    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    final double ARM_TICKS_PER_DEGREE =
            28 // number of encoder ticks per rotation of the bare motor
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1/360.0; // we want ticks per degree, not per rotation

    final double ARM_COLLAPSED_INTO_ROBOT  = 20;
    final double ARM_COLLECT               = 5 * ARM_TICKS_PER_DEGREE; //Original Value = 250
    final double ARM_CLEAR_BARRIER         = 16 * ARM_TICKS_PER_DEGREE; //Original Value = 230
    final double ARM_SCORE_SPECIMEN        = 70 * ARM_TICKS_PER_DEGREE; //Original Value = 160

    final double ARM_SCORE_SPECIMEN_DOWN        = 48 * ARM_TICKS_PER_DEGREE; //Original Value = 160


    final double ARM_SCORE_SAMPLE_IN_LOW   = 80 * ARM_TICKS_PER_DEGREE; //Original Value = 160
    final double ARM_SCORE_SAMPLE_IN_HIGH   = 90 * ARM_TICKS_PER_DEGREE; //Original Value = 160 //Added by Serat
    final double ARM_ATTACH_HANGING_HOOK   = 130 * ARM_TICKS_PER_DEGREE; //Original Value = 120
    final double ARM_WINCH_ROBOT           = 12  * ARM_TICKS_PER_DEGREE; //Original Value = 15

    /* Variables to store the speed the intake servo should be set at to intake, and deposit game elements. */
    final double INTAKE_COLLECT    = -1.0;
    final double INTAKE_OFF        =  0.0;
    final double INTAKE_DEPOSIT    =  0.5;


    final double FINGER_UP    = -1.0;
    final double FINGER_DOWN        =  0.0;
    final double FINGER_STRAIGHT    =  0.4;





    /* Variables to store the positions that the wrist should be set to when folding in, or folding out. */
    final double WRIST_FOLDED_IN   = 0.2; // Serat - This was 0.8333
    final double WRIST_FOLDED_OUT  = 0.75; // Serat - This was 0.5
    final double WRIST_RIGHT_FUDGE  = 0.85; // Added for wrist control
    final double WRIST_LEFT_FUDGE  = 0.65; // Added for wrist control


    /* A number in degrees that the triggers can adjust the arm position by */
    final double FUDGE_FACTOR = 15 * ARM_TICKS_PER_DEGREE; //= 15 * ARM_TICKS_PER_DEGREE;

    final double VIPER_FUDGE_FACTOR = 100;

    final double WRIST_FUDGE_FACTOR = 0.10;


    /* Variables that are used to set the arm to a specific position */
    double armPosition = (int)ARM_COLLAPSED_INTO_ROBOT;
    double armPositionFudgeFactor;
    double viperPositionFudgeFactor;
    double wristPositionFudgeFactor;

    double viperCurrentPosition;
    double viperNewPosition;

    private static final int SLIDE_MIN_POSITION = 0;     // Retracted position - Original Value = 0
    private static final int SLIDE_MAX_POSITION = 2050;  // Fully extended position Original Value = 3000
    private static final int SLIDE_MID_POSITION = 1000;  // Midway point Original Value = 1500

    private static final int SLIDE_MAX_IN_SUBMERSIBLE = 1000;  // Added this on 01/15/2025 to restrict the slide from going out of robot limits


    double slidetargetPosition = (int)SLIDE_MIN_POSITION;


    public void init(HardwareMap ahwMap) {
        BlocksOpModeCompanion.hardwareMap = ahwMap;


        armMotor   = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "left_arm"); //the arm motor
        viperSlide = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "viperSlide"); //Added for viperSlide

        frontLeft = BlocksOpModeCompanion.hardwareMap.dcMotor.get("frontLeft");
        frontRight = BlocksOpModeCompanion.hardwareMap.dcMotor.get("frontRight");
        backLeft = BlocksOpModeCompanion.hardwareMap.dcMotor.get("backLeft");
        backRight = BlocksOpModeCompanion.hardwareMap.dcMotor.get("backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        armMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        viperSlide.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //Added for ViperSlide

        ((DcMotorEx) armMotor).setCurrentAlert(5, CurrentUnit.AMPS);

        ((DcMotorEx) viperSlide).setCurrentAlert(5,CurrentUnit.AMPS);

        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        viperSlide.setTargetPosition(0);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperSlide.setPower(0);
        viperSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        claw  = BlocksOpModeCompanion.hardwareMap.get(Servo.class, "claw");
        wrist  = BlocksOpModeCompanion.hardwareMap.get(Servo.class, "wrist");
        finger  = BlocksOpModeCompanion.hardwareMap.get(Servo.class, "finger");

        claw.setPosition(0.0);

        wrist.setPosition(0.65);
        wrist.setDirection(Servo.Direction.REVERSE);
        finger.setPosition(FINGER_UP);

        /* OLD CODE
        // Two motor wheel driving
        //leftDriveMotor = hardwareMap.get(DcMotor.class, "left_drive_motor");
        //rightDriveMotor = hardwareMap.get(DcMotor.class, "right_drive_motor");
        //Replace code above with this code for Mecanum Wheels. Change the variables if needed.
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        //leftDriveMotor.setDirection(DcMotor.Direction.FORWARD);
        //rightDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        // Replace code above with this code for Mecanum Wheels. Change the variables if needed.
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        // armExtension wrist and gecko wheel
        armExtensionMotor = hardwareMap.get(DcMotor.class, "arm_extension_motor");
        armWristTorqueServo = hardwareMap.get(Servo.class, "arm_wrist_torque_servo");
        geckoWheelSpeedServo = hardwareMap.get(Servo.class, "gecko_wheel_speed_servo");

        armExtensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armWristTorqueServo.setPosition(SERVO_POWER_MID);
        geckoWheelSpeedServo.setPosition(SERVO_POWER_DOWN);

         */
    }
}

