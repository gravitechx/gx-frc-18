package org.gravitechx.frc2018.utils.wrappers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedController;
import org.gravitechx.frc2018.robot.Constants;

public class SafeMotorController <C extends SpeedController> implements SpeedController {
    C mMotorController;
    PowerDistributionPanel pdp;
    private double mMaximumAmperage;
    private int channel;

    public SafeMotorController(C motorController, PowerDistributionPanel pdp, int channel, double maxAmperage){
        mMotorController = motorController;
        this.pdp = pdp;
        this.channel = channel;
        this.mMaximumAmperage = maxAmperage;
    }

    public SafeMotorController(C motorController, PowerDistributionPanel pdp, int channel){
        this(motorController, pdp, channel, Constants.MAX_AMPERAGE);
    }

    /**
     * Sets the speed if it is deemed safe by the amperage limit
     * @param speed speed to set
     */
    public void set(double speed){
        if(pdp.getCurrent(channel) < mMaximumAmperage) mMotorController.set(speed);
    }

    public double get(){
        return mMotorController.get();
    }

    public void setInverted(boolean isInverted){
        mMotorController.setInverted(isInverted);
    }

    public boolean getInverted(){
        return mMotorController.getInverted();
    }

    public void disable(){
        mMotorController.disable();
    }

    public void stopMotor(){
        mMotorController.stopMotor();
    }

    public void pidWrite(double input){
        if(pdp.getCurrent(channel) < mMaximumAmperage) pidWrite(input);
    }
}
