package org.gravitechx.frc2018.utils.drivehelpers;

import java.util.function.UnaryOperator;

public class RotationalDriveSignal extends DriveSignal {
    private double xSpeed, zRotation;

    public static final RotationalDriveSignal NEUTRAL = new RotationalDriveSignal(0.0, 0.0);
    public static final RotationalDriveSignal BREAK = new RotationalDriveSignal(0.0, 0.0, true);

    public RotationalDriveSignal(double xSpeed, double zRotation){
        super(false);
        this.xSpeed = xSpeed;
        this.zRotation = zRotation;
    }

    public RotationalDriveSignal(double xSpeed, double zRotation, boolean breakActive){
        super(breakActive);
        this.xSpeed = xSpeed;
        this.zRotation = zRotation;
    }

    public double getZRoation(){
        return zRotation;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    /**
     * Normalizes the speeds and rotation to [-1, 1].
     */
    public void limit() {
        xSpeed = DriveSignal.limit(xSpeed);
        zRotation = DriveSignal.limit(zRotation);
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setzRotation(double zRotation) {
        this.zRotation = zRotation;
    }

    /**
     * Converts a rotational drive signal to a differential drive signal.
     * @param rotationIsOverpowered specifies whether the rotation should be reduced.
     * @return
     */
    public DifferentialDriveSignal toDifferencialDriveSignal(){
        double leftMotorOutput = xSpeed + zRotation;
        double rightMotorOutput = xSpeed - zRotation;

        return new DifferentialDriveSignal(leftMotorOutput, rightMotorOutput);
    }

    @Override
    public String toString() {
        return "xSpeed: " + Double.toString(xSpeed) + " zRotation: " + Double.toString(zRotation) + "\n";
    }

    /**
     * Motifies the zRotation using the provided function.
     * @param transpositionFunction
     */
    public void transposeZRotation(UnaryOperator<Double> transpositionFunction){
        zRotation = DriveSignal.limit(transpositionFunction.apply(zRotation));
    }
    /**
     * Motifies the xSpeed using the provided function.
     * @param transpositionFunction
     */
    public void transposeXSpeed(UnaryOperator<Double> transpositionFunction){
        xSpeed = DriveSignal.limit(transpositionFunction.apply(xSpeed));
    }

    /**
     * Applies a deadband to the xSpeed and yRotation
     */

    public void applyXZDeadband(double xDeaband, double zDeadband){
        xSpeed = DriveSignal.applyDeadband(xDeaband, xSpeed);
        zRotation = DriveSignal.applyDeadband(zDeadband, zRotation);
    }
}
