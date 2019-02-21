package frc.team3238.robot.control.splitmode;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team3238.robot.FREDDX;
import frc.team3238.robot.control.FREDDXControlScheme;
import frc.team3238.robot.control.joystick.Button;
import frc.team3238.robot.control.joystick.JoystickButton;

import static frc.team3238.robot.FREDDXConstants.*;

//TODO: Not yet implemented
class ManipulatorAutoMode extends FREDDXControlScheme {

    private final Button hatchLevelOne;
    private final Button hatchLevelTwo;
    private final Button hatchLevelThree;
    private final Button cargoLevelOne;
    private final Button cargoLevelTwo;
    private final Button cargoLevelThree;
    private final Button stowCollector;
    private final Button levelCollector;


    public ManipulatorAutoMode(FREDDX robot) {
        super(robot);
        //Initialize all the buttons on the joystick.
        hatchLevelOne   = new JoystickButton(manipulatorJoystick,HATCH_LEVEL_ONE_BUTTON);
        hatchLevelTwo   = new JoystickButton(manipulatorJoystick,HATCH_LEVEL_TWO_BUTTON);
        hatchLevelThree = new JoystickButton(manipulatorJoystick,HATCH_LEVEL_THREE_BUTTON);
        cargoLevelOne   = new JoystickButton(manipulatorJoystick,CARGO_LEVEL_ONE_BUTTON);
        cargoLevelTwo   = new JoystickButton(manipulatorJoystick,CARGO_LEVEL_TWO_BUTTON);
        cargoLevelThree = new JoystickButton(manipulatorJoystick,CARGO_LEVEL_THREE_BUTTON);
        stowCollector   = new JoystickButton(manipulatorJoystick,COLLECTOR_STOW_BUTTON);
        levelCollector  = new JoystickButton(manipulatorJoystick,COLLECTOR_FLAT_BUTTON);
    }

    @Override
    public void updateControls() {
        //TODO: Not yet implemented
    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public String toString() {
        return "Manipulator Auto Mode";
    }
}
