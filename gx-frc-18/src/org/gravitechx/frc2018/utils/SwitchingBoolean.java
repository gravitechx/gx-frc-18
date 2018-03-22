package org.gravitechx.frc2018.utils;

import org.gravitechx.frc2018.utils.looping.RemoteTimestamp;

public class SwitchingBoolean {
    boolean value;
    RemoteTimestamp timestamp;
    double timestep;

    public SwitchingBoolean(boolean value, double timestamp){
        this.value = value;
        this.timestamp = new RemoteTimestamp();
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
