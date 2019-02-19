package frc.team3238.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team3238.robot.control.FREDDXControlScheme;
import frc.team3238.robot.control.SwitchableControls;

import static frc.team3238.robot.FREDDXConstants.*;


public final class FREDDX extends TimedRobot {

    private Joystick            driveJoystick;
    private Joystick            manipulatorJoystick;
    private FREDDXControlScheme controller;
    private DifferentialDrive   drive;
    private WPI_TalonSRX        spudsTalon;
    private WPI_TalonSRX        rollerTalon;
    private WPI_TalonSRX        breacherMasterTalon;
    private WPI_TalonSRX        liftTalon;
    private WPI_TalonSRX        wristTalon;
    private WPI_TalonSRX        beakTalon;
    private Servo               cameraPanServo;
    private Servo               cameraTiltServo;

    //FredX Control Loop Methods -------------------------------------------------------------

    @Override
    public void robotInit() {
        //Initialize controls
        driveJoystick      = new Joystick(DRIVE_JOYSTICK_PORT);
        manipulatorJoystick = new Joystick(MANIPULATOR_JOYSTICK_PORT);

        //Initialize talons
        WPI_TalonSRX driveLeftMasterTalon  = new WPI_TalonSRX(DRIVE_LEFT_MASTER_NUM);
        WPI_TalonSRX driveLeftSlaveTalon   = new WPI_TalonSRX(DRIVE_LEFT_SLAVE_NUM);
        WPI_TalonSRX driveRightMasterTalon = new WPI_TalonSRX(DRIVE_RIGHT_MASTER_NUM);
        WPI_TalonSRX driveRightSlaveTalon  = new WPI_TalonSRX(DRIVE_RIGHT_SLAVE_NUM);
        WPI_TalonSRX breacherSlaveTalon    = new WPI_TalonSRX(BREACHER_SLAVE_NUM);
        spudsTalon          = new WPI_TalonSRX(SPUDS_NUM);
        rollerTalon         = new WPI_TalonSRX(ROLLER_NUM);
        breacherMasterTalon = new WPI_TalonSRX(BREACHER_MASTER_NUM);
        liftTalon           = new WPI_TalonSRX(LIFT_NUM);
        wristTalon          = new WPI_TalonSRX(WRIST_NUM);
        beakTalon           = new WPI_TalonSRX(BEAK_NUM);

        //Initialize servos
        cameraPanServo  = new Servo(CAMERA_PAN_CHANNEL);
        cameraTiltServo = new Servo(CAMERA_TILT_CHANNEL);

        //Apply talon reversals
        driveLeftMasterTalon.setInverted(REVERSE_DRIVE);
        driveLeftSlaveTalon.setInverted(REVERSE_DRIVE);
        driveRightMasterTalon.setInverted(REVERSE_DRIVE);
        driveRightSlaveTalon.setInverted(REVERSE_DRIVE);
        spudsTalon.setInverted(REVERSE_SPUDS);
        rollerTalon.setInverted(REVERSE_ROLLER);
        breacherMasterTalon.setInverted(REVERSE_BREACHER);
        breacherSlaveTalon.setInverted(REVERSE_BREACHER);

        liftTalon.setInverted(REVERSE_LIFT);
        wristTalon.setInverted(REVERSE_WRIST);
        beakTalon.setInverted(REVERSE_BEAK);

        //Configure talon brake state
        driveLeftMasterTalon.setNeutralMode(DRIVE_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        driveLeftSlaveTalon.setNeutralMode(DRIVE_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        driveRightMasterTalon.setNeutralMode(DRIVE_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        driveRightSlaveTalon.setNeutralMode(DRIVE_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        spudsTalon.setNeutralMode(SPUDS_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        rollerTalon.setNeutralMode(ROLLER_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        breacherMasterTalon.setNeutralMode(BREACHER_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        breacherSlaveTalon.setNeutralMode(BREACHER_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        liftTalon.setNeutralMode(LIFT_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        wristTalon.setNeutralMode(WRIST_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        beakTalon.setNeutralMode(BEAK_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);

        //Pair up talons
        driveLeftSlaveTalon.follow(driveLeftMasterTalon, FollowerType.PercentOutput);
        driveRightSlaveTalon.follow(driveRightMasterTalon, FollowerType.PercentOutput);
        breacherSlaveTalon.follow(breacherMasterTalon, FollowerType.PercentOutput);
        breacherSlaveTalon.setInverted(InvertType.OpposeMaster);

        //Configure feedback sensors
        spudsTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
        breacherMasterTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
        liftTalon.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TALON_TIMEOUT);
        wristTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);

        //Set sensor phase (used to make sensor negative when motion is negative)
        spudsTalon.setSensorPhase(FLIP_SPUD_SENSOR);
        breacherMasterTalon.setSensorPhase(FLIP_BREACHER_SENSOR);
        liftTalon.setSensorPhase(FLIP_LIFT_SENSOR);
        wristTalon.setSensorPhase(FLIP_WRIST_SENSOR);

        //Set target velocities
        spudsTalon.configMotionCruiseVelocity(SPUDS_VELOCITY, TALON_TIMEOUT);
        breacherMasterTalon.configMotionCruiseVelocity(BREACHER_VELOCITY, TALON_TIMEOUT);
        liftTalon.configMotionCruiseVelocity(LIFT_VELOCITY, TALON_TIMEOUT);
        wristTalon.configMotionCruiseVelocity(WRIST_VELOCITY, TALON_TIMEOUT);

        //Set target accelerations
        spudsTalon.configMotionAcceleration(SPUDS_ACCELERATION, TALON_TIMEOUT);
        breacherMasterTalon.configMotionAcceleration(BREACHER_ACCELERATION, TALON_TIMEOUT);
        liftTalon.configMotionAcceleration(LIFT_ACCELERATION, TALON_TIMEOUT);
        wristTalon.configMotionAcceleration(WRIST_ACCELERATION, TALON_TIMEOUT);

        //Putting in PID constants
        spudsTalon.config_kP(0, SPUDS_kP, TALON_TIMEOUT);
        spudsTalon.config_kI(0, SPUDS_kI, TALON_TIMEOUT);
        spudsTalon.config_kD(0, SPUDS_kD, TALON_TIMEOUT);
        breacherMasterTalon.config_kP(0, BREACHER_kP, TALON_TIMEOUT);
        breacherMasterTalon.config_kI(0, BREACHER_kI, TALON_TIMEOUT);
        breacherMasterTalon.config_kD(0, BREACHER_kD, TALON_TIMEOUT);
        liftTalon.config_kP(0, LIFT_kP, TALON_TIMEOUT);
        liftTalon.config_kI(0, LIFT_kI, TALON_TIMEOUT);
        liftTalon.config_kD(0, LIFT_kD, TALON_TIMEOUT);
        wristTalon.config_kP(0, WRIST_kP, TALON_TIMEOUT);
        wristTalon.config_kI(0, WRIST_kI, TALON_TIMEOUT);
        wristTalon.config_kD(0, WRIST_kD, TALON_TIMEOUT);

        //Initialize drive
        drive = new DifferentialDrive(driveLeftMasterTalon, driveRightMasterTalon);
        drive.setDeadband(0);
        drive.setSafetyEnabled(true);

        //Initialize controller
        controller = new SwitchableControls(this);
    }

    @Override
    public void robotPeriodic() {
        controller.updateControls();
    }

    @Override
    public void teleopPeriodic() {
        controller.teleopPeriodic();
    }

    @Override
    public void autonomousPeriodic() {
        teleopPeriodic();
    }

    // Getters ---------------------------------------------------------------------------------------------------------

    public Joystick getDriverJoystick() {
        return driveJoystick;
    }

    public Joystick getManipulatorJoystick() {
        return manipulatorJoystick;
    }

    public DifferentialDrive getDrive() {
        return drive;
    }

    public WPI_TalonSRX getBreacher() {
        return breacherMasterTalon;
    }

    public WPI_TalonSRX getSpuds() {
        return spudsTalon;
    }

    public WPI_TalonSRX getRoller() {
        return rollerTalon;
    }

    public WPI_TalonSRX getLift() {
        return liftTalon;
    }

    public WPI_TalonSRX getWrist() {
        return wristTalon;
    }

    public WPI_TalonSRX getBeak() {
        return beakTalon;
    }

    public Servo getCameraPanServo() {
        return cameraPanServo;
    }

    public Servo getCameraTiltServo() {
        return cameraTiltServo;
    }
}
