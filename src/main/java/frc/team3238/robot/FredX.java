package frc.team3238.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team3238.robot.settings.talons.BreacherSettings;
import frc.team3238.robot.settings.talons.DriveSettings;

import static frc.team3238.robot.settings.FredXConstants.*;


public final class FredX extends TimedRobot {

    //FredX Systems --------------------------------------------------------------------------
    private FredXControls     controls;
    private DifferentialDrive drive;

    //Talons ---------------------------------------------------------------------------------
    private WPI_TalonSRX driveLeftMasterTalon;
    private WPI_TalonSRX driveLeftSlaveTalon;
    private WPI_TalonSRX driveRightMasterTalon;
    private WPI_TalonSRX driveRightSlaveTalon;
    private WPI_TalonSRX spudsTalon;
    private WPI_TalonSRX rollerTalon;
    private WPI_TalonSRX breacherMasterTalon;
    private WPI_TalonSRX breacherSlaveTalon;
    private WPI_TalonSRX liftTalon;
    private WPI_TalonSRX wristTalon;
    private WPI_TalonSRX beakTalon;

    //Servos ---------------------------------------------------------------------------------
    private Servo cameraPanServo;
    private Servo cameraTiltServo;

    //FredX Control Loop Methods -------------------------------------------------------------

    @Override
    public void robotInit() {
        //Initialize controls
        controls = new FredXControls();

        //Initialize talons
        driveLeftMasterTalon  = new WPI_TalonSRX(DriveSettings.LEFT_MASTER_ID);
        driveLeftSlaveTalon   = new WPI_TalonSRX(DriveSettings.LEFT_SLAVE_ID);
        driveRightMasterTalon = new WPI_TalonSRX(DriveSettings.RIGHT_MASTER_ID);
        driveRightSlaveTalon  = new WPI_TalonSRX(DriveSettings.RIGHT_SLAVE_ID);
        spudsTalon            = new WPI_TalonSRX(SPUDS_NUM);
        rollerTalon           = new WPI_TalonSRX(ROLLER_NUM);
        breacherMasterTalon   = new WPI_TalonSRX(BreacherSettings.RIGHT_ID);
        breacherSlaveTalon    = new WPI_TalonSRX(BreacherSettings.LEFT_ID);
        liftTalon             = new WPI_TalonSRX(LIFT_NUM);
        wristTalon            = new WPI_TalonSRX(WRIST_NUM);
        beakTalon             = new WPI_TalonSRX(BEAK_NUM);

        //Initialize servos
        cameraPanServo  = new Servo(CAMERA_PAN_CHANNEL);
        cameraTiltServo = new Servo(CAMERA_TILT_CHANNEL);

        //Apply talon reversals
        driveLeftMasterTalon.setInverted(DriveSettings.REVERSED);
        driveLeftSlaveTalon.setInverted(DriveSettings.REVERSED);
        driveRightMasterTalon.setInverted(DriveSettings.REVERSED);
        driveRightSlaveTalon.setInverted(DriveSettings.REVERSED);
        spudsTalon.setInverted(REVERSE_SPUDS);
        rollerTalon.setInverted(REVERSE_ROLLER);
        breacherMasterTalon.setInverted(BreacherSettings.REVERSED);
        breacherSlaveTalon.setInverted(!BreacherSettings.REVERSED);
        liftTalon.setInverted(REVERSE_LIFT);
        wristTalon.setInverted(REVERSE_WRIST);
        beakTalon.setInverted(REVERSE_BEAK);

        //Configure talon brake state
        driveLeftMasterTalon.setNeutralMode(DriveSettings.BRAKE_IN_NEUTRAL ? NeutralMode.Brake : NeutralMode.Coast);
        driveLeftSlaveTalon.setNeutralMode(DriveSettings.BRAKE_IN_NEUTRAL ? NeutralMode.Brake : NeutralMode.Coast);
        driveRightMasterTalon.setNeutralMode(DriveSettings.BRAKE_IN_NEUTRAL ? NeutralMode.Brake : NeutralMode.Coast);
        driveRightSlaveTalon.setNeutralMode(DriveSettings.BRAKE_IN_NEUTRAL ? NeutralMode.Brake : NeutralMode.Coast);
        spudsTalon.setNeutralMode(SPUDS_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        rollerTalon.setNeutralMode(ROLLER_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        breacherMasterTalon.setNeutralMode(BreacherSettings.BRAKES_IN_NEUTRAL ? NeutralMode.Brake : NeutralMode.Coast);
        breacherSlaveTalon.setNeutralMode(BreacherSettings.BRAKES_IN_NEUTRAL ? NeutralMode.Brake : NeutralMode.Coast);
        liftTalon.setNeutralMode(LIFT_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        wristTalon.setNeutralMode(WRIST_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        beakTalon.setNeutralMode(BEAK_NEUTRAL_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);

        //Pair up talons
        driveLeftSlaveTalon.follow(driveLeftMasterTalon, FollowerType.PercentOutput);
        driveRightSlaveTalon.follow(driveRightMasterTalon, FollowerType.PercentOutput);
        breacherSlaveTalon.follow(breacherMasterTalon, FollowerType.PercentOutput);

        //Configure feedback sensors
        spudsTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
        breacherMasterTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
        liftTalon.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TALON_TIMEOUT);
        wristTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);

        //Set sensor phase (used to make sensor negative when motion is negative)
        spudsTalon.setSensorPhase(FLIP_SPUD_SENSOR);
        breacherMasterTalon.setSensorPhase(BreacherSettings.FLIP_SENSOR);
        liftTalon.setSensorPhase(FLIP_LIFT_SENSOR);
        wristTalon.setSensorPhase(FLIP_WRIST_SENSOR);

        //Set target velocities
        spudsTalon.configMotionCruiseVelocity(SPUDS_VELOCITY, TALON_TIMEOUT);
        breacherMasterTalon.configMotionCruiseVelocity(BreacherSettings.cruiseVelocity, TALON_TIMEOUT);
        liftTalon.configMotionCruiseVelocity(LIFT_VELOCITY, TALON_TIMEOUT);
        wristTalon.configMotionCruiseVelocity(WRIST_VELOCITY, TALON_TIMEOUT);

        //Set target accelerations
        spudsTalon.configMotionAcceleration(SPUDS_ACCELERATION, TALON_TIMEOUT);
        breacherMasterTalon.configMotionAcceleration(BreacherSettings.acceleration, TALON_TIMEOUT);
        liftTalon.configMotionAcceleration(LIFT_ACCELERATION, TALON_TIMEOUT);
        wristTalon.configMotionAcceleration(WRIST_ACCELERATION, TALON_TIMEOUT);

        //Putting in PID-F constants
        spudsTalon.config_kP(0, SPUDS_kP, TALON_TIMEOUT);
        spudsTalon.config_kI(0, SPUDS_kI, TALON_TIMEOUT);
        spudsTalon.config_kD(0, SPUDS_kD, TALON_TIMEOUT);
        breacherMasterTalon.config_kP(0, BreacherSettings.kP, TALON_TIMEOUT);
        breacherMasterTalon.config_kI(0, BreacherSettings.kI, TALON_TIMEOUT);
        breacherMasterTalon.config_kD(0, BreacherSettings.kD, TALON_TIMEOUT);
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
    }

    @Override
    public void robotPeriodic() {
        controls.updateControls(); //Polls for new control state
    }

    @Override
    public void teleopPeriodic() {
        //Setting throttles
        drive.arcadeDrive(controls.getDriveThrottle(), controls.getSteer());
        rollerTalon.set(ControlMode.PercentOutput, controls.getRollerThrottle());

        //Setting positions
        breacherMasterTalon.set(ControlMode.MotionMagic, controls.getBreacherExtension());
        spudsTalon.set(ControlMode.MotionMagic, controls.getSpudsExtension());
        liftTalon.set(ControlMode.MotionMagic, controls.getLiftExtension());
        wristTalon.set(ControlMode.MotionMagic, controls.getWristExtension());

        //Camera position update
        cameraPanServo.setAngle(controls.getCameraPanAngle());
        cameraTiltServo.setAngle(controls.getCameraTiltAngle());
    }

    @Override
    public void autonomousPeriodic() {
        teleopPeriodic();
    }
}
