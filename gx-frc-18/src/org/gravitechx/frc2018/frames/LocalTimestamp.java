package org.gravitechx.frc2018.frames;

/**
 * Represents a time in seconds.
 * Uses getFPGATimestamp from Timer
 */
public class LocalTimestamp implements Timestamp {
    private double time_S;
    private double dt_S;

    /**
     * Set the time manually.
     * @param time
     */
    public LocalTimestamp(double time){
        this.time_S = time;
    }

    /**
     * Set the time to the current time from the FPGA.
     */
    public LocalTimestamp(){
        time_S = System.currentTimeMillis() / 1000.0;
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
        dt_S = System.currentTimeMillis() / 1000.0 - time_S;
        time_S = System.currentTimeMillis() / 1000.0;
    }
}
