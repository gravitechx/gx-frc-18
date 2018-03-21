package org.gravitechx.frc2018.frames;

import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;

public class VisionFrame extends Frame {
    private double boxOffset;
    private double boxDistance;
    private double tapeOffset;
    private double tapeDistance;

    /*------------------------------------------------------------.
    |          T I M E S T A M P    C O N S T R U C T O R         |
    |public VisionFrame(Timestamp timestamp, double movement){    |
    |    super(FrameType.AMP, timestamp);                         |
    |   this.movement = movement;                                 |
    |}                                                            |
    -------------------------------------------------------------*/

    public VisionFrame(JsonObject baseObject){
        super(baseObject);
        boxOffset = baseObject.getJsonNumber("BOX_OFFSET").doubleValue();
        boxDistance = baseObject.getJsonNumber("BOX_DISTANCE").doubleValue();
        tapeOffset = baseObject.getJsonNumber("TAPE_OFFSET").doubleValue();
        tapeDistance = baseObject.getJsonNumber("TAPE_DISTANCE").doubleValue();
        System.out.println( boxOffset + ", " + boxDistance + ", " + tapeOffset+ ", " + tapeDistance);
    }
    @Override
    protected JsonGenerator encode(JsonGenerator generator) {
        return generator.write("BOX_OFFSET", boxOffset)
                .write("BOX_DISTANCE", boxDistance).write("TAPE_OFFSET", tapeOffset).write("TAPE_DISTANCE", tapeDistance);
    }

    public double getBoxOffset() {
        return boxOffset;
    }
    public double getBoxDistance(){
        return boxDistance;
    }
    public double getTapeOffset(){
        return tapeOffset;
    }
    public double getTapeDistance(){
        return tapeDistance;
    }
}


