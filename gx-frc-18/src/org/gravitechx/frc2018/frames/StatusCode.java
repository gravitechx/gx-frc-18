package org.gravitechx.frc2018.frames;

public enum StatusCode {
    START(0, "Connection Established"),
    STOP(1, "Connection Ended"),
    DISABLED(2, "Robot Disable"),
    ERROR(3, "Robot Error"),
    CUSTOM(4, "Custom");

    private String message;
    private int id;

    public int getId() {
        return id;
    }

    StatusCode(int id, String message){
        this.id = id;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static StatusCode getByID(int id){
        return StatusCode.values()[id];
    }
}
