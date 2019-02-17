package frc.team3238.robot;

/**
 * A central configuration page for all constants used by FredX
 *
 * @author Loren
 */
final class FredXConstants {

    //Joystick port numbers -------------------------------------------------------------
    static final int DRIVE_JOYSTICK_PORT       = 0;
    static final int MANIPULATOR_JOYSTICK_PORT = 1;

    //Talon CAN ids ---------------------------------------------------------------------
    static final int DRIVE_LEFT_MASTER_NUM  = 0;
    static final int DRIVE_LEFT_SLAVE_NUM   = 2;
    static final int DRIVE_RIGHT_MASTER_NUM = 1;
    static final int DRIVE_RIGHT_SLAVE_NUM  = 3;
    static final int SPUDS_NUM              = 4;
    static final int ROLLER_NUM             = 5;
    static final int BREACHER_MASTER_NUM    = 6;
    static final int BREACHER_SLAVE_NUM     = 7;
    static final int LIFT_NUM               = 8;
    static final int WRIST_NUM              = 9;
    static final int BEAK_NUM               = 10;

    //Talon direction adjustments -------------------------------------------------------
    static final boolean REVERSE_DRIVE    = false;
    static final boolean REVERSE_BREACHER = false;
    static final boolean REVERSE_SPUDS    = false;
    static final boolean REVERSE_ROLLER   = false;
    static final boolean REVERSE_LIFT     = false;
    static final boolean REVERSE_WRIST    = false;
    static final boolean REVERSE_BEAK     = false;

    //Talons brake in neutral -----------------------------------------------------------
    static final boolean DRIVE_NEUTRAL_BRAKE    = true;
    static final boolean BREACHER_NEUTRAL_BRAKE = true;
    static final boolean SPUDS_NEUTRAL_BRAKE    = true;
    static final boolean ROLLER_NEUTRAL_BRAKE   = true;
    static final boolean LIFT_NEUTRAL_BRAKE     = true;
    static final boolean WRIST_NEUTRAL_BRAKE    = true;
    static final boolean BEAK_NEUTRAL_BRAKE     = true;

    //Sensor phase adjustments ----------------------------------------------------------
    static final boolean FLIP_DRIVE_SENSOR    = false;
    static final boolean FLIP_BREACHER_SENSOR = false;
    static final boolean FLIP_SPUD_SENSOR     = false;
    static final boolean FLIP_LIFT_SENSOR     = false;
    static final boolean FLIP_WRIST_SENSOR    = false;

    //Talon configuration timeout ------------------------------------------------------0
    static final int TALON_TIMEOUT = 30;

    //Speed adjustments for manual operation --------------------------------------------
    static final double BREACHER_SPEED    = 1.00;
    static final double SPUD_SPEED        = 1.00;
    static final double SPUD_ROLLER_SPEED = 1.00;
    static final double LIFT_SPEED        = 1.00;
    static final double WRIST_SPEED       = 1.00;
    static final double BEAK_SPEED        = 1.00;
    static final double CAMERA_SPEED      = 1.00;

    //Joystick button ids ---------------------------------------------------------------
    static final int SAFETY_BUTTON           = 8;
    static final int SPUDS_UP_BUTTON         = 5;
    static final int SPUDS_DOWN_BUTTON       = 3;
    static final int ROLLER_FORWARD_BUTTON   = 1;
    static final int ROLLER_BACKWARD_BUTTON  = 2;
    static final int BREACHER_EXTEND_BUTTON  = 6;
    static final int BREACHER_RETRACT_BUTTON = 4;
    static final int WRIST_UP_BUTTON         = 5;
    static final int WRIST_DOWN_BUTTON       = 3;
    static final int BEAK_EXTEND_BUTTON      = 2;
    static final int BEAK_RETRACT_BUTTON     = 1;

    //Joystick deadbands ----------------------------------------------------------------
    static final double THROTTLE_DEADBAND = 0.2;
    static final double STEERING_DEADBAND = 0.2;
    static final double LIFTING_DEADBAND  = 0.2;

    //Servo channels --------------------------------------------------------------------
    static final int CAMERA_PAN_CHANNEL  = 0;
    static final int CAMERA_TILT_CHANNEL = 1;

    //Camera default position -----------------------------------------------------------
    static final double CAMERA_PAN_DEFAULT  = 90;
    static final double CAMERA_TILT_DEFAULT = 120;

    //Camera HAT angles -----------------------------------------------------------------
    static final int CAMERA_UP         = 0;
    static final int CAMERA_RIGHT_UP   = 45;
    static final int CAMERA_RIGHT      = 90;
    static final int CAMERA_RIGHT_DOWN = 135;
    static final int CAMERA_DOWN       = 180;
    static final int CAMERA_LEFT_DOWN  = 225;
    static final int CAMERA_LEFT       = 270;
    static final int CAMERA_LEFT_UP    = 315;

    //Travel limits (inclusive) ----------------------------------------------------------
    static final double BREACHER_MAX_EXTEND = Double.MAX_VALUE; //TODO: Limit breacher range
    static final double BREACHER_MIN_EXTEND = Double.MIN_VALUE;
    static final double SPUDS_MAX_EXTEND    = Double.MAX_VALUE; //TODO: Limit spud range
    static final double SPUD_MIN_EXTEND     = Double.MIN_VALUE;
    static final double LIFT_MAX_EXTEND     = Double.MAX_VALUE; //TODO: Limit lift range
    static final double LIFT_MIN_EXTEND     = Double.MIN_VALUE;
    static final double WRIST_MAX_EXTEND    = Double.MAX_VALUE; //TODO: Limit wrist range
    static final double WRIST_MIN_EXTEND    = Double.MIN_VALUE;
    static final double CAMERA_MAX_PAN      = 180;
    static final double CAMERA_MIN_PAN      = 0;
    static final double CAMERA_MAX_TILT     = 180;
    static final double CAMERA_MIN_TILT     = 0;

    //Target accelerations (native-units per 100ms per second) --------------------------
    static final int BREACHER_ACCELERATION = 100;
    static final int SPUDS_ACCELERATION    = 100;
    static final int LIFT_ACCELERATION     = 100;
    static final int WRIST_ACCELERATION    = 100;

    //Target velocities (native-units per 100ms) ----------------------------------------
    static final int BREACHER_VELOCITY = 500;
    static final int SPUDS_VELOCITY    = 500;
    static final int LIFT_VELOCITY     = 500;
    static final int WRIST_VELOCITY    = 500;

    //PID-F constants -------------------------------------------------------------------
    static final double BREACHER_kP = 0;
    static final double BREACHER_kI = 0;
    static final double BREACHER_kD = 0;
    static final double BREACHER_kF = 0;

    static final double SPUDS_kP = 0;
    static final double SPUDS_kI = 0;
    static final double SPUDS_kD = 0;
    static final double SPUDS_kF = 0;

    static final double LIFT_kP = 0;
    static final double LIFT_kI = 0;
    static final double LIFT_kD = 0;
    static final double LIFT_kF = 0;

    static final double WRIST_kP = 0;
    static final double WRIST_kI = 0;
    static final double WRIST_kD = 0;
    static final double WRIST_kF = 0;
}
