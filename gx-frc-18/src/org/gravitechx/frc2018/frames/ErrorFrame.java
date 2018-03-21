package org.gravitechx.frc2018.frames;

import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;

public class ErrorFrame extends StatusFrame {
    String mCustomMessage;

    public ErrorFrame(Timestamp timestamp, String customMessage){
        super(timestamp, StatusCode.ERROR);
        mCustomMessage = customMessage;
    }

    public ErrorFrame(JsonObject jsonObject){
        super(jsonObject);
        mCustomMessage = jsonObject.getString("CUSTOM_MESSAGE");
    }

    @Override
    protected JsonGenerator encode(JsonGenerator generator) {
        return super.encode(generator)
                .write("CUSTOM_MESSAGE", mCustomMessage);
    }
}
