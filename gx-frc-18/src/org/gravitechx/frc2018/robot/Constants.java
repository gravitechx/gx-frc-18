package org.gravitechx.frc2018.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.gravitechx.frc2018.utils.motorconfigs.TalonConfig;
import org.gravitechx.frc2018.utils.motorconfigs.TalonPIDConfig;

import java.util.function.UnaryOperator;

/**
 * Contains all constants used on the 2018 robot.
 */
public class Constants {
    /* TESTING CONSTANTS */
    public static int kPIDLoopIdx = 0;
    public static int kTimeoutMs = 1000;

    /* ========================== */
    /* Motor controller constants */
    /* ========================== */

    // Drive Motor Controllers
    public static final int LEFT_TALON_CAN_CHANNEL = 3;
    public static final int RIGHT_TALON_CAN_CHANNEL = 1;
    public static final int LEFT_VICTOR_CAN_CHANNEL = 1;
    public static final int RIGHT_VICTOR_CAN_CHANNEL = 3;

    // ARM Motor Controllers
    public static final int LEFT_LIFT_TALON_CAN_CHANNEL = 0;
    public static final int RIGHT_LIFT_TALON_CAN_CHANNEL = 2;

    public static final int LEFT_LIFT_FRONT_SPARK_PWM_CHANNEL = 0;
    public static final int LEFT_LIFT_BACK_SPARK_PWM_CHANNEL = 1;

    public static final int RIGHT_LIFT_FRONT_SPARK_PWM_CHANNEL = 2;
    public static final int RIGHT_LIFT_BACK_SPARK_PWM_CHANNEL = 3;

    // Is reversed
    public static final boolean LEFT_DRIVE_MOTOR_REVERSED = false;
    public static final boolean RIGHT_DRIVE_MOTOR_REVERSED = false;

    /* PID */
    public static final TalonPIDConfig DRIVE_PID_CONFIG =
            new TalonPIDConfig(0.20, 1.0E-5, 0.15, 0.1, 1.0);
    public static final TalonPIDConfig NO_PID_CONFIG = new TalonPIDConfig(0.0, 0.0, 0.0, 0.0, 0.0);

    public static final double DRIVE_ENCODER_MOTIFIER =  4096.0 * 500.0 / 600.0 * 2.0;

    /* NEGATIVE INERTIA CONSTANTS */
    public static final double NEG_INERTIA_TURN_SCALAR = 2.5;
    public static final double NEG_INERTIA_THREASHOLD = 0.65;
    public static final double NEG_INERTIA_CLOSE_SCALAR = 4.0;
    public static final double NEG_IRERTIA_FAR_SCALAR = 5.0;

    /* QUICK STOP */
    public static final double QUICK_STOP_DEADBAND =  0.16;
    public static final double QUICK_STOP_WEIGHT = 0.15;
    public static final double QUICK_STOP_SCALAR = 1.5;

    /* == */
    /* IO */
    /* == */

    /* WHEEL PORTS */
    public static final int ROTATION_STICK_PORT = 0;
    public static final int THROTTLE_STICK_PORT = 1;
    public static final int IO_QUICK_TURN_BUTTON = 2;

    /* Control System Deadband */
    public static final double THROTTLE_DEADBAND = 0.04;
    public static final double WHEEL_DEADBAND = 0.02;

    /* Throttle Reverse */
    public static final boolean REVERSE_THROTTLE_STICK = false;

    /* LIFT */
    public static int PRIMARY_LIFT_STICK_PORT = 2;
    public static int SECONDARY_LIFT_STICK_PORT = 3;
    public static double DEFAULT_AXIS_FUSE_RATIO = .75;

    /* BIO */
    public static final int BIO_OPEN_PORT = 0;
    public static final int BIO_CLOSE_PORT = 1;
    public static final int ROTATOR_PORT = 2;
    public static final int NULL_PORT = 7;

    public static final int LEFT_BIO_MOTOR_CAN_PORT = 0;
    public static final int RIGHT_BIO_MOTOR_CAN_PORT = 2;

    public static final double BIO_INHALE_SPEED = .45;
    public static final double BIO_EXHALE_SPEED = -.27;

    public static final double BUTTON_SWITCH_SPEED = .2;

    public static final DoubleSolenoid.Value BIO_GRASP_OPEN_SOLENOID_POSITION = DoubleSolenoid.Value.kForward;
    public static final DoubleSolenoid.Value BIO_GRASP_CLOSED_SOLENOID_POSITION = DoubleSolenoid.Value.kReverse;

    public static final DoubleSolenoid.Value BIO_ROTATOR_UP_SOLENOID_POSITION = DoubleSolenoid.Value.kReverse;
    public static final DoubleSolenoid.Value BIO_ROTATOR_DOWN_SOLENOID_POSITION = DoubleSolenoid.Value.kForward;

    /* Control System Joystick Functions */
    public static final UnaryOperator<Double> THROTTLE_TRANSPOSITION_OPERATION = new UnaryOperator<Double>() {
        @Override
        public Double apply(Double signal) {
            if(signal > 0.0){
                return signal * signal;
            }else{
                return - 1 * signal * signal;
            }
        }
    };

    /* Control System Transposition Function for Wheel */
    public static final double WHEEL_NONLINEARITY = 0.3;
    public static final UnaryOperator<Double> WHEEL_TRANSPOSITION_OPERATION = new UnaryOperator<Double>() {
        final double denominator = Math.sin(Math.PI / 2.0 *  WHEEL_NONLINEARITY);

        @Override
        public Double apply(Double signal) {
            signal = Math.sin(Math.PI / 2.0 * WHEEL_NONLINEARITY * signal) / denominator;
            signal = Math.sin(Math.PI / 2.0 * WHEEL_NONLINEARITY * signal) / denominator;
            return signal;
        }
    };

    /* ============= */
    /* MOTOR CONFIGS */
    /* ============= */

    public static double MAX_LIFT_VOLTAGE = 8.0;

    /* Talon on the drive train */
    public static class DriveTalonConfig extends TalonConfig {
        public DriveTalonConfig(){
            super();
            this.BREAK_MODE = NeutralMode.Coast;
            this.CLOSED_LOOP_RAMP_RATE_SEC = .35;
            this.OPEN_LOOP_RAMP_RATE_SEC = .35;
        }
    }
}