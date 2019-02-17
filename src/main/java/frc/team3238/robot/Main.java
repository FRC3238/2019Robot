package frc.team3238.robot;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * The entry point for the robot program.
 * <p>
 * Nothing much to see here.
 *
 * @author Loren
 */
public class Main {

    public static void main(String[] args) {
        RobotBase.startRobot(FredX::new);
    }
}
