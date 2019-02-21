package frc.team3238.robot.control.joystick;

/**
 * A button that can be pushed by a user.
 */
public interface Button {

    /**
     * Gets if the button was just pushed down.
     * <p>
     * Here pressed specifically means that on the last check the button was not down, and that now it is.
     *
     * @return If the button was just pressed down.
     */
    boolean isPressed();

    /**
     * Gets if the button is down.
     *
     * @return If the button is currently down;
     */
    boolean isHeld();

    /**
     * Gets if the button was just let go.
     *
     * @return If the button was just released
     */
    boolean isReleased();

    /**
     * Updates the state by checking the joystick.
     * <p>
     * The joystick is not checked until this method is called, and the state of this button will not change until this
     * method is called again.
     */
    void update();
}
