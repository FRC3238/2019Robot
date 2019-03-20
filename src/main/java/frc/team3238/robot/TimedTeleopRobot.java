package frc.team3238.robot;

import edu.wpi.first.wpilibj.TimedRobot;

/**
 * A reduced version of TimedRobot that only has two states: enabled and disabled
 */
public class TimedTeleopRobot extends TimedRobot {

    private boolean wasDisabledLast;

    {
        wasDisabledLast = false;
    }

    public TimedTeleopRobot() {
        super();
    }

    /**
     * @param period The length of the period in seconds
     */
    public TimedTeleopRobot(double period) {
        super(period);
    }

    @Override
    public final void autonomousInit() {
        modeSwitched();
    }

    @Override
    public final void autonomousPeriodic() {
        enabledPeriodic();
    }

    @Override
    public final void teleopInit() {
        modeSwitched();
    }

    @Override
    public final void teleopPeriodic() {
        enabledPeriodic();
    }

    @Override
    public final void testInit() {
        modeSwitched();
    }

    @Override
    public final void testPeriodic() {
        enabledPeriodic();
    }

    @Override
    public final void disabledInit() {
        wasDisabledLast = true;
        disabledBegin();
    }

    /**
     * Called when the robot becomes enabled
     */
    public void enabledBegin() {

    }

    /**
     * Called periodically while the robot is enabled
     */
    public void enabledPeriodic() {

    }

    /**
     * Called when the robot enters the disabled state
     */
    public void disabledBegin() {

    }


    /**
     * Checks if the robot is becoming enabled.
     * <p>
     * If so, then this method will call {@link #enabledBegin()}
     */
    private void modeSwitched() {
        if(wasDisabledLast) {
            wasDisabledLast = false;
            enabledBegin();
        }
    }
}
