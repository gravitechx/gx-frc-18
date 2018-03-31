package org.gravitechx.frc2018.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.robot.subsystems.Drive;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;
import org.gravitechx.frc2018.robot.Robot;
import org.gravitechx.frc2018.robot.subsystems.BIO;
import org.gravitechx.frc2018.robot.subsystems.Lift;
import org.gravitechx.frc2018.utils.visionhelpers.VisionInfo;

public class GoToTape extends Command {
    private int num_of_terms_to_average;
    private boolean finished;
    private Drive drive;
    private RotationalDriveSignal way_to_move;
    private Timer forwardtimer;
    private double tapedistance,tapeangle;
    //private CHANGE jetsonserver;
    public GoToTape() {
        requires(Robot.drive);
        drive = Drive.getInstance(); //Get drive instance
        finished=false; //The command isn't finished yet
        num_of_terms_to_average = 5;
        //jetsonserver = JETSONSERVERINSTANCE;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize(){
        VisionInfo info = Robot.rs.getVisionInfo();
        if(info != null) {
            tapedistance = info.getTapeDistance() * num_of_terms_to_average;
            tapeangle = info.getTapeAngle() * num_of_terms_to_average;
        }// Possibly a new object of Katie's class? How does grabbing a variable from another running class/thread work?
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        VisionInfo info = Robot.rs.getVisionInfo();
        if (tapedistance == 0 && tapeangle == 0 && info != null) {
            tapedistance = info.getTapeDistance() * num_of_terms_to_average;
            tapeangle = info.getTapeAngle() * num_of_terms_to_average;
        } else if (info != null) {
            tapedistance = tapedistance * (num_of_terms_to_average - 1) / num_of_terms_to_average + info.getTapeDistance();//INSERT VARIABLE FROM KATIE HERE
            tapeangle = tapeangle * (num_of_terms_to_average - 1) / num_of_terms_to_average + info.getTapeAngle();//INSERT VARIABLE FROM KATIE HERE: PROBABLY IN DEGREES
            System.out.println("New tape distance: " + info.getTapeDistance() + " , Average value: " + (tapedistance/num_of_terms_to_average));
            System.out.println("New tape angle: " + info.getTapeAngle() + " , Average value: " + (tapeangle/num_of_terms_to_average));
        }

        if (forwardtimer != null) { //Run if timer is set
            if(forwardtimer.hasPeriodPassed(1)) { //If the robot has gone forward enough after the close enough distance
                forwardtimer.stop();
                end();
            } else if(tapeangle/num_of_terms_to_average*Constants.ANGLE_TO_ROTATION_RATIO < 90) { //If the angle isn't impossibly crazy. :)
                way_to_move = new RotationalDriveSignal(0.3,tapeangle/num_of_terms_to_average*Constants.ANGLE_TO_ROTATION_RATIO);
                drive.set(way_to_move.toDifferentialDriveSignal());
            } else { System.out.println("Timer not null but angle too crazy. (GoToTape)"); }
        } else if(tapedistance/num_of_terms_to_average<=Constants.AT_TAPE_DISTANCE) {//Run if box is within grabbing distance
            forwardtimer = new Timer();
            forwardtimer.start();
        } else if (tapedistance/num_of_terms_to_average*Constants.DISTANCE_TO_POWER_RATIO <= 0.9 && tapeangle/num_of_terms_to_average*Constants.ANGLE_TO_ROTATION_RATIO < 90) {
            way_to_move = new RotationalDriveSignal(tapedistance/num_of_terms_to_average*Constants.DISTANCE_TO_POWER_RATIO,tapeangle/num_of_terms_to_average*Constants.ANGLE_TO_ROTATION_RATIO); //Create mew drivesignal (Rotational) that uses the vision distance and angle. Tune with constants.
            drive.set(way_to_move.toDifferentialDriveSignal());
        } else {
            System.out.println("GoToTape not driving.");
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
