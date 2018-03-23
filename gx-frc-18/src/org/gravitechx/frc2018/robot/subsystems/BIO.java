package org.gravitechx.frc2018.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.gravitechx.frc2018.robot.Constants;

public class BIO {
    private DoubleSolenoid mGripper;
    private DoubleSolenoid mRotator;

    private WPI_VictorSPX mRightIntake;
    private WPI_VictorSPX mLeftIntake;
    
    private ControlState mControlState;

    public enum ControlState {
        NEUTRAL, INHALING, EXHALING
    }

    public enum GraspingStatus {
        CLOSED, NEUTRAL, OPEN
    }

    private GraspingStatus mGraspingStatus;

    private boolean mShouldExhale;

    public ControlState getControlState() {
        return mControlState;
    }

    private BIO(){
        mGripper = new DoubleSolenoid(Constants.BIO_OPEN_PORT, Constants.BIO_CLOSE_PORT);
        mRotator = new DoubleSolenoid(Constants.ROTATOR_PORT, Constants.NULL_PORT);
        mRightIntake = new WPI_VictorSPX(Constants.RIGHT_BIO_MOTOR_CAN_PORT);
        mLeftIntake = new WPI_VictorSPX(Constants.LEFT_BIO_MOTOR_CAN_PORT);
        mControlState = ControlState.NEUTRAL;
        mShouldExhale = false;

        grasp(GraspingStatus.CLOSED);
        mLeftIntake.setNeutralMode(NeutralMode.Coast);
        mRightIntake.setNeutralMode(NeutralMode.Coast);
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
        if(graspingStatus != mGraspingStatus) {
            switch (graspingStatus) {
                case OPEN:
                    mGripper.set(Constants.BIO_GRASP_OPEN_SOLENOID_POSITION);
                    break;
                case NEUTRAL:
                    mGripper.set(DoubleSolenoid.Value.kOff);
                    break;
                case CLOSED:
                    mGripper.set(Constants.BIO_GRASP_CLOSED_SOLENOID_POSITION);
            }
            mGraspingStatus = graspingStatus;
        }
    }

    public void rotate(boolean isUp){
        if(isUp){
            mRotator.set(Constants.BIO_ROTATOR_UP_SOLENOID_POSITION);
        }else{
            mRotator.set(Constants.BIO_ROTATOR_DOWN_SOLENOID_POSITION);
        }
    }

    public void setIntake(double speed){
        mLeftIntake.set(speed);
        mRightIntake.set(-speed);
    }

    public boolean isShouldExhale() {
        return mShouldExhale;
    }

    public void setShouldExhale(boolean mShouldExhale) {
        this.mShouldExhale = mShouldExhale;
    }

    public void update(){
        switch (mControlState){
            case INHALING:
                setIntake(Constants.BIO_INHALE_SPEED);
                break;
            case EXHALING:
                if(mShouldExhale) {
                    setIntake(Constants.BIO_EXHALE_SPEED);
                }else{
                    setIntake(0.0);
                }
                break;
            default:
                setIntake(0.0);
        }
    }
}
