package org.usfirst.frc.team6619.robot.commands;

import org.usfirst.frc.team6619.robot.Robot;
import org.usfirst.frc.team6619.robot.subsystems.AutoDrive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveR extends Command {
	//CLASS LEVEL VARIABLES
	double sec, power;
	Timer timer = new Timer();
	boolean done = false;
	
	//Takes in both doubles seconds and power.
	//Tells the system it is using AutoDrive and sets the seconds and power to the class level variables above.
	public DriveR (double sec, double power) {
    	requires(Robot.autoDrive);
    	this.sec = sec;
    	this.power = power;
	}
	
	//Makes it so if only one double is given, the automatic power is set to 0.3. 
	//Refers to the above DriveR after it finishes assigning the 0.3 power.
	public DriveR (double sec) {
		this(sec, 0.3);
	}
	
	//Start is called whenever you begin the command
	public void start() {
		initialize();//refers to initialize - see right below
	}
	
	protected void initialize() {
		timer.start();//starts the timer needed in execute
	}

	//This part 'execute' runs over and over automatically
	protected void execute() {
		//FULL REVERSE ON at determined power
    	AutoDrive.reverse(power);
    	//Determines if the reverse is done yet
    	if (timer.get() >= sec){
    		end();
    	}
	}
	
	//When isFinished runs true as a boolean execute stops running
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return done;//done will become true when end is called
	}
	
	//Ends the whole thingymajig [add to dictionary]
	protected void end() {
    	AutoDrive.stop();//stops AutoDrive just in case
		timer.stop();//stops the timer [duh]
		done = true;//sets done to true triggering isFinished to stop running execute
	}
	
	//This runs if another thing needs to use AutoDrive as well, cutting off this whole thing.
	protected void interrupted() {
		end();//refers to the end method above to exit everything as normal
	}
}