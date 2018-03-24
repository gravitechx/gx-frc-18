package org.gravitechx.frc2018.utils.visionhelpers;

public class VisionInfo {

    private double box_distance,tape_distance;
    private double box_angle,tape_angle;
    private double box_offset, tape_offset;

    public VisionInfo(double bdistance, double bangle, double boffset, double tdistance, double tangle, double toffset){
        box_distance = bdistance;
        box_angle = bangle;
        box_offset = boffset;
        tape_distance = tdistance;
        tape_angle = tangle;
        tape_offset = toffset;
    }

    public double getBoxDistance() {
        return box_distance;
    }

    public double getBoxAngle() {
        return box_angle;
    }

    public double getBoxOffset() {
        return box_offset;
    }
    public double getTapeDistance() {
        return tape_distance;
    }
    public double getTapeAngle() {
        return tape_angle;
    }
    public double getTapeOffset() {
        return tape_offset;
    }
}