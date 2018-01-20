package org.gravitechx.frc2018.utils.wrappers;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * Basic wrapper to the Talon that adds the ability for it to direct the talon.
 * @see EfficientTalonSRX
 * @see SpeedController
 */
public class MasterTalonSRX extends EfficientTalonSRX {
    private SpeedController mSlave;

    /**
     * Initiates a Talon and Slave speed controller using the
     * @param deviceNumber
     * @param mSlave
     */
    public MasterTalonSRX(int deviceNumber, SpeedController mSlave){
        super(deviceNumber);
        this.mSlave = mSlave;
    }

    /**
     * Sets the master and slave talons to the same value.
     * @param value
     */
    @Override
    public void set(double value){
        super.set(value);
        mSlave.set(value);
    }

    /**
     * Returns the slave speed controller.
     * @return the slave speed controller
     */
    public SpeedController getSlave(){
        return mSlave;
    }
}
