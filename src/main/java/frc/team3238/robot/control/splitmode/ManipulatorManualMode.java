package frc.team3238.robot.control.splitmode;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team3238.robot.control.joystick.Button;
import frc.team3238.robot.FREDDX;
import frc.team3238.robot.control.FREDDXControlScheme;
import frc.team3238.robot.control.joystick.JoystickButton;

import static frc.team3238.robot.FREDDXConstants.*;

/*
*   Used to drive the manipulators in the Manual Mode
*
 */
class ManipulatorManualMode extends FREDDXControlScheme {

    private final Button wristUp;
    private final Button wristDown;
    private final Button beakExtend;
    private final Button beakRetract;

    public ManipulatorManualMode(FREDDX robot) {
        super(robot);
        wristUp     = new JoystickButton(manipulatorJoystick, WRIST_UP_BUTTON);
        wristDown   = new JoystickButton(manipulatorJoystick, WRIST_DOWN_BUTTON);
        beakExtend  = new JoystickButton(manipulatorJoystick, BEAK_EXTEND_BUTTON);
        beakRetract = new JoystickButton(manipulatorJoystick, BEAK_RETRACT_BUTTON);
    }

    @Override
    public void updateControls() {
        //Not used
    }

    @Override
    public void teleopPeriodic() {
        double manipulatorThrottle = remapThrottle(manipulatorJoystick.getThrottle());
        wristUp.update();
        wristDown.update();
        beakExtend.update();
        beakRetract.update();

        //Drive the wrist
        driveTalonFwdRevOrStop(wrist, wristDown.isPressed(), wristUp.isPressed(),manipulatorThrottle);

        //Drive the beak
        driveTalonFwdRevOrStop(beak, beakExtend.isPressed(), beakRetract.isPressed(),manipulatorThrottle);

        //Drive the lift
        lift.set(ControlMode.PercentOutput,manipulatorThrottle);
    }

    public static double remapThrottle(double rawThrottle) {
        return (rawThrottle + 1) / 2;
    }
}
