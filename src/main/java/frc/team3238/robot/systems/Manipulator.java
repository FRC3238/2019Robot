package frc.team3238.robot.systems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import static frc.team3238.robot.FREDDXConstants.*;

public final class Manipulator {

    //Yes I chose not to encapsulate these variables.
    public final CANSparkMax      lift;
    public final WPI_TalonSRX     wrist;
    public final WPI_TalonSRX     beak;
    public final CANPIDController liftPID;
    public final CANEncoder       liftEncoder;

    public Manipulator(CANSparkMax lift, WPI_TalonSRX wrist, WPI_TalonSRX beak) {
        this.lift        = lift;
        this.liftPID     = lift.getPIDController();
        this.liftEncoder = lift.getEncoder();
        this.wrist       = wrist;
        this.beak        = beak;

        lift.setInverted(REVERSE_LIFT);
        lift.setIdleMode(CANSparkMax.IdleMode.kBrake);
        liftPID.setP(LIFT_kP);
        liftPID.setI(LIFT_kP);
        liftPID.setD(LIFT_kD);

        wrist.setInverted(REVERSE_WRIST);
        wrist.setNeutralMode(WRIST_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        wrist.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TALON_TIMEOUT);
        wrist.configAllowableClosedloopError(0, 5);
        wrist.setSensorPhase(FLIP_WRIST_SENSOR);
        wrist.config_kP(0, WRIST_kP, TALON_TIMEOUT);
        wrist.config_kI(0, WRIST_kI, TALON_TIMEOUT);
        wrist.config_kD(0, WRIST_kD, TALON_TIMEOUT);

        beak.setInverted(REVERSE_BEAK);
        beak.setNeutralMode(BEAK_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        beak.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        beak.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    }

    public Manipulator(int liftId, int wristId, int beakTalonId) {
        this(new CANSparkMax(liftId, CANSparkMaxLowLevel.MotorType.kBrushless),
             new WPI_TalonSRX(wristId),
             new WPI_TalonSRX(beakTalonId));
    }

    public Manipulator() {
        this(LIFT_ID, WRIST_ID, BEAK_ID);
    }
}
