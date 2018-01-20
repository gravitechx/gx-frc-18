package org.gravitechx.frc2018.utils.motorconfigs;

import edu.wpi.first.wpilibj.MotorSafety;

/**
 * Holds all configuration options for a Victor motor controller.
 * Allows the ability to save a variety of motor controllers.
 */
public class VictorSPConfig extends MotorConfig {
    public boolean MOTOR_SAFTEY_ENABLED = false;
    public boolean ENABLE_DEADBAND_ELIMINATION = false;
    public double EXPIRATION_TIMEOUT_SECONDS = MotorSafety.DEFAULT_SAFETY_EXPIRATION;
}
