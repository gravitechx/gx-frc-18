package org.gravitechx.frc2018.utils.drivehelpers;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Represents a drive signal using left and right motor values.
 */
public class DifferentialDriveSignal extends DriveSignal {
    private double leftMotorOutput, rightMotorOutput;

    public static final DifferentialDriveSignal BREAK = new DifferentialDriveSignal(0.0, 0.0, true);
    public static final DifferentialDriveSignal NEUTRAL = new DifferentialDriveSignal(0.0, 0.0);

    /**
     * Constructs a drive signal assuming break mode isn't on.
     * @param leftMotorOutput
     * @param rightMotorOutput
     */
    public DifferentialDriveSignal(double leftMotorOutput, double rightMotorOutput){
        super(false);
        this.leftMotorOutput = leftMotorOutput;
        this.rightMotorOutput = rightMotorOutput;
    }

    public DifferentialDriveSignal(double leftMotorOutput, double rightMotorOutput, boolean breakActive){
        super(breakActive);
        this.leftMotorOutput = leftMotorOutput;
        this.rightMotorOutput = rightMotorOutput;
    }

    public void setLeftMotorOutput(double leftMotorOutput) {
        this.leftMotorOutput = leftMotorOutput;
    }

    public void setRightMotorOutput(double rightMotorOutput) {
        this.rightMotorOutput = rightMotorOutput;
    }

    public double getLeftMotorOutput() {
        return leftMotorOutput;
    }

    public double getRightMotorOutput() {
        return rightMotorOutput;
    }

    /**
     * Reduces the rotation proportionally
     */
        public void limitRotation(){
            if (leftMotorOutput > 1.0) {
                rightMotorOutput -= leftMotorOutput - 1.0;
                leftMotorOutput = 1.0;
            } else if (rightMotorOutput > 1.0) {
                leftMotorOutput -= rightMotorOutput - 1.0;
                rightMotorOutput = 1.0;
            } else if (leftMotorOutput < -1.0) {
                rightMotorOutput -= leftMotorOutput + 1.0;
                leftMotorOutput = -1.0;
            } else if (rightMotorOutput < -1.0) {
                leftMotorOutput -= rightMotorOutput + 1.0;
                rightMotorOutput = -1.0;
            }
    }
}
