package frc.team3238.robot.control;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.robot.FREDDX;
import frc.team3238.robot.control.splitmode.SplitControlMode;

public class SwitchableControls extends FREDDXControlScheme {

    private final SendableChooser<FREDDXControlScheme> chooser;

    private FREDDXControlScheme selectedControlScheme;

    public SwitchableControls(FREDDX robot) {
        super(robot);
        FREDDXControlScheme globalManualControl = new FullManualControl(robot);
        FREDDXControlScheme splitControlMode    = new SplitControlMode(robot);

        //Create the control scheme chooser
        chooser = new SendableChooser<>();
        chooser.setDefaultOption(globalManualControl.toString(), globalManualControl);
        chooser.addOption(splitControlMode.toString(), splitControlMode);
        //TODO: Change split mode to default once it proves itself
    }

    @Override
    public void updateControls() {
        //Change to selected control scheme
        selectedControlScheme = chooser.getSelected();

        //Display chooser
        SmartDashboard.putData("Current Control Scheme", chooser);

        //Display sensor values
        SmartDashboard.putNumber("Breacher Sensor", breachers.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Spuds Sensor", spuds.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Lift Sensor", lift.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Wrist Sensor", wrist.getSelectedSensorPosition(0));
    }

    @Override
    public void manualPeriodic() {
        //React to other control schemes only in teleop
        selectedControlScheme.updateControls();

        //Let the controller do it's work
        selectedControlScheme.manualPeriodic();
    }

    @Override
    public String toString() {
        return "Switchable Control Scheme";
    }
}
