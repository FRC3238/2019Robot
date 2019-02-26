package frc.team3238.robot.control;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import frc.team3238.robot.control.joystick.Button;
import frc.team3238.robot.control.joystick.JoystickButton;

import static frc.team3238.robot.FREDDXConstants.*;

public class CameraController {

    private final Joystick controlStick;
    private final Servo    cameraPanServo;
    private final Servo    cameraTiltServo;

    private int panAngle;
    private int tiltAngle;

    private final Button resetCamera;

    public CameraController(Joystick stick) {
        controlStick    = stick;
        cameraPanServo  = new Servo(CAMERA_PAN_CHANNEL);
        cameraTiltServo = new Servo(CAMERA_TILT_CHANNEL);
        resetCamera     = new JoystickButton(controlStick, RESET_CAMERA);
        homeCamera();
    }

    public void updateControls() {
        resetCamera.update();
        double povAngle = controlStick.getPOV();
        if(povAngle != -1) {
            if(povAngle != 0 && povAngle != 180)
                incrementPan(povAngle < 180 ? -CAMERA_SPEED : CAMERA_SPEED);

            if(povAngle != 90 && povAngle != 270)
                incrementTilt((povAngle > 270 || povAngle < 90) ? -CAMERA_SPEED : CAMERA_SPEED);
        }

        if(resetCamera.isReleased()) {
            homeCamera();
        }
    }

    public void move() {
        cameraPanServo.setAngle(panAngle);
        cameraTiltServo.setAngle(tiltAngle);
    }

    /**
     * Returns the camera to it's home position
     */
    private void homeCamera() {
        setPanAngle(CAMERA_PAN_DEFAULT);
        setTiltAngle(CAMERA_TILT_DEFAULT);
    }

    private void incrementPan(int degrees) {
        setPanAngle(panAngle + degrees);
    }

    private void incrementTilt(int degrees) {
        setTiltAngle(tiltAngle + degrees);
    }

    private void setPanAngle(int degrees) {
        if(degrees < CAMERA_MIN_PAN)
            panAngle = CAMERA_MIN_PAN;
        else if(degrees > CAMERA_MAX_PAN)
            panAngle = CAMERA_MAX_PAN;
        else
            panAngle = degrees;
    }

    private void setTiltAngle(int degrees) {
        if(degrees < CAMERA_MIN_TILT)
            tiltAngle = CAMERA_MIN_TILT;
        else if(degrees > CAMERA_MAX_TILT)
            tiltAngle = CAMERA_MAX_TILT;
        else
            tiltAngle = degrees;
    }
}
