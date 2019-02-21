package frc.team3238.robot.control.splitmode;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.robot.FREDDX;
import frc.team3238.robot.control.joystick.Button;
import frc.team3238.robot.control.joystick.JoystickButton;

import static frc.team3238.robot.FREDDXConstants.*;

class DriverAutoMode extends DriverManualMode {

    private final Button safetyButton;
    private final Button cancelButton;
    private final Button climbButton;

    public DriverAutoMode(FREDDX robot) {
        super(robot);
        safetyButton = new JoystickButton(driveJoystick, SAFETY_BUTTON);
        cancelButton = new JoystickButton(driveJoystick, CANCEL_BUTTON);
        climbButton  = new JoystickButton(driveJoystick, CLIMB_BUTTON);
    }

    private boolean isClimbing;

    @Override
    public void updateControls() {
        if(safetyButton.isHeld() && climbButton.isReleased())
            isClimbing = true;

        if(cancelButton.isHeld())
            isClimbing = false;
    }

    @Override
    public void teleopPeriodic() {
        if(isClimbing) {
            climbingPeriodic();
        }
        else {
            super.teleopPeriodic();
        }
    }

    @Override
    protected void climbingPeriodic() {
        if(isClimbing) {
            //TODO: automate the climb somehow
            drive.arcadeDrive(0, 0); //Keep the safety watchdog happy (TEMP)
        }
        else {
            spuds.set(ControlMode.PercentOutput, 0);
            roller.set(ControlMode.PercentOutput, 0);
            breachers.set(ControlMode.PercentOutput, 0);
        }

        SmartDashboard.putBoolean("Auto Climb Active", isClimbing);
    }
}
