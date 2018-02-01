package org.gravitechx.frc2018.utils.motorconfigs;

/**
 * Holds all the PID values for a profile.
 * Public variables for kP, kI, kD and kF
 */
public class PIDConfig {
    public double kP;
    public double kI;
    public double kD;
    public double kF;

    public PIDConfig(double kP, double kI, double kD, double kF, double scalingFactor){
        this(scalingFactor * kP, scalingFactor * kI, scalingFactor * kD, scalingFactor * kF);
    }

    public PIDConfig(double kP, double kI, double kD, double kF){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }
}
