package org.gravitechx.frc2018.utils.visionhelpers;

public class VisionInfo {
    private double distance;
    private double angle;
    private double offset;

    public VisionInfo(double distance, double angle, double offset){
        this.distance = distance;
        this.angle = angle;
        this.offset = offset;
    }

    public double getDistance() {
        return distance;
    }

    public double getAngle() {
        return angle;
    }

    public double getOffset() {
        return offset;
    }
}
