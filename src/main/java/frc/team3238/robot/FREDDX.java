package frc.team3238.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team3238.robot.control.CameraController;
import frc.team3238.robot.control.FREDDXControlScheme;
import frc.team3238.robot.control.SwitchableControls;
import frc.team3238.robot.systems.PodDrive;

import static frc.team3238.robot.FREDDXConstants.*;

public final class FREDDX extends TimedRobot {

    private static final FREDDX robot = new FREDDX();

    private Joystick            driveJoystick;
    private Joystick            manipulatorJoystick;
    private CameraController    cameraController;
    private FREDDXControlScheme robotController;
    private DifferentialDrive   drive;
    private WPI_TalonSRX        spudsTalon;
    private WPI_TalonSRX        rollerTalon;
    private WPI_TalonSRX        breacherMasterTalon;
    private WPI_TalonSRX        liftTalon;
    private WPI_TalonSRX        wristTalon;
    private WPI_TalonSRX        beakTalon;

    private FREDDX() {

    }

    @Override
    public void robotInit() {
        //Initialize controls
        driveJoystick       = new Joystick(DRIVE_JOYSTICK_PORT);
        manipulatorJoystick = new Joystick(MANIPULATOR_JOYSTICK_PORT);

        //Initialize talons
        WPI_TalonSRX breacherSlaveTalon = new WPI_TalonSRX(BREACHER_LEFT_ID);
        spudsTalon          = new WPI_TalonSRX(SPUDS_ID);
        rollerTalon         = new WPI_TalonSRX(ROLLER_ID);
        breacherMasterTalon = new WPI_TalonSRX(BREACHER_RIGHT_ID);
        liftTalon           = new WPI_TalonSRX(LIFT_ID);
        wristTalon          = new WPI_TalonSRX(WRIST_ID);
        beakTalon           = new WPI_TalonSRX(BEAK_ID);

        //Apply talon reversals
        spudsTalon.setInverted(REVERSE_SPUDS);
        rollerTalon.setInverted(REVERSE_ROLLER);
        breacherMasterTalon.setInverted(REVERSE_BREACHER);
        breacherSlaveTalon.setInverted(REVERSE_BREACHER);
        liftTalon.setInverted(REVERSE_LIFT);
        wristTalon.setInverted(REVERSE_WRIST);
        beakTalon.setInverted(REVERSE_BEAK);

        //Configure talon brake state
        spudsTalon.setNeutralMode(SPUDS_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        rollerTalon.setNeutralMode(ROLLER_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        breacherMasterTalon.setNeutralMode(BREACHER_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        breacherSlaveTalon.setNeutralMode(BREACHER_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        liftTalon.setNeutralMode(LIFT_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        wristTalon.setNeutralMode(WRIST_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        beakTalon.setNeutralMode(BEAK_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);

        //Pair up talons
        breacherSlaveTalon.follow(breacherMasterTalon, FollowerType.PercentOutput);
        breacherSlaveTalon.setInverted(InvertType.OpposeMaster);

        //Configure feedback sensors
        spudsTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
        breacherMasterTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
        liftTalon.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TALON_TIMEOUT);
        wristTalon.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TALON_TIMEOUT);

        wristTalon.configAllowableClosedloopError(0, 5);
        liftTalon.configAllowableClosedloopError(0, 5);

        //Set sensor phase (used to make sensor negative when motion is negative)
        spudsTalon.setSensorPhase(FLIP_SPUD_SENSOR);
        breacherMasterTalon.setSensorPhase(FLIP_BREACHER_SENSOR);
        liftTalon.setSensorPhase(FLIP_LIFT_SENSOR);
        wristTalon.setSensorPhase(FLIP_WRIST_SENSOR);

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
        drive = new PodDrive(DRIVE_LEFT_MASTER_ID, DRIVE_LEFT_SLAVE_ID, DRIVE_RIGHT_MASTER_ID, DRIVE_RIGHT_SLAVE_ID);
        drive.setDeadband(0);
        drive.setSafetyEnabled(true);

        //Initialize controllers
        cameraController = new CameraController(manipulatorJoystick);
        robotController  = new SwitchableControls(this);
    }

    @Override
    public void robotPeriodic() {
        robotController.updateControls();
    }

    @Override
    public void teleopPeriodic() {
        //Update and move camera
        cameraController.updateControls();
        cameraController.move();

        //Move robot
        robotController.manualPeriodic();
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

    public static FREDDX getRobot() {
        return robot;
    }
}
