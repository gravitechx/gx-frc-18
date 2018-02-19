package org.gravitechx.frc2018.frames;

public enum FrameType {
    STATUS (0, "STATUS"),
    AMP (1, "AMP");

    private final int id;
    private final String label;

    FrameType(int id, String label){
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static FrameType getByID(int frameId){
        return FrameType.values()[frameId];
    }
}
