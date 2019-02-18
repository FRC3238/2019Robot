package frc.team3238.robot.control;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team3238.robot.FREDDX;

import static frc.team3238.robot.FREDDXConstants.*;

/**
 * Runs FREDDX with every motor controlled by throttle alone.
 * <p>
 * If you're on our team then you've probably seen the motor box before. This is the same thing, but you can control the
 * entire robot at once.
 */
public class ThrottleBasedControl extends FREDDXControlScheme {

    public ThrottleBasedControl(FREDDX robot) {
        super(robot);
    }

    @Override
    public void updateControls() {
        //Not used
    }

    @Override
    public void teleopPeriodic() {
        double steer         = deadbandAdjust(driveJoystick.getTwist(), STEERING_DEADBAND);
        double throttle      = driveJoystick.getThrottle();
        double driveThrottle = deadbandAdjust(driveJoystick.getY(), THROTTLE_DEADBAND);
        double liftThrottle  = deadbandAdjust(driveJoystick.getY(), LIFTING_DEADBAND);
        drive.arcadeDrive(driveThrottle, steer);

        //Run the breacher
        driveTalonFwdRevOrStop(breachers,
                               driveJoystick.getRawButton(BREACHER_EXTEND_BUTTON),
                               driveJoystick.getRawButton(BREACHER_RETRACT_BUTTON),
                               throttle);

        //Run the spuds
        driveTalonFwdRevOrStop(spuds,
                               driveJoystick.getRawButton(SPUDS_DOWN_BUTTON),
                               driveJoystick.getRawButton(SPUDS_UP_BUTTON),
                               throttle);

        //Run the roller
        driveTalonFwdRevOrStop(roller,
                               driveJoystick.getRawButton(ROLLER_FORWARD_BUTTON),
                               driveJoystick.getRawButton(ROLLER_BACKWARD_BUTTON),
                               throttle);

        //Run the wrist
        driveTalonFwdRevOrStop(wrist,
                               driveJoystick.getRawButton(WRIST_DOWN_BUTTON),
                               driveJoystick.getRawButton(WRIST_UP_BUTTON),
                               throttle);

        //Run the beak
        driveTalonFwdRevOrStop(beak,
                               driveJoystick.getRawButton(BEAK_EXTEND_BUTTON),
                               driveJoystick.getRawButton(BEAK_RETRACT_BUTTON),
                               throttle);

        //Run the lift
        lift.set(ControlMode.PercentOutput, liftThrottle);
    }

    @Override
    public String toString() {
        return "Motor Throttle Control";
    }
}
