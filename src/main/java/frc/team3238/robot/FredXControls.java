package frc.team3238.robot;

import edu.wpi.first.wpilibj.Joystick;

final class FredXControls
{

    private Joystick driveJoystick;
    private Joystick manipulatorJoystick;

    private double  throttle;
    private double  steer;
    private boolean safetyOff;
    private boolean spudsGoUp;
    private boolean spudsGoDown;
    private boolean spudsRollForward;
    private boolean spudsRollBack;
    private boolean liferGoUp;
    private boolean liferGoDown;

    public FredXControls()
    {
        driveJoystick = new Joystick(FredXConstants.DRIVE_JOYSTICK_PORT);
        manipulatorJoystick = new Joystick(FredXConstants.MANIPULATION_JOYSTICK_PORT);
    }

    public void updateControls()
    {
        //Drive controls
        throttle = -driveJoystick.getY();
        steer = driveJoystick.getTwist();

        //Button checks
        safetyOff = driveJoystick.getRawButton(FredXConstants.SAFETY_BUTTON);
        spudsGoUp = driveJoystick.getRawButton(FredXConstants.SPUDS_UP_BUTTON);
        spudsGoDown = driveJoystick.getRawButton(FredXConstants.SPUDS_DOWN_BUTTON);
        spudsRollForward = driveJoystick.getRawButton(FredXConstants.SPUD_ROLLER_FORWARD);
        spudsRollBack = driveJoystick.getRawButton(FredXConstants.SPUD_ROLLER_BACKWARD);

        //TODO: Query if lifer should go up or down
    }

    public double getThrottle()
    {
        return throttle;
    }

    public double getSteer()
    {
        return steer;
    }

    public boolean safetyIsOff()
    {
        return safetyOff;
    }

    public boolean spudsShouldGoUp()
    {
        return spudsGoUp;
    }

    public boolean spudsShouldGoDown()
    {
        return spudsGoDown;
    }

    public boolean spudsShouldRollForward()
    {
        return spudsRollForward;
    }

    public boolean spudsShouldRollBack()
    {
        return spudsRollBack;
    }

    public boolean liferShouldGoUp()
    {
        return liferGoUp;
    }

    public boolean liferShouldGoDown()
    {
        return liferGoDown;
    }
}
