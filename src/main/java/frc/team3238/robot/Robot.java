package frc.team3238.robot;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Loren
 */
public class Robot extends TimedRobot {

//Joystick Constants ---------------------------------------------------------------------

    private static final int DRIVE_JOYSTICK_PORT         = 0;

    private static final int MANIPULATION_JOYSTICK_PORT  = 1;

//Talon SRX Constants --------------------------------------------------------------------

    private static final int  LEFT_MASTER_DRIVE_NUM      = 0;

    private static final int RIGHT_MASTER_DRIVE_NUM      = 1;

    private static final int   LEFT_SLAVE_DRIVE_NUM      = 2;

    private static final int  RIGHT_SLAVE_DRIVE_NUM      = 3;

    private static final int         SPUD_DRIVE_NUM      = 4;

    private static final int         SPUD_ROLLER_NUM     = 5;

    private static final int          ARM_MASTER_NUM     = 6;

    private static final int           ARM_SLAVE_NUM     = 7;

    private static final int        LIFT_ACTUATOR_NUM    = 8;

    private static final boolean REVERSE_DRIVE_TALONS       = false;

    private static final boolean REVERSE_ARM_TALONS         = false;

    private static final boolean REVERSE_SPUD_DRIVE_TALON   = false;

    private static final boolean REVERSE_SPUD_ROLLER_TALON  = false;

    private static final boolean REVERSE_ARM_ACTUATOR       = false;

//Joysticks ------------------------------------------------------------------------------

    private Joystick driveJoystick;

    private Joystick manipulatorJoystick;

//Drive ----------------------------------------------------------------------------------

    private DifferentialDrive drive;

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

//Robot Control Loop Methods -------------------------------------------------------------

    @Override
    public void robotInit() {

        //Initialize all the Talons ------------------------------------------

        leftMasterDriveTalon   = new WPI_TalonSRX(LEFT_MASTER_DRIVE_NUM);
        rightMasterDriveTalon  = new WPI_TalonSRX(RIGHT_MASTER_DRIVE_NUM);
        leftSlaveDriveTalon    = new WPI_TalonSRX(LEFT_SLAVE_DRIVE_NUM);
        rightSlaveDriveTalon   = new WPI_TalonSRX(RIGHT_SLAVE_DRIVE_NUM);
        spudDriveTalon         = new WPI_TalonSRX(SPUD_DRIVE_NUM);
        spudRollerTalon        = new WPI_TalonSRX(SPUD_ROLLER_NUM);
        armMasterTalon         = new WPI_TalonSRX(ARM_MASTER_NUM);
        armSlaveTalon          = new WPI_TalonSRX(ARM_SLAVE_NUM);
        liftActuatorTalon      = new WPI_TalonSRX(LIFT_ACTUATOR_NUM);


        // Configure all the Talon Directions --------------------------------

        leftMasterDriveTalon.setInverted  (REVERSE_DRIVE_TALONS);
        rightMasterDriveTalon.setInverted (REVERSE_DRIVE_TALONS);
        leftSlaveDriveTalon.setInverted   (REVERSE_DRIVE_TALONS);
        rightSlaveDriveTalon.setInverted  (REVERSE_DRIVE_TALONS);
        spudDriveTalon.setInverted        (REVERSE_SPUD_DRIVE_TALON);
        spudRollerTalon.setInverted       (REVERSE_SPUD_ROLLER_TALON);
        armMasterTalon.setInverted        (REVERSE_ARM_TALONS);
        armSlaveTalon.setInverted         (REVERSE_ARM_TALONS);
        liftActuatorTalon.setInverted     (REVERSE_ARM_ACTUATOR);


        //Pairing up Talons ---------------------------------------------------------------------

        leftSlaveDriveTalon.follow   (leftMasterDriveTalon,   FollowerType.PercentOutput);
        rightSlaveDriveTalon.follow  (rightMasterDriveTalon,  FollowerType.PercentOutput);
        armSlaveTalon.follow         (armMasterTalon,         FollowerType.PercentOutput);


        //Setting up Joysticks ------------------------------------------------------------------

        driveJoystick        = new Joystick(DRIVE_JOYSTICK_PORT);
        manipulatorJoystick  = new Joystick(MANIPULATION_JOYSTICK_PORT);


        //Setting up the DifferentialDrive ------------------------------------------------------

        drive = new DifferentialDrive(leftMasterDriveTalon, rightMasterDriveTalon);
        drive.setSafetyEnabled(true);
    }

    @Override
    public void teleopPeriodic() {
        drive.arcadeDrive(-driveJoystick.getY(), driveJoystick.getTwist());
    }

    public static void main(String[] args) {
        RobotBase.startRobot(Robot::new);
    }
}
