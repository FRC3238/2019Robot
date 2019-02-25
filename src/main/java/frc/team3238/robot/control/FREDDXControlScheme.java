package frc.team3238.robot.control;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team3238.robot.FREDDX;

/**
 * A way of controlling FREDDX.
 * <p>
 * One control scheme could control the robot using positions for everything, and another could just rely on timing.
 */
public abstract class FREDDXControlScheme {

    protected final FREDDX            robot;
    protected final Joystick          driveJoystick;
    protected final Joystick          manipulatorJoystick;
    protected final DifferentialDrive drive;
    protected final WPI_TalonSRX      breachers;
    protected final WPI_TalonSRX      spuds;
    protected final WPI_TalonSRX      roller;
    protected final WPI_TalonSRX      lift;
    protected final WPI_TalonSRX      wrist;
    protected final WPI_TalonSRX      beak;

    public FREDDXControlScheme(FREDDX robot) {
        this.robot          = robot;
        driveJoystick       = robot.getDriverJoystick();
        manipulatorJoystick = robot.getManipulatorJoystick();
        drive               = robot.getDrive();
        breachers           = robot.getBreacher();
        spuds               = robot.getSpuds();
        roller              = robot.getRoller();
        lift                = robot.getLift();
        wrist               = robot.getWrist();
        beak                = robot.getBeak();
    }

    /**
     * Updates the state of the controls which command the robot what to do.
     */
    public abstract void updateControls();

    /**
     * Controls the robot for teleoperation mode.
     */
    public abstract void teleopPeriodic();

    /**
     * Takes a raw value as input and applies a deadband to it.
     *
     * @param rawValue [-1, 1] The raw value
     * @param deadband [0, 1) The amount of deadband to use
     * @return The deadband-adjusted value
     */
    public static double deadbandAdjust(double rawValue, double deadband) {
        if(Math.abs(rawValue) < deadband)
            return 0;
        else {
            if(rawValue < 0)
                return (rawValue + deadband) / (1 - deadband);
            else
                return (rawValue - deadband) / (1 - deadband);
        }
    }

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

    public static double remapThrottle(double rawThrottle) {
        return (-rawThrottle + 1) / 2;
    }

    @Override
    public String toString() {
        return "FREDDX Control Scheme";
    }
}
