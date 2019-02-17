package frc.team3238.robot.settings.talons;

public class DriveSettings {

    //Talon CAN ids
    public static final int LEFT_MASTER_ID  = 0;
    public static final int LEFT_SLAVE_ID   = 2;
    public static final int RIGHT_MASTER_ID = 1;
    public static final int RIGHT_SLAVE_ID  = 3;

    /**
     * Reverses the drive.
     * <p>
     * It's used to correct the robot if forward inputs make the robot go backwards.
     */
    public static final boolean REVERSED = false;

    /**
     * Has the Talon try to passively influence the motors against moving. Essentially, it's a brake system that makes
     * the motor harder to turn without using more energy.
     */
    public static final boolean BRAKE_IN_NEUTRAL = true;
}
