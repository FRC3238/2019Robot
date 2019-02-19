package frc.team3238.robot.control;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.robot.FREDDX;

import static frc.team3238.robot.FREDDXConstants.*;
import static frc.team3238.robot.FREDDXConstants.SPUDS_MAX_EXTEND;

public class PositionButtonsControl extends FREDDXControlScheme {

    private static final int MANUAL_BREACHER_SPEED = 10;
    private static final int MANUAL_SPUDS_SPEED    = 10;

    private int    breacherSetpoint = BREACHER_MIN_EXTEND;
    private int    spudsSetpoint    = SPUD_MIN_EXTEND;
    private int    wristSetpoint    = WRIST_STOW_POS;
    private double liftSetpoint     = LIFT_STOW_POS;

    public PositionButtonsControl(FREDDX robot) {
        super(robot);
    }

    @Override
    public void updateControls() {
        int breacherChange = getSignedSetpointIncrement(driveJoystick,
                                                        BREACHER_EXTEND_BUTTON, BEAK_RETRACT_BUTTON,
                                                        MANUAL_BREACHER_SPEED);

        int spudsChange = getSignedSetpointIncrement(driveJoystick,
                                                     SPUDS_DOWN_BUTTON, SPUDS_UP_BUTTON,
                                                     MANUAL_SPUDS_SPEED);

        if(manipulatorJoystick.getRawButton(HATCH_LEVEL_ONE_BUTTON))
            setLiftSetpoint(LIFT_HATCH_LEVEL_ONE);
        else if(manipulatorJoystick.getRawButton(HATCH_LEVEL_TWO_BUTTON))
            setLiftSetpoint(LIFT_HATCH_LEVEL_TWO);
        else if(manipulatorJoystick.getRawButton(HATCH_LEVEL_THREE_BUTTON))
            setLiftSetpoint(LIFT_HATCH_LEVEL_THREE);
        else if(manipulatorJoystick.getRawButton(CARGO_LEVEL_ONE_BUTTON))
            setLiftSetpoint(LIFT_CARGO_LEVEL_ONE);
        else if(manipulatorJoystick.getRawButton(CARGO_LEVEL_TWO_BUTTON))
            setLiftSetpoint(LIFT_CARGO_LEVEL_TWO);
        else if(manipulatorJoystick.getRawButton(CARGO_LEVEL_THREE_BUTTON))
            setLiftSetpoint(LIFT_CARGO_LEVEL_THREE);
        else if(manipulatorJoystick.getRawButton(COLLECTOR_STOW_BUTTON))
            setLiftSetpoint(LIFT_STOW_POS);

        if(manipulatorJoystick.getRawButton(COLLECTOR_FLAT_BUTTON))
            setWristSetpoint(WRIST_FLAT_POS);
        else if(manipulatorJoystick.getRawButton(COLLECTOR_DOWN_BUTTON))
            setWristSetpoint(WRIST_DOWN_POS);
        else if(manipulatorJoystick.getRawButton(COLLECTOR_FLOOR_BUTTON))
            setWristSetpoint(WRIST_FLOOR_COLLECT_POS);
        else if(manipulatorJoystick.getRawButton(COLLECTOR_STOW_BUTTON))
            setWristSetpoint(WRIST_STOW_POS);

        //Update setpoints
        setBreacherSetpoint(breacherSetpoint + breacherChange);
        setSpudsSetpoint(spudsSetpoint + spudsChange);

        //Display on dash
        SmartDashboard.putNumber("Breacher Setpoint", breacherSetpoint);
        SmartDashboard.putNumber("Spuds Setpoint", spudsSetpoint);
        SmartDashboard.putNumber("Wrist Setpoint", wristSetpoint);
        SmartDashboard.putNumber("Lift Setpoint", liftSetpoint);
    }

    @Override
    public void teleopPeriodic() {
        //Throttle based control for driving
        double driveThrottle    = deadbandAdjust(-driveJoystick.getY(), THROTTLE_DEADBAND);
        double steeringThrottle = deadbandAdjust(driveJoystick.getTwist(), STEERING_DEADBAND);
        drive.arcadeDrive(driveThrottle, steeringThrottle);

        //Put in set-points for motion-magic devices
        breachers.set(ControlMode.MotionMagic, breacherSetpoint);
        spuds.set(ControlMode.MotionMagic, spudsSetpoint);
        lift.set(ControlMode.MotionMagic, liftSetpoint);
        wrist.set(ControlMode.MotionMagic, wristSetpoint);

        //Throttle based control for the roller
        driveTalonFwdRevOrStop(roller,
                               driveJoystick.getRawButton(ROLLER_FORWARD_BUTTON),
                               driveJoystick.getRawButton(ROLLER_BACKWARD_BUTTON),
                               SPUD_ROLLER_SPEED);

        //Throttle based control for the beak
        driveTalonFwdRevOrStop(beak,
                               manipulatorJoystick.getRawButton(BEAK_EXTEND_BUTTON),
                               manipulatorJoystick.getRawButton(BEAK_RETRACT_BUTTON),
                               BEAK_SPEED);
    }

    /**
     * Gets an increment for a set-point that is controlled by two buttons, one up, the other down.
     *
     * @param stick      The joystick which has the buttons
     * @param upButton   The id of the button for positive increments
     * @param downButton The id of the button for negative increments
     * @param increment  The absolute value of the increment to return
     * @return A positive increment if up is pressed, a negative increment if down is pressed, or 0 if neither is
     * pressed
     */
    private static int getSignedSetpointIncrement(Joystick stick, int upButton, int downButton, int increment) {
        if(stick.getRawButton(upButton))
            return increment;
        else if(stick.getRawButton(downButton))
            return -increment;
        else
            return 0;
    }

    private void setLiftSetpoint(double setpoint) {
        if(setpoint < LIFT_MIN_EXTEND)
            liftSetpoint = LIFT_MIN_EXTEND;
        else if(setpoint > LIFT_MAX_EXTEND)
            liftSetpoint = LIFT_MAX_EXTEND;
        else
            liftSetpoint = setpoint;
    }

    private void setWristSetpoint(int setpoint) {
        if(setpoint < WRIST_MIN_EXTEND)
            wristSetpoint = WRIST_MIN_EXTEND;
        else if(setpoint > WRIST_MAX_EXTEND)
            wristSetpoint = WRIST_MAX_EXTEND;
        else
            wristSetpoint = setpoint;
    }

    private void setBreacherSetpoint(int value) {
        if(value < BREACHER_MIN_EXTEND)
            breacherSetpoint = BREACHER_MIN_EXTEND;
        else if(value > BREACHER_MAX_EXTEND)
            breacherSetpoint = BREACHER_MAX_EXTEND;
        else
            breacherSetpoint = value;
    }

    private void setSpudsSetpoint(int value) {
        if(value < SPUD_MIN_EXTEND)
            spudsSetpoint = SPUD_MIN_EXTEND;
        else if(value > SPUDS_MAX_EXTEND)
            spudsSetpoint = SPUDS_MAX_EXTEND;
        else
            spudsSetpoint = value;
    }

    @Override
    public String toString() {
        return "Position Buttons Control";
    }
}
