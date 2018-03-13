package org.gravitechx.frc2018.utils.wrappers;

import com.kauailabs.navx.AHRSProtocol;
import edu.wpi.first.wpilibj.SPI;

import com.kauailabs.navx.AHRSProtocol.AHRSUpdateBase;
import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.ITimestampedDataSubscriber;

public class GravAHRS {
    protected long mLastSensorTimestampMs;
    protected double mYawDegrees;
    protected double mYawRateDegreesPerSecond;
    protected double mPitchDegrees;
    protected double mPitchDegreesPerSecond;
    protected int kInvalidTimestamp = -1;

    protected double mXDisplacement = 0.0;
    protected double mXVelocity = 0.0;
    protected double dt = 0.0;

    private static GravAHRS mInstance = new GravAHRS();

    public static GravAHRS getInstance(){
        return mInstance;
    }

    protected class Callback implements ITimestampedDataSubscriber {
        @Override
        public void timestampedDataReceived(long system_timestamp, long sensor_timestamp, AHRSUpdateBase update, Object context) {
            synchronized (GravAHRS.this) {
                if (mLastSensorTimestampMs != kInvalidTimestamp && mLastSensorTimestampMs < sensor_timestamp) {
                    dt = (sensor_timestamp - mLastSensorTimestampMs);

                    mYawRateDegreesPerSecond = 1000.0 * (-mYawDegrees - update.yaw) / dt;
                    mPitchDegreesPerSecond = 1000 * (-mPitchDegrees - update.pitch) / dt;
                }
                mXVelocity += -update.linear_accel_x * dt;
                mXDisplacement += mXVelocity * dt;

                mLastSensorTimestampMs = sensor_timestamp;
                mYawDegrees = -update.yaw;
                mPitchDegrees = -update.pitch;
            }
        }
    }

    private GravAHRS() {
        mAHRS = new AHRS(SPI.Port.kMXP, (byte) 200);
        mAHRS.registerCallback(new Callback(), null);
    }

    public void reset(){
        mLastSensorTimestampMs = kInvalidTimestamp;
        mYawDegrees = 0.0;
        mYawRateDegreesPerSecond = 0.0;
        mXDisplacement = 0.0;
        mXVelocity = 0.0;
    }

    private AHRS mAHRS;

    public AHRS getmAHRS() {
        return mAHRS;
    }

    public double getmXDisplacement() {
        return mXDisplacement;
    }

    public void setmXDisplacement(double mXDisplacement) {
        this.mXDisplacement = mXDisplacement;
    }

    public double getmXVelocity() {
        return mXVelocity;
    }

    public void setmXVelocity(double mXVelocity) {
        this.mXVelocity = mXVelocity;
    }

    public double getYawDegrees() {
        return mYawDegrees;
    }

    public double getYawRateDegreesPerSecond() {
        return mYawRateDegreesPerSecond;
    }

    public double getPitchDegrees() {
        return mPitchDegrees;
    }

    public double getPitchDegreesPerSecond() {
        return mPitchDegreesPerSecond;
    }

    public double getYawRad() {return mYawDegrees * Math.PI / 180.0;}

    public double getYawRateRadPerSec() {return mYawRateDegreesPerSecond * Math.PI / 180.0; }
}
