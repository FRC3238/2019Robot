package frc.team3238.robot.settings;

public final class FredXConstants {

    //Joystick port numbers -------------------------------------------------------------
    public static final int DRIVE_JOYSTICK_PORT       = 0;
    public static final int MANIPULATOR_JOYSTICK_PORT = 1;

    //Talon CAN ids ---------------------------------------------------------------------
    public static final int SPUDS_NUM              = 4;
    public static final int ROLLER_NUM             = 5;
    public static final int LIFT_NUM               = 8;
    public static final int WRIST_NUM              = 9;
    public static final int BEAK_NUM               = 10;

    //Talon direction adjustments -------------------------------------------------------
    public static final boolean REVERSE_SPUDS    = false;
    public static final boolean REVERSE_ROLLER   = false;
    public static final boolean REVERSE_LIFT     = false;
    public static final boolean REVERSE_WRIST    = false;
    public static final boolean REVERSE_BEAK     = false;

    //Talons brake in neutral -----------------------------------------------------------
    public static final boolean SPUDS_NEUTRAL_BRAKE    = true;
    public static final boolean ROLLER_NEUTRAL_BRAKE   = true;
    public static final boolean LIFT_NEUTRAL_BRAKE     = true;
    public static final boolean WRIST_NEUTRAL_BRAKE    = true;
    public static final boolean BEAK_NEUTRAL_BRAKE     = true;

    //Sensor phase adjustments ----------------------------------------------------------
    public static final boolean FLIP_SPUD_SENSOR     = false;
    public static final boolean FLIP_LIFT_SENSOR     = false;
    public static final boolean FLIP_WRIST_SENSOR    = false;

    //Talon configuration timeout ------------------------------------------------------0
    public static final int TALON_TIMEOUT = 30;

    //Speed adjustments for manual operation --------------------------------------------
    public static final double SPUD_SPEED        = 1.00;
    public static final double SPUD_ROLLER_SPEED = 1.00;
    public static final double LIFT_SPEED        = 1.00;
    public static final double WRIST_SPEED       = 1.00;
    public static final double BEAK_SPEED        = 1.00;
    public static final double CAMERA_SPEED      = 1.00;

    //Joystick button ids ---------------------------------------------------------------
    public static final int SAFETY_BUTTON           = 8;
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
    public static final double CAMERA_PAN_DEFAULT  = 90;
    public static final double CAMERA_TILT_DEFAULT = 120;

    //Camera HAT angles -----------------------------------------------------------------
    public static final int CAMERA_UP         = 0;
    public static final int CAMERA_RIGHT_UP   = 45;
    public static final int CAMERA_RIGHT      = 90;
    public static final int CAMERA_RIGHT_DOWN = 135;
    public static final int CAMERA_DOWN       = 180;
    public static final int CAMERA_LEFT_DOWN  = 225;
    public static final int CAMERA_LEFT       = 270;
    public static final int CAMERA_LEFT_UP    = 315;

    //Travel limits (inclusive) ----------------------------------------------------------
    public static final double SPUDS_MAX_EXTEND    = Double.MAX_VALUE; //TODO: Limit spud range
    public static final double SPUD_MIN_EXTEND     = Double.MIN_VALUE;
    public static final double LIFT_MAX_EXTEND     = Double.MAX_VALUE; //TODO: Limit lift range
    public static final double LIFT_MIN_EXTEND     = Double.MIN_VALUE;
    public static final double WRIST_MAX_EXTEND    = Double.MAX_VALUE; //TODO: Limit wrist range
    public static final double WRIST_MIN_EXTEND    = Double.MIN_VALUE;
    public static final double CAMERA_MAX_PAN      = 180;
    public static final double CAMERA_MIN_PAN      = 0;
    public static final double CAMERA_MAX_TILT     = 180;
    public static final double CAMERA_MIN_TILT     = 0;

    //Target accelerations (native-units per 100ms per second) --------------------------
    public static final int SPUDS_ACCELERATION    = 100;
    public static final int LIFT_ACCELERATION     = 100;
    public static final int WRIST_ACCELERATION    = 100;

    //Target velocities (native-units per 100ms) ----------------------------------------
    public static final int SPUDS_VELOCITY    = 500;
    public static final int LIFT_VELOCITY     = 500;
    public static final int WRIST_VELOCITY    = 500;

    //PID constants -------------------------------------------------------------------
    public static final double SPUDS_kP = 0.15;
    public static final double SPUDS_kI = 0;
    public static final double SPUDS_kD = 0;

    public static final double LIFT_kP = 0.15;
    public static final double LIFT_kI = 0;
    public static final double LIFT_kD = 0;

    public static final double WRIST_kP = 0.15;
    public static final double WRIST_kI = 0;
    public static final double WRIST_kD = 0;
}
