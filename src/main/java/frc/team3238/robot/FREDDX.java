package frc.team3238.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.robot.control.CameraController;
import frc.team3238.robot.control.joystick.Button;
import frc.team3238.robot.control.joystick.JoystickButton;
import frc.team3238.robot.systems.Climber;
import frc.team3238.robot.systems.Manipulator;
import frc.team3238.robot.systems.PodDrive;

import static frc.team3238.robot.FREDDXConstants.*;

public final class FREDDX extends TimedTeleopRobot {
    //Systems
    private Joystick          driveJoystick;
    private Joystick          manipulatorJoystick;
    private CameraController  cameraController;
    private DifferentialDrive drive;
    private Manipulator       manipulator;
    private Climber           climber;

    //Buttons
    private Button cargoLevelOne;
    private Button cargoLevelTwo;
    private Button cargoLevelThree;
    private Button stowCollector;
    private Button collectBall;
    private Button ejectBall;
    private Button spudsUp;
    private Button spudsDown;
    private Button rollerForward;
    private Button breachersOut;
    private Button breachersBack;

    private double  liftSetpoint;
    private double  spudsSetpoint;
    private double  breacherSetpoint;
    private boolean isManipulatorAuto;
    private boolean isDriveAuto;

    @Override
    public void robotInit() {
        //Joysticks
        driveJoystick       = new Joystick(DRIVE_JOYSTICK_PORT);
        manipulatorJoystick = new Joystick(MANIPULATOR_JOYSTICK_PORT);

        //Camera streaming
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(CAMERA_NUMBER);
        camera.setResolution(CAMERA_WIDTH, CAMERA_HEIGHT);
        camera.setFPS(CAMERA_FPS);
        camera.setWhiteBalanceManual(CAMERA_WHITE_BALANCE);
        camera.setExposureManual(CAMERA_EXPOSURE);

        //Initialize systems
        drive            = new PodDrive();
        manipulator      = new Manipulator();
        climber          = new Climber();
        cameraController = new CameraController(manipulatorJoystick);

        //Initialize buttons
        cargoLevelOne   = new JoystickButton(manipulatorJoystick, CARGO_LEVEL_ONE);
        cargoLevelTwo   = new JoystickButton(manipulatorJoystick, CARGO_LEVEL_TWO);
        cargoLevelThree = new JoystickButton(manipulatorJoystick, CARGO_LEVEL_THREE);
        stowCollector   = new JoystickButton(manipulatorJoystick, STOW);
        collectBall     = new JoystickButton(manipulatorJoystick, COLLECT_BALL);
        ejectBall       = new JoystickButton(manipulatorJoystick, EJECT_BALL);
        spudsUp         = new JoystickButton(driveJoystick, SPUDS_UP);
        spudsDown       = new JoystickButton(driveJoystick, SPUDS_DOWN);
        rollerForward   = new JoystickButton(driveJoystick, ROLL_FORWARD);
        breachersOut    = new JoystickButton(driveJoystick, BREACHER_OUT);
        breachersBack   = new JoystickButton(driveJoystick, BREACHER_IN);

        setLiftSetpoint(LIFT_MIN_UP);
        spudsSetpoint     = climber.spuds.getSelectedSensorPosition(0);
        breacherSetpoint  = climber.breacherRight.getSelectedSensorPosition(0);
        isManipulatorAuto = false;
        isDriveAuto       = false;
    }

    @Override
    public void robotPeriodic() {
        //Allow buttons to update
        cargoLevelOne.update();
        cargoLevelTwo.update();
        cargoLevelThree.update();
        stowCollector.update();
        collectBall.update();
        ejectBall.update();
        spudsUp.update();
        spudsDown.update();
        rollerForward.update();
        breachersOut.update();
        breachersBack.update();

        //Manipulator auto-mode switch
        boolean newIsManipulatorAuto = remapThrottle(manipulatorJoystick.getThrottle()) < 0.5;
        if(newIsManipulatorAuto && !isManipulatorAuto)
            liftSetpoint = manipulator.lift.getSelectedSensorPosition();
        isManipulatorAuto = newIsManipulatorAuto;
        isDriveAuto       = remapThrottle(driveJoystick.getThrottle()) < 0.5;

        //SmartDashboard data
        SmartDashboard.putBoolean("Manipulator Auto", isManipulatorAuto);
        SmartDashboard.putBoolean("Drive Auto", isDriveAuto);
        SmartDashboard.putNumber("Lift Encoder", manipulator.lift.getSelectedSensorPosition());
        SmartDashboard.putNumber("Lift Setpoint", liftSetpoint);
        SmartDashboard.putNumber("Spuds Setpoint", spudsSetpoint);
        SmartDashboard.putNumber("Spud Encoder", climber.spuds.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Right Breacher Encoder", climber.breacherRight.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Left Breacher Encoder", climber.breacherLeft.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Breacher Setpoint", breacherSetpoint);
    }

    @Override
    public void enabledBegin() {
        //Only turns on the brakes that are enabled in the configs
        climber.useBrakes();
    }

    @Override
    public void enabledPeriodic() {
        //Update and move camera
        cameraController.updateControls();
        cameraController.move();

        if(isManipulatorAuto) {
            if(stowCollector.isReleased())
                setLiftSetpoint(LIFT_MIN_UP);
            else if(cargoLevelOne.isReleased())
                setLiftSetpoint(LIFT_CARGO_LEVEL_ONE);
            else if(cargoLevelTwo.isReleased())
                setLiftSetpoint(LIFT_CARGO_LEVEL_TWO);
            else if(cargoLevelThree.isReleased())
                setLiftSetpoint(LIFT_CARGO_LEVEL_THREE);

            //Lift
            manipulator.lift.set(ControlMode.Position, liftSetpoint);
        }
        //Lift manual control
        else {
            double manipulatorThrottle = deadbandAdjust(manipulatorJoystick.getY(), LIFTING_DEADBAND);

            //Lift
            manipulator.lift.set(ControlMode.PercentOutput, manipulatorThrottle);
        }

        //Collector
        driveTalonFwdRevOrStop(manipulator.collector, collectBall.isHeld(), ejectBall.isHeld(), COLLECTOR_SPEED);

        //Climber
        if(spudsDown.isHeld() || spudsUp.isHeld()) {
            if(spudsDown.isHeld()) {
                climber.spuds.set(SPUDS_SPEED);
            }
            else {
                climber.spuds.set(-SPUDS_SPEED);
            }
            spudsSetpoint = climber.spuds.getSelectedSensorPosition(0);
        }
        //When driver is not telling the spuds what to do
        else {
            if(isDriveAuto)
                climber.spuds.set(ControlMode.Position, spudsSetpoint);
            else
                climber.spuds.set(ControlMode.PercentOutput, 0);
        }
        if(breachersOut.isHeld() || breachersBack.isHeld()) {
            if(breachersOut.isHeld()) {
                climber.breacherRight.set(-BREACHERS_SPEED);
                climber.breacherLeft.set(-BREACHERS_SPEED);
            }
            //Make it stop
            else {
                climber.breacherRight.set(BREACHERS_SPEED);
                climber.breacherLeft.set(BREACHERS_SPEED);
            }
            breacherSetpoint = climber.breacherRight.getSelectedSensorPosition(0);
        }
        //When driver is not telling the breachers what to do
        else {
            if(isDriveAuto) {
                climber.breacherRight.set(ControlMode.Position, breacherSetpoint);
                climber.breacherLeft.set(ControlMode.Position, breacherSetpoint);
            }
            //Make it stop
            else {
                climber.breacherRight.set(0);
                climber.breacherLeft.set(0);
            }
        }
        driveTalonFwdRevOrStop(climber.roller, rollerForward.isHeld(), false, ROLLER_SPEED);

        //Drive
        double driveThrottle = deadbandAdjust(-driveJoystick.getY(), THROTTLE_DEADBAND);
        double steer         = deadbandAdjust(driveJoystick.getTwist(), STEERING_DEADBAND);
        drive.arcadeDrive(driveThrottle, steer);
    }

    @Override
    public void disabledBegin() {
        climber.disableBrakes();
    }

    private void setLiftSetpoint(double setpoint) {
        if(setpoint < LIFT_MIN_UP)
            liftSetpoint = LIFT_MIN_UP;
        else if(setpoint > LIFT_MAX_UP)
            liftSetpoint = LIFT_MAX_UP;
        else
            liftSetpoint = setpoint;
    }

    private static double remapThrottle(double rawThrottle) {
        return (-rawThrottle + 1) / 2;
    }

    private static void driveTalonFwdRevOrStop(WPI_TalonSRX talon, boolean forward, boolean reverse, double throttle) {
        if(forward) {
            talon.set(ControlMode.PercentOutput, throttle);
        }
        else if(reverse) {
            talon.set(ControlMode.PercentOutput, -throttle);
        }
        else {
            talon.set(ControlMode.PercentOutput, 0);
        }
    }

    /**
     * Takes a raw value as input and applies a deadband to it.
     *
     * @param rawValue [-1, 1] The raw value
     * @param deadband [0, 1) The amount of deadband to use
     * @return The deadband-adjusted value
     */
    private static double deadbandAdjust(double rawValue, double deadband) {
        if(Math.abs(rawValue) < deadband)
            return 0;
        else {
            if(rawValue < 0)
                return (rawValue + deadband) / (1 - deadband);
            else
                return (rawValue - deadband) / (1 - deadband);
        }
    }
}
