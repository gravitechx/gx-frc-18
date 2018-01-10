package org.gravitechx.frc2018.utils.motorconfigs;

import edu.wpi.first.wpilibj.MotorSafety;

/**
 * Holds all configuration options for a Victor motor controller.
 */
public class VictorSPConfig extends MotorConfig {
    public boolean MOTOR_SAFTEY_ENABLED = false;
    public boolean ENABLE_DEADBAND_ELIMINATION = false;
    public double EXPIRATION_TIMEOUT_SECONDS = MotorSafety.DEFAULT_SAFETY_EXPIRATION;

    public VictorSPConfig(int port) {
        super(port);
    }
}
