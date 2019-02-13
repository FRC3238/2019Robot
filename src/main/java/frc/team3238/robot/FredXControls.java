package frc.team3238.robot;

import edu.wpi.first.wpilibj.Joystick;

import static frc.team3238.robot.FredXConstants.*;

final class FredXControls {

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
    private boolean panCameraRight;
    private boolean panCameraLeft;
    private boolean tiltCameraUp;
    private boolean tiltCameraDown;
    private boolean armsGoOut;
    private boolean armsPullIn;

    public FredXControls() {
        driveJoystick       = new Joystick(DRIVE_JOYSTICK_PORT);
        manipulatorJoystick = new Joystick(MANIPULATION_JOYSTICK_PORT);
    }

    public void updateControls() {
        //Drive controls
        throttle = deadbandAdjust(-driveJoystick.getY(), THROTTLE_DEADBAND);
        steer    = deadbandAdjust(driveJoystick.getTwist(), STEERING_DEADBAND);

        //Button checks
        safetyOff        = driveJoystick.getRawButton(SAFETY_BUTTON);
        spudsGoUp        = driveJoystick.getRawButton(SPUDS_UP_BUTTON);
        spudsGoDown      = driveJoystick.getRawButton(SPUDS_DOWN_BUTTON);
        spudsRollForward = driveJoystick.getRawButton(SPUD_ROLLER_FORWARD);
        spudsRollBack    = driveJoystick.getRawButton(SPUD_ROLLER_BACKWARD);
        armsGoOut        = driveJoystick.getRawButton(ARMS_OUT);
        armsPullIn       = driveJoystick.getRawButton(ARMS_IN);


        double mjy = deadbandAdjust(manipulatorJoystick.getY(), LIFTING_DEADBAND);
        liferGoUp   = mjy > 0;
        liferGoDown = mjy < 0;



        //Camera control check
        switch(manipulatorJoystick.getPOV()) {
            case CAMERA_RIGHT:
                panCameraRight = true;
                panCameraLeft = false;
                tiltCameraUp = false;
                tiltCameraDown = false;
                break;
            case CAMERA_RIGHT_UP:
                panCameraRight = true;
                panCameraLeft = false;
                tiltCameraUp = true;
                tiltCameraDown = false;
                break;
            case CAMERA_UP:
                panCameraRight = false;
                panCameraLeft = false;
                tiltCameraUp = true;
                tiltCameraDown = false;
                break;
            case CAMERA_LEFT_UP:
                panCameraRight = false;
                panCameraLeft = true;
                tiltCameraUp = true;
                tiltCameraDown = false;
                break;
            case CAMERA_LEFT:
                panCameraRight = false;
                panCameraLeft = true;
                tiltCameraUp = false;
                tiltCameraDown = false;
                break;
            case CAMERA_LEFT_DOWN:
                panCameraRight = false;
                panCameraLeft = true;
                tiltCameraUp = false;
                tiltCameraDown = true;
                break;
            case CAMERA_DOWN:
                panCameraRight = false;
                panCameraLeft = false;
                tiltCameraUp = false;
                tiltCameraDown = true;
                break;
            case CAMERA_RIGHT_DOWN:
                panCameraRight = true;
                panCameraLeft = false;
                tiltCameraUp = false;
                tiltCameraDown = true;
                break;
        }
    }

    public double getThrottle() {
        return throttle;
    }

    public double getSteer() {
        return steer;
    }

    public boolean safetyIsOff() {
        return safetyOff;
    }

    public boolean spudsShouldGoUp() {
        return spudsGoUp;
    }

    public boolean spudsShouldGoDown() {
        return spudsGoDown;
    }

    public boolean spudsShouldRollForward() {
        return spudsRollForward;
    }

    public boolean spudsShouldRollBack() {
        return spudsRollBack;
    }

    public boolean liferShouldGoUp() {
        return liferGoUp;
    }

    public boolean liferShouldGoDown() {
        return liferGoDown;
    }

    public boolean cameraShouldPanRight() {
        return panCameraRight;
    }

    public boolean cameraShouldPanLeft() {
        return panCameraLeft;
    }

    public boolean cameraShouldTiltUp() {
        return tiltCameraUp;
    }

    public boolean cameraShouldTiltDown() {
        return tiltCameraDown;
    }

    public boolean armsShouldGoOut() {
        return armsGoOut;
    }

    public boolean armsShouldPullIn() {
        return armsPullIn;
    }

    /**
     * Takes a raw value as input and applies a deadband to it.
     *
     * @param rawValue [-1, 1] The raw value
     * @param deadband [0, 1) The amount of deadband to use
     * @return The deadband-adjusted value
     */
    private static double deadbandAdjust(double rawValue, double deadband) {
        if(Math.abs(rawValue) < deadband)
            return 0;
        else
            return rawValue / (1 - deadband);
    }
}
