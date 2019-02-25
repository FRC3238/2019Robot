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

    private boolean isClimbing;

    public DriverAutoMode(FREDDX robot) {
        super(robot);
        safetyButton = new JoystickButton(driveJoystick, SAFETY);
        cancelButton = new JoystickButton(driveJoystick, CANCEL);
        climbButton  = new JoystickButton(driveJoystick, CLIMB);
    }

    @Override
    public void updateControls() {
        if(safetyButton.isHeld() && climbButton.isReleased())
            isClimbing = true;

        if(cancelButton.isHeld())
            isClimbing = false;
    }

    @Override
    public void manualPeriodic() {
        if(isClimbing) {
            climbingPeriodic();
        }
        else {
            super.manualPeriodic();
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
