package frc.team3238.robot.systems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import frc.team3238.robot.Utility;
import frc.team3238.robot.control.joystick.Button;
import frc.team3238.robot.control.joystick.JoystickButton;

import static frc.team3238.robot.FREDDXConstants.*;

public class BallCollector implements PeriodicMechanism {

    private final WPI_TalonSRX roller;

    private final Button collectBall;
    private final Button ejectBall;

    public BallCollector() {
        roller = new WPI_TalonSRX(WRIST_ID);
        roller.setInverted(REVERSE_COLLECTOR);
        roller.setNeutralMode(USE_COLLECTOR_BRAKES ? NeutralMode.Brake : NeutralMode.Coast);

        var manipulatorJoystick = new Joystick(MANIPULATOR_JOYSTICK_PORT);

        collectBall     = new JoystickButton(manipulatorJoystick, COLLECT_BALL);
        ejectBall       = new JoystickButton(manipulatorJoystick, EJECT_BALL);
    }

    @Override
    public void periodic() {
        collectBall.update();
        ejectBall.update();

        Utility.driveTalonFwdRevOrStop(roller, collectBall.isHeld(), ejectBall.isHeld(), COLLECTOR_SPEED);
    }
}
