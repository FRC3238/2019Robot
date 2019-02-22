package frc.team3238.robot.control.splitmode;

import frc.team3238.robot.FREDDX;
import frc.team3238.robot.control.FREDDXControlScheme;
import frc.team3238.robot.control.joystick.Button;
import frc.team3238.robot.control.joystick.JoystickButton;

import static frc.team3238.robot.FREDDXConstants.*;

class DriverManualMode extends FREDDXControlScheme {

    private final Button spudsUp;
    private final Button spudsDown;
    private final Button rollerForward;
    private final Button breachersOut;
    private final Button breachersBack;

    public DriverManualMode(FREDDX robot) {
        super(robot);
        spudsUp       = new JoystickButton(driveJoystick, SPUDS_UP_BUTTON);
        spudsDown     = new JoystickButton(driveJoystick, SPUDS_DOWN_BUTTON);
        rollerForward = new JoystickButton(driveJoystick, ROLLER_FORWARD_BUTTON);
        breachersOut  = new JoystickButton(driveJoystick, BREACHER_EXTEND_BUTTON);
        breachersBack = new JoystickButton(driveJoystick, BREACHER_RETRACT_BUTTON);
    }

    @Override
    public void updateControls() {
        //Not used
    }

    @Override
    public void teleopPeriodic() {
        double driveThrottle = deadbandAdjust(-driveJoystick.getY(), THROTTLE_DEADBAND);
        double steer         = deadbandAdjust(driveJoystick.getTwist(), STEERING_DEADBAND);
        drive.arcadeDrive(driveThrottle, steer);

        climbingPeriodic();
    }

    protected void climbingPeriodic() {
        spudsUp.update();
        spudsDown.update();
        rollerForward.update();
        breachersOut.update();
        breachersBack.update();

        driveTalonFwdRevOrStop(spuds, spudsUp.isHeld(), spudsDown.isHeld(), SPUDS_MANUAL_SPEED);
        driveTalonFwdRevOrStop(breachers, breachersOut.isHeld(), breachersBack.isHeld(), BREACHERS_MANUAL_SPEED);
        driveTalonFwdRevOrStop(roller, rollerForward.isHeld(), false, ROLLER_MANUAL_SPEED);
    }
}
