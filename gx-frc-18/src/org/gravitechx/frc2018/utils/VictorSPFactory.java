package org.gravitechx.frc2018.utils;

import edu.wpi.first.wpilibj.VictorSP;
import org.gravitechx.frc2018.utils.motorconfigs.VictorSPConfig;

import static org.gravitechx.frc2018.utils.VictorSPFactory.configureVictorSP;

/**
 * Constructs Victor objects using various configurations.
 */
public class VictorSPFactory {
    /**
     * Creates a victor given the config specified in @see{TalonConfig}.
     * @param config
     * @return
     */
    public static VictorSP createVictorSP(int pwmPort, VictorSPConfig config) {
        return configureVictorSP(new VictorSP(pwmPort), config);
    }

    /**
     * Creates a talon using the default configuration and the PWM port.
     * @param pwmPort
     * @return
     */
    public static VictorSP createDefaultVictor(int pwmPort){
        return createVictorSP(pwmPort, new VictorSPConfig());
    }

    public static VictorSP configureVictorSP(VictorSP victor, VictorSPConfig config){
        victor.setInverted(config.INVERTED);
        victor.setSafetyEnabled(config.MOTOR_SAFTEY_ENABLED);
        victor.enableDeadbandElimination(config.ENABLE_DEADBAND_ELIMINATION);
        victor.setExpiration(config.EXPIRATION_TIMEOUT_SECONDS);
        return victor;
    }
}
