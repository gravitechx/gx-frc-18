package org.gravitechx.frc2018.utils.drivehelpers;

/**
 * Repersents an arbitrary drive signal.
 */
abstract public class DriveSignal {
    private boolean mBreakActive;

    public DriveSignal(boolean breakActive){
        this.mBreakActive = breakActive;
    }

    /**
     * Get whether break mode is active.
     * @return breakMode
     */
    public boolean isBreakActive() {
        return mBreakActive;
    }

    /**
     * Set whether break mode is active.
     * @param breakActive
     */
    public void setBreakActive(boolean breakActive) {
        this.mBreakActive = breakActive;
    }

    /**
     * Limits the value by setting it equal to the limit or -limit if it is outside [-limit, limit]
     * @param signal a double that is a number
     * @param limit a positive value
     * @return Limited signal
     */
    public static double limit(double signal,double limit){
        if(signal > limit){
            return limit;
        } else if (signal < -limit){
            return -limit;
        }
        return signal;
    }

    /**
     * Limits the signal to [-1, 1]. If it is out of bounds, uses 1 or -1.
     * @param signal
     * @return Limited value
     */
    public static double limit(double signal){
        return limit(signal, 1.0);
    }

    /**
     * Eliminates the deadband of a function given the signal (deadband) This is needed if the stick's travel distance before actuation of the motor is too far.
     * @param deadband
     * @param signal
     * @return Signal without a deadband
     */
    public static double eliminateDeadband(double deadband, double signal){
        if(signal >= 0.0) {
            return (1.0 - deadband) * signal + deadband;
        }else{
            return (deadband - 1.0) * Math.abs(signal) - deadband;
        }
    }

    /**
     * Introduces a deadband of a function given the signal. This is needed if the stick is "too touchy" and moves with slight bumps.
     * @param deadband
     * @param signal
     * @return New signal
     */
    public static double applyDeadband(double deadband, double signal){
        if (Math.abs(signal) > deadband) {
            if (signal > 0.0) {
                return (signal - deadband) / (1.0 - deadband);
            } else {
                return (signal + deadband) / (1.0 - deadband);
            }
        } else {
            return 0.0;
        }
    }
}
