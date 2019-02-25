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
import frc.team3238.robot.systems.Manipulator;
import frc.team3238.robot.systems.PodDrive;

import static frc.team3238.robot.FREDDXConstants.*;

public final class FREDDX extends TimedRobot {

    private static final FREDDX robot = new FREDDX();

    private Joystick            driveJoystick;
    private Joystick            manipulatorJoystick;
    private CameraController    cameraController;
    private FREDDXControlScheme robotController;
    private DifferentialDrive   drive;
    private Manipulator         manipulator;
    private WPI_TalonSRX        spudsTalon;
    private WPI_TalonSRX        rollerTalon;
    private WPI_TalonSRX        breacherMasterTalon;

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

        //Apply talon reversals
        spudsTalon.setInverted(REVERSE_SPUDS);
        rollerTalon.setInverted(REVERSE_ROLLER);
        breacherMasterTalon.setInverted(REVERSE_BREACHER);
        breacherSlaveTalon.setInverted(REVERSE_BREACHER);

        //Configure talon brake state
        spudsTalon.setNeutralMode(SPUDS_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        rollerTalon.setNeutralMode(ROLLER_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        breacherMasterTalon.setNeutralMode(BREACHER_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        breacherSlaveTalon.setNeutralMode(BREACHER_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);

        //Pair up talons
        breacherSlaveTalon.follow(breacherMasterTalon, FollowerType.PercentOutput);
        breacherSlaveTalon.setInverted(InvertType.OpposeMaster);

        //Configure feedback sensors
        spudsTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
        breacherMasterTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);

        //Set sensor phase (used to make sensor negative when motion is negative)
        spudsTalon.setSensorPhase(FLIP_SPUD_SENSOR);
        breacherMasterTalon.setSensorPhase(FLIP_BREACHER_SENSOR);

        //Putting in PID constants
        spudsTalon.config_kP(0, SPUDS_kP, TALON_TIMEOUT);
        spudsTalon.config_kI(0, SPUDS_kI, TALON_TIMEOUT);
        spudsTalon.config_kD(0, SPUDS_kD, TALON_TIMEOUT);
        breacherMasterTalon.config_kP(0, BREACHER_kP, TALON_TIMEOUT);
        breacherMasterTalon.config_kI(0, BREACHER_kI, TALON_TIMEOUT);
        breacherMasterTalon.config_kD(0, BREACHER_kD, TALON_TIMEOUT);

        //Initialize drive
        drive = new PodDrive(DRIVE_LEFT_MASTER_ID, DRIVE_LEFT_SLAVE_ID, DRIVE_RIGHT_MASTER_ID, DRIVE_RIGHT_SLAVE_ID);
        drive.setDeadband(0);
        drive.setSafetyEnabled(true);

        //Initialize manipulator
        manipulator = new Manipulator();

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
        return manipulator.lift;
    }

    public WPI_TalonSRX getWrist() {
        return manipulator.wrist;
    }

    public WPI_TalonSRX getBeak() {
        return manipulator.beak;
    }

    public static FREDDX getRobot() {
        return robot;
    }
}
