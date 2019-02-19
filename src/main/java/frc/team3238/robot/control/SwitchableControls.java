package frc.team3238.robot.control;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.robot.FREDDX;

public class SwitchableControls extends FREDDXControlScheme {

    private final SendableChooser<FREDDXControlScheme> chooser;

    private FREDDXControlScheme selectedControlScheme;

    public SwitchableControls(FREDDX robot) {
        super(robot);
        ThrottleBasedControl throttleControl = new ThrottleBasedControl(robot);
        PositionBasedControl positionControl = new PositionBasedControl(robot);

        //Create the control scheme chooser
        chooser = new SendableChooser<>();
        chooser.setDefaultOption(throttleControl.toString(), throttleControl);
        chooser.addOption(positionControl.toString(), positionControl);

        //Display chooser
        SmartDashboard.putData("Control Scheme", chooser);
    }

    @Override
    public void updateControls() {
        //Change to selected control scheme
        selectedControlScheme = chooser.getSelected();

        //Display sensor values
        SmartDashboard.putNumber("Breacher Sensor", breachers.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Spuds Sensor", spuds.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Lift Sensor", lift.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Wrist Sensor", wrist.getSelectedSensorPosition(0));
    }

    @Override
    public void teleopPeriodic() {
        //React to other control schemes only in teleop
        selectedControlScheme.updateControls();

        //Let the other controllers do their  work
        selectedControlScheme.teleopPeriodic();
    }

    @Override
    public String toString() {
        return "Switchable Control Scheme";
    }
}
