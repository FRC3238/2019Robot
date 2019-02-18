package frc.team3238.robot;

import edu.wpi.first.wpilibj.Joystick;

import static frc.team3238.robot.FREDDXConstants.*;

public final class FREDDXControls {

    /**
     * The joystick used by the person driving FREDDX
     */
    private final Joystick driveJoystick;

    /**
     * The joystick used by person running FREDDX's manipulator systems
     */
    private final Joystick manipulatorJoystick;

    public FREDDXControls(int driveJoystickPort, int manipulatorJoystickPort) {
        driveJoystick       = new Joystick(driveJoystickPort);
        manipulatorJoystick = new Joystick(manipulatorJoystickPort);
    }

    public Joystick getDriveJoystick() {
        return driveJoystick;
    }

    public Joystick getManipulatorJoystick() {
        return manipulatorJoystick;
    }
}
