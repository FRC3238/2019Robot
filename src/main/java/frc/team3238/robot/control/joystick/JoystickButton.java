package frc.team3238.robot.control.joystick;

import edu.wpi.first.wpilibj.Joystick;

/**
 * A button on a {@link Joystick}
 */
public class JoystickButton implements Button {
    private final Joystick stick;
    private final int      button;

    private boolean isPressed;
    private boolean isHeld;
    private boolean isReleased;

    /**
     * @param stick  The joystick the button is on
     * @param button The id of the button on the joystick
     */
    public JoystickButton(Joystick stick, int button) {
        this.stick  = stick;
        this.button = button;
    }

    @Override
    public boolean isPressed() {
        return isPressed;
    }

    @Override
    public boolean isHeld() {
        return isHeld;
    }

    @Override
    public boolean isReleased() {
        return isReleased;
    }

    @Override
    public void update() {
        boolean newHeld = stick.getRawButton(button);
        isPressed  = !isHeld && newHeld;
        isReleased = isHeld && !newHeld;
        isHeld     = newHeld;
    }
}
