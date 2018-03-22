package org.gravitechx.frc2018.utils.lifthelpers;

import edu.wpi.first.wpilibj.Counter;
import org.gravitechx.frc2018.robot.Constants;

public class ElevatorHallEffect extends Counter {
    private double mOffset = 0.0;

    public ElevatorHallEffect(int dioChannel, double mOffset){
        this(dioChannel);
        this.mOffset = mOffset;
        this.setMaxPeriod(.25);
    }

    public ElevatorHallEffect(int dioChannel){
        super(dioChannel);
    }

    public boolean isPressed(){
        boolean pressed = super.get() > 0;
        super.reset();
        return pressed;
    }

    @Override
    public String toString() {
        return "Is pressed: " + isPressed() + "\n";
    }

    public double getOffset() {
        return mOffset;
    }

    public void setOffset(double offset) {
        this.mOffset = offset;
    }
}
