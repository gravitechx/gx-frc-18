package org.gravitechx.frc2018.robot.io.controlschemes;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;

//an abstract class for all common methods between control schemes

public abstract class ControlScheme {
    protected double fuseRatio = Constants.DEFAULT_AXIS_FUSE_RATIO;
    public abstract double getThrottle();
    public abstract double getWheel();
    public abstract boolean getQuickTurnButton();
    public abstract RotationalDriveSignal getRotationalDriveSignal();
    public abstract boolean getInhalingButton();
    public abstract boolean getExhalingButton();
    public abstract boolean getGrabbingButton();
    public abstract void update(double time);
    public abstract double getDAxis();

    public abstract double getLiftManualAxis();
    public abstract double getLiftAutomaticAxis();

    public double getFusedAxis(){
        return fuseAxises(getLiftAutomaticAxis(), getLiftManualAxis(), fuseRatio);
    }

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

    /**
     * Fuses two axises into a single axis.
     */

    public static double fuseAxises(double primarySignal, double secondarySignal, double ratio){
        return ratio * primarySignal + (1 - ratio) * secondarySignal;
    }
}