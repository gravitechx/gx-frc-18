package org.gravitechx.frc2018.robot.io.controlschemes;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;

public class DefaultControlScheme extends ControlScheme {
    private static Joystick rotationStick;

    //singleton pattern to prevent multiple instances of DefaultControlScheme
    private static DefaultControlScheme mInstance = new DefaultControlScheme();
    public static DefaultControlScheme getInstance() { return mInstance; }

    protected DefaultControlScheme(){
        rotationStick = new Joystick(Constants.ROTATION_STICK_PORT);
    }

    // Returns the throttle of the inputted Joystick (throttle is the Y axis)
    @Override
    public double getThrottle() {
        return ControlScheme.transformSignal(rotationStick.getY(), -1, 1, 0, 1);
    }
    // Returns the rotation value of the inputted joystick(rotation is the X axis)
    @Override
    public double getWheel() {
        return rotationStick.getX();
    }

    public static Joystick getRotationStick (){
        return rotationStick;
    }

    // Returns the rotational drive signal for the inputs of the throttleStick Y value and rotationStick X value
    public RotationalDriveSignal getRotationalDriveSignal(){
        return new RotationalDriveSignal(getThrottle(), getWheel());
    }

    @Override
    public boolean getReversedButton() {
        return rotationStick.getRawButton(Constants.IO_REVERSED_BUTTON);
    }

    @Override
    public boolean getQuickTurnButton() {
        return rotationStick.getRawButton(Constants.IO_QUICK_TURN_BUTTON);
    }
}
