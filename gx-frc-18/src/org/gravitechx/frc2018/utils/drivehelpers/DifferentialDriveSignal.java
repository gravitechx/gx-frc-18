package org.gravitechx.frc2018.utils.drivehelpers;

public class DifferentialDriveSignal extends DriveSignal {
    public double leftMotorOutput, rightMotorOutput;

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
     * Reduces drive signal to motor values between 100% speed and -100% speed.
     */
    public void normalize(){
        if(leftMotorOutput > 1.0){
            rightMotorOutput -= leftMotorOutput - 1.0;
            leftMotorOutput = 1.0;
        }else if(leftMotorOutput < -1.0){
            rightMotorOutput -= leftMotorOutput + 1.0;
            leftMotorOutput = -1.0;
        }else if(rightMotorOutput > 1.0){
            leftMotorOutput -= rightMotorOutput - 1.0;
            rightMotorOutput = 1.0;
        }else if(rightMotorOutput < -1.0){
            rightMotorOutput -= rightMotorOutput + 1.0;
            rightMotorOutput = -1.0;
        }
    }

}
