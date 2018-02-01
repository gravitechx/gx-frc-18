package org.gravitechx.frc2018.robot.io.controlschemes;
<<<<<<< HEAD
=======

>>>>>>> io
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;

//an abstract class for all common methods between control schemes

public abstract class ControlScheme {
    public abstract double getThrottle();
    public abstract double getWheel();
    public abstract boolean getQuickTurnButton();
    public abstract RotationalDriveSignal getRotationalDriveSignal();

    /**
     * Transforms the signal from one domain to anther domain using a simple linear equation.
     * @param signal Input signal
     * @param initialLowerBound current lower bound
     * @param initialUpperBound current upper bound
     * @param finalUpperBound desired lower bound
     * @param finalLowerBound desired upper bound
     * @return
     */
    public static double transformSignal(double signal, double initialLowerBound, double initialUpperBound, double finalLowerBound, double finalUpperBound){
        return (finalUpperBound - finalLowerBound) / (initialUpperBound - initialLowerBound) * (signal - initialLowerBound) + finalLowerBound;
    }
}