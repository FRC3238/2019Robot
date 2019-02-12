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

    public FredXControls() {
        driveJoystick       = new Joystick(DRIVE_JOYSTICK_PORT);
        manipulatorJoystick = new Joystick(MANIPULATION_JOYSTICK_PORT);
    }

    public void updateControls() {
        //Drive controls
        throttle = -driveJoystick.getY();
        steer    = driveJoystick.getTwist();

        //Button checks
        safetyOff        = driveJoystick.getRawButton(SAFETY_BUTTON);
        spudsGoUp        = driveJoystick.getRawButton(SPUDS_UP_BUTTON);
        spudsGoDown      = driveJoystick.getRawButton(SPUDS_DOWN_BUTTON);
        spudsRollForward = driveJoystick.getRawButton(SPUD_ROLLER_FORWARD);
        spudsRollBack    = driveJoystick.getRawButton(SPUD_ROLLER_BACKWARD);

        //TODO: Query if lifer should go up or down

        //Camera control check
        switch (manipulatorJoystick.getPOV()) {
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
}
