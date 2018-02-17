package org.gravitechx.frc2018.utils;
import org.gravitechx.frc2018.utils.motorconfigs.PIDConfig;

public class PIDController {
    private PIDConfig pid;
    private double accum = 0.0;
    private double result = 0.0;
    private double error = 0.0;
    private double lastError = 0.0;
    private double lastTime = 0.0;
    private double setPoint = 0.0;
    private double dt = 0.0;

    private double upperBound = 1.0, lowerBound = -1.0;

    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
    }

    public PIDController(double kP, double kI, double kD, double kF){
        this.pid = new PIDConfig(kP, kI, kD, kF);
    }

    public PIDController(PIDConfig pidConfig){
        this.pid = pidConfig;
    }

    public void run(double sensor, double time){
        error = sensor - setPoint;

        dt = time - lastTime;

        if (dt < 1E-6)
            dt = 1E-6;

        accum += dt * error;

        result = pid.kP * error + pid.kD * (error - lastError) / dt
                + pid.kI * accum + pid.kF * setPoint;

        if(result > upperBound){
            result = lowerBound;
        }else if(result < lowerBound){
            result = upperBound;
        }

        lastTime = time;
        lastError = error;
    }

    public void setSetPoint(double setPoint){
        this.setPoint = setPoint;
    }

    public void resetIntegralAccumulator(){
        accum = 0.0;
    }

    public void reset(double time){
        accum = 0.0;
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

    public double getSetPoint() {
        return setPoint;
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
        return pid.kD * (error - lastError) / dt;
    }

    public double getKI(){
        return pid.kI * accum;
    }

    public double getKF(){
        return pid.kF * setPoint;
    }
}
