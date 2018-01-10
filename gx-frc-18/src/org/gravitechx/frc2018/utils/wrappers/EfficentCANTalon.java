package org.gravitechx.frc2018.utils.wrappers;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

/**
 * Wraps a efficient version of the CANTalon as done my the CheesyPoofs.
 * Basically the Talon class makes multiple calls setting the Talons at the same speed.
 */
public class EfficentCANTalon extends CANTalon {
    protected double mLastSet = Double.NaN;
    protected TalonControlMode mLastControlMode = null;

    public EfficentCANTalon(int deviceNumber, int controlPeriodMs, int enablePeriodMs){
        super(deviceNumber, controlPeriodMs, enablePeriodMs);
    }

    public EfficentCANTalon(int deviceNumber, int controlPeriodMs) {
        super(deviceNumber, controlPeriodMs);
    }


    public EfficentCANTalon(int deviceNumber){
        super(deviceNumber);
    }

    @Override
    public void set(double value){
        // Don't write the value to the CAN bus if it is the same as the one before it.
        if(value != mLastSet || getControlMode() != mLastControlMode){
            mLastSet = value;
            mLastControlMode = getControlMode();
            super.set(value);
        }
    }
}
