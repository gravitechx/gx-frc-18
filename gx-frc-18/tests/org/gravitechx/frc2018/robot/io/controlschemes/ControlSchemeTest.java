package org.gravitechx.frc2018.robot.io.controlschemes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControlSchemeTest {
    @Test
    public void transformSignal(){

        /* Simple [-1, 1] to [0, 1] translation */

        assertEquals(0.5,
                ControlScheme.transformSignal(0,
                        -1,
                        1,
                        0,
                        1)
        );

        assertEquals(0,
                ControlScheme.transformSignal(
                        -1,
                        -1,
                        1,
                        0,
                        1)
        );

        assertEquals(1,
                ControlScheme.transformSignal(
                        1,
                        -1,
                        1,
                        0,
                        1)
        );

        /* More Complex [-1.5, .5] to [-1, 0] translation */

        assertEquals(-1,
                ControlScheme.transformSignal(-1.5,
                        -1.5,
                        .5,
                        -1,
                        1)
        );

        assertEquals(0,
                ControlScheme.transformSignal(-.5,
                        -1.5,
                        .5,
                        -1,
                        1)
        );

        assertEquals(1.0,
                ControlScheme.transformSignal(.5,
                        -1.5,
                        .5,
                        -1,
                        1)
        );
    }
}