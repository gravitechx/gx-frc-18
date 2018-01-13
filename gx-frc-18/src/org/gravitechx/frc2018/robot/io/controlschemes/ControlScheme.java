package org.gravitechx.frc2018.robot.io.controlschemes;

import edu.wpi.first.wpilibj.Joystick;
//an abstract class for all common methods between control schemes

import edu.wpi.first.wpilibj.Joystick;

public abstract class ControlScheme {
    public abstract ControlScheme getInstance();
    public abstract double getThrottle(Joystick throttleStick);
    public abstract double getWheel(Joystick rotationStick);


}
