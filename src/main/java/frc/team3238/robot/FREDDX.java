package frc.team3238.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.robot.control.CameraController;
import frc.team3238.robot.control.joystick.Button;
import frc.team3238.robot.control.joystick.JoystickButton;
import frc.team3238.robot.systems.Climber;
import frc.team3238.robot.systems.Manipulator;
import frc.team3238.robot.systems.PodDrive;

import static frc.team3238.robot.FREDDXConstants.*;

public final class FREDDX extends TimedRobot {
    //Systems
    private Joystick          driveJoystick;
    private Joystick          manipulatorJoystick;
    private CameraController  cameraController;
    private DifferentialDrive drive;
    private Manipulator       manipulator;
    private Climber           climber;

    //Buttons
    private Button wristUp;
    private Button wristDown;
    private Button beakExtend;
    private Button beakRetract;
    private Button hatchLevelOne;
    private Button hatchLevelTwo;
    private Button hatchLevelThree;
    private Button cargoLevelOne;
    private Button cargoLevelTwo;
    private Button cargoLevelThree;
    private Button stowCollector;
    private Button spudsUp;
    private Button spudsDown;
    private Button rollerForward;
    private Button breachersOut;
    private Button breachersBack;

    private double  liftSetpoint;
    private double  spudsSetpoint;
    private boolean isManipulatorAuto;

    //RIO Sensors
    private Potentiometer liftPot;

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
        wristUp         = new JoystickButton(manipulatorJoystick, WRIST_UP);
        wristDown       = new JoystickButton(manipulatorJoystick, WRIST_DOWN);
        beakExtend      = new JoystickButton(manipulatorJoystick, BEAK_OPEN);
        beakRetract     = new JoystickButton(manipulatorJoystick, BEAK_CLOSE);
        hatchLevelOne   = new JoystickButton(manipulatorJoystick, HATCH_LEVEL_ONE);
        hatchLevelTwo   = new JoystickButton(manipulatorJoystick, HATCH_LEVEL_TWO);
        hatchLevelThree = new JoystickButton(manipulatorJoystick, HATCH_LEVEL_THREE);
        cargoLevelOne   = new JoystickButton(manipulatorJoystick, CARGO_LEVEL_ONE);
        cargoLevelTwo   = new JoystickButton(manipulatorJoystick, CARGO_LEVEL_TWO);
        cargoLevelThree = new JoystickButton(manipulatorJoystick, CARGO_LEVEL_THREE);
        stowCollector   = new JoystickButton(manipulatorJoystick, STOW);
        spudsUp         = new JoystickButton(driveJoystick, SPUDS_UP);
        spudsDown       = new JoystickButton(driveJoystick, SPUDS_DOWN);
        rollerForward   = new JoystickButton(driveJoystick, ROLL_FORWARD);
        breachersOut    = new JoystickButton(driveJoystick, BREACHER_OUT);
        breachersBack   = new JoystickButton(driveJoystick, BREACHER_IN);

        setLiftSetpoint(LIFT_MIN_UP);
        spudsSetpoint     = climber.spuds.getSelectedSensorPosition(0);
        isManipulatorAuto = false;

        //Sensors
        liftPot = new AnalogPotentiometer(LIFT_POTENTIOMETER_CHANNEL);
    }

    @Override
    public void robotPeriodic() {
        //Allow buttons to update
        wristUp.update();
        wristDown.update();
        beakExtend.update();
        beakRetract.update();
        hatchLevelOne.update();
        hatchLevelTwo.update();
        hatchLevelThree.update();
        cargoLevelOne.update();
        cargoLevelTwo.update();
        cargoLevelThree.update();
        stowCollector.update();
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

        //SmartDashboard data
        SmartDashboard.putBoolean("Manipulator Auto", isManipulatorAuto);
        SmartDashboard.putNumber("Lift Potentiometer", liftPot.get());
        SmartDashboard.putNumber("Lift Encoder", manipulator.lift.getSelectedSensorPosition());
        SmartDashboard.putNumber("Lift Setpoint", liftSetpoint);
        SmartDashboard.putNumber("Spuds Setpoint", spudsSetpoint);
        SmartDashboard.putNumber("Wrist Potentiometer", manipulator.wrist.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Spud Encoder", climber.spuds.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Breacher Encoder", climber.breacherMaster.getSelectedSensorPosition(0));
    }

    @Override
    public void teleopPeriodic() {
        //Update and move camera
        cameraController.updateControls();
        cameraController.move();

        if(isManipulatorAuto) {
            if(stowCollector.isReleased())
                setLiftSetpoint(LIFT_MIN_UP);
            else if(hatchLevelOne.isReleased())
                setLiftSetpoint(LIFT_HATCH_LEVEL_ONE);
            else if(hatchLevelTwo.isReleased())
                setLiftSetpoint(LIFT_HATCH_LEVEL_TWO);
            else if(hatchLevelThree.isReleased())
                setLiftSetpoint(LIFT_HATCH_LEVEL_THREE);
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
        driveTalonFwdRevOrStop(manipulator.wrist, wristUp.isHeld(), wristDown.isHeld(), WRIST_SPEED);
        driveTalonFwdRevOrStop(manipulator.beak, beakRetract.isHeld(), beakExtend.isHeld(), BEAK_SPEED);

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
        driveTalonFwdRevOrStop(climber.breacherMaster, breachersOut.isHeld(), breachersBack.isHeld(), BREACHERS_SPEED);
        driveTalonFwdRevOrStop(climber.roller, rollerForward.isHeld(), false, ROLLER_SPEED);

        //Drive
        double driveThrottle = deadbandAdjust(-driveJoystick.getY(), THROTTLE_DEADBAND);
        double steer         = deadbandAdjust(driveJoystick.getTwist(), STEERING_DEADBAND);
        drive.arcadeDrive(driveThrottle, steer);
    }

    @Override
    public void autonomousPeriodic() {
        teleopPeriodic();
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
