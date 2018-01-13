package org.gravitechx.frc2018.robot.io.controlschemes;

import edu.wpi.first.wpilibj.Joystick;

import org.gravitechx.frc2018.robot.Constants;


public class DefaultControlScheme extends ControlScheme{


    private static DefaultControlScheme mInstance = new DefaultControlScheme();

    //singleton pattern to prevent multiple instances of DefaultControlScheme

    protected DefaultControlScheme(){
        //prevents initialization
    }

    //returns the instance of DefaultControlScheme
    public static DefaultControlScheme getInstance() {

        return mInstance;

    }
    //returns the throttle of the inputed Joystick (throttle is the Y axis)
    @Override
    public double getThrottle(Joystick throttleStick) {
        return throttleStick.getY();
    }
    //returns the rotation value of the inputed joystick(rotation is the X axis)
    @Override
    public double getWheel(Joystick rotationStick) {
        return rotationStick.getX();
    }
}
