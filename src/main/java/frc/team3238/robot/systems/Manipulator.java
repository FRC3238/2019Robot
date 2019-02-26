package frc.team3238.robot.systems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import static frc.team3238.robot.FREDDXConstants.*;

public final class Manipulator {

    //Yes I chose not to encapsulate these variables.
    public final WPI_TalonSRX lift;
    public final WPI_TalonSRX wrist;
    public final WPI_TalonSRX beak;

    public Manipulator(WPI_TalonSRX lift, WPI_TalonSRX wrist, WPI_TalonSRX beak) {
        this.lift  = lift;
        this.wrist = wrist;
        this.beak  = beak;

        lift.setInverted(REVERSE_LIFT);
        lift.setNeutralMode(LIFT_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        lift.configVoltageCompSaturation(12.0, TALON_TIMEOUT);
        lift.configForwardSoftLimitEnable(false, TALON_TIMEOUT);
        lift.configForwardSoftLimitThreshold((int) LIFT_MIN_UP, TALON_TIMEOUT);
        lift.configReverseSoftLimitEnable(false, TALON_TIMEOUT);
        lift.configReverseSoftLimitThreshold((int) LIFT_MAX_UP, TALON_TIMEOUT);
        lift.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TALON_TIMEOUT);
        lift.configAllowableClosedloopError(0, 5);
        lift.setSensorPhase(FLIP_LIFT_SENSOR);
        lift.config_kP(0, LIFT_kP, TALON_TIMEOUT);
        lift.config_kI(0, LIFT_kI, TALON_TIMEOUT);
        lift.config_kD(0, LIFT_kD, TALON_TIMEOUT);

        wrist.setInverted(REVERSE_WRIST);
        wrist.setNeutralMode(WRIST_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        wrist.configVoltageCompSaturation(12.0, TALON_TIMEOUT);
        wrist.configForwardSoftLimitEnable(true, TALON_TIMEOUT);
        wrist.configForwardSoftLimitThreshold((int) WRIST_MAX_EXTEND, TALON_TIMEOUT);
        wrist.configReverseSoftLimitEnable(true, TALON_TIMEOUT);
        wrist.configReverseSoftLimitThreshold((int) WRIST_MIN_EXTEND, TALON_TIMEOUT);
        wrist.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TALON_TIMEOUT);
        wrist.configAllowableClosedloopError(0, 5);
        wrist.setSensorPhase(FLIP_WRIST_SENSOR);
        wrist.config_kP(0, WRIST_kP, TALON_TIMEOUT);
        wrist.config_kI(0, WRIST_kI, TALON_TIMEOUT);
        wrist.config_kD(0, WRIST_kD, TALON_TIMEOUT);

        beak.setInverted(REVERSE_BEAK);
        beak.setNeutralMode(BEAK_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        beak.configVoltageCompSaturation(12.0, TALON_TIMEOUT);
    }

    public Manipulator(int liftId, int wristId, int beakTalonId) {
        this(new WPI_TalonSRX(liftId), new WPI_TalonSRX(wristId), new WPI_TalonSRX(beakTalonId));
    }

    public Manipulator() {
        this(LIFT_ID, WRIST_ID, BEAK_ID);
    }
}
