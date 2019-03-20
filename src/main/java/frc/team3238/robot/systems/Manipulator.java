package frc.team3238.robot.systems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import static frc.team3238.robot.FREDDXConstants.*;

public final class Manipulator {

    //Yes I chose not to encapsulate these variables.
    public final WPI_TalonSRX     lift;
    public final WPI_TalonSRX     collector;
    public       SensorCollection sensorCollection;

    public Manipulator(WPI_TalonSRX lift, WPI_TalonSRX collector) {
        this.lift        = lift;
        this.collector   = collector;
        sensorCollection = collector.getSensorCollection();

        lift.setInverted(REVERSE_LIFT);
        lift.setNeutralMode(USE_LIFT_BRAKES ? NeutralMode.Brake : NeutralMode.Coast);
        lift.configSelectedFeedbackSensor(FeedbackDevice.Analog);
        lift.config_kP(0, LIFT_kP);
        lift.config_kI(0, LIFT_kI);
        lift.config_kD(0, LIFT_kD);

        collector.setInverted(REVERSE_COLLECTOR);
        collector.setNeutralMode(USE_COLLECTOR_BRAKES ? NeutralMode.Brake : NeutralMode.Coast);
    }

    public Manipulator(int liftId, int collectorId) {
        this(new WPI_TalonSRX(liftId),
             new WPI_TalonSRX((collectorId)));
    }

    public Manipulator() {
        this(LIFT_ID, COLLECTOR_ID);
    }
}
