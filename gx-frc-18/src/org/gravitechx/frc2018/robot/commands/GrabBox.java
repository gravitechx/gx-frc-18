package org.gravitechx.frc2018.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.robot.subsystems.Drive;
import org.gravitechx.frc2018.utils.drivehelpers.DifferentialDriveSignal;
import org.gravitechx.frc2018.robot.Robot;
import org.gravitechx.frc2018.robot.subsystems.BIO;

/**
 *
 */
public class GrabBox extends Command {
	private boolean finished;
	private BIO bio;
	public GrabBox() {
		// Might also need to use custom Drive.setControlState. Ask Alex.
		requires(Robot.drive);
		bio = BIO.getInstance();
		finished=false;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		
		// Possibly a new object of Katie's class? How does grabbing a varaible from another running class/thread work?
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double boxdistance=4;//INSERT VARIABLE FROM KATIE HERE
		double boxoffset=8;//INSERT VARIABLE FROM KATIE HERE
        double boxangle=5;//INSERT VARIABLE FROM KATIE HERE: PROBABLY IN DEGREES
		if(boxdistance<=Constants.DISTANCE_TO_CLOSE_BIO_AT) {//Run if box is within grabbing distance
			bio.grasp(GraspingStatus.CLOSED);
			end();
		} else {
			//bio.set(ControlState.NEUTRAL);
			DifferentialDriveSignal way_to_move;
			if(boxoffset<0) {//If the box is to the left
				way_to_move = new DifferentialDriveSignal(boxdistance*DISTANCE_TO_POWER_RATIO-Math.abs(boxoffset)*TURN_TOWARDS_BOX_RATIO*boxdistance,boxdistance*DISTANCE_TO_POWER_RATIO);//Need to figure out what max motor output is. Multiply by boxdistance so that corrections are smaller when closer to the box.
			} else {//If the box is to the right
				way_to_move = new DifferentialDriveSignal(boxdistance*DISTANCE_TO_POWER_RATIO,boxdistance*DISTANCE_TO_POWER_RATIO-boxoffset*TURN_TOWARDS_BOX_RATIO*boxdistance);//Need to figure out what max motor output is.
			}
			Drive.set(way_to_move);
		}
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return finished;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		//Possibly release Katie's class
		//Possibly change Drive.setControlState. Ask Alex.
		finished=true;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
