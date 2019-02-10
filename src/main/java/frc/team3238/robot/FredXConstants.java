package frc.team3238.robot;

/**
 * A central configuration page for all constants used by FredX
 *
 * @author Loren
 */
final class FredXConstants
{

    //Joystick port numbers -------------------------------------------------------------
    static final int DRIVE_JOYSTICK_PORT        = 0;
    static final int MANIPULATION_JOYSTICK_PORT = 1;


    //Talon CAN ids ---------------------------------------------------------------------
    static final int LEFT_MASTER_DRIVE_NUM  = 0;
    static final int RIGHT_MASTER_DRIVE_NUM = 1;
    static final int LEFT_SLAVE_DRIVE_NUM   = 2;
    static final int RIGHT_SLAVE_DRIVE_NUM  = 3;
    static final int SPUD_DRIVE_NUM         = 4;
    static final int SPUD_ROLLER_NUM        = 5;
    static final int ARM_MASTER_NUM         = 6;
    static final int ARM_SLAVE_NUM          = 7;
    static final int LIFT_ACTUATOR_NUM      = 8;


    //Talon direction adjustments -------------------------------------------------------
    static final boolean REVERSE_DRIVE_TALONS      = false;
    static final boolean REVERSE_ARM_TALONS        = false;
    static final boolean REVERSE_SPUD_DRIVE_TALON  = false;
    static final boolean REVERSE_SPUD_ROLLER_TALON = false;
    static final boolean REVERSE_ARM_ACTUATOR      = false;


    //Speed adjustments -----------------------------------------------------------------
    static final double SPUD_SPEED        = 1.00;
    static final double SPUD_ROLLER_SPEED = 1.00;


    //Joystick button ids ---------------------------------------------------------------
    static final int SAFETY_BUTTON        = 8;
    static final int SPUDS_UP_BUTTON      = 5;
    static final int SPUDS_DOWN_BUTTON    = 3;
    static final int SPUD_ROLLER_FORWARD  = 1;
    static final int SPUD_ROLLER_BACKWARD = 2;
}
