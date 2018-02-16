package org.gravitechx.frc2018.utils.lifthelpers;

import org.gravitechx.frc2018.robot.Constants;

public class ElevatorPipeline {
    private double mDistance_M = 0.0;
    private double mRestDecay = 0.0;
    private double mLastSignal;
    private boolean mAutofall = true;

    public double apply(double signal){

        mDistance_M += Constants.LIFT_COMPOUNDING_STEP * signal;

        mRestDecay += Constants.LIFT_REST_DECAY_PROPORTIONAL * mDistance_M + Constants.LIFT_PERSISTENT_DECAY_PROPORTIONAL;

        if(mAutofall)
            mDistance_M -= mRestDecay;

        if(mDistance_M > Constants.LIFT_MAX_TRAVEL_M){
            mDistance_M = Constants.LIFT_MAX_TRAVEL_M;
        }else if(mDistance_M < Constants.LIFT_MIN_TRAVEL_M){
            mDistance_M = Constants.LIFT_MAX_TRAVEL_M;
        }

        mLastSignal = signal;

        return mDistance_M;
    }
}
