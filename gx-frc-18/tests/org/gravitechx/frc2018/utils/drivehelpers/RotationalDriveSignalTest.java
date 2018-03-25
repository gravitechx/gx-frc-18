package org.gravitechx.frc2018.utils.drivehelpers;

import org.junit.Test;

import java.util.function.UnaryOperator;

import static junit.framework.TestCase.assertEquals;

class RotationalDriveSignalTest {
    @Test
    void limit() {
        RotationalDriveSignal overDrive = new RotationalDriveSignal(1.2, -1.2);
        overDrive.limit();
        assertEquals(overDrive.getXSpeed(), 1.0);
        assertEquals(overDrive.getZRoation(), -1.0);

        RotationalDriveSignal normalDrive = new RotationalDriveSignal(-.2, .6);
        normalDrive.limit();
        assertEquals(normalDrive.getXSpeed(), -.2);
        assertEquals(normalDrive.getZRoation(), .6);
    }

    @Test
    void transposeXSpeed() {
        UnaryOperator<Double> squaredInputs = new UnaryOperator<Double>(){
            @Override
            public Double apply(Double signal) {
                if(signal > 0.0){
                    return signal * signal;
                }else{
                    return -1 * signal * signal;
                }
            }
        };

        RotationalDriveSignal slowTurn = new RotationalDriveSignal(0.1, -0.2);
        slowTurn.transposeXSpeed(squaredInputs);

        assertEquals(slowTurn.getXSpeed(), 0.01, 1.0e-6);
    }

    @Test
    void transposeZRotation() {
        UnaryOperator<Double> squaredInputs = new UnaryOperator<Double>(){
            @Override
            public Double apply(Double signal) {
                if(signal > 0.0){
                    return signal * signal;
                }else{
                    return -1 * signal * signal;
                }
            }
        };

        RotationalDriveSignal slowTurn = new RotationalDriveSignal(0.1, -0.2);
        slowTurn.transposeZRotation(squaredInputs);

        assertEquals(slowTurn.getZRoation(), -0.04, 1.0e-6);
    }

    @Test
    void toDifferentialDriveSignal() {
        RotationalDriveSignal drive = new RotationalDriveSignal(0.6, -.2);
        DifferentialDriveSignal diffDrive = drive.toDifferentialDriveSignal();

        assertEquals(diffDrive.getLeftMotorOutput(), 0.6 -.2, 1.0e-6);
        assertEquals(diffDrive.getRightMotorOutput(), 0.6 + .2, 1.0e-6);

        drive = new RotationalDriveSignal(1.0, -.2);
        diffDrive = drive.toDifferentialDriveSignal();
        diffDrive.limitRotation();

        assertEquals(.6, diffDrive.getLeftMotorOutput(), 1.0e-6);
        assertEquals(1.0, diffDrive.getRightMotorOutput(), 1.0e-6);
    }
}