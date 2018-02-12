package org.gravitechx.frc2018.robot.io.controlschemes;

import edu.wpi.first.wpilibj.Joystick;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.utils.SwitchingBoolean;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;

public class DefaultControlScheme extends ControlScheme {
    private static Joystick rotationStick;
    private static Joystick throttleStick;
    private static Joystick primaryLift;
    private static Joystick secondaryLift;
    private boolean isReversed = Constants.REVERSE_THROTTLE_STICK;
    private SwitchingBoolean grabbingBoolean;

    //singleton pattern to prevent multiple instances of DefaultControlScheme
    private static DefaultControlScheme mInstance = new DefaultControlScheme();
    public static DefaultControlScheme getInstance() { return mInstance; }

    protected DefaultControlScheme(){
        rotationStick = new Joystick(Constants.ROTATION_STICK_PORT);
        throttleStick = new Joystick(Constants.THROTTLE_STICK_PORT);
        primaryLift = new Joystick(Constants.PRIMARY_LIFT_STICK_PORT);
        secondaryLift = new Joystick(Constants.SECONDARY_LIFT_STICK_PORT);
        grabbingBoolean = new SwitchingBoolean(true, Constants.BUTTON_SWITCH_SPEED);
    }

    @Override
    public double getLiftPrimaryAxis(){
        return primaryLift.getY();
    }

    @Override
    public double getLiftSecondaryAxis(){
        return secondaryLift.getY();
    }

    // Returns the throttle of the inputted Joystick (throttle is the Y axis)
    @Override
    public double getThrottle() {
        return isReversed ? throttleStick.getY() : -1 * throttleStick.getY();
    }

    public boolean isReversed() {
        return isReversed;
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

    @Override
    public boolean getInhalingButton() {
        return primaryLift.getTrigger();
    }

    @Override
    public boolean getExhalingButton() {
        return secondaryLift.getTrigger();
    }

    @Override
    public boolean getGrabbingButton() {
        return grabbingBoolean.getValue();
    }

    public void update(){
        if(primaryLift.getTop()){
            grabbingBoolean.switchValue();
        }
    }
}
