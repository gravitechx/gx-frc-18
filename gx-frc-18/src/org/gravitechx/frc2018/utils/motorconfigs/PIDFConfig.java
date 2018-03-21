package org.gravitechx.frc2018.utils.motorconfigs;

public class PIDFConfig extends PIDConfig {
    public double kV;
    public double kA;

    public PIDFConfig(double kP, double kI, double kD, double kF, double kV, double kA){
        super(kP, kI, kD, kF);
        this.kV = kV;
        this.kA = kA;
    }

    public PIDFConfig(double kP, double kI, double kD, double kF, double kV, double kA, double scalingFactor){
        this(kP * scalingFactor, kI * scalingFactor, kD * scalingFactor,
                kF * scalingFactor, kV *scalingFactor, kA * scalingFactor);
    }
}
