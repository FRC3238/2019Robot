package frc.team3238.robot.control.splitmode;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.robot.control.FREDDXControlScheme;
import frc.team3238.robot.control.joystick.Button;
import frc.team3238.robot.control.joystick.JoystickButton;

import static frc.team3238.robot.FREDDXConstants.*;

public final class DriverControl extends FREDDXControlScheme {

    private static final double MANUAL_ADJUST_SPEED = 10;

    private final Button spudsUp;
    private final Button spudsDown;
    private final Button rollerForward;
    private final Button breachersOut;
    private final Button breachersBack;
    private final Button safetyButton;
    private final Button cancelButton;
    private final Button climbButton;

    private double spudsSetpoint;
    private double breacherSetpoint;

    public DriverControl() {
        safetyButton  = new JoystickButton(driveJoystick, SAFETY);
        cancelButton  = new JoystickButton(driveJoystick, CANCEL);
        climbButton   = new JoystickButton(driveJoystick, CLIMB);
        spudsUp       = new JoystickButton(driveJoystick, SPUDS_UP);
        spudsDown     = new JoystickButton(driveJoystick, SPUDS_DOWN);
        rollerForward = new JoystickButton(driveJoystick, ROLL_FORWARD);
        breachersOut  = new JoystickButton(driveJoystick, BREACHER_OUT);
        breachersBack = new JoystickButton(driveJoystick, BREACHER_IN);

        setSetpointsCurrent();
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

        SmartDashboard.putNumber("Spuds Setpoint", spudsSetpoint);
        SmartDashboard.putNumber("Breacher Setpoint", breacherSetpoint);
    }

    @Override
    public void manualPeriodic() {
        manualDrive();

        driveTalonFwdRevOrStop(spuds, spudsUp.isHeld(), spudsDown.isHeld(), SPUDS_SPEED);
        driveTalonFwdRevOrStop(breachers, breachersOut.isHeld(), breachersBack.isHeld(), BREACHERS_SPEED);
        driveTalonFwdRevOrStop(roller, rollerForward.isHeld(), false, ROLLER_SPEED);
    }

    @Override
    public void enteringAuto() {
        setSetpointsCurrent();
    }

    @Override
    public void autoPeriodic() {
        boolean isAutoClimbing = safetyButton.isHeld() && climbButton.isReleased() && !cancelButton.isHeld();

        if(spudsUp.isHeld())
            setSpudsSetpoint(spudsSetpoint - MANUAL_ADJUST_SPEED);
        else if(spudsDown.isHeld())
            setSpudsSetpoint(spudsSetpoint + MANUAL_ADJUST_SPEED);

        if(breachersBack.isHeld())
            setBreacherSetpoint(breacherSetpoint - MANUAL_ADJUST_SPEED);
        else if(breachersOut.isHeld())
            setBreacherSetpoint(breacherSetpoint + MANUAL_ADJUST_SPEED);

        if(isAutoClimbing)
            autoClimbPeriodic();
        else {
            manualDrive();
            spuds.set(ControlMode.Position, spudsSetpoint);
            breachers.set(ControlMode.Position, breacherSetpoint);
        }

        SmartDashboard.putBoolean("Auto Climb Active", isAutoClimbing);
    }

    private void autoClimbPeriodic() {
        //TODO: automate the climb somehow
        drive.arcadeDrive(0, 0); //Keep the safety watchdog happy (TEMP)
    }

    private void manualDrive() {
        double driveThrottle = deadbandAdjust(-driveJoystick.getY(), THROTTLE_DEADBAND);
        double steer         = deadbandAdjust(driveJoystick.getTwist(), STEERING_DEADBAND);
        drive.arcadeDrive(driveThrottle, steer);
    }

    private void setSetpointsCurrent() {
        setSpudsSetpoint(spuds.getSelectedSensorPosition());
        setBreacherSetpoint(breachers.getSelectedSensorPosition());
    }

    public void setSpudsSetpoint(double setpoint) {
        if(setpoint < SPUD_MIN_DOWN)
            spudsSetpoint = SPUD_MIN_DOWN;
        else if(setpoint > SPUDS_MAX_DOWN)
            spudsSetpoint = SPUDS_MAX_DOWN;
        else
            spudsSetpoint = setpoint;
    }

    public void setBreacherSetpoint(double setpoint) {
        if(setpoint < BREACHER_MIN_OUT)
            breacherSetpoint = BREACHER_MIN_OUT;
        else if(setpoint > BREACHER_MAX_OUT)
            breacherSetpoint = BREACHER_MAX_OUT;
        else
            breacherSetpoint = setpoint;
    }
}
