package frc.team3238.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.team3238.robot.settings.talons.BreacherSettings;

import static frc.team3238.robot.settings.FredXConstants.*;

final class FredXControls {

    private Joystick driveJoystick;
    private Joystick manipulatorJoystick;

    private double driveThrottle;
    private double rollerThrottle;
    private double steer;
    private double spudsExtension;
    private double breacherExtension;
    private double liftExtension;
    private double wristExtension;
    private double beakExtension; //TODO: Implement use of the beak

    private double cameraPan  = CAMERA_PAN_DEFAULT;
    private double cameraTilt = CAMERA_TILT_DEFAULT;

    public FredXControls() {
        driveJoystick       = new Joystick(DRIVE_JOYSTICK_PORT);
        manipulatorJoystick = new Joystick(MANIPULATOR_JOYSTICK_PORT);
    }

    public void updateControls() {
        //Drive controls
        driveThrottle = deadbandAdjust(-driveJoystick.getY(), THROTTLE_DEADBAND);
        steer         = deadbandAdjust(driveJoystick.getTwist(), STEERING_DEADBAND);

        //Adjusting camera position
        switch(manipulatorJoystick.getPOV()) {
            case CAMERA_UP:
                setCameraTilt(cameraTilt + CAMERA_SPEED);
                break;
            case CAMERA_RIGHT_UP:
                setCameraPan(cameraPan + CAMERA_SPEED);
                setCameraTilt(cameraTilt + CAMERA_SPEED);
                break;
            case CAMERA_RIGHT:
                setCameraPan(cameraPan + CAMERA_SPEED);
                break;
            case CAMERA_RIGHT_DOWN:
                setCameraPan(cameraPan + CAMERA_SPEED);
                setCameraTilt(cameraTilt - CAMERA_SPEED);
                break;
            case CAMERA_DOWN:
                setCameraTilt(cameraTilt - CAMERA_SPEED);
                break;
            case CAMERA_LEFT_DOWN:
                setCameraPan(cameraPan - CAMERA_SPEED);
                setCameraTilt(cameraTilt - CAMERA_SPEED);
                break;
            case CAMERA_LEFT:
                setCameraPan(cameraPan - CAMERA_SPEED);
                break;
            case CAMERA_LEFT_UP:
                setCameraPan(cameraPan - CAMERA_SPEED);
                setCameraTilt(cameraTilt + CAMERA_SPEED);
                break;
        }

        //Configure safety-enabled items
        if(driveJoystick.getRawButton(SAFETY_BUTTON)) {

            //Roller throttle
            if(driveJoystick.getRawButton(ROLLER_FORWARD_BUTTON))
                rollerThrottle = SPUD_ROLLER_SPEED;
            else if(driveJoystick.getRawButton(ROLLER_BACKWARD_BUTTON))
                rollerThrottle = -SPUD_ROLLER_SPEED;
            else
                rollerThrottle = 0;

            //Spuds extension
            if(driveJoystick.getRawButton(SPUDS_UP_BUTTON))
                setSpudsExtension(spudsExtension + SPUD_SPEED);
            else if(driveJoystick.getRawButton(SPUDS_DOWN_BUTTON))
                setSpudsExtension(spudsExtension - SPUD_SPEED);

            //Breacher extension
            if(driveJoystick.getRawButton(BREACHER_EXTEND_BUTTON))
                setBreacherExtension(breacherExtension + BreacherSettings.manualSpeed);
            else if(driveJoystick.getRawButton(BREACHER_RETRACT_BUTTON))
                setBreacherExtension(breacherExtension - BreacherSettings.manualSpeed);
        }
        else {
            rollerThrottle = 0;
        }

        //Lift extension adjust
        double liftThrottle = deadbandAdjust(manipulatorJoystick.getY(), LIFTING_DEADBAND);
        setLiftExtension(liftExtension + liftThrottle * LIFT_SPEED);

        //Wrist extension adjust
        if(manipulatorJoystick.getRawButton(WRIST_UP_BUTTON))
            setWristExtension(wristExtension + WRIST_SPEED);
        else if(manipulatorJoystick.getRawButton(WRIST_DOWN_BUTTON))
            setWristExtension(wristExtension - WRIST_SPEED);
    }

    public double getDriveThrottle() {
        return driveThrottle;
    }

    public double getSteer() {
        return steer;
    }

    public double getRollerThrottle() {
        return rollerThrottle;
    }

    public double getSpudsExtension() {
        return spudsExtension;
    }

    public double getBreacherExtension() {
        return breacherExtension;
    }

    public double getLiftExtension() {
        return liftExtension;
    }

    public double getWristExtension() {
        return wristExtension;
    }

    public double getCameraPanAngle() {
        return cameraPan;
    }

    public double getCameraTiltAngle() {
        return cameraTilt;
    }

    private void setSpudsExtension(double nativeUnits) {
        if(nativeUnits < SPUD_MIN_EXTEND)
            spudsExtension = SPUD_MIN_EXTEND;
        else if(nativeUnits > SPUDS_MAX_EXTEND)
            spudsExtension = SPUDS_MAX_EXTEND;
        else
            spudsExtension = nativeUnits;
    }

    private void setBreacherExtension(double nativeUnits) {
        if(nativeUnits < BreacherSettings.MIN_EXTENSION)
            breacherExtension = BreacherSettings.MIN_EXTENSION;
        else if(nativeUnits > BreacherSettings.MAX_EXTENSION)
            breacherExtension = BreacherSettings.MAX_EXTENSION;
        else
            breacherExtension = nativeUnits;
    }

    private void setLiftExtension(double nativeUnits) {
        if(nativeUnits < LIFT_MIN_EXTEND)
            liftExtension = LIFT_MIN_EXTEND;
        else if(nativeUnits > LIFT_MAX_EXTEND)
            liftExtension = LIFT_MAX_EXTEND;
        else
            liftExtension = nativeUnits;
    }

    private void setWristExtension(double nativeUnits) {
        if(nativeUnits < WRIST_MIN_EXTEND)
            wristExtension = WRIST_MIN_EXTEND;
        else if(nativeUnits > WRIST_MAX_EXTEND)
            wristExtension = WRIST_MAX_EXTEND;
        else
            wristExtension = nativeUnits;
    }

    private void setCameraPan(double degrees) {
        if(degrees < CAMERA_MIN_PAN)
            cameraPan = CAMERA_MIN_PAN;
        else if(degrees > CAMERA_MAX_PAN)
            cameraPan = CAMERA_MAX_PAN;
        else
            cameraPan = degrees;
    }

    private void setCameraTilt(double degrees) {
        if(degrees < CAMERA_MIN_TILT)
            cameraTilt = CAMERA_MIN_TILT;
        else if(degrees > CAMERA_MAX_TILT)
            cameraTilt = CAMERA_MAX_TILT;
        else
            cameraTilt = degrees;
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
        else {
            if(rawValue < 0)
                return (rawValue + deadband) / (1 - deadband);
            else
                return (rawValue - deadband) / (1 - deadband);
        }
    }
}
