package org.gravitechx.frc2018.robot.io.controlschemes;

import edu.wpi.first.wpilibj.Joystick;

import org.gravitechx.frc2018.robot.Constants;


public class DefaultControlScheme extends ControlScheme{
    Joystick throttleStick = new Joystick(Constants.THROTTLE_STICK_PORT);
    Joystick rotationStick = new Joystick(Constants.ROTATION_STICK_PORT);

    private DefaultControlScheme mInstance = new DefaultControlScheme();

    //singleton pattern to prevent multiple instances of DefaultControlScheme

    protected DefaultControlScheme(){
        //prevents initialization
    }

    //returns the instance of DefaultControlScheme
    @Override
    public ControlScheme getInstance() {

        return mInstance;

    }
    //returns the throttle of the inputed Joystick (throttle is the Y axis)
    @Override
    public double getThrottle(Joystick throttleStick) {
        throttleStick = this.throttleStick;
        return throttleStick.getY();
    }
    //returns the rotation value of the inputed joystick(rotation is the X axis)
    @Override
    public double getWheel(Joystick rotationStick) {
        rotationStick = this.rotationStick;
        return rotationStick.getX();
    }
}
