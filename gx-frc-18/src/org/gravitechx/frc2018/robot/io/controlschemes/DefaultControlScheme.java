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

    @Override
    public ControlScheme getInstance() {

        return mInstance;

    }

    @Override
    public double getThrottle(Joystick throttleStick) {
        throttleStick = this.throttleStick;
        return throttleStick.getX();
    }

    @Override
    public double getWheel(Joystick rotationStick) {
        rotationStick = this.rotationStick;
        return rotationStick.getY();
    }
}
