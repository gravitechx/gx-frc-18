package org.gravitechx.frc2018.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.gravitechx.frc2018.utils.motorconfigs.PIDConfig;
import org.gravitechx.frc2018.utils.motorconfigs.PIDFConfig;
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
    public static final int JANKEY_SENSOR_TALON = 0;

    public static final int MID_LIFT_LEFT_PWM_CHANNEL = 1;
    public static final int MID_LIFT_RIGHT_PWM_CHANNEL = 0;

    // Is reversed
    public static final boolean LEFT_DRIVE_MOTOR_REVERSED = false;
    public static final boolean RIGHT_DRIVE_MOTOR_REVERSED = false;

    /* PID */
    //public static final PIDConfig LIFT_PID_CONFIG = new PIDConfig(.7, 0.55, .03, 0.0, .7);
    public static final PIDFConfig LIFT_PIDF_CONFIG = new PIDFConfig(.30, 0.00, 0.000, 0.0, 0.0,0.0);
    public static final TalonPIDConfig DRIVE_PID_CONFIG =
            new TalonPIDConfig(0.20, 1.0E-5, 0.15, 0.1, 1.0);
    public static final TalonPIDConfig NO_PID_CONFIG = new TalonPIDConfig(0.0, 0.0, 0.0, 0.0, 0.0);

    public static final double DRIVE_ENCODER_MOTIFIER =  4096.0 * 500.0 / 600.0 * 2.0; // 3415.0

    public static final double DRIVE_TO_M_ENCODER_MOTIFIER =  7.7922e-5; // 12.566in diff from enc mot (10699.0)

    /* NEGATIVE INERTIA CONSTANTS */
    public static final double NEG_INERTIA_TURN_SCALAR = 2.5;
    public static final double NEG_INERTIA_THREASHOLD = 0.65;
    public static final double NEG_INERTIA_CLOSE_SCALAR = 4.0;
    public static final double NEG_IRERTIA_FAR_SCALAR = 5.0;

    /* QUICK STOP */
    public static final double QUICK_STOP_DEADBAND =  0.16;
    public static final double QUICK_STOP_WEIGHT = 0.7;
    public static final double QUICK_STOP_SCALAR = .1;

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
    public static int MANUAL_LIFT_STICK_PORT = 2;
    public static int AUTOMATIC_LIFT_STICK_PORT = 3;
    public static double DEFAULT_AXIS_FUSE_RATIO = .75;

    public static int HALL_TOP_DIO_CHANNEL = 2;
    public static int HALL_MID_DIO_CHANNEL = 0;
    public static int HALL_BOTTOM_DIO_CHANNEL = 1;

    public static double HALL_MID_DISTANCE = 0.4633468;
    public static double HALL_TOP_DISTANCE = 1.72;
    public static double HALL_BOTTOM_DISTANCE = 0.0699262;

    public static double MAX_AMPERAGE = 26.0;


    /* LIFT PIPELINE */
    public static double NOMINAL_UP_VOLTAGE = 0.0; // 2.9
    public static double LIFT_COMPOUNDING_STEP = .02;
    public static double LIFT_MAX_TRAVEL_M = 1.70;
    public static double LIFT_MIN_TRAVEL_M = 0.0;
    public static double GEARBOX_NONLINEARITY_RATIO = 1.0;

    public static double LIFT_REST_DECAY_PROPORTIONAL = 0.1;
    public static double LIFT_PERSISTENT_DECAY_PROPORTIONAL = .05;
    public static double QUADRATURE_TO_METER_LIFT = 2.0 * 2.51312e-5 * 1.3123;

    public static double LIFT_POSITION_DEADBAND_M = .03;

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

    public static double MAX_LIFT_VOLTAGE = 12.0;

    /* Talon on the drive train */
    public static class DriveTalonConfig extends TalonConfig {
        public DriveTalonConfig(){
            super();
            this.BREAK_MODE = NeutralMode.Coast;
            this.CLOSED_LOOP_RAMP_RATE_SEC = .35;
            this.OPEN_LOOP_RAMP_RATE_SEC = .35;
        }
    }

    public static class ElevatorTalonConfig extends TalonConfig {
        public ElevatorTalonConfig(){
            super();
            this.BREAK_MODE = NeutralMode.Coast;
        }
    }

    /* ====== */
    /* CAMERA */
    /* ====== */
    public static int CAMERA_FPS = 15;
    public static int TOP_CAM = 1;
}