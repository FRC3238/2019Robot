package frc.team3238.robot;

/**
 * A central configuration page for all constants used by FreddX
 *
 * @author Loren
 */
public final class FREDDXConstants {
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    //                           CAMERA SETTINGS                             //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////

    public static final int CAMERA_NUMBER        = 0;
    public static final int CAMERA_FPS           = 30;
    public static final int CAMERA_WIDTH         = 200;
    public static final int CAMERA_HEIGHT        = 200;
    public static final int CAMERA_WHITE_BALANCE = 4200;
    public static final int CAMERA_EXPOSURE      = 30;

    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    //                          CONTROLS SETTINGS                            //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////

    //Joystick ids
    public static final int DRIVE_JOYSTICK_PORT       = 0;
    public static final int MANIPULATOR_JOYSTICK_PORT = 1;

    //Button ids
    public static final int SPUDS_UP          = 6;
    public static final int SPUDS_DOWN        = 4;
    public static final int ROLL_FORWARD      = 1;
    public static final int BREACHER_OUT      = 5;
    public static final int BREACHER_IN       = 3;
    public static final int COLLECT_BALL      = 2;
    public static final int EJECT_BALL        = 1;
    public static final int STOW              = 4;
    public static final int CARGO_LEVEL_ONE   = 12;
    public static final int CARGO_LEVEL_TWO   = 10;
    public static final int CARGO_LEVEL_THREE = 8;
    public static final int RESET_CAMERA      = 6;

    //Deadbands
    public static final double THROTTLE_DEADBAND = 0.2;
    public static final double STEERING_DEADBAND = 0.2;
    public static final double LIFTING_DEADBAND  = 0.2;


    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    //                       POSITION CONTROL SETTINGS                       //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////

    //Soft-travel limits (inclusive)
    public static final double LIFT_MAX_UP     = -205;
    public static final double LIFT_MIN_UP     = -985;
    public static final int    CAMERA_MAX_PAN  = 180;
    public static final int    CAMERA_MIN_PAN  = 0;
    public static final int    CAMERA_MAX_TILT = 180;
    public static final int    CAMERA_MIN_TILT = 0;

    //Positions
    public static final double LIFT_CARGO_LEVEL_ONE   = -767;
    public static final double LIFT_CARGO_LEVEL_TWO   = -435;
    public static final double LIFT_CARGO_LEVEL_THREE = -205;
    public static final int    CAMERA_PAN_DEFAULT     = 95;
    public static final int    CAMERA_TILT_DEFAULT    = 120;

    //PID constants
    public static final double BREACHER_kP = 3;
    public static final double BREACHER_kI = 0.03;
    public static final double BREACHER_kD = 300;

    public static final double SPUDS_kP = 3;
    public static final double SPUDS_kI = 0.03;
    public static final double SPUDS_kD = 0;

    public static final double LIFT_kP = 12;
    public static final double LIFT_kI = 0;
    public static final double LIFT_kD = 0;


    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    //                          BRAKE SETTINGS                               //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////

    //Drive
    public static final boolean USE_DRIVE_BRAKES = false;

    //Manipulator systems
    public static final boolean USE_COLLECTOR_BRAKES = true;
    public static final boolean USE_LIFT_BRAKES      = true;

    //Climbing systems
    public static final boolean USE_BREACHER_BRAKES          = true;
    public static final boolean USE_ROLLER_BRAKES            = true;
    public static final boolean USE_SPUDS_BRAKES             = true;
    public static final boolean DISABLE_BRAKES_WHEN_DISABLED = true;

    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    //                          ROBOT SETTINGS                               //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////

    //CAN ids
    public static final int DRIVE_LEFT_MASTER_ID  = 11;
    public static final int DRIVE_LEFT_SLAVE_ID   = 2;
    public static final int DRIVE_RIGHT_MASTER_ID = 1;
    public static final int DRIVE_RIGHT_SLAVE_ID  = 3;
    public static final int SPUDS_ID              = 4;
    public static final int ROLLER_ID             = 5;
    public static final int BREACHER_RIGHT_ID     = 7;
    public static final int BREACHER_LEFT_ID      = 6;
    public static final int LIFT_ID               = 8;
    public static final int COLLECTOR_ID          = 9;

    //Servo channels
    public static final int CAMERA_PAN_CHANNEL  = 0;
    public static final int CAMERA_TILT_CHANNEL = 1;

    //Direction
    public static final boolean REVERSE_DRIVE     = false;
    public static final boolean REVERSE_BREACHER  = false;
    public static final boolean REVERSE_SPUDS     = true;
    public static final boolean REVERSE_ROLLER    = true;
    public static final boolean REVERSE_LIFT      = false;
    public static final boolean REVERSE_COLLECTOR = false;

    //Sensor phase adjust
    public static final boolean FLIP_RIGHT_BREACHER_SENSOR = true;
    public static final boolean FLIP_LEFT_BREACHER_SENSOR  = true;
    public static final boolean FLIP_SPUD_SENSOR           = true;

    //Manual speeds
    public static final int    CAMERA_SPEED    = 1;
    public static final double SPUDS_SPEED     = 1;
    public static final double BREACHERS_SPEED = 1;
    public static final double ROLLER_SPEED    = 1;
    public static final double COLLECTOR_SPEED = 0.6;

    //Current limits (amps)
    public static final int DRIVE_MOTOR_CURRENT_LIMIT = 80;

    //Talon configuration timeout
    public static final int TALON_TIMEOUT = 30;
}
