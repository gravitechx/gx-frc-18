package org.gravitechx.frc2018.robot.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.gravitechx.frc2018.robot.Constants;

public class BIO {
    private DoubleSolenoid mGripper;
    private DoubleSolenoid mRotator;
    
    private ControlState mControlState;

    public enum ControlState {
        NEUTRAL, GRASPING, INHALING, EXHALING
    }

    public enum GraspingStatus {
        CLOSED, NEUTRAL, OPEN
    }

    private boolean isRotated;
    private GraspingStatus mGraspingStatus;

    private BIO(){
        mGripper = new DoubleSolenoid(Constants.BIO_OPEN_PORT, Constants.BIO_CLOSE_PORT);
        mRotator = new DoubleSolenoid(Constants.ROTATOR_PORT, Constants.NULL_PORT);
        mControlState = ControlState.NEUTRAL;
    }

    private static final BIO mBIO = new BIO();

    public static BIO getInstance(){
        return mBIO;
    }

    public void set(ControlState cState){
        if(mControlState == cState) { return; } // Don't double set values
        switch (cState){
            case NEUTRAL:
                rotate(true);
                if(mGraspingStatus == GraspingStatus.NEUTRAL){
                    grasp(GraspingStatus.OPEN);
                }
                break;
            case GRASPING:
                grasp(GraspingStatus.CLOSED);
                break;
            case EXHALING:
                rotate(false);
                grasp(GraspingStatus.OPEN);
            case INHALING:
                rotate(false);
                grasp(GraspingStatus.OPEN);
        }
        mControlState = cState;
    }

    public void grasp(GraspingStatus graspingStatus){
        switch(graspingStatus){
            case OPEN:
                mGripper.set(DoubleSolenoid.Value.kForward);
                break;
            case NEUTRAL:
                mGripper.set(DoubleSolenoid.Value.kOff);
                break;
            case CLOSED:
                mGripper.set(DoubleSolenoid.Value.kReverse);
        }
        mGraspingStatus = graspingStatus;
    }

    public void rotate(boolean isUp){
        if(isUp){
            mRotator.set(DoubleSolenoid.Value.kForward);
        }else{
            mRotator.set(DoubleSolenoid.Value.kOff);
        }
    }
}
