package frc.team3238.robot.control.splitmode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.robot.control.FREDDXControlScheme;

public final class SplitControlMode extends FREDDXControlScheme {

    private final FREDDXControlScheme driver;
    private final FREDDXControlScheme manipulator;

    private boolean isDriverAuto;
    private boolean wasDriverAutoLast;
    private boolean isManipulatorAuto;
    private boolean wasManipulatorAutoLast;

    public SplitControlMode() {
        driver      = new DriverControl();
        manipulator = new ManipulatorControl();
    }

    @Override
    public void updateControls() {
        isDriverAuto = remapThrottle(driveJoystick.getThrottle()) < 0.5;
        SmartDashboard.putBoolean("Split Drive Auto", isDriverAuto);

        isManipulatorAuto = remapThrottle(manipulatorJoystick.getThrottle()) < 0.5;
        SmartDashboard.putBoolean("Split Manipulator Auto", isManipulatorAuto);
    }

    @Override
    public void manualPeriodic() {
        driver.updateControls();
        manipulator.updateControls();

        if(isDriverAuto) {
            if(!wasDriverAutoLast) {
                driver.enteringAuto();
            }
            driver.autoPeriodic();
        }
        else {
            if(wasDriverAutoLast) {
                driver.enteringManual();
            }
            driver.manualPeriodic();
        }
        wasDriverAutoLast = isDriverAuto;

        if(isManipulatorAuto) {
            if(!wasManipulatorAutoLast) {
                manipulator.enteringAuto();
            }
            manipulator.autoPeriodic();
        }
        else {
            if(wasManipulatorAutoLast) {
                manipulator.enteringManual();
            }
            manipulator.manualPeriodic();
        }
        wasManipulatorAutoLast = isManipulatorAuto;
    }

    @Override
    public String toString() {
        return "Split Mode Control";
    }
}
