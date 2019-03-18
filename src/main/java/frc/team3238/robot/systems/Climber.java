package frc.team3238.robot.systems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import static frc.team3238.robot.FREDDXConstants.*;

public final class Climber {

    //Yes they are not encapsulated on purpose
    public final WPI_TalonSRX roller;
    public final WPI_TalonSRX spuds;
    public final WPI_TalonSRX breacherRight;
    public final WPI_TalonSRX breacherLeft;

    public Climber(WPI_TalonSRX roller, WPI_TalonSRX spuds, WPI_TalonSRX breacherLeft, WPI_TalonSRX breacherRight) {
        this.roller        = roller;
        this.spuds         = spuds;
        this.breacherRight = breacherRight;
        this.breacherLeft  = breacherLeft;

        roller.setInverted(REVERSE_ROLLER);
        roller.setNeutralMode(ROLLER_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);

        spuds.setInverted(REVERSE_SPUDS);
        spuds.setNeutralMode(SPUDS_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        spuds.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
        spuds.setSensorPhase(FLIP_SPUD_SENSOR);
        spuds.config_kP(0, SPUDS_kP, TALON_TIMEOUT);
        spuds.config_kI(0, SPUDS_kI, TALON_TIMEOUT);
        spuds.config_kD(0, SPUDS_kD, TALON_TIMEOUT);
        spuds.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        spuds.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
        spuds.setSelectedSensorPosition(0);

        breacherRight.setInverted(REVERSE_BREACHER);
        breacherRight.setNeutralMode(BREACHER_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        breacherRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TALON_TIMEOUT);
        breacherRight.setSensorPhase(FLIP_RIGHT_BREACHER_SENSOR);
        breacherRight.config_kP(0, BREACHER_kP, TALON_TIMEOUT);
        breacherRight.config_kI(0, BREACHER_kI, TALON_TIMEOUT);
        breacherRight.config_kD(0, BREACHER_kD, TALON_TIMEOUT);
        breacherRight.setSelectedSensorPosition(0);

        breacherLeft.setInverted(REVERSE_BREACHER);
        breacherLeft.setNeutralMode(BREACHER_BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
        breacherLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TALON_TIMEOUT);
        breacherLeft.setSensorPhase(FLIP_LEFT_BREACHER_SENSOR);
        breacherLeft.config_kP(0, BREACHER_kP, TALON_TIMEOUT);
        breacherLeft.config_kI(0, BREACHER_kI, TALON_TIMEOUT);
        breacherLeft.config_kD(0, BREACHER_kD, TALON_TIMEOUT);
        breacherLeft.setSelectedSensorPosition(0);
    }

    public Climber(int rollerTalonId, int spudsTalonId, int breacherLeftTalonId, int breacherRightTalonId) {
        this(new WPI_TalonSRX(rollerTalonId), new WPI_TalonSRX(spudsTalonId), new WPI_TalonSRX(breacherLeftTalonId),
             new WPI_TalonSRX(breacherRightTalonId));
    }

    public Climber() {
        this(ROLLER_ID, SPUDS_ID, BREACHER_LEFT_ID, BREACHER_RIGHT_ID);
    }
}
