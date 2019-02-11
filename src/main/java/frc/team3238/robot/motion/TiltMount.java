package frc.team3238.robot.motion;

/**
 * A mounting point that is capable of tilting whatever is on it
 *
 * @author Loren
 */
public interface TiltMount
{
    /**
     * Sets the default amount of degrees by which the tilt angle can be adjusted.
     * <p>
     * This value is used when a call is made to tilt the mount, but the amount of degrees is unspecified.
     *
     * @param degrees The default number of degrees to adjust the tilt angle by
     */
    void setDefaultTiltAmount (double degrees);

    /**
     * Sets the absolute minimum angle that the mount can be tilted to.
     *
     * @param degrees The minimum angle that the mount can be tilted to
     */
    void setMinimumTiltAngle (double degrees);


    /**
     * Sets the absolute maximum angle the mount can be tilted to.
     *
     * @param degrees The maximum angle that the mount can be tilted to
     */
    void setMaximumTiltAngle (double degrees);

    /**
     * Tilts the mount up by the default number of degrees.
     */
    void tiltUp ();

    /**
     * Tilts the mount down by the default amount
     */
    void tiltDown ();

    /**
     * Tilts the mount by the specified number of degrees.
     * <p>
     * Positive amounts make it move one way, negative amounts move it the other.
     *
     * @param degrees The number of degrees to change the tilt angle by, positive or negative.
     */
    void tiltBy (double degrees);

    /**
     * Reverses the tilting directions of the mount.
     * <p>
     * This can be used to configure the mount such that calling {@link #tiltUp} will actually tilt the mount upward if
     * it was tilting the mount downward previously.
     */
    void reverseDirections ();
}
