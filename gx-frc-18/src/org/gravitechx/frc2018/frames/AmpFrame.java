package org.gravitechx.frc2018.frames;

import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonGenerator;

public class AmpFrame extends Frame {
    double amps;

    public AmpFrame(Timestamp timestamp, double amps){
        super(FrameType.AMP, timestamp);
        this.amps = amps;
    }

    public AmpFrame(JsonObject baseObject){
        super(baseObject);
        amps = baseObject.getJsonNumber("AMPS").doubleValue();
    }

    @Override
    protected JsonGenerator encode(JsonGenerator generator){
        return generator.write("AMPS", amps);
    }

    public double getAmps() {
        return amps;
    }

}
