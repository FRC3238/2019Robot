package frc.team3238.robot.systems;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import frc.team3238.robot.Utility;
import frc.team3238.robot.control.joystick.Button;
import frc.team3238.robot.control.joystick.JoystickButton;

import static frc.team3238.robot.FREDDXConstants.*;

public class BeakCollector implements PeriodicMechanism{
    private final WPI_TalonSRX wrist;
    private final WPI_TalonSRX beak;

    private final Button openBeak;
    private final Button closeBeak;
    private final Button wristUp;
    private final Button wristDown;

    public BeakCollector() {
        beak = new WPI_TalonSRX(COLLECTOR_ID);
        beak.setNeutralMode(USE_COLLECTOR_BRAKES ? NeutralMode.Brake : NeutralMode.Coast);
        beak.setInverted(REVERSE_COLLECTOR);
        beak.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        beak.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);

        wrist = new WPI_TalonSRX(WRIST_ID);
        wrist.setNeutralMode(USE_WRIST_BRAKES ? NeutralMode.Brake : NeutralMode.Coast);
        wrist.setInverted(REVERSE_WRIST);

        var manipulatorJoystick = new Joystick(DRIVE_JOYSTICK_PORT);

        openBeak  = new JoystickButton(manipulatorJoystick, EJECT_BALL);
        closeBeak = new JoystickButton(manipulatorJoystick, COLLECT_BALL);
        wristUp   = new JoystickButton(manipulatorJoystick, WRIST_UP);
        wristDown = new JoystickButton(manipulatorJoystick, WRIST_DOWN);
    }

    @Override
    public void periodic() {
        openBeak.update();
        closeBeak.update();
        wristUp.update();
        wristDown.update();

        Utility.driveTalonFwdRevOrStop(beak, openBeak.isHeld(), closeBeak.isHeld(), BEAK_SPEED);
        Utility.driveTalonFwdRevOrStop(wrist, wristUp.isHeld(), wristDown.isHeld(), WRIST_SPEED);
    }
}
