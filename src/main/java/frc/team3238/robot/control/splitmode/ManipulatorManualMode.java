package frc.team3238.robot.control.splitmode;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team3238.robot.control.joystick.Button;
import frc.team3238.robot.FREDDX;
import frc.team3238.robot.control.FREDDXControlScheme;
import frc.team3238.robot.control.joystick.JoystickButton;

import static frc.team3238.robot.FREDDXConstants.*;

/**
 * Used to drive the manipulators in the Manual Mode
 */
class ManipulatorManualMode extends FREDDXControlScheme {

    private final Button wristUp;
    private final Button wristDown;
    private final Button beakExtend;
    private final Button beakRetract;

    public ManipulatorManualMode(FREDDX robot) {
        super(robot);
        wristUp     = new JoystickButton(manipulatorJoystick, WRIST_UP);
        wristDown   = new JoystickButton(manipulatorJoystick, WRIST_DOWN);
        beakExtend  = new JoystickButton(manipulatorJoystick, BEAK_OPEN);
        beakRetract = new JoystickButton(manipulatorJoystick, BEAK_CLOSE);
    }

    @Override
    public void manualPeriodic() {
        double manipulatorThrottle = deadbandAdjust(manipulatorJoystick.getY(), LIFTING_DEADBAND);
        wristUp.update();
        wristDown.update();
        beakExtend.update();
        beakRetract.update();

        //Drive the wrist
        driveTalonFwdRevOrStop(wrist, wristUp.isHeld(), wristDown.isHeld(), WRIST_SPEED);

        //Drive the beak
        driveTalonFwdRevOrStop(beak, beakRetract.isHeld(), beakExtend.isHeld(), BEAK_SPEED);

        //Drive the lift
        lift.set(ControlMode.PercentOutput, manipulatorThrottle);
    }

    @Override
    public String toString() {
        return "Manipulator Manual Mode";
    }
}
