package frc.team3238.robot.control.splitmode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.robot.FREDDX;
import frc.team3238.robot.control.FREDDXControlScheme;

public class SplitControlMode extends FREDDXControlScheme {

    private final FREDDXControlScheme driverManual;
    private final FREDDXControlScheme driverAuto;
    private final FREDDXControlScheme manipulatorManual;
    private final FREDDXControlScheme manipulatorAuto;

    private FREDDXControlScheme driverScheme;
    private FREDDXControlScheme manipulatorScheme;

    public SplitControlMode(FREDDX robot) {
        super(robot);

        driverManual      = new DriverManualMode(robot);
        driverAuto        = new DriverAutoMode(robot);
        manipulatorManual = new ManipulatorManualMode(robot);
        manipulatorAuto   = new ManipulatorAutoMode(robot);

        driverScheme      = driverAuto;
        manipulatorScheme = manipulatorAuto;
    }

    @Override
    public void updateControls() {
        if(remapThrottle(manipulatorJoystick.getThrottle()) < 0.5) {
            manipulatorScheme = manipulatorAuto;
            SmartDashboard.putBoolean("Split Manipulator Auto", true);
        }
        else {
            manipulatorScheme = manipulatorManual;
            SmartDashboard.putBoolean("Split Manipulator Auto", false);
        }

        if(remapThrottle(driveJoystick.getThrottle()) < 0.5) {
            driverScheme = driverAuto;
            SmartDashboard.putBoolean("Split Drive Auto", true);
        }
        else {
            driverScheme = driverManual;
            SmartDashboard.putBoolean("Split Drive Auto", false);
        }
    }

    @Override
    public void manualPeriodic() {
        driverScheme.updateControls();
        driverScheme.manualPeriodic();

        manipulatorScheme.updateControls();
        manipulatorScheme.manualPeriodic();
    }

    @Override
    public String toString() {
        return "Split Mode Control";
    }
}
