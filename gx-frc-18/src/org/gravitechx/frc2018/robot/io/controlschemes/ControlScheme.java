package org.gravitechx.frc2018.robot.io.controlschemes;

import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;
//an abstract class for all common methods between control schemes

public abstract class ControlScheme {
    public abstract double getThrottle();
    public abstract double getWheel();
    public abstract boolean getQuickTurnButton();
    public abstract RotationalDriveSignal getRotationalDriveSignal();

    public static double transformSignal(double signal, double initialX, double initialY, double finalX, double finalY){
        return (finalY - finalX) / (initialY - initialX) * (signal - initialX) + initialY;
    }
}