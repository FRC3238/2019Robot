package frc.team3238.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Utility {
    /**
     * Powers a TalonSRX in forward, reverse, or stops it.
     *
     * @param talon    The TalonSRX to control
     * @param forward  If the talon should be powered forward. Takes priority over reverse.
     * @param reverse  If the talon should be powered in reverse.
     * @param throttle The amount of throttle to use when powering the talon.
     */
    public static void driveTalonFwdRevOrStop(WPI_TalonSRX talon, boolean forward, boolean reverse, double throttle) {
        if(forward) {
            talon.set(ControlMode.PercentOutput, throttle);
        }
        else if(reverse) {
            talon.set(ControlMode.PercentOutput, -throttle);
        }
        else {
            talon.set(ControlMode.PercentOutput, 0);
        }
    }

    /**
     * Takes a raw value as input and applies a deadband to it.
     *
     * @param rawValue [-1, 1] The raw value
     * @param deadband [0, 1) The amount of deadband to use
     * @return The deadband-adjusted value
     */
    public static double applyDeadband(double rawValue, double deadband) {
        if(Math.abs(rawValue) < deadband)
            return 0;
        else {
            if(rawValue < 0)
                return (rawValue + deadband) / (1 - deadband);
            else
                return (rawValue - deadband) / (1 - deadband);
        }
    }

    /**
     * Remaps the throttle slider on a joystick to 0 to 1.
     *
     * @param rawThrottle The raw input value from the joystick
     * @return The remapped result.
     */
    public static double remapThrottleSlider(double rawThrottle) {
        return (-rawThrottle + 1) / 2;
    }
}
