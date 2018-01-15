package utils.drivehelpers;

import org.gravitechx.frc2018.utils.drivehelpers.DriveSignal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test all generalized signal functions.")
class DriveSignalTest {
    @Test
    @DisplayName("Test that the limit function works correctly.")
    public void limit() {
        assertEquals(1.0, DriveSignal.limit(1.2), "Limit 1.2 to 1.");
        assertEquals(-1.0, DriveSignal.limit(-1.2), "Limit -1.2 to -1");
        assertEquals(.5, DriveSignal.limit(.5), "Limit for .5 gives .5");
        assertEquals(-.5, DriveSignal.limit(-.5), "Limit for -.5 gives -.5");
    }

    @Test
    @DisplayName("Tests a the limit function with a non-one value.")
    public void doubleLimit() {
        assertEquals(2.0, DriveSignal.limit(7.8, 2.0), "Limits 7.8 to 2.0");
        assertEquals(-2.0, DriveSignal.limit(-7.8, 2.0), "Limits -7.8 to -2.0");
        assertEquals(1.3, DriveSignal.limit(2.0, 1.3), "Limits 1.3 to 2.0");
        assertEquals(-1.3, DriveSignal.limit(2.0, -1.3), "Limits -1.3 to 2.0");
    }

    @Test
    @DisplayName("Testing that the eliminate deadband function works in positive and negative contexts.")
    public void eliminateDeadband() {
        double[] signalInputs = new double[]{0.0, -0.2, .5, 1.0, -1.0};
        double[] expectedOutputs = new double[]{0.1, -0.28, 0.55, 1.0, -1.0};
        for(int i = 0; i < signalInputs.length; i++){
            assertEquals(expectedOutputs[i], DriveSignal.eliminateDeadband(0.1, signalInputs[i]));
        }
    }

    @Test
    @DisplayName("Testing that the apply deadband function works in positive and negative contextes.")
    public void applyDeadband() {
        double[] signalInputs = new double[]{0.0, 0.05, -0.05, -0.2, .5, 1.0, -1.0, -.5};
        double[] expectedOutputs = new double[]{0.0, 0.0, 0.0, -0.1111, .4444, 1.0, -1.0, -.4444};

        for(int i = 0; i < signalInputs.length; i++){
            assertEquals(expectedOutputs[i], DriveSignal.applyDeadband(.1, signalInputs[i]), .001);
        }
    }
}