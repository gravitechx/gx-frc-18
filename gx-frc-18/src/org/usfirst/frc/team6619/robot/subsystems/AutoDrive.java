package org.usfirst.frc.team6619.robot.subsystems;

import org.usfirst.frc.team6619.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class AutoDrive extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public static void move (double power) {
		Robot.rm.myRobot.drive(power, 0.0);//Has the myRobot object in the RobotMap (in the RobotMap instance created in Robot.java) drive forward at power magnitude at a curve of 0
	}
	
	public static void stop () {
		Robot.rm.myRobot.stopMotor();//Has the myRobot object in the RobotMap (in the RobotMap instance created in Robot.java) stop moving the motors
	}
	
	public static void turnLeft (double power) {
		Robot.rm.myRobot.drive(power, -1.0);//Has the myRobot object in the RobotMap (in the RobotMap instance created in Robot.java) turn to the left as sharply as possible at power
	}
	
	public static void turnRight (double power) {
		Robot.rm.myRobot.drive(power, 1.0);//Has the myRobot object in the RobotMap (in the RobotMap instance created in Robot.java) turn to the right as sharply as possible at power
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
