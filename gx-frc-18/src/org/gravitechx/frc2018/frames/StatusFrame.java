package org.gravitechx.frc2018.frames;

import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;

public class StatusFrame extends Frame {
    private StatusCode mStatusCode;

    public StatusFrame(JsonObject baseObject){
        super(baseObject);
        mStatusCode = StatusCode.getByID(baseObject.getInt("STATUS_CODE"));
    }

    public StatusFrame(Timestamp timestamp, StatusCode statusCode) {
        super(FrameType.STATUS, timestamp);
        mStatusCode = statusCode;
    }

    @Override
    protected JsonGenerator encode(JsonGenerator generator){
        return generator.write("STATUS_CODE", mStatusCode.getId());
    }

    public StatusCode getmStatusCode() {
        return mStatusCode;
    }

}
