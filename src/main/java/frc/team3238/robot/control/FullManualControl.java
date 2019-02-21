package frc.team3238.robot.control;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.robot.FREDDX;

import static frc.team3238.robot.FREDDXConstants.*;

/**
 * Runs FREDDX with every motor controlled by throttle alone.
 * <p>
 * If you're on our team then you've probably seen the motor box before. This is the same thing, but you can control the
 * entire robot at once.
 */
public class FullManualControl extends FREDDXControlScheme {

    public FullManualControl(FREDDX robot) {
        super(robot);
    }

    @Override
    public void updateControls() {
        //Not used
    }

    @Override
    public void teleopPeriodic() {
        double steer         = deadbandAdjust(driveJoystick.getTwist(), STEERING_DEADBAND);
        double driverThrottle      = remapThrottle(driveJoystick.getThrottle());
        double manipulatorThrottle = remapThrottle(manipulatorJoystick.getThrottle());
        double driveThrottle = deadbandAdjust(-driveJoystick.getY(), THROTTLE_DEADBAND);
        double liftThrottle  = deadbandAdjust(manipulatorJoystick.getY(), LIFTING_DEADBAND);
        drive.arcadeDrive(driveThrottle, steer);

        //Run the breacher
        driveTalonFwdRevOrStop(breachers,
                               driveJoystick.getRawButton(BREACHER_EXTEND_BUTTON),
                               driveJoystick.getRawButton(BREACHER_RETRACT_BUTTON),
                               driverThrottle);

        //Run the spuds
        driveTalonFwdRevOrStop(spuds,
                               driveJoystick.getRawButton(SPUDS_DOWN_BUTTON),
                               driveJoystick.getRawButton(SPUDS_UP_BUTTON),
                               driverThrottle);

        SmartDashboard.putNumber("Spud throttle", driverThrottle);
        SmartDashboard.putBoolean("Spud up", driveJoystick.getRawButton(SPUDS_UP_BUTTON));
        SmartDashboard.putNumber("Spud draw", spuds.getOutputCurrent());

        //Run the roller
        driveTalonFwdRevOrStop(roller,
                               driveJoystick.getRawButton(ROLLER_FORWARD_BUTTON),
                        false,
                               driverThrottle);

        //Run the wrist
        driveTalonFwdRevOrStop(wrist,
                               manipulatorJoystick.getRawButton(WRIST_DOWN_BUTTON),
                               manipulatorJoystick.getRawButton(WRIST_UP_BUTTON),
                               manipulatorThrottle);

        //Run the beak
        driveTalonFwdRevOrStop(beak,
                               manipulatorJoystick.getRawButton(BEAK_EXTEND_BUTTON),
                               manipulatorJoystick.getRawButton(BEAK_RETRACT_BUTTON),
                               manipulatorThrottle);

        //Run the lift
        lift.set(ControlMode.PercentOutput, liftThrottle);
    }

    @Override
    public String toString() {
        return "Full Manual Control";
    }
}