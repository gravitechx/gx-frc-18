package org.gravitechx.frc2018.utils.motorconfigs;

import edu.wpi.first.wpilibj.Talon;

public class TalonPIDConfig extends PIDConfig {
    public int PID_ID = 0;

    public TalonPIDConfig(double kP, double kI, double kD, double kF){
        super(kP, kI, kD, kF);
    }
}
