package org.gravitechx.frc2018.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.robot.subsystems.Drive;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;
import org.gravitechx.frc2018.robot.Robot;
import org.gravitechx.frc2018.robot.subsystems.BIO;
import org.gravitechx.frc2018.robot.subsystems.Lift;
import org.gravitechx.frc2018.utils.visionhelpers.VisionInfo;

public class GrabBox extends Command {
	private boolean finished;
	private BIO bio;
	private Drive drive;
	private Lift lift;
	private RotationalDriveSignal way_to_move;
	private VisionInfo visioninfo;
	public GrabBox() {
		requires(Robot.drive);
		//requires(Robot.bio);
		bio = BIO.getInstance();
		drive = Drive.getInstance();
		lift = Lift.getInstance();
		finished=false;
		lift.zeroPosition(); //Set the elevator to the bottom "zero" position
		bio.set(BIO.ControlState.NEUTRAL); //Set BIO to open
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		boxdistance=katiesnumber
		// Possibly a new object of Katie's class? How does grabbing a variable from another running class/thread work?
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		boxdistance=boxdistance*4/5 + newboxdi;//INSERT VARIABLE FROM KATIE HERE
        boxangle=boxangle*4/5 + katiesnumber;//INSERT VARIABLE FROM KATIE HERE: PROBABLY IN DEGREES
		if(boxdistance<=Constants.DISTANCE_TO_CLOSE_BIO_AT) {//Run if box is within grabbing distance
			end(); //End command
		} else {
			way_to_move = new RotationalDriveSignal(boxdistance*Constants.DISTANCE_TO_POWER_RATIO,boxangle*Constants.ANGLE_TO_ROTATION_RATIO); //Create mew drivesignal (Rotational) that uses the vision distance and angle. Tune with constants.
			drive.set(way_to_move.toDifferentialDriveSignal());
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
		drive.set(new RotationalDriveSignal(0,0).toDifferentialDriveSignal()); //Stops the robot from moving
		bio.grasp(BIO.GraspingStatus.CLOSED); //Closes BIO
		finished=true; //Command now finished
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
