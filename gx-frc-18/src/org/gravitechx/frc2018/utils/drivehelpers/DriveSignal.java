package org.gravitechx.frc2018.utils.drivehelpers;

public class DriveSignal {
    private boolean breakActive;

    public DriveSignal(boolean breakActive){
        this.breakActive = breakActive;
    }

    public boolean isBreakActive() {
        return breakActive;
    }

    public void setBreakActive(boolean breakActive) {
        this.breakActive = breakActive;
    }

    /**
     * Transposes a function to [-1, 1]
     */
    public static double limit(double signal){
        if(signal > 1.0){
            return 1.0;
        } else if (signal < -1.0){
            return -1.0;
        }

        return signal;
    }

    /**
     * Eliminates the deadband of a function given the signal (deadband)
     * @param deadband
     * @param signal
     * @return
     */
    public static double eliminateDeadband(double deadband, double signal){
        if(signal > 0.0) {
            return (1.0 - deadband) * signal + deadband;
        }else if(signal < 0.0){
            return (deadband - 1.0) * Math.abs(signal) - deadband;
        }
        return 0.0;
    }

    /**
     * Introduces a deadband of a function given the signal
     * @param deadband
     * @param signal
     * @return
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
