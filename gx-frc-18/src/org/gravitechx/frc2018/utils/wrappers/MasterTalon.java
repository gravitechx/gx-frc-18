package org.gravitechx.frc2018.utils.wrappers;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * Basic wrapper to the Talon that adds the ability for it to direct the talon.
 */
public class MasterTalon extends EfficentCANTalon {
    SpeedController slave;

    public MasterTalon(int deviceNumber, SpeedController slave, int controlPeriodMs, int enablePeriodMs){
        super(deviceNumber, controlPeriodMs, enablePeriodMs);
        this.slave = slave;
    }

    public MasterTalon(int deviceNumber, SpeedController slave, int controlPeriodMs) {
        super(deviceNumber, controlPeriodMs);
        this.slave = slave;
    }

    public MasterTalon(int deviceNumber, SpeedController slave){
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
