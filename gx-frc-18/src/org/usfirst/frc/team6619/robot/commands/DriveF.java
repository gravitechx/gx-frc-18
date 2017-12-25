package org.usfirst.frc.team6619.robot.commands;

import org.usfirst.frc.team6619.robot.Robot;
import org.usfirst.frc.team6619.robot.subsystems.AutoDrive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveF extends Command {
	double sec, power;
	Timer timer;
	boolean done;
	
    public DriveF(double sec, double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.autoDrive);
    	this.sec = sec;
    	this.power=power;
    	timer=new Timer();
    	done=false;
    }
    public DriveF (double sec) {
    	this(sec,0.3);
    }
    
    public void start () {//Called whenever you begin this Drive command
    	initialize();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();//Start the dependent timer in execute method
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	AutoDrive.forward(power);
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
