package frc.team3238.robot.motion;

import edu.wpi.first.wpilibj.Servo;

/**
 * A pan-tilt mounting point that uses Servos to move both of its axises.
 *
 * @author Loren
 */
public class ServoPTMount extends PanTiltMount
{
    public ServoPTMount (Servo panServo, Servo tiltServo, double defaultPanAmount, double defaultTiltAmount,
                         double startingPanAngle, double startingTiltAngle, double minPanAngle, double maxPanAngle,
                         double minTiltAngle, double maxTiltAngle)
    {
        super(new ServoTiltMount(panServo, defaultPanAmount, startingPanAngle, minPanAngle, maxPanAngle),
              new ServoTiltMount(tiltServo, defaultTiltAmount, startingTiltAngle, minTiltAngle, maxTiltAngle));
    }

    public ServoPTMount (int panChannel, int tiltChannel, double defaultPanAmount, double defaultTiltAmount,
                         double startingPanAngle, double startingTiltAngle, double minPanAngle, double maxPanAngle,
                         double minTiltAngle, double maxTiltAngle)
    {
        this(new Servo(panChannel), new Servo(tiltChannel), defaultPanAmount, defaultTiltAmount, startingPanAngle,
             startingTiltAngle, minPanAngle, maxPanAngle, minTiltAngle, maxTiltAngle);
    }
}
