package frc.team3238.robot;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team3238.robot.motion.PanTiltMount;
import frc.team3238.robot.motion.ServoPTMount;

import static frc.team3238.robot.FredXConstants.*;


public final class FredX extends TimedRobot {

    //FredX Systems --------------------------------------------------------------------------
    private FredXControls     controls;
    private DifferentialDrive drive;
    private PanTiltMount      cameraMount;

    //Talons ---------------------------------------------------------------------------------
    private WPI_TalonSRX leftMasterDriveTalon;
    private WPI_TalonSRX rightMasterDriveTalon;
    private WPI_TalonSRX leftSlaveDriveTalon;
    private WPI_TalonSRX rightSlaveDriveTalon;
    private WPI_TalonSRX spudDriveTalon;
    private WPI_TalonSRX spudRollerTalon;
    private WPI_TalonSRX armMasterTalon;
    private WPI_TalonSRX armSlaveTalon;
    private WPI_TalonSRX liftActuatorTalon;

    //FredX Control Loop Methods -------------------------------------------------------------

    @Override
    public void robotInit() {
        //Initialize the controls
        controls = new FredXControls();

        //Initialize all the Talons
        leftMasterDriveTalon  = new WPI_TalonSRX(LEFT_MASTER_DRIVE_NUM);
        rightMasterDriveTalon = new WPI_TalonSRX(RIGHT_MASTER_DRIVE_NUM);
        leftSlaveDriveTalon   = new WPI_TalonSRX(LEFT_SLAVE_DRIVE_NUM);
        rightSlaveDriveTalon  = new WPI_TalonSRX(RIGHT_SLAVE_DRIVE_NUM);
        spudDriveTalon        = new WPI_TalonSRX(SPUD_DRIVE_NUM);
        spudRollerTalon       = new WPI_TalonSRX(SPUD_ROLLER_NUM);
        armMasterTalon        = new WPI_TalonSRX(ARM_MASTER_NUM);
        armSlaveTalon         = new WPI_TalonSRX(ARM_SLAVE_NUM);
        liftActuatorTalon     = new WPI_TalonSRX(LIFT_ACTUATOR_NUM);


        // Configure all the Talon Directions
        leftMasterDriveTalon.setInverted(REVERSE_DRIVE_TALONS);
        rightMasterDriveTalon.setInverted(REVERSE_DRIVE_TALONS);
        leftSlaveDriveTalon.setInverted(REVERSE_DRIVE_TALONS);
        rightSlaveDriveTalon.setInverted(REVERSE_DRIVE_TALONS);
        spudDriveTalon.setInverted(REVERSE_SPUD_DRIVE_TALON);
        spudRollerTalon.setInverted(REVERSE_SPUD_ROLLER_TALON);
        armMasterTalon.setInverted(REVERSE_ARM_TALONS);
        armSlaveTalon.setInverted(REVERSE_ARM_TALONS);
        liftActuatorTalon.setInverted(REVERSE_ARM_ACTUATOR);


        //Pairing up Talons
        leftSlaveDriveTalon.follow(leftMasterDriveTalon, FollowerType.PercentOutput);
        rightSlaveDriveTalon.follow(rightMasterDriveTalon, FollowerType.PercentOutput);
        armSlaveTalon.follow(armMasterTalon, FollowerType.PercentOutput);


        //Setup camera mount
        cameraMount = new ServoPTMount(CAMERA_PAN_CHANNEL, CAMERA_TILT_CHANNEL, CAMERA_SPEED, CAMERA_SPEED,
                                       DEFAULT_CAMERA_PAN, DEFAULT_CAMERA_TILT, CAMERA_MIN_PAN, CAMERA_MAX_PAN,
                                       CAMERA_MIN_TILT, CAMERA_MAX_TILT);


        //Setting up the DifferentialDrive
        drive = new DifferentialDrive(leftMasterDriveTalon, rightMasterDriveTalon);
        drive.setSafetyEnabled(true);
    }

    @Override
    public void robotPeriodic() {
        controls.updateControls(); //Polls for new control state
    }

    @Override
    public void teleopPeriodic() {
        drive.arcadeDrive(controls.getThrottle(), controls.getSteer());

        if(controls.safetyIsOff()) {
            //Move the spuds
            if(controls.spudsShouldGoDown()) {
                spudDriveTalon.set(SPUD_SPEED);
            }
            else if(controls.spudsShouldGoUp()) {
                spudDriveTalon.set(-SPUD_SPEED);
            }
            else {
                spudDriveTalon.set(0); //Spuds stop
            }

            //Move the spud roller
            if(controls.spudsShouldRollForward()) {
                spudRollerTalon.set(SPUD_ROLLER_SPEED);
            }
            else if(controls.spudsShouldRollBack()) {
                spudRollerTalon.set(-SPUD_ROLLER_SPEED);
            }
            else {
                spudRollerTalon.set(0); //Stop rolling
            }
        }
        else {
            //Stop all safety enabled components
            spudDriveTalon.set(0);
            spudRollerTalon.set(0);
        }

        //Camera Pan Update
        if(controls.cameraShouldPanRight()) {
            cameraMount.panRight();
        }
        else if(controls.cameraShouldPanLeft()) {
            cameraMount.panLeft();
        }

        //Camera Tilt Update
        if(controls.cameraShouldTiltUp()) {
            cameraMount.tiltUp();
        }
        else if(controls.cameraShouldTiltDown()) {
            cameraMount.tiltDown();
        }
    }
}
