package org.gravitechx.frc2018.robot.auton;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.gravitechx.frc2018.utils.drivehelpers.DifferentialDriveSignal;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;
import org.gravitechx.frc2018.utils.wrappers.GravAHRS;

public class AutonHelper {
    static class ProportionalDriver {
        private double currentDistance = 0.0;
        double targetDistance;
        double targetSpeed;
        double targetHeading;
        double kP;
        boolean running = true;

        public ProportionalDriver(double targetDistance, double targetSpeed, double targetHeading, double kP){
            this.targetSpeed = targetSpeed;
            this.targetDistance = targetDistance;
            this.targetHeading = targetHeading;
            this.kP = kP;
        }

        public DifferentialDriveSignal proportionalDistanceLoop(double measuredDistance, double yaw) {
            if (measuredDistance - currentDistance < targetDistance) {
                return new RotationalDriveSignal(targetSpeed, kP * yaw).toDifferencialDriveSignal();
            }

            running = false;
            return new DifferentialDriveSignal(0.0, 0.0);
        }

        public boolean getRunning(){
            return running;
        }
    }
}
