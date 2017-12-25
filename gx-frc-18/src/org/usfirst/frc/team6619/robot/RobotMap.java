package org.usfirst.frc.team6619.robot;

import edu.wpi.first.wpilibj.RobotDrive;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	//Set Ports
	private static int TAL_LEFT_MOTOR = 0;//Port for left motor
	private static int TAL_RIGHT_MOTOR =1;//Port for right motor
	
	//Objects TO USE
	public RobotDrive myRobot;//Sets up myRobot as a RobotDrive (imported)
	
	//Constructors
	public RobotMap() {
		myRobot = new RobotDrive (TAL_LEFT_MOTOR, TAL_RIGHT_MOTOR);//Implements myRobot with RobotDrive (left then right motor channel) with above port definitions 
		myRobot.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);//Invert right motor - both motors would naturally turn a circle FIX
	}
}
