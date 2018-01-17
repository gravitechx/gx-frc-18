package org.gravitechx.frc2018.utils.wrappers;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * Basic wrapper to the Talon that adds the ability for it to direct the talon.
 */
public class MasterTalonSRX extends EfficientTalonSRX {
    SpeedController slave;

    public MasterTalonSRX(int deviceNumber, SpeedController slave){
        super(deviceNumber);
        this.slave = slave;
    }

    // Set the master and slave to the slave value
    @Override
    public void set(double value){
        super.set(value);
        slave.set(value);
    }
}
