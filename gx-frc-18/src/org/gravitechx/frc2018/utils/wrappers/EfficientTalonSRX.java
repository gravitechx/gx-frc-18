package org.gravitechx.frc2018.utils.wrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Wraps a efficient version of the TalonSRX as done my the CheesyPoofs.
 * Basically the Talon class makes multiple calls setting the Talons at the same speed.
 */
public class EfficientTalonSRX extends WPI_TalonSRX {
    protected double mLastSet = Double.NaN;
    protected ControlMode mLastControlMode = null;

    public EfficientTalonSRX(int deviceNumber){
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
