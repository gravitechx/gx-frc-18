package org.gravitechx.frc2018.frames;

import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;

public class VisionFrame extends Frame {
    private double boxOffset;
    private double boxDistance;
    private double boxAngle;
    private double tapeOffset;
    private double tapeDistance;
    private double tapeAngle;


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
        boxAngle = baseObject.getJsonNumber("BOX_ANGLE").doubleValue();
        tapeOffset = baseObject.getJsonNumber("TAPE_OFFSET").doubleValue();
        tapeDistance = baseObject.getJsonNumber("TAPE_DISTANCE").doubleValue();
        tapeAngle = baseObject.getJsonNumber("TAPE_ANGLE").doubleValue();

        System.out.println( boxOffset + ", " + boxDistance + ", " + boxAngle + ", " + tapeOffset+ ", " + tapeDistance + ", " + tapeAngle);
    }
    @Override
    protected JsonGenerator encode(JsonGenerator generator) {
        return generator.write("BOX_OFFSET", boxOffset)
                .write("BOX_DISTANCE", boxDistance).write("BOX_ANGLE", boxAngle).write("TAPE_OFFSET", tapeOffset).write("TAPE_DISTANCE", tapeDistance).write("TAPE_ANGLE", tapeAngle);
    }

    public double getBoxOffset() {
        return boxOffset;
    }
    public double getBoxDistance(){
        return boxDistance;
    }
    public double getBoxAngle(){
        return boxAngle;
    }
    public double getTapeOffset(){
        return tapeOffset;
    }
    public double getTapeDistance(){
        return tapeDistance;
    }
    public double getTapeAngle(){
        return tapeAngle;
    }
  //  public VisionInfo getVisionInfo(){ return }
}


//