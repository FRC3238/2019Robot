package frc.team3238.robot.control.splitmode;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.robot.control.FREDDXControlScheme;
import frc.team3238.robot.control.joystick.Button;
import frc.team3238.robot.control.joystick.JoystickButton;

import static frc.team3238.robot.FREDDXConstants.*;
import static frc.team3238.robot.FREDDXConstants.BEAK_CLOSE;

public final class ManipulatorControl extends FREDDXControlScheme {

    private final Button wristUp;
    private final Button wristDown;
    private final Button beakExtend;
    private final Button beakRetract;
    private final Button hatchLevelOne;
    private final Button hatchLevelTwo;
    private final Button hatchLevelThree;
    private final Button cargoLevelOne;
    private final Button cargoLevelTwo;
    private final Button cargoLevelThree;
    private final Button stowCollector;

    private double liftSetpoint;
    private int    wristOption;

    public ManipulatorControl() {
        //Initialize all the buttons on the joystick.
        wristUp         = new JoystickButton(manipulatorJoystick, WRIST_UP);
        wristDown       = new JoystickButton(manipulatorJoystick, WRIST_DOWN);
        beakExtend      = new JoystickButton(manipulatorJoystick, BEAK_OPEN);
        beakRetract     = new JoystickButton(manipulatorJoystick, BEAK_CLOSE);
        hatchLevelOne   = new JoystickButton(manipulatorJoystick, HATCH_LEVEL_ONE);
        hatchLevelTwo   = new JoystickButton(manipulatorJoystick, HATCH_LEVEL_TWO);
        hatchLevelThree = new JoystickButton(manipulatorJoystick, HATCH_LEVEL_THREE);
        cargoLevelOne   = new JoystickButton(manipulatorJoystick, CARGO_LEVEL_ONE);
        cargoLevelTwo   = new JoystickButton(manipulatorJoystick, CARGO_LEVEL_TWO);
        cargoLevelThree = new JoystickButton(manipulatorJoystick, CARGO_LEVEL_THREE);
        stowCollector   = new JoystickButton(manipulatorJoystick, STOW);

        setLiftSetpoint(LIFT_MIN_UP);
        setWristOption(0);
    }

    @Override
    public void updateControls() {
        wristUp.update();
        wristDown.update();
        beakExtend.update();
        beakRetract.update();
        hatchLevelOne.update();
        hatchLevelTwo.update();
        hatchLevelThree.update();
        cargoLevelOne.update();
        cargoLevelTwo.update();
        cargoLevelThree.update();
        stowCollector.update();
    }

    @Override
    public void manualPeriodic() {
        double manipulatorThrottle = deadbandAdjust(manipulatorJoystick.getY(), LIFTING_DEADBAND);

        //Drive the wrist
        driveTalonFwdRevOrStop(wrist, wristUp.isHeld(), wristDown.isHeld(), WRIST_SPEED);

        //Drive the beak
        driveTalonFwdRevOrStop(beak, beakRetract.isHeld(), beakExtend.isHeld(), BEAK_SPEED);

        //Drive the lift
        lift.set(ControlMode.PercentOutput, manipulatorThrottle);
    }

    @Override
    public void enteringAuto() {
        setLiftSetpoint(lift.getSelectedSensorPosition());
    }

    @Override
    public void autoPeriodic() {
        if(stowCollector.isReleased()) {
            setLiftSetpoint(LIFT_MIN_UP);
            setWristOption(0);
        }
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

        if(wristUp.isReleased()) {
            setWristOption(wristOption - 1);
        }
        else if(wristDown.isReleased() && (liftSetpoint != LIFT_MIN_UP || wristOption < 1)) {
            setWristOption(wristOption + 1);
        }

        SmartDashboard.putNumber("Wrist Option", wristOption);
        SmartDashboard.putNumber("Lift Setpoint", liftSetpoint);

        lift.set(ControlMode.Position, liftSetpoint);
        double wristSetpoint;
        switch(wristOption) {
            case 1:
                wristSetpoint = WRIST_FLAT_POS;
                break;
            case 2:
                wristSetpoint = WRIST_DOWN_POS;
                break;
            default:
                wristSetpoint = WRIST_STOW_POS;
        }
        wrist.set(ControlMode.Position, wristSetpoint);
        driveTalonFwdRevOrStop(beak, beakRetract.isHeld(), beakExtend.isHeld(), BEAK_SPEED);
    }

    private void setLiftSetpoint(double setpoint) {
        if(setpoint < LIFT_MIN_UP)
            liftSetpoint = LIFT_MIN_UP;
        else if(setpoint > LIFT_MAX_UP)
            liftSetpoint = LIFT_MAX_UP;
        else
            liftSetpoint = setpoint;
    }

    private void setWristOption(int option) {
        if(option < 0)
            wristOption = 0;
        else if(option > 2)
            wristOption = 2;
        else
            wristOption = option;
    }
}