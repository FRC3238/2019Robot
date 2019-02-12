package frc.team3238.robot.motion;

/**
 * A mount that can move in two directions.
 * <p>
 * The left-right direction is known as the pan direction. The up-down direction is known as the tilt direction.
 *
 * @author Loren
 */
public class PanTiltMount {
    private TiltMount panMount;

    private TiltMount tiltMount;

    public PanTiltMount(TiltMount panAxis, TiltMount tiltAxis) {
        panMount  = panAxis;
        tiltMount = tiltAxis;
    }

    /**
     * Sets the default amount by which the pan axis can move.
     *
     * @param degrees The default amount the pan axis moves by.
     */
    public void setDefaultPanAmount(double degrees) {
        panMount.setDefaultTiltAmount(degrees);
    }

    /**
     * Sets the minimum angle that can be panned to.
     *
     * @param degrees The minimum angle that can be panned to
     */
    public void setMinimumPanAngle(double degrees) {
        panMount.setMaximumTiltAngle(degrees);
    }

    /**
     * Sets the maximum angle that can be panned to.
     *
     * @param degrees The maximum angle that can be panned to
     */
    public void setMaximumPanAngle(double degrees) {
        panMount.setMaximumTiltAngle(degrees);
    }

    /**
     * Moves the pan axis rightward by its default amount.
     */
    public void panRight() {
        panMount.tiltUp();
    }

    /**
     * Moves the pan axis leftward by its default amount.
     */
    public void panLeft() {
        panMount.tiltDown();
    }

    /**
     * Moves the pan axis by the specified number of degrees.
     * <p>
     * Note that positive amounts move the pan axis one way, and that negative amounts move it the other.
     *
     * @param degrees The number of degrees to move the pan axis by
     */
    public void panBy(double degrees) {
        panMount.tiltBy(degrees);
    }

    /**
     * Reverses the direction of the pan axis.
     * <p>
     * The effect of this method is such that after it is called, moves which would previously pan left, now will pan
     * right.
     */
    public void reversePanDirection() {
        panMount.reverseDirections();
    }

    /**
     * Sets the default number of degrees that the tilt axis moves by.
     *
     * @param degrees The default number of degrees that the tilt axis moves by
     */
    public void setDefaultTiltAmount(double degrees) {
        tiltMount.setDefaultTiltAmount(degrees);
    }

    /**
     * Sets the minimum angle that the tilt axis can move to.
     *
     * @param degrees The minimum that the tilt axis can move to
     */
    public void setMinimumTiltAngle(double degrees) {
        tiltMount.setMinimumTiltAngle(degrees);
    }

    /**
     * Sets the maximum angle that the tilt axis can move to.
     *
     * @param degrees The maximum angle that the tilt axis can move to
     */
    public void setMaximumTiltAngle(double degrees) {
        tiltMount.setMaximumTiltAngle(degrees);
    }

    /**
     * Moves the tilt axis up by its default amount.
     */
    public void tiltUp() {
        tiltMount.tiltUp();
    }

    /**
     * Moves the tilt axis down by its default amount.
     */
    public void tiltDown() {
        tiltMount.tiltDown();
    }

    /**
     * Moves the tilt axis by the specified number of degrees.
     * <p>
     * Positive amounts move it one way, and negatives the other.
     *
     * @param degrees The number of degrees by which to move the axis.
     */
    public void tiltBy(double degrees) {
        tiltMount.tiltBy(degrees);
    }

    /**
     * Reverses the directions of the tilt axis
     * <p>
     * Thus, after this is invoked, tilting down has the effect of tilting up, and vice-versa.
     */
    public void reverseTiltDirection() {
        tiltMount.reverseDirections();
    }
}
