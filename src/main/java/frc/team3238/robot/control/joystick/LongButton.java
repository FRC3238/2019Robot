package frc.team3238.robot.control.joystick;

import edu.wpi.first.wpilibj.Joystick;

public class LongButton implements Button {
    private final Joystick stick;
    private final int      button;
    private final int      minCycles;

    private boolean isPressed;
    private boolean isHeld;
    private boolean isLongHeld;
    private boolean isReleased;
    private int     heldCount;

    public LongButton(Joystick stick, int button, int minCycles) {
        this.stick     = stick;
        this.button    = button;
        this.minCycles = minCycles;
    }

    @Override
    public boolean isPressed() {
        return isPressed;
    }

    /**
     * Gets if the button is being held down.
     * <p>
     * A hold only registers if the hold exceeds the time specified by {@link #minCycles}
     *
     * @return If the button is being held down and has been held long enough.
     */
    @Override
    public boolean isHeld() {
        return isLongHeld;
    }

    @Override
    public boolean isReleased() {
        return isReleased;
    }

    @Override
    public void update() {
        boolean newHeld = stick.getRawButton(button);
        isPressed  = !isHeld && newHeld;
        isReleased = isLongHeld && !newHeld;

        if(newHeld == isHeld)
            ++heldCount;
        else
            heldCount = 0;

        isLongHeld = heldCount >= minCycles;
        isHeld     = newHeld;
    }
}
