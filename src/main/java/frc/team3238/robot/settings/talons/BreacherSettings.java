package frc.team3238.robot.settings.talons;

public class BreacherSettings {

    //Talon CAN ids
    public static final int LEFT_ID  = 7;
    public static final int RIGHT_ID = 6;

    /**
     * Reverses the breachers.
     * <p>
     * It's used to change the motion direction of the breachers. Like if they were going back in when they were
     * supposed to be going out.
     */
    public static final boolean REVERSED = false;

    /**
     * Controls a feature on the TalonSRX that makes the motor passively resist being manually spun.
     * <p>
     * This is effectively a brake system. It tries to help the motor hold position when it's not being powered.
     */
    public static final boolean BRAKES_IN_NEUTRAL = true;

    /**
     * Reverses the counting direction of the sensor tied to the breachers.
     * <p>
     * This is used to correct the sensor if it, let's say, counts down when the motion was supposed to have it count
     * up.
     */
    public static final boolean FLIP_SENSOR = false;

    /**
     * The maximum distance that the breachers can extend to.
     * <p>
     * This is only a software limit, but the intent is that the breachers will never be told to go to a position beyond
     * this. It's measured in native sensor-units.
     */
    public static final int MAX_EXTENSION = 1000;

    /**
     * The minimum distance that the breachers can be pulled back to.
     * <p>
     * This is only a software limit, but the intent is that the breachers will never be told to pull back further than
     * this far in. It's measured in native sensor-units.
     */
    public static final int MIN_EXTENSION = 0;

    /**
     * The increase in the breacher set-point per control update while in manual mode.
     * <p>
     * This value is in the native units of the sensor used for feedback on the breachers.
     */
    public static int manualSpeed = 10;

    /**
     * The target velocity for the breachers to run at.
     * <p>
     * This velocity is expressed in native units per 100ms.
     */
    public static int cruiseVelocity = 500;

    /**
     * The target acceleration for the breachers to use when beginning a motion.
     * <p>
     * This determines just how fast the breachers can get get to their cruise velocity when making a motion.
     * The units for this setting are int (native units per 100ms) per second.
     */
    public static int acceleration = 500;

    /**
     * The proportional coefficient for PID control.
     */
    public static double kP = 0.15;

    /**
     * The integral coefficient for PID control.
     */
    public static double kI = 0;

    /**
     * The derivative coefficient for PID control.
     */
    public static double kD = 0;
}
