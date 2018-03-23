package org.gravitechx.frc2018.robot.auton;

import org.gravitechx.frc2018.utils.drivehelpers.DifferentialDriveSignal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutonHelperTest {
    public double getError(double mag, double per){
        return 2.0 * mag * (0.5 - Math.random()) + per;
    }

    @Test
    public void differencialTest(){
       AutonHelper.ProportionalDriver pd = new AutonHelper.ProportionalDriver(2.0, .80, 0.0, 5.0);
       double accumulatedDistance = 0.0;
       double heading = 0.0;
       double w = .635;
       double x = 0.0;
       double y = 0.0;
       double[][] data = new double[3][30];

       for (double t = 0.0; t < 30.0; t += 1.0){
           DifferentialDriveSignal signal = pd.proportionalDistanceLoop(y, heading);
           heading += (signal.getLeftMotorOutput() - signal.getRightMotorOutput()) / w + getError(0.1, .005);
           accumulatedDistance += (signal.getLeftMotorOutput() + signal.getRightMotorOutput()) / 2 + getError(0.1, .005);
           y = Math.sin(heading) * accumulatedDistance;
           x = Math.cos(heading) * accumulatedDistance;
           data[0][(int)t] = t;
           data[1][(int)t] = x;
           data[2][(int)t] = y;
       }

       for (int i = 0; i < 3; i++){
           System.out.print("=========\n");
           for (int j = 0; j < data[0].length; j++){
               System.out.printf("%f\n", data[i][j]);
           }
       }
    }
}