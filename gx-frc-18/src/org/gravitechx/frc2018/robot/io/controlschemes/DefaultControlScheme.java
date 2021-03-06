package org.gravitechx.frc2018.robot.io.controlschemes;

import edu.wpi.first.wpilibj.Joystick;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.utils.SwitchingBoolean;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;

public class DefaultControlScheme extends ControlScheme {
    private static Joystick rotationStick;
    private static Joystick throttleStick;
    private static Joystick manualLift;
    private static Joystick automaticLift;
    private boolean isReversed = Constants.REVERSE_THROTTLE_STICK;
    private SwitchingBoolean grabbingBoolean;

    private static double lastManualAxis = 0.0;
    private static double lastTime = 0.0;
    private static double dAxis = 0.0;

    //singleton pattern to prevent multiple instances of DefaultControlScheme
    private static DefaultControlScheme mInstance = new DefaultControlScheme();
    public static DefaultControlScheme getInstance() { return mInstance; }

    protected DefaultControlScheme(){
        rotationStick = new Joystick(Constants.ROTATION_STICK_PORT);
        throttleStick = new Joystick(Constants.THROTTLE_STICK_PORT);
        manualLift = new Joystick(Constants.MANUAL_LIFT_STICK_PORT);
        automaticLift = new Joystick(Constants.AUTOMATIC_LIFT_STICK_PORT);
        grabbingBoolean = new SwitchingBoolean(true, Constants.BUTTON_SWITCH_SPEED);
    }

    public double getDAxis(){
        return dAxis;
    }

    @Override
    public double getLiftManualAxis(){
        if(-manualLift.getY() < -.5){
            return -0.5;
        }else{
            return -manualLift.getY();
        }
    }

    @Override
    public double getLiftAutomaticAxis(){
        return -automaticLift.getY();
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
        return manualLift.getTrigger();
    }

    @Override
    public boolean getExhalingButton() {
        return automaticLift.getTrigger();
    }

    @Override
    public boolean getGrabbingButton() {
        return grabbingBoolean.getValue();
    }

    public void update(double time){
        if(manualLift.getTop()){
            grabbingBoolean.switchValue();
        }
        dAxis = (getLiftManualAxis() - lastManualAxis) /(time - lastTime);
        lastManualAxis = getLiftManualAxis();
        lastTime = time;
    }
}
