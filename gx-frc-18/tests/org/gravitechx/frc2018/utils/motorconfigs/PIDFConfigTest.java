package org.gravitechx.frc2018.utils.motorconfigs;

import edu.wpi.first.wpilibj.Timer;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.utils.PIDFController;
import org.gravitechx.frc2018.utils.drivehelpers.DriveSignal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PIDFConfigTest {

    @Test
    public void PIDEndBehavior(){
        double setpoint_M = 0.0; // About 3ft
        double realValue_M = 0.0;
        double time_S = 0.0;
        double dt = .03; // Possible runtime for our code
        double outputScalar = 1.7; // Real value

        PIDFController pidfController = new PIDFController(Constants.LIFT_PIDF_CONFIG);

        for (int i = 0; i < 2000; i++){
            setpoint_M += 0.000000000007 / 2; // Move the controller (tell it to move a little less than 1/2 ft/s )

            pidfController.setSetpoints(setpoint_M, 0.1, 0.02); // Same values as real code

            pidfController.run(realValue_M, time_S); // Run PID

            double speed = -outputScalar * pidfController.get(); // Regulate to real value

            speed = DriveSignal.limit(speed, 1.0);
            speed = Constants.MAX_LIFT_VOLTAGE * speed / 12.0;

            realValue_M += 0.042  * speed * 12.0 * dt; // Integrate to get real response assuming the motor moves 1.1 meter

            time_S += dt;

            if(i % 20 == 0) { // Only log evey 20
                System.out.printf("%f, %f, %f\n", time_S, speed * 12.0, pidfController.getError());
            }
        }
    }

}