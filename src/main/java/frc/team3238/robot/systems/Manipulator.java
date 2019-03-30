package frc.team3238.robot.systems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import static frc.team3238.robot.FREDDXConstants.*;

public final class Manipulator {

    public final WPI_TalonSRX lift;

    public Manipulator() {
        this.lift = new WPI_TalonSRX(LIFT_ID);

        lift.setInverted(REVERSE_LIFT);
        lift.setNeutralMode(USE_LIFT_BRAKES ? NeutralMode.Brake : NeutralMode.Coast);
        lift.configSelectedFeedbackSensor(FeedbackDevice.Analog);
        lift.config_kP(0, LIFT_kP);
        lift.config_kI(0, LIFT_kI);
        lift.config_kD(0, LIFT_kD);
    }
}
