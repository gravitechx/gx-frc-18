package org.usfirst.frc.team6619.robot.commands;

import org.usfirst.frc.team6619.robot.Robot;
import org.usfirst.frc.team6619.robot.subsystems.AutoDrive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive extends Command {
	double sec;
	Timer timer = new Timer();
	boolean done = false;
	
    public Drive(double sec) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.autoDrive);
    	this.sec = sec;
    }
    
    public void start () {//Called whenever you begin this Drive command
    	timer.start();//Start the dependent timer in execute method
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	AutoDrive.forward();
    	if (timer.get() >= sec){
    		end();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	AutoDrive.stop();
		timer.stop();
		done = true;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
