package org.gravitechx.frc2018.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PIDControllerTest {

    @Test
    void numericalOutput(){
        PIDController pidController = new PIDController(.5, .0002, .25, .1);
        pidController.setSetPoint(.5);
        for (double i = 0.0; i < 10.0; i += .02){
            pidController.run(2.0 * i, i);
        }

        assertNotEquals(Double.NaN, pidController.get());
    }
}