package org.gravitechx.frc2018.utils.drivehelpers;

import org.gravitechx.frc2018.robot.Constants;

public class DrivePipeline {
    double lastZRotation = 0.0;
    double mNegInertiaScalar = Constants.NEG_INERTIA_TURN_SCALAR;
    double mNegInertiaAccumlator = 0.0;
    double mQuickStopAccum = 0.0;

    /**
     * This methods uses a version of cheesy drive for teleop.
     *
     * @param rotationalDriveSignal
     * @param isQuickTurn
     */
    public DifferentialDriveSignal apply(RotationalDriveSignal rotationalDriveSignal, boolean isQuickTurn) {
        /* APPLY LIMITS AND DEADBAND */
        rotationalDriveSignal.limit();
        rotationalDriveSignal.applyXZDeadband(Constants.THROTTLE_DEADBAND, Constants.WHEEL_DEADBAND);

        /* CALCULATE A dZ FOR NEGATIVE INERTIA */

        double dZ = rotationalDriveSignal.getZRoation() - lastZRotation;
        lastZRotation = rotationalDriveSignal.getZRoation();

        /* SIGNAL FUNCTION TRANSPOSITION */
        rotationalDriveSignal.transposeXSpeed(Constants.THROTTLE_TRANSPOSITION_OPERATION);
        rotationalDriveSignal.transposeZRotation(Constants.WHEEL_TRANSPOSITION_OPERATION);

        /* NEGATIVE INERTIA */

        // If we are trying to turn left or right
        if (dZ * rotationalDriveSignal.getZRoation() > 0.0) {
            mNegInertiaScalar = Constants.NEG_INERTIA_TURN_SCALAR;
        } else {
            // If we are trying to get to 0.0
            if (Math.abs(rotationalDriveSignal.getZRoation()) > Constants.NEG_INERTIA_THREASHOLD) {
                // If we are outside the negative inertia threshold
                mNegInertiaScalar = Constants.NEG_IRERTIA_FAR_SCALAR;
            } else {
                // If we are inside the negative inertia threshold
                mNegInertiaScalar = Constants.NEG_INERTIA_CLOSE_SCALAR;
            }
        }

        /* Calculate a dZ */
        mNegInertiaAccumlator += mNegInertiaScalar * dZ;
        rotationalDriveSignal.setzRotation(rotationalDriveSignal.getZRoation() + mNegInertiaAccumlator);

        if (mNegInertiaAccumlator > 1.0) {
            mNegInertiaAccumlator -= 1.0;
        } else if (mNegInertiaAccumlator < -1.0) {
            mNegInertiaAccumlator += 1.0;
        } else {
            mNegInertiaAccumlator = 0.0;
        }

        /* QUICK TURN */
        if (isQuickTurn) {
            if (Math.abs(rotationalDriveSignal.getXSpeed()) > Constants.QUICK_STOP_DEADBAND) {
                mQuickStopAccum = (1 - Constants.QUICK_STOP_WEIGHT) * mQuickStopAccum
                        + Constants.QUICK_STOP_WEIGHT * DriveSignal.limit(rotationalDriveSignal.getZRoation()) * Constants.QUICK_STOP_SCALAR;
            }
        } else {
            rotationalDriveSignal.setzRotation(Math.abs(rotationalDriveSignal.getXSpeed()) * rotationalDriveSignal.getZRoation() - mQuickStopAccum);
            if (mQuickStopAccum > 1) {
                mQuickStopAccum -= 1;
            } else if (mQuickStopAccum < -1) {
                mQuickStopAccum += 1;
            } else {
                mQuickStopAccum = 0.0;
            }
        }

        return rotationalDriveSignal.toDifferencialDriveSignal();
    }
}