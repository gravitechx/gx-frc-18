package org.gravitechx.frc2018.frames;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonGenerator;
import java.io.PrintWriter;
import java.io.StringReader;

abstract public class Frame {
    private FrameType mType;
    private Timestamp timestamp;

    public Frame(FrameType mType, Timestamp timestamp){
        this.mType = mType;
        this.timestamp = timestamp;
    }

    public Frame(JsonObject baseObject){
        mType = FrameType.getByID(baseObject.getInt("FRAME_ID"));
        timestamp = new LocalTimestamp(baseObject.getJsonNumber("TIME").doubleValue());
    }

    protected abstract JsonGenerator encode(JsonGenerator generator);

    private JsonGenerator wrap(JsonGenerator generator) {
        return encode(
                generator.writeStartObject()
                        .write("FRAME_ID", mType.getId())
                        .write("TIME", timestamp.get()))
                .writeEnd();
    }

    public FrameType getType() {
        return mType;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    private static Frame unwrap(JsonReader parser){
        JsonObject baseObject = parser.readObject();
        FrameType type = FrameType.getByID(baseObject.getInt("FRAME_ID"));
        Frame frame;
        switch (type){
            case STATUS:
                frame = new StatusFrame(baseObject);
                break;
            case AMP:
                frame = new AmpFrame(baseObject);
                break;
            default:
                frame = new ErrorFrame(new LocalTimestamp(), "No frame ID.");
        }

        return frame;
    }

    public void send(PrintWriter out){
        wrap(Json.createGenerator(out)).flush();
        out.print("\n");
    }

    public static Frame toFrame(String str){
        JsonReader parser = Json.createReader(new StringReader(str));
        return unwrap(parser);
    }
}
