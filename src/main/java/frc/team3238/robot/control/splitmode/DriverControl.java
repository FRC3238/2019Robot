package frc.team3238.robot.control.splitmode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.robot.control.FREDDXControlScheme;
import frc.team3238.robot.control.joystick.Button;
import frc.team3238.robot.control.joystick.JoystickButton;

import static frc.team3238.robot.FREDDXConstants.*;

public final class DriverControl extends FREDDXControlScheme {

    private final Button spudsUp;
    private final Button spudsDown;
    private final Button rollerForward;
    private final Button breachersOut;
    private final Button breachersBack;
    private final Button safetyButton;
    private final Button cancelButton;
    private final Button climbButton;

    private boolean isAutoClimbing;

    public DriverControl() {
        safetyButton  = new JoystickButton(driveJoystick, SAFETY);
        cancelButton  = new JoystickButton(driveJoystick, CANCEL);
        climbButton   = new JoystickButton(driveJoystick, CLIMB);
        spudsUp       = new JoystickButton(driveJoystick, SPUDS_UP);
        spudsDown     = new JoystickButton(driveJoystick, SPUDS_DOWN);
        rollerForward = new JoystickButton(driveJoystick, ROLL_FORWARD);
        breachersOut  = new JoystickButton(driveJoystick, BREACHER_OUT);
        breachersBack = new JoystickButton(driveJoystick, BREACHER_IN);

        isAutoClimbing = false;
    }

    @Override
    public void updateControls() {
        spudsUp.update();
        spudsDown.update();
        rollerForward.update();
        breachersOut.update();
        breachersBack.update();
        safetyButton.update();
        climbButton.update();
        cancelButton.update();
    }

    @Override
    public void manualPeriodic() {
        double driveThrottle = deadbandAdjust(-driveJoystick.getY(), THROTTLE_DEADBAND);
        double steer         = deadbandAdjust(driveJoystick.getTwist(), STEERING_DEADBAND);
        drive.arcadeDrive(driveThrottle, steer);

        driveTalonFwdRevOrStop(spuds, spudsUp.isHeld(), spudsDown.isHeld(), SPUDS_SPEED);
        driveTalonFwdRevOrStop(breachers, breachersOut.isHeld(), breachersBack.isHeld(), BREACHERS_SPEED);
        driveTalonFwdRevOrStop(roller, rollerForward.isHeld(), false, ROLLER_SPEED);
    }

    @Override
    public void autoPeriodic() {
        if(safetyButton.isHeld() && climbButton.isReleased())
            isAutoClimbing = true;

        if(cancelButton.isHeld())
            isAutoClimbing = false;

        if(isAutoClimbing)
            autoClimbPeriodic();

        SmartDashboard.putBoolean("Auto Climb Active", isAutoClimbing);
    }

    private void autoClimbPeriodic() {
        //TODO: automate the climb somehow
        drive.arcadeDrive(0, 0); //Keep the safety watchdog happy (TEMP)
    }
}
