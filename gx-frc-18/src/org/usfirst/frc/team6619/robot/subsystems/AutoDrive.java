package org.usfirst.frc.team6619.robot.subsystems;

import org.usfirst.frc.team6619.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class AutoDrive extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public static void forward (double power) {
		Robot.rm.myRobot.drive(power,  0);
	}
	public static void reverse (double power) {
		Robot.rm.myRobot.drive(-power, 0.0);
	}
	
	public static void stop () {
		Robot.rm.myRobot.stopMotor();
	}
	
	public static void turnLeft () {
		Robot.rm.myRobot.drive(0.0, -1.0);
	}
	
	public static void turnRight () {
		Robot.rm.myRobot.drive(0.0, 1.0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
