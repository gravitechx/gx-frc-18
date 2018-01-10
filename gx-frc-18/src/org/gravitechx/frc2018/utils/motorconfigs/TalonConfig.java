package org.gravitechx.frc2018.utils.motorconfigs;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.MotorSafety;

/**
 * Repersents the configuration of a Talon motor controller.
 * Designed to be implemented in the TalonFacotry.
 */

public class TalonConfig extends MotorConfig {

    public TalonConfig(int port){
        super(port);
    }

    public boolean LIMIT_SWITCH_NORMALLY_OPEN = true;
    public double MAX_OUTPUT_VOLTAGE = 12;
    public double NOMINAL_VOLTAGE = 0;
    public double PEAK_VOLTAGE = 12;
    public boolean ENABLE_BRAKE = false;
    public boolean ENABLE_CURRENT_LIMIT = false;
    public boolean ENABLE_SOFT_LIMIT = false;
    public boolean ENABLE_LIMIT_SWITCH = false;
    public int CURRENT_LIMIT = 0;
    public double EXPIRATION_TIMEOUT_SECONDS = MotorSafety.DEFAULT_SAFETY_EXPIRATION;
    public double FORWARD_SOFT_LIMIT = 0;
    public double NOMINAL_CLOSED_LOOP_VOLTAGE = 12;
    public double REVERSE_SOFT_LIMIT = 0;
    public boolean SAFETY_ENABLED = false;

    public int CONTROL_FRAME_PERIOD_MS = 5;
    public int MOTION_CONTROL_FRAME_PERIOD_MS = 100;
    public int GENERAL_STATUS_FRAME_RATE_MS = 5;
    public int FEEDBACK_STATUS_FRAME_RATE_MS = 100;
    public int QUAD_ENCODER_STATUS_FRAME_RATE_MS = 100;
    public int ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS = 100;
    public int PULSE_WIDTH_STATUS_FRAME_RATE_MS = 100;

    public CANTalon.VelocityMeasurementPeriod VELOCITY_MEASUREMENT_PERIOD = CANTalon.VelocityMeasurementPeriod.Period_100Ms;
    public int VELOCITY_MEASUREMENT_ROLLING_AVERAGE_WINDOW = 64;

    public double VOLTAGE_COMPENSATION_RAMP_RATE = 0;
    public double VOLTAGE_RAMP_RATE = 0;
}
