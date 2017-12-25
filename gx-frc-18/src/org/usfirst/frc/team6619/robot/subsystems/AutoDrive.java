package org.usfirst.frc.team6619.robot.subsystems;

import org.usfirst.frc.team6619.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class AutoDrive extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public static void forward () {
		Robot.rm.myRobot.drive(0.3,  0);
	}
	public static void reverse () {
		Robot.rm.myRobot.drive(-0.3, 0.0);
	}
	
	public static void stop () {
		Robot.rm.myRobot.stopMotor();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
