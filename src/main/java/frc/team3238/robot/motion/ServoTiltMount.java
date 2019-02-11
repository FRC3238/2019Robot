package frc.team3238.robot.motion;

import edu.wpi.first.wpilibj.Servo;

/**
 * A tilting mounting-point which uses a Servo to move it.
 *
 * @author Loren
 */
public final class ServoTiltMount implements TiltMount
{
    private Servo   servo;
    private double  angle;
    private double  minTiltAngle;
    private double  maxTiltAngle;
    private double  defaultTiltAmount;
    private boolean isReversed;

    public ServoTiltMount (Servo servo, double defaultTiltAmount, double defaultAngle, double minTiltAngle,
                           double maxTiltAngle)
    {
        this.servo = servo;

        setMinimumTiltAngle(minTiltAngle);
        setMaximumTiltAngle(maxTiltAngle);
        setDefaultTiltAmount(defaultTiltAmount);

        tiltTo(defaultAngle);
    }

    public ServoTiltMount (int servoChannel, double defaultTiltAmount, double defaultAngle, double minTiltAngle,
                           double maxTiltAngle)
    {
        this(new Servo(servoChannel), defaultTiltAmount, defaultAngle, minTiltAngle, maxTiltAngle);
    }

    /**
     * Moves the servo to the specified angle while respecting the minimum / maximum angles
     *
     * @param degrees The angle to which the servo should be turned
     */
    private void tiltTo (double degrees)
    {
        angle = degrees;

        if (angle < minTiltAngle)
        {
            angle = minTiltAngle;
        }
        else if (angle > maxTiltAngle)
        {
            angle = maxTiltAngle;
        }

        servo.setAngle(angle);
    }

    @Override
    public void setDefaultTiltAmount (double degrees)
    {
        defaultTiltAmount = degrees;
    }

    @Override
    public void setMinimumTiltAngle (double degrees)
    {
        minTiltAngle = degrees;
    }

    @Override
    public void setMaximumTiltAngle (double degrees)
    {
        maxTiltAngle = degrees;
    }

    @Override
    public void tiltBy (double degrees)
    {
        tiltTo(angle + degrees);
    }

    @Override
    public void tiltDown ()
    {
        if (isReversed)
        {
            tiltBy(defaultTiltAmount);
        }
        else
        {
            tiltBy(-defaultTiltAmount);
        }
    }

    @Override
    public void tiltUp ()
    {
        if (isReversed)
        {
            tiltBy(-defaultTiltAmount);
        }
        else
        {
            tiltBy(defaultTiltAmount);
        }
    }

    @Override
    public void reverseDirections ()
    {
        isReversed = !isReversed;
    }
}
