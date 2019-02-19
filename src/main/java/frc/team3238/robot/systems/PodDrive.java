package frc.team3238.robot.systems;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Team 3238's standard drive setup.
 * <p>
 * It consists of two pods. One pod per side. Each pod has three wheels and is driven by two CIM motors joined in a
 * gearbox.
 */
public class PodDrive extends DifferentialDrive {
    /**
     * @param leftMaster  The master Talon SRX on the left side
     * @param leftSlave   The slave Talon SRX on the left side
     * @param rightMaster The master Talon SRX on the right side
     * @param rightSlave  The slave Talon SRX on the right side
     * @param flip        When true the forward direction of the drive is reversed
     */
    private PodDrive(WPI_TalonSRX leftMaster, WPI_TalonSRX leftSlave,
                     WPI_TalonSRX rightMaster, WPI_TalonSRX rightSlave, boolean flip, boolean brakeInNeutral) {
        super(leftMaster, rightMaster);

        //Erase any stored settings
        leftMaster.configFactoryDefault();
        leftSlave.configFactoryDefault();
        rightMaster.configFactoryDefault();
        rightSlave.configFactoryDefault();

        //Synchronize forward directions
        leftMaster.setInverted(flip);
        leftSlave.setInverted(flip);
        rightMaster.setInverted(flip);
        rightSlave.setInverted(flip);

        //Setup followers
        leftSlave.follow(leftMaster, FollowerType.PercentOutput);
        rightMaster.follow(rightMaster, FollowerType.PercentOutput);

        //Config neutral state
        leftMaster.setNeutralMode(brakeInNeutral ? NeutralMode.Brake : NeutralMode.Coast);
        leftSlave.setNeutralMode(brakeInNeutral ? NeutralMode.Brake : NeutralMode.Coast);
        rightMaster.setNeutralMode(brakeInNeutral ? NeutralMode.Brake : NeutralMode.Coast);
        rightSlave.setNeutralMode(brakeInNeutral ? NeutralMode.Brake : NeutralMode.Coast);
    }

    /**
     * @param leftTalon1ID  The CAN ID of the first Talon SRX on the left side
     * @param leftTalon2ID  The CAN ID of the second Talon SRX on the left side
     * @param rightTalon1ID The CAN ID of the first Talon SRX on the left side
     * @param rightTalon2ID The CAN ID of the second Talon SRX on the right side
     * @param flip          When true the forward direction of the drive is reversed
     */
    public PodDrive(int leftTalon1ID, int leftTalon2ID, int rightTalon1ID, int rightTalon2ID,
                    boolean flip, boolean brakeInNeutral) {
        this(new WPI_TalonSRX(leftTalon1ID), new WPI_TalonSRX(leftTalon2ID),
             new WPI_TalonSRX(rightTalon1ID), new WPI_TalonSRX(rightTalon2ID), flip, brakeInNeutral);
    }
}
