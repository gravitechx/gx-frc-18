package org.gravitechx.frc2018.utils.motorconfigs;

/**
 * Inheretance class for all Motor Configs.
 */
public class MotorConfig {
    public int PORT;
    public boolean INVERTED = false;

    public MotorConfig(int port){
        this.PORT = port;
    }
}
