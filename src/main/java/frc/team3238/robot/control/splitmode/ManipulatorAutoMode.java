package frc.team3238.robot.control.splitmode;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.robot.FREDDX;
import frc.team3238.robot.control.FREDDXControlScheme;
import frc.team3238.robot.control.joystick.Button;
import frc.team3238.robot.control.joystick.JoystickButton;

import static frc.team3238.robot.FREDDXConstants.*;

class ManipulatorAutoMode extends FREDDXControlScheme {

    private static double liftSetpoint;
    private static double wristSetpoint;

    private final Button hatchLevelOne;
    private final Button hatchLevelTwo;
    private final Button hatchLevelThree;
    private final Button cargoLevelOne;
    private final Button cargoLevelTwo;
    private final Button cargoLevelThree;
    private final Button stowCollector;
    private final Button levelCollector;
    private final Button wristUp;
    private final Button wristDown;
    private final Button wristFloor;

    public ManipulatorAutoMode(FREDDX robot) {
        super(robot);
        //Initialize all the buttons on the joystick.
        hatchLevelOne   = new JoystickButton(manipulatorJoystick,HATCH_LEVEL_ONE_BUTTON);
        hatchLevelTwo   = new JoystickButton(manipulatorJoystick,HATCH_LEVEL_TWO_BUTTON);
        hatchLevelThree = new JoystickButton(manipulatorJoystick,HATCH_LEVEL_THREE_BUTTON);
        cargoLevelOne   = new JoystickButton(manipulatorJoystick,CARGO_LEVEL_ONE_BUTTON);
        cargoLevelTwo   = new JoystickButton(manipulatorJoystick,CARGO_LEVEL_TWO_BUTTON);
        cargoLevelThree = new JoystickButton(manipulatorJoystick,CARGO_LEVEL_THREE_BUTTON);
        stowCollector   = new JoystickButton(manipulatorJoystick,STOW_BUTTON);
        levelCollector  = new JoystickButton(manipulatorJoystick,COLLECTOR_FLAT_BUTTON);
        wristUp         = new JoystickButton(manipulatorJoystick, WRIST_UP_BUTTON);
        wristDown       = new JoystickButton(manipulatorJoystick, WRIST_DOWN_BUTTON);
        wristFloor      = new JoystickButton(manipulatorJoystick,WRIST_FLOOR_BUTTON);

        setLiftSetpoint(LIFT_MIN_EXTEND);
        setWristSetpoint(WRIST_STOW_POS);
    }

    @Override
    public void updateControls() {
        updateButtons();
        if(stowCollector.isReleased())
            setLiftSetpoint(LIFT_MIN_EXTEND);
        else if(hatchLevelOne.isReleased())
            setLiftSetpoint(LIFT_HATCH_LEVEL_ONE);
        else if(hatchLevelTwo.isReleased())
            setLiftSetpoint(LIFT_HATCH_LEVEL_TWO);
        else if(hatchLevelThree.isReleased())
            setLiftSetpoint(LIFT_HATCH_LEVEL_THREE);
        else if(cargoLevelOne.isReleased())
            setLiftSetpoint(LIFT_CARGO_LEVEL_ONE);
        else if(cargoLevelTwo.isReleased())
            setLiftSetpoint(LIFT_CARGO_LEVEL_TWO);
        else if(cargoLevelThree.isReleased())
            setLiftSetpoint(LIFT_CARGO_LEVEL_THREE);

        if(stowCollector.isReleased())
            setWristSetpoint(WRIST_STOW_POS);
        else if(wristUp.isReleased())
            setWristSetpoint(WRIST_UP_POS);
        else if(levelCollector.isReleased())
            setWristSetpoint(WRIST_FLAT_POS);
        else if(wristFloor.isReleased())
            setWristSetpoint(WRIST_FLOOR_COLLECT_POS);
        else if(wristDown.isReleased())
            setWristSetpoint(WRIST_DOWN_POS);

        SmartDashboard.putNumber("Wrist Setpoint", wristSetpoint);
        SmartDashboard.putNumber("Lift Setpoint", liftSetpoint);

    }

    @Override
    public void teleopPeriodic() {
        lift.set(ControlMode.Position, liftSetpoint);
        wrist.set(ControlMode.Position, wristSetpoint);
    }

    private void updateButtons() {
        hatchLevelOne.update();
        hatchLevelTwo.update();
        hatchLevelThree.update();
        cargoLevelOne.update();
        cargoLevelTwo.update();
        cargoLevelThree.update();
        stowCollector.update();
        levelCollector.update();
    }

    private void setLiftSetpoint(double setpoint) {
        if(setpoint < LIFT_MIN_EXTEND)
            liftSetpoint = LIFT_MIN_EXTEND;
        else if(setpoint > LIFT_MAX_EXTEND)
            liftSetpoint = LIFT_MAX_EXTEND;
        else
            liftSetpoint = setpoint;
    }

    private void setWristSetpoint(double setpoint) {
        if(setpoint < WRIST_MIN_EXTEND)
            wristSetpoint = WRIST_MIN_EXTEND;
        else if (setpoint > WRIST_MAX_EXTEND)
            wristSetpoint = WRIST_MAX_EXTEND;
        else
            wristSetpoint = setpoint;
    }

    @Override
    public String toString() {
        return "Manipulator Auto Mode";
    }
}
