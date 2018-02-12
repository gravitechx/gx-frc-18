package org.gravitechx.frc2018.utils.wrappers;

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

    protected class Callback implements ITimestampedDataSubscriber {
        @Override
        public void timestampedDataReceived(long system_timestamp, long sensor_timestamp, AHRSUpdateBase update, Object context) {
            synchronized (GravAHRS.this) {
                if (mLastSensorTimestampMs != kInvalidTimestamp && mLastSensorTimestampMs < sensor_timestamp) {
                    mYawRateDegreesPerSecond = 1000.0 * (-mYawDegrees - update.yaw)
                            / (double) (sensor_timestamp - mLastSensorTimestampMs);
                    mPitchDegreesPerSecond = 1000 * (-mPitchDegrees - update.pitch)
                            / (double) (sensor_timestamp - mLastSensorTimestampMs);
                }
                mLastSensorTimestampMs = sensor_timestamp;
                mYawDegrees = -update.yaw;
                mPitchDegrees = -update.pitch;
            }
        }
    }

    public GravAHRS(SPI.Port SPI_ID) {
        mAHRS = new AHRS(SPI_ID, (byte) 200);
        mAHRS.registerCallback(new Callback(), null);
    }

    public void reset(){
        mLastSensorTimestampMs = kInvalidTimestamp;
        mYawDegrees = 0.0;
        mYawRateDegreesPerSecond = 0.0;
    }

    private AHRS mAHRS;

    public AHRS getmAHRS() {
        return mAHRS;
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

}
