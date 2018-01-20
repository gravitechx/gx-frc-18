package org.gravitechx.frc2018.utils.looping;

import edu.wpi.first.wpilibj.Timer;

/**
 * Represents a time in seconds.
 * Uses getFPGATimestamp from Timer
 */
public class Timestamp {
    private double time_S;
    private double dt_S;

    /**
     * Set the time manually.
     * @param time
     */
    public Timestamp(double time){
        this.time_S = time;
    }

    /**
     * Set the time to the current time from the FPGA.
     */
    public Timestamp(){
        time_S = Timer.getFPGATimestamp();
        dt_S = 0.0;
    }

    /**
     * Get the time differential between the last two updates.
     * @return
     */
    public double dt() {
        return dt_S;
    }

    /**
     * Returns the time as a double.
     * @return
     */
    public double get() {
        return time_S;
    }

    /**
     * Update the time value.
     */
    public void update() {
        dt_S = Timer.getFPGATimestamp() - time_S;
        time_S = Timer.getFPGATimestamp();
    }
}
