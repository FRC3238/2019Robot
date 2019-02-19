package frc.team3238.robot.control;

import edu.wpi.first.wpilibj.Servo;
import frc.team3238.robot.FREDDX;

import static frc.team3238.robot.FREDDXConstants.*;

public class CameraControl extends FREDDXControlScheme {

    private double cameraPan;
    private double cameraTilt;
    private Servo  cameraPanServo;
    private Servo  cameraTiltServo;

    public CameraControl(FREDDX robot) {
        super(robot);
        cameraPanServo  = robot.getCameraPanServo();
        cameraTiltServo = robot.getCameraTiltServo();
        homeCamera();
    }

    @Override
    public void updateControls() {
        double povAngle = manipulatorJoystick.getPOV();
        if(povAngle != -1) {
            if(povAngle != 0 && povAngle != 180)
                incrementPan(povAngle < 180 ? CAMERA_SPEED : -CAMERA_SPEED);

            if(povAngle != 90 && povAngle != 270)
                incrementTilt((povAngle > 270 || povAngle < 90) ? CAMERA_SPEED : -CAMERA_SPEED);
        }
    }

    @Override
    public void teleopPeriodic() {
        cameraPanServo.setAngle(cameraPan);
        cameraTiltServo.setAngle(cameraTilt);
    }

    /**
     * Returns the camera to it's home position
     */
    private void homeCamera() {
        setCameraPan(CAMERA_PAN_DEFAULT);
        setCameraTilt(CAMERA_TILT_DEFAULT);
    }

    private void incrementPan(double degrees) {
        setCameraPan(cameraPan + degrees);
    }

    private void incrementTilt(double degrees) {
        setCameraTilt(cameraTilt + degrees);
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
}
