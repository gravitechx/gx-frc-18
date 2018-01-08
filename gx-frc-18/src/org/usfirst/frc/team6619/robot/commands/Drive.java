package org.usfirst.frc.team6619.robot.commands;

import org.usfirst.frc.team6619.robot.Robot;
import org.usfirst.frc.team6619.robot.subsystems.AutoDrive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive extends Command {
	//CLASS LEVEL VARIABLES
	double sec, power;
	Timer timer;
	boolean done;
	
	//Takes in both doubles seconds and power to set up the command
    public Drive(double sec, double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.autoDrive);//Reserves the AutoDrive subsystem to use in its commands
    	this.sec = sec;//Sets the class level variable 'sec' to what the command is given
    	this.power = power;//Sets the class level variable 'power' to what the command is given
    	timer = new Timer();//Sets the class level variable 'timer' to a new instance of a Timer
    	done = false;//Sets the class level boolean 'done' to false
    }
    
    //Makes it so if only one double is given, the automatic power is set to 0.3
    public Drive (double sec) {
    	this(sec, 0.3);//Refers to the above DriveR to set everything else up with the power 0.3
    }
    
    //Called whenever you begin the command
    public void start () {
    	initialize();//refers to initialize - see right below
    }

    // Called just before this Command. This is to be run first to set everything up for execution
    protected void initialize() {
    	timer.start();//Starts the timer needed to keep track of time robot is driving
    }

    // Called repeatedly when this Command is 'running'
    protected void execute() {
    	AutoDrive.move(power);//Tells the subsystem 'AutoDrive' to start moving at power
    	if (timer.hasPeriodPassed(sec)){//If the time that the robot has been moving is passed, the following statements will run
    		end();//Calls the method to finish off the command
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;//Returns the boolean holding if the command is done running or not
    }

    // Called once after isFinished returns true
    protected void end() {
    	AutoDrive.stop();//Stops the robot moving
		timer.stop();//Stops the timer
		done = true;//Allows isFinished() to return true
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();//Calls the method to finish off the command
    }
}
