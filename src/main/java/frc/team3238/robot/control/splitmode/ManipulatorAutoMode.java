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
    private final Button beakExtend;
    private final Button beakRetract;

    public ManipulatorAutoMode(FREDDX robot) {
        super(robot);
        //Initialize all the buttons on the joystick.
        hatchLevelOne   = new JoystickButton(manipulatorJoystick, HATCH_LEVEL_ONE);
        hatchLevelTwo   = new JoystickButton(manipulatorJoystick, HATCH_LEVEL_TWO);
        hatchLevelThree = new JoystickButton(manipulatorJoystick, HATCH_LEVEL_THREE);
        cargoLevelOne   = new JoystickButton(manipulatorJoystick, CARGO_LEVEL_ONE);
        cargoLevelTwo   = new JoystickButton(manipulatorJoystick, CARGO_LEVEL_TWO);
        cargoLevelThree = new JoystickButton(manipulatorJoystick, CARGO_LEVEL_THREE);
        stowCollector   = new JoystickButton(manipulatorJoystick, STOW);
        levelCollector  = new JoystickButton(manipulatorJoystick, WRIST_FLAT);
        wristUp         = new JoystickButton(manipulatorJoystick, WRIST_UP);
        wristDown       = new JoystickButton(manipulatorJoystick, WRIST_DOWN);
        wristFloor      = new JoystickButton(manipulatorJoystick, WRIST_FLOOR);
        beakExtend  = new JoystickButton(manipulatorJoystick, BEAK_OPEN);
        beakRetract = new JoystickButton(manipulatorJoystick, BEAK_CLOSE);

        setLiftSetpoint(LIFT_MIN_UP);
        setWristSetpoint(WRIST_STOW_POS);
    }

    @Override
    public void updateControls() {
        updateButtons();
        if(stowCollector.isReleased())
            setLiftSetpoint(LIFT_MIN_UP);
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
        driveTalonFwdRevOrStop(wrist, wristUp.isHeld(), wristDown.isHeld(), WRIST_SPEED);
        driveTalonFwdRevOrStop(beak, beakRetract.isHeld(), beakExtend.isHeld(), BEAK_SPEED);
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
        wristUp.update();
        wristDown.update();
        beakExtend.update();
        beakRetract.update();
    }

    private void setLiftSetpoint(double setpoint) {
        if(setpoint < LIFT_MIN_UP)
            liftSetpoint = LIFT_MIN_UP;
        else if(setpoint > LIFT_MAX_UP)
            liftSetpoint = LIFT_MAX_UP;
        else
            liftSetpoint = setpoint;
    }

    private void setWristSetpoint(double setpoint) {
        if(setpoint < WRIST_MIN_UP)
            wristSetpoint = WRIST_MIN_UP;
        else if(setpoint > WRIST_MAX_UP)
            wristSetpoint = WRIST_MAX_UP;
        else
            wristSetpoint = setpoint;
    }

    @Override
    public String toString() {
        return "Manipulator Auto Mode";
    }
}
