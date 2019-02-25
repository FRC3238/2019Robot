package frc.team3238.robot.systems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import static frc.team3238.robot.FREDDXConstants.*;

public final class Climber {

    //Yes they are not encapsulated on purpose
    public final WPI_TalonSRX roller;
    public final WPI_TalonSRX spuds;
    public final WPI_TalonSRX breacherMaster;

    public Climber(WPI_TalonSRX roller, WPI_TalonSRX spuds, WPI_TalonSRX breacherLeft, WPI_TalonSRX breacherRight) {
        this.roller         = roller;
        this.spuds          = spuds;
        this.breacherMaster = breacherRight;

        roller.setInverted(REVERSE_ROLLER);
        roller.setNeutralMode(ROLLER_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        roller.configVoltageCompSaturation(12.0, TALON_TIMEOUT);

        spuds.setInverted(REVERSE_SPUDS);
        spuds.setNeutralMode(SPUDS_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        spuds.configVoltageCompSaturation(12.0, TALON_TIMEOUT);
        spuds.configForwardSoftLimitEnable(true, TALON_TIMEOUT);
        spuds.configForwardSoftLimitThreshold((int) SPUDS_MAX_DOWN, TALON_TIMEOUT);
        spuds.configReverseSoftLimitEnable(true, TALON_TIMEOUT);
        spuds.configReverseSoftLimitThreshold((int) SPUD_MIN_DOWN, TALON_TIMEOUT);
        spuds.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
        spuds.setSensorPhase(FLIP_SPUD_SENSOR);
        spuds.config_kP(0, SPUDS_kP, TALON_TIMEOUT);
        spuds.config_kI(0, SPUDS_kI, TALON_TIMEOUT);
        spuds.config_kD(0, SPUDS_kD, TALON_TIMEOUT);

        breacherRight.setInverted(REVERSE_BREACHER);
        breacherRight.setNeutralMode(BREACHER_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        breacherRight.configVoltageCompSaturation(12.0, TALON_TIMEOUT);
        breacherRight.configForwardSoftLimitEnable(true, TALON_TIMEOUT);
        breacherRight.configForwardSoftLimitThreshold((int) BREACHER_MAX_OUT, TALON_TIMEOUT);
        breacherRight.configReverseSoftLimitEnable(true, TALON_TIMEOUT);
        breacherRight.configReverseSoftLimitThreshold((int) BREACHER_MIN_OUT, TALON_TIMEOUT);
        breacherRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
        breacherRight.setSensorPhase(FLIP_BREACHER_SENSOR);
        breacherRight.config_kP(0, BREACHER_kP, TALON_TIMEOUT);
        breacherRight.config_kI(0, BREACHER_kI, TALON_TIMEOUT);
        breacherRight.config_kD(0, BREACHER_kD, TALON_TIMEOUT);

        breacherLeft.setInverted(REVERSE_BREACHER);
        breacherLeft.setNeutralMode(BREACHER_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        breacherLeft.configVoltageCompSaturation(12.0, TALON_TIMEOUT);
        breacherLeft.follow(breacherRight, FollowerType.PercentOutput);
        breacherLeft.setInverted(InvertType.OpposeMaster);
    }

    public Climber(int rollerTalonId, int spudsTalonId, int breacherLeftTalonId, int breacherRightTalonId) {
        this(new WPI_TalonSRX(rollerTalonId), new WPI_TalonSRX(spudsTalonId), new WPI_TalonSRX(breacherLeftTalonId),
             new WPI_TalonSRX(breacherRightTalonId));
    }

    public Climber() {
        this(ROLLER_ID, SPUDS_ID, BREACHER_LEFT_ID, BREACHER_RIGHT_ID);
    }
}
