package frc.team3238.robot.systems;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import static frc.team3238.robot.FREDDXConstants.*;
import static frc.team3238.robot.FREDDXConstants.DRIVE_RIGHT_SLAVE_ID;

/**
 * Team 3238's standard drive setup.
 * <p>
 * It consists of two pods. One pod per side. Each pod has three wheels and is driven by two CIM motors joined in a
 * gearbox.
 */
public final class PodDrive extends DifferentialDrive {
    /**
     * @param leftMaster  The master Talon SRX on the left side
     * @param leftSlave   The slave Talon SRX on the left side
     * @param rightMaster The master Talon SRX on the right side
     * @param rightSlave  The slave Talon SRX on the right side
     */
    private PodDrive(WPI_TalonSRX leftMaster, WPI_TalonSRX leftSlave,
                     WPI_TalonSRX rightMaster, WPI_TalonSRX rightSlave) {
        super(leftMaster, rightMaster);

        //Synchronize forward directions
        leftMaster.setInverted(REVERSE_DRIVE);
        leftSlave.setInverted(REVERSE_DRIVE);
        rightMaster.setInverted(REVERSE_DRIVE);
        rightSlave.setInverted(REVERSE_DRIVE);

        //Setup followers
        leftSlave.follow(leftMaster, FollowerType.PercentOutput);
        rightMaster.follow(rightMaster, FollowerType.PercentOutput);

        //Config neutral state
        leftMaster.setNeutralMode(DRIVE_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        leftSlave.setNeutralMode(DRIVE_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        rightMaster.setNeutralMode(DRIVE_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        rightSlave.setNeutralMode(DRIVE_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);

        //Extra configuration
        setDeadband(0);
        setSafetyEnabled(true);
    }

    /**
     * @param leftTalon1ID  The CAN ID of the first Talon SRX on the left side
     * @param leftTalon2ID  The CAN ID of the second Talon SRX on the left side
     * @param rightTalon1ID The CAN ID of the first Talon SRX on the left side
     * @param rightTalon2ID The CAN ID of the second Talon SRX on the right side
     */
    public PodDrive(int leftTalon1ID, int leftTalon2ID, int rightTalon1ID, int rightTalon2ID) {
        this(new WPI_TalonSRX(leftTalon1ID), new WPI_TalonSRX(leftTalon2ID),
             new WPI_TalonSRX(rightTalon1ID), new WPI_TalonSRX(rightTalon2ID));
    }

    public PodDrive() {
        this(DRIVE_LEFT_MASTER_ID, DRIVE_LEFT_SLAVE_ID, DRIVE_RIGHT_MASTER_ID, DRIVE_RIGHT_SLAVE_ID);
    }
}
