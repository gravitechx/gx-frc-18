package org.gravitechx.frc2018.utils.drivehelpers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrivePipelineTest {

    @Test
    @DisplayName("If the drive function works, a input of zero should output zero on both motors.")
    public void zeroIterationTest(){
        DrivePipeline dp = new DrivePipeline();
        RotationalDriveSignal rs = RotationalDriveSignal.NEUTRAL;
        DifferentialDriveSignal ds = dp.apply(rs, true);
        for(int i = 0; i < 40; i++){
            for(int f = 0; f < 100; f++) {
                ds = dp.apply(rs, true);
            }
            assertEquals(0.0, ds.getLeftMotorOutput(), 1.0e-4);
            assertEquals(0.0, ds.getRightMotorOutput(), 1.0e-4);
        }
    }

    @Test
    @DisplayName("For any input, the output should always between 1 and -1 on both motors.")
    void outputIterationTest() {
        DrivePipeline dp = new DrivePipeline();
        DifferentialDriveSignal ds;
        for(double t = -2.0; t >= 2.0; t =+ .01) {
            for (double i = -2.0; i >= 2.0; i = +.01) {
                ds = dp.apply(new RotationalDriveSignal(t, i), true);
                System.out.printf("Left: %.2f, Right: %.2f\n", ds.getLeftMotorOutput(), ds.getRightMotorOutput());
                assertTrue(ds.getLeftMotorOutput() <= 1.0 && ds.getLeftMotorOutput() >= -1.0);
                assertTrue(ds.getRightMotorOutput() <= 1.0 && ds.getRightMotorOutput() >= -1.0);
            }
        }
    }
}