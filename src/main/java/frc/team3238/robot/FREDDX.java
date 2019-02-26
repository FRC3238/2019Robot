package frc.team3238.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team3238.robot.control.CameraController;
import frc.team3238.robot.control.FREDDXControlScheme;
import frc.team3238.robot.control.SwitchableControls;
import frc.team3238.robot.systems.Climber;
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
    private Climber             climber;

    private FREDDX() {

    }

    @Override
    public void robotInit() {
        //Initialize user controls
        driveJoystick       = new Joystick(DRIVE_JOYSTICK_PORT);
        manipulatorJoystick = new Joystick(MANIPULATOR_JOYSTICK_PORT);

        //Camera streaming
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(CAMERA_NUMBER);
        camera.setResolution(CAMERA_WIDTH, CAMERA_HEIGHT);
        camera.setFPS(CAMERA_FPS);
        camera.setWhiteBalanceManual(CAMERA_WHITE_BALANCE);
        camera.setExposureManual(CAMERA_EXPOSURE);

        //Initialize systems
        drive = new PodDrive();
        manipulator = new Manipulator();
        climber     = new Climber();

        //Initialize controllers
        cameraController = new CameraController(manipulatorJoystick);
        robotController  = new SwitchableControls();
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
        return climber.breacherMaster;
    }

    public WPI_TalonSRX getSpuds() {
        return climber.spuds;
    }

    public WPI_TalonSRX getRoller() {
        return climber.roller;
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
