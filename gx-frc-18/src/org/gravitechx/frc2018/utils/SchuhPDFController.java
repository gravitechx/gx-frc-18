package org.gravitechx.frc2018.utils;

import org.gravitechx.frc2018.utils.motorconfigs.PIDFConfig;

public class SchuhPDFController {
    private PIDFConfig pid;
    private double result = 0.0;
    private double error = 0.0;
    private double lastError = 0.0;
    private double lastTime = 0.0;
    private double dt = 0.0;

    private double lastkD = 0.0;

    private double positionSetPoint = 0.0;
    private double velocitySetPoint = 0.0;
    private double accelerationSetPoint = 0.0;

    private double velocityRampRate = 1.0;
    private double accelerationRampRate = 1.0;


    private double upperBound = 1.0, lowerBound = -1.0;


    public SchuhPDFController(PIDFConfig pid) {
        this.pid = pid;
    }

    public double daccum = 0.0;

    public void run(double accPosition, double time) {
        error = positionSetPoint - accPosition;

        dt = time - lastTime;

        if (dt < 1E-6)
            dt = 1E-6;

        if (velocitySetPoint >= velocityRampRate) velocitySetPoint = velocityRampRate;
        if (accelerationSetPoint >= accelerationRampRate) accelerationSetPoint = accelerationRampRate;

        result = pid.kP * error + pid.kD * ((error - lastError) / dt - velocityRampRate)
                + pid.kV * velocitySetPoint + pid.kA * accelerationSetPoint;

        lastkD = pid.kD * (error - lastError) / dt;

        if (result > upperBound) {
            result = upperBound;
        } else if (result < lowerBound) {
            result = lowerBound;
        }

        lastTime = time;
        lastError = error;
    }

    public void setSetpoints(double positionSetPoint, double velocitySetPoint, double accelerationSetPoint){
        this.positionSetPoint = positionSetPoint;
        this.velocitySetPoint = velocitySetPoint;
        this.accelerationSetPoint = accelerationSetPoint;
    }

    public void reset(double time){
        result = 0.0;
        error = 0.0;
        lastError = 0.0;
        lastTime = time;
    }

    public double get(){
        return result;
    }

    public double getError() {
        return error;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getKP(){
        return pid.kP * error;
    }

    public double getKD(){
        return lastkD;
    }

    public double getKV() { return pid.kV * velocitySetPoint; }

    public double getKA() { return pid.kA * accelerationSetPoint; }

    public void setKP(double kp) { pid.kP = kp; }
    public void setKD(double kd) { pid.kD = kd; }
    public void setKV(double kv){ pid.kV = kv; }
    public void setKA(double ka){ pid.kA = ka; }

    public double getPositionSetPoint() {
        return positionSetPoint;
    }

    public void setPositionSetPoint(double positionSetPoint) {
        this.positionSetPoint = positionSetPoint;
    }

    public double getVelocitySetPoint() {
        return velocitySetPoint;
    }

    public void setVelocitySetPoint(double velocitySetPoint) {
        this.velocitySetPoint = velocitySetPoint;
    }

    public double getAccelerationSetPoint() {
        return accelerationSetPoint;
    }

    public void setAccelerationSetPoint(double accelerationSetPoint) {
        this.accelerationSetPoint = accelerationSetPoint;
    }

    public double getVelocityRampRate() {
        return velocityRampRate;
    }

    public void setVelocityRampRate(double velocityRampRate) {
        this.velocityRampRate = velocityRampRate;
    }

    public double getAccelerationRampRate() {
        return accelerationRampRate;
    }

    public void setAccelerationRampRate(double accelerationRampRate) {
        this.accelerationRampRate = accelerationRampRate;
    }
}
