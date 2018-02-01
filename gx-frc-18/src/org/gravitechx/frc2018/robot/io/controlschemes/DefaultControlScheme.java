package org.gravitechx.frc2018.robot.io.controlschemes;

import edu.wpi.first.wpilibj.Joystick;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;

public class DefaultControlScheme extends ControlScheme {
    private static Joystick rotationStick;
    private static Joystick throttleStick;
    private boolean isReversed = Constants.REVERSE_THROTTLE_STICK;

    //singleton pattern to prevent multiple instances of DefaultControlScheme
    private static DefaultControlScheme mInstance = new DefaultControlScheme();
    public static DefaultControlScheme getInstance() { return mInstance; }

    protected DefaultControlScheme(){
        rotationStick = new Joystick(Constants.ROTATION_STICK_PORT);
        throttleStick = new Joystick(Constants.THROTTLE_STICK_PORT);
    }

    // Returns the throttle of the inputted Joystick (throttle is the Y axis)
    @Override
    public double getThrottle() {
<<<<<<< HEAD
        return ControlScheme.transformSignal(rotationStick.getY(), -1, 1, 0, 1);
=======
        return isReversed ? throttleStick.getY() : -1 * throttleStick.getY();
    }

    public boolean isReversed() {
        return isReversed;
>>>>>>> io
    }

    public void setReversed(boolean reversed) {
        isReversed = reversed;
    }

    // Returns the rotation value of the inputted joystick(rotation is the X axis)
    @Override
    public double getWheel() {
        return rotationStick.getX();
    }

    //returns the rotation stick

    public static Joystick getRotationStick (){
        return rotationStick;
    }

    //returns the throttle stick

    public static Joystick getThrottleStick (){
        return throttleStick;
    }

    // Returns the rotational drive signal for the inputs of the throttleStick Y value and rotationStick X value
    public RotationalDriveSignal getRotationalDriveSignal(){
        return new RotationalDriveSignal(getThrottle(), getWheel());
    }

    @Override
    public boolean getQuickTurnButton() {
        return throttleStick.getRawButton(Constants.IO_QUICK_TURN_BUTTON);
    }
}
