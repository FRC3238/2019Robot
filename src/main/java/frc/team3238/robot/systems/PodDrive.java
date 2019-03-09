package frc.team3238.robot.systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import static frc.team3238.robot.FREDDXConstants.*;

/**
 * Team 3238's standard drive setup.
 * <p>
 * It consists of two pods. One pod per side. Each pod has three wheels and is driven by two CIM motors joined in a
 * gearbox.
 */
public final class PodDrive extends DifferentialDrive {
    /**
     * @param leftMaster  The master Spark for the left side
     * @param leftSlave   The slave Spark for the left side
     * @param rightMaster The master Spark for the right side
     * @param rightSlave  The slave Spark for the right side
     */
    private PodDrive(CANSparkMax leftMaster, CANSparkMax leftSlave,
                     CANSparkMax rightMaster, CANSparkMax rightSlave) {
        super(leftMaster, rightMaster);

        //Synchronize forward directions
        leftMaster.setInverted(REVERSE_DRIVE);
        leftSlave.setInverted(REVERSE_DRIVE);
        rightMaster.setInverted(REVERSE_DRIVE);
        rightSlave.setInverted(REVERSE_DRIVE);

        //Setup followers
        leftSlave.follow(leftMaster);
        rightSlave.follow(rightMaster);

        //Config neutral state
        leftMaster.setIdleMode(DRIVE_BRAKE ? CANSparkMax.IdleMode.kBrake : CANSparkMax.IdleMode.kCoast);
        leftSlave.setIdleMode(DRIVE_BRAKE ? CANSparkMax.IdleMode.kBrake : CANSparkMax.IdleMode.kCoast);
        rightMaster.setIdleMode(DRIVE_BRAKE ? CANSparkMax.IdleMode.kBrake : CANSparkMax.IdleMode.kCoast);
        rightSlave.setIdleMode(DRIVE_BRAKE ? CANSparkMax.IdleMode.kBrake : CANSparkMax.IdleMode.kCoast);

        //Extra configuration
        setDeadband(0);
        setSafetyEnabled(true);
    }

    /**
     * @param leftFirstID   The CAN ID of the first Spark MAX for the left side
     * @param leftSecondID  The CAN ID of the second Spark MAX for the left side
     * @param rightFirstID  The CAN ID of the first Spark MAX for the right side
     * @param rightSecondID The CAN ID of the second Spark MAX for the right side
     */
    public PodDrive(int leftFirstID, int leftSecondID, int rightFirstID, int rightSecondID) {
        this(new CANSparkMax(leftFirstID, CANSparkMaxLowLevel.MotorType.kBrushless),
             new CANSparkMax(leftSecondID, CANSparkMaxLowLevel.MotorType.kBrushless),
             new CANSparkMax(rightFirstID, CANSparkMaxLowLevel.MotorType.kBrushless),
             new CANSparkMax(rightSecondID, CANSparkMaxLowLevel.MotorType.kBrushless));
    }

    public PodDrive() {
        this(DRIVE_LEFT_MASTER_ID, DRIVE_LEFT_SLAVE_ID, DRIVE_RIGHT_MASTER_ID, DRIVE_RIGHT_SLAVE_ID);
    }
}
