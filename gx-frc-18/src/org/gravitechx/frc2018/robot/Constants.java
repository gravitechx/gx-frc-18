package org.gravitechx.frc2018.robot;

import org.gravitechx.frc2018.utils.motorconfigs.TalonConfig;
import org.gravitechx.frc2018.utils.motorconfigs.TalonPIDConfig;

import java.util.function.UnaryOperator;

/**
 * Contains all constants used on the 2018 robot.
 */
public class Constants {
    /* ========================== */
    /* Motor controller constants */
    /* ========================== */
    public static final int leftTalonCanChannel = 0;
    public static final int rightTalonCanChannel = 1;
    public static final int leftVictorSPPwmChannel = 0;
    public static final int rightVictorSPPwmChannel = 1;

    public static final TalonPIDConfig DRIVE_PID_CONFIG =
            new TalonPIDConfig(.2, .01, 1.0, .1);

    public static final TalonConfig DRIVE_TALON_CONFIG = new TalonConfig();

    /* Control System Deadband */
    public static final double THROTTLE_DEADBAND = 0.04;
    public static final double WHEEL_DEADBAND = 0.02;

    /* Control System Joystick Functions */
    public static final UnaryOperator<Double> THROTTLE_TRANSPOSITION_OPERATION = new UnaryOperator<Double>() {
        @Override
        public Double apply(Double signal) {
            return signal / 6.0;
        }
    };

    /* Control System Transposition Function for Wheel */

    public static final double WHEEL_NONLINEARITY = 0.5;

    public static final UnaryOperator<Double> WHEEL_TRANSPOSITION_OPERATION = new UnaryOperator<Double>() {
        final double denominator = Math.sin(Math.PI / 2.0 *  WHEEL_NONLINEARITY);

        @Override
        public Double apply(Double signal) {
            signal = Math.sin(Math.PI / 2.0 * WHEEL_NONLINEARITY * signal) / denominator;
            signal = Math.sin(Math.PI / 2.0 * WHEEL_NONLINEARITY * signal) / denominator;
            signal = Math.sin(Math.PI / 2.0 * WHEEL_NONLINEARITY * signal) / denominator;
            return signal;

        }
    };

    /* NEGATIVE INERTIA CONSTANTS */
    public static final double NEG_INERTIA_TURN_SCALAR = 3.5;
    public static final double NEG_INERTIA_THREASHOLD = 0.65;
    public static final double NEG_INERTIA_CLOSE_SCALAR = 4.0;
    public static final double NEG_IRERTIA_FAR_SCALAR = 5.0;
}
