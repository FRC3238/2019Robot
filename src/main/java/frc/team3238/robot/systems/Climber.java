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

    public Climber() {
        this.roller        = new WPI_TalonSRX(ROLLER_ID);
        this.spuds         = new WPI_TalonSRX(SPUDS_ID);
        this.breacherRight = new WPI_TalonSRX(BREACHER_RIGHT_ID);
        this.breacherLeft  = new WPI_TalonSRX(BREACHER_LEFT_ID);

        //Erase previous configs
        roller.configFactoryDefault();
        spuds.configFactoryDefault();
        breacherRight.configFactoryDefault();
        breacherLeft.configFactoryDefault();

        //Turn on the brakes if they need to be on
        if(!DISABLE_BRAKES_WHEN_DISABLED)
            useBrakes();


        roller.setInverted(REVERSE_ROLLER);

        spuds.setInverted(REVERSE_SPUDS);
        spuds.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
        spuds.setSensorPhase(FLIP_SPUD_SENSOR);
        spuds.config_kP(0, SPUDS_kP, TALON_TIMEOUT);
        spuds.config_kI(0, SPUDS_kI, TALON_TIMEOUT);
        spuds.config_kD(0, SPUDS_kD, TALON_TIMEOUT);
        spuds.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        spuds.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
        spuds.setSelectedSensorPosition(0);

        breacherRight.setInverted(!REVERSE_BREACHER);
        breacherRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TALON_TIMEOUT);
        breacherRight.configAllowableClosedloopError(0, 75);
        breacherRight.setSensorPhase(FLIP_RIGHT_BREACHER_SENSOR);
        breacherRight.config_kP(0, BREACHER_kP, TALON_TIMEOUT);
        breacherRight.config_kI(0, BREACHER_kI, TALON_TIMEOUT);
        breacherRight.config_kD(0, BREACHER_kD, TALON_TIMEOUT);
        breacherRight.setSelectedSensorPosition(0);

        breacherLeft.setInverted(REVERSE_BREACHER);
        breacherLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TALON_TIMEOUT);
        breacherRight.configAllowableClosedloopError(0, 75);
        breacherLeft.setSensorPhase(FLIP_LEFT_BREACHER_SENSOR);
        breacherLeft.config_kP(0, BREACHER_kP, TALON_TIMEOUT);
        breacherLeft.config_kI(0, BREACHER_kI, TALON_TIMEOUT);
        breacherLeft.config_kD(0, BREACHER_kD, TALON_TIMEOUT);
        breacherLeft.setSelectedSensorPosition(0);
    }

    public void useBrakes() {
        if(USE_ROLLER_BRAKES)
            roller.setNeutralMode(NeutralMode.Brake);
        if(USE_SPUDS_BRAKES)
            spuds.setNeutralMode(NeutralMode.Brake);
        if(USE_BREACHER_BRAKES) {
            breacherRight.setNeutralMode(NeutralMode.Brake);
            breacherLeft.setNeutralMode(NeutralMode.Brake);
        }
    }

    public void disableBrakes() {
        roller.setNeutralMode(NeutralMode.Coast);
        spuds.setNeutralMode(NeutralMode.Coast);
        breacherRight.setNeutralMode(NeutralMode.Coast);
        breacherLeft.setNeutralMode(NeutralMode.Coast);
    }
}
