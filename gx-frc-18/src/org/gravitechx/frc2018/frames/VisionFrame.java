package org.gravitechx.frc2018.frames;

import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;

public class VisionFrame extends Frame {
    double offset;
    double distance;

    /*------------------------------------------------------------.
    |          T I M E S T A M P    C O N S T R U C T O R         |
    |public VisionFrame(Timestamp timestamp, double movement){    |
    |    super(FrameType.AMP, timestamp);                         |
    |   this.movement = movement;                                 |
    |}                                                            |
    -------------------------------------------------------------*/

    public VisionFrame(JsonObject baseObject){
        super(baseObject);
        offset = baseObject.getJsonNumber("OFFSET").doubleValue();
        distance = baseObject.getJsonNumber("DISTANCE").doubleValue();
    }
    @Override
    protected JsonGenerator encode(JsonGenerator generator) {
        return generator.write("OFFSET", offset)
                .write("DISTANCE", distance);
    }

    public double getOffset() {
        return offset;
    }
    public double getDistance(){
        return distance;
    }
}


