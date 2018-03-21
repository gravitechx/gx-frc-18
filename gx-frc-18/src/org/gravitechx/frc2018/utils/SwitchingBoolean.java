package org.gravitechx.frc2018.utils;

import org.gravitechx.frc2018.utils.looping.Timestamp;

public class SwitchingBoolean {
    boolean value;
    Timestamp timestamp;
    double timestep;

    public SwitchingBoolean(boolean value, double timestamp){
        this.value = value;
        this.timestamp = new Timestamp();
        this.timestep = timestamp;
    }

    public void update(boolean value){
        if(timestamp.cdt() > timestep){
            timestamp.update();
            this.value = value;
        }
    }

    public void switchValue(){
        update(!value);
    }

    public boolean getValue(){
        return value;
    }
}
