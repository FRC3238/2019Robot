package frc.team3238.robot;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Loren
 */
public class Robot extends TimedRobot {

    private static final int JOYSTICK_USB_PORT_NUM = 0;

    private static final int LEFT_MASTER_TALON_DEVICE_NUM = 0;

    private static final int RIGHT_MASTER_TALON_DEVICE_NUM = 1;

    private static final int LEFT_SLAVE_TALON_DEVICE_NUM = 2;

    private static final int RIGHT_SLAVE_TALON_DEVICE_NUM = 3;

    /**
     * The driver station's joystick.
     * <p>
     * Used to get input from the driver.
     */
    private Joystick joystick;

    /**
     * The robot's drive train.
     * <p>
     * We're using a West Coast Drive.
     */
    private DifferentialDrive drivePlatform;

    public Robot() {
        super(20);
    }

    @Override
    public void robotInit() {
        joystick = new Joystick(JOYSTICK_USB_PORT_NUM);

        //Declaring all the TalonSRXs for the West Cost Drive
        var leftMasterTalon = new WPI_TalonSRX(LEFT_MASTER_TALON_DEVICE_NUM);
        var rightMasterTalon = new WPI_TalonSRX(RIGHT_MASTER_TALON_DEVICE_NUM);
        var leftSlaveTalon = new WPI_TalonSRX(LEFT_SLAVE_TALON_DEVICE_NUM);
        var rightSlaveTalon = new WPI_TalonSRX(RIGHT_SLAVE_TALON_DEVICE_NUM);

        //Setting slave talons to follow their masters
        leftSlaveTalon.follow(leftMasterTalon, FollowerType.PercentOutput);
        rightSlaveTalon.follow(rightMasterTalon, FollowerType.PercentOutput);

        //Differential drive is the WPI class for the West Coast Drive that we use.
        drivePlatform = new DifferentialDrive(leftMasterTalon, rightMasterTalon);
        drivePlatform.setSafetyEnabled(true);
    }

    @Override
    public void teleopPeriodic() {
        //Might need to limit the values within a specific range.
        //For now, they're being used raw.
        drivePlatform.arcadeDrive(-joystick.getY(), joystick.getTwist());

        //Debugging output to SmartDashboard. Will help for figuring out the ranges of values from the joystick.
        SmartDashboard.putNumber("Joystick X", joystick.getX());
        SmartDashboard.putNumber("Joystick Y", joystick.getY());
        SmartDashboard.putNumber("Joystick Twist", joystick.getTwist());
    }
}
