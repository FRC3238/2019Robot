package frc.team3238.robot;

/**
 * A central configuration page for all constants used by FredX
 *
 * @author Loren
 */
public final class FREDDXConstants {

    //Joystick port numbers -------------------------------------------------------------
    public static final int DRIVE_JOYSTICK_PORT       = 0;
    public static final int MANIPULATOR_JOYSTICK_PORT = 1;

    //Talon CAN ids ---------------------------------------------------------------------
    public static final int DRIVE_LEFT_MASTER_NUM  = 0;
    public static final int DRIVE_LEFT_SLAVE_NUM   = 2;
    public static final int DRIVE_RIGHT_MASTER_NUM = 1;
    public static final int DRIVE_RIGHT_SLAVE_NUM  = 3;
    public static final int SPUDS_NUM              = 4;
    public static final int ROLLER_NUM             = 5;
    public static final int BREACHER_MASTER_NUM    = 7;
    public static final int BREACHER_SLAVE_NUM     = 6;
    public static final int LIFT_NUM               = 8;
    public static final int WRIST_NUM              = 9;
    public static final int BEAK_NUM               = 10;

    //Talon direction adjustments -------------------------------------------------------
    public static final boolean REVERSE_DRIVE    = false;
    public static final boolean REVERSE_BREACHER = false;
    public static final boolean REVERSE_SPUDS    = false;
    public static final boolean REVERSE_ROLLER   = false;
    public static final boolean REVERSE_LIFT     = false;
    public static final boolean REVERSE_WRIST    = false;
    public static final boolean REVERSE_BEAK     = false;

    //Talons brake in neutral -----------------------------------------------------------
    public static final boolean DRIVE_NEUTRAL_BRAKE    = true;
    public static final boolean BREACHER_NEUTRAL_BRAKE = true;
    public static final boolean SPUDS_NEUTRAL_BRAKE    = true;
    public static final boolean ROLLER_NEUTRAL_BRAKE   = true;
    public static final boolean LIFT_NEUTRAL_BRAKE     = true;
    public static final boolean WRIST_NEUTRAL_BRAKE    = true;
    public static final boolean BEAK_NEUTRAL_BRAKE     = true;

    //Sensor phase adjustments ----------------------------------------------------------
    public static final boolean FLIP_BREACHER_SENSOR = false;
    public static final boolean FLIP_SPUD_SENSOR     = false;
    public static final boolean FLIP_LIFT_SENSOR     = false;
    public static final boolean FLIP_WRIST_SENSOR    = false;

    //Talon configuration timeout -------------------------------------------------------
    public static final int TALON_TIMEOUT = 30;

    //Motor speed adjustments -----------------------------------------------------------
    public static final double SPUD_ROLLER_SPEED = 1.00;
    public static final double BEAK_SPEED        = 1.00;
    public static final int    CAMERA_SPEED      = 1;

    //Joystick button ids ---------------------------------------------------------------
    public static final int SPUDS_UP_BUTTON         = 5;
    public static final int SPUDS_DOWN_BUTTON       = 3;
    public static final int ROLLER_FORWARD_BUTTON   = 1;
    public static final int ROLLER_BACKWARD_BUTTON  = 2;
    public static final int BREACHER_EXTEND_BUTTON  = 6;
    public static final int BREACHER_RETRACT_BUTTON = 4;
    public static final int WRIST_UP_BUTTON         = 5;
    public static final int WRIST_DOWN_BUTTON       = 3;
    public static final int BEAK_EXTEND_BUTTON      = 2;
    public static final int BEAK_RETRACT_BUTTON     = 1;

    //Joystick deadbands ----------------------------------------------------------------
    public static final double THROTTLE_DEADBAND = 0.2;
    public static final double STEERING_DEADBAND = 0.2;
    public static final double LIFTING_DEADBAND  = 0.2;

    //Servo channels --------------------------------------------------------------------
    public static final int CAMERA_PAN_CHANNEL  = 0;
    public static final int CAMERA_TILT_CHANNEL = 1;

    //Camera default position -----------------------------------------------------------
    public static final int CAMERA_PAN_DEFAULT  = 90;
    public static final int CAMERA_TILT_DEFAULT = 120;

    //Travel limits (inclusive) ----------------------------------------------------------
    public static final int    BREACHER_MAX_EXTEND = 4096;
    public static final int    BREACHER_MIN_EXTEND = 0;
    public static final int    SPUDS_MAX_EXTEND    = 4096;
    public static final int    SPUD_MIN_EXTEND     = 0;
    public static final double LIFT_MAX_EXTEND     = 5;
    public static final double LIFT_MIN_EXTEND     = 0;
    public static final int    WRIST_MAX_EXTEND    = 720;
    public static final int    WRIST_MIN_EXTEND    = 0;
    public static final int    CAMERA_MAX_PAN      = 180;
    public static final int    CAMERA_MIN_PAN      = 0;
    public static final int    CAMERA_MAX_TILT     = 180;
    public static final int    CAMERA_MIN_TILT     = 0;

    //Target accelerations (native-units per 100ms per second) --------------------------
    public static final int BREACHER_ACCELERATION = 100;
    public static final int SPUDS_ACCELERATION    = 100;
    public static final int LIFT_ACCELERATION     = 100;
    public static final int WRIST_ACCELERATION    = 100;

    //Target velocities (native-units per 100ms) ----------------------------------------
    public static final int BREACHER_VELOCITY = 500;
    public static final int SPUDS_VELOCITY    = 500;
    public static final int LIFT_VELOCITY     = 500;
    public static final int WRIST_VELOCITY    = 500;

    //PID-F constants -------------------------------------------------------------------
    public static final double BREACHER_kP = 0;
    public static final double BREACHER_kI = 0;
    public static final double BREACHER_kD = 0;

    public static final double SPUDS_kP = 0;
    public static final double SPUDS_kI = 0;
    public static final double SPUDS_kD = 0;

    public static final double LIFT_kP = 0;
    public static final double LIFT_kI = 0;
    public static final double LIFT_kD = 0;

    public static final double WRIST_kP = 0;
    public static final double WRIST_kI = 0;
    public static final double WRIST_kD = 0;
}
