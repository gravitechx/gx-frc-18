package org.gravitechx.frc2018.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.robot.subsystems.Drive;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;
import org.gravitechx.frc2018.robot.Robot;

/**
 *
 */
public class GoToTape extends Command {
    private boolean finished;
    private Drive drive;
    private RotationalDriveSignal way_to_move;
    public GoToTape() {
        // Might also need to use custom Drive.setControlState. Ask Alex.
        requires(Robot.drive);
        drive = Drive.getInstance();
        finished=false;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

        // Possibly a new object of Katie's class? How does grabbing a variable from another running class/thread work?
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double tapedistance=4;//INSERT VARIABLE FROM KATIE HERE
        double tapeangle=5;//INSERT VARIABLE FROM KATIE HERE: PROBABLY IN DEGREES
        if(tapedistance<=Constants.TAPE_CLOSE_ENOUGH_DISTANCE) {//Run if box is within grabbing distance
            end(); //End command
        } else {
            way_to_move = new RotationalDriveSignal(tapedistance*Constants.DISTANCE_TO_POWER_RATIO,tapeangle*Constants.ANGLE_TO_ROTATION_RATIO); //Create mew drivesignal (Rotational) that uses the vision distance and angle. Tune with constants.
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
        finished=true; //Command now finished
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
