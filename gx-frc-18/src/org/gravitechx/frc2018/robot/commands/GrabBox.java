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
	private int num_of_terms_to_average;
	private boolean finished;
	private BIO bio;
	private Drive drive;
	private Lift lift;
	private RotationalDriveSignal way_to_move;
	private double boxdistance,boxangle;
	public GrabBox() {
		requires(Robot.drive);
		//requires(Robot.bio);
		bio = BIO.getInstance(); //Get BIO instance
		drive = Drive.getInstance(); //Get drive instance
		lift = Lift.getInstance(); //Get lift instance
		finished=false; //The command isn't finished yet
		num_of_terms_to_average = 5;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize(){
		lift.zeroPosition(); //Set the elevator to the bottom "zero" position
		bio.set(BIO.ControlState.NEUTRAL); //Set BIO to open
		VisionInfo info = Robot.rs.getVisionInfo();
		if(info != null) {
			boxdistance = info.getBoxDistance() * num_of_terms_to_average;
			boxangle = info.getBoxAngle() * num_of_terms_to_average;
		}
		// Possibly a new object of Katie's class? How does grabbing a variable from another running class/thread work?
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		VisionInfo info = Robot.rs.getVisionInfo();
		System.out.println(Robot.rs.getnumber());
		if (boxdistance == 0 && boxangle == 0 && info != null) {
			boxdistance = info.getBoxDistance() * num_of_terms_to_average;
			boxangle = info.getBoxAngle() * num_of_terms_to_average;
		} else if (info != null) {
			boxdistance = boxdistance * (num_of_terms_to_average - 1) / num_of_terms_to_average + info.getBoxDistance();//INSERT VARIABLE FROM KATIE HERE
			boxangle = boxangle * (num_of_terms_to_average - 1) / num_of_terms_to_average + info.getBoxAngle();//INSERT VARIABLE FROM KATIE HERE: PROBABLY IN DEGREES
			System.out.println("New distance: " + info.getBoxDistance() + " , Average value: " + (boxdistance/num_of_terms_to_average));
			System.out.println("New angle: " + info.getBoxAngle() + " , Average value: " + (boxangle/num_of_terms_to_average));
			System.out.println("Driving power: " + (boxdistance/num_of_terms_to_average*Constants.DISTANCE_TO_POWER_RATIO) + ", Driving angle: " + (boxangle/num_of_terms_to_average*Constants.ANGLE_TO_ROTATION_RATIO));
		}
		/*if(boxdistance/num_of_terms_to_average<=Constants.DISTANCE_TO_CLOSE_BIO_AT) {//Run if box is within grabbing distance
			end(); //End command
		} else if (boxdistance/num_of_terms_to_average*Constants.DISTANCE_TO_POWER_RATIO <=1 && boxangle/num_of_terms_to_average*Constants.ANGLE_TO_ROTATION_RATIO <90) {
			way_to_move = new RotationalDriveSignal(boxdistance/num_of_terms_to_average*Constants.DISTANCE_TO_POWER_RATIO,boxangle/num_of_terms_to_average*Constants.ANGLE_TO_ROTATION_RATIO); //Create mew drivesignal (Rotational) that uses the vision distance and angle. Tune with constants.
			drive.set(way_to_move.toDifferentialDriveSignal());
		} else {
			System.out.println("GrabBox not driving; too crazy of numbers")
		}*/
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
