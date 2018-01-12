package org.gravitechx.frc2018.utils.motorconfigs;


import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import edu.wpi.first.wpilibj.MotorSafety;
import com.ctre.phoenix.motorcontrol.NeutralMode;


/**
 * Repersents the configuration of a Talon motor controller.
 * Designed to be implemented in the TalonFacotry.
 */

public class TalonConfig extends MotorConfig {

    public TalonConfig(int port){
        super(port);
    }


    public int PID_ID = 0;
    public int SLOT_ID = 0;

    /* New Voltages */
    public double PEAK_OUTPUT_FORWARD = 1.0;
    public double PEAK_OUTPUT_REVERSE = -1.0;
    public double NOMINAL_OUTPUT_FORWARD = 0.0;
    public double NOMINAL_OUTPUT_REVERSE = 0.0;

    public int TIME_TILL_ERROR_MS = 0;
    public NeutralMode BREAK_MODE = NeutralMode.Coast;

    public LimitSwitchSource LIMIT_SWITCH_FORWARD_SOURCE = LimitSwitchSource.Deactivated;
    public LimitSwitchNormal LIMIT_SWITCH_FORWARD_NORMAL_POSITION = LimitSwitchNormal.Disabled;
    public LimitSwitchSource LIMIT_SWITCH_REVERSE_SOURCE = LimitSwitchSource.Deactivated;
    public LimitSwitchNormal LIMIT_SWITCH_REVERSED_NORMAL_POSITION = LimitSwitchNormal.Disabled;

    public boolean ENABLE_CURRENT_LIMIT = false;
    public boolean ENABLE_SOFT_LIMIT = false;
    public boolean ENABLE_LIMIT_SWITCH = false;
    public int CURRENT_LIMIT = 0;
    public double EXPIRATION_TIMEOUT_SECONDS = MotorSafety.DEFAULT_SAFETY_EXPIRATION;
    public int FORWARD_SOFT_LIMIT = 0;
    public int REVERSE_SOFT_LIMIT = 0;
    public boolean SAFETY_ENABLED = false;

    //public int CONTROL_FRAME_PERIOD_MS = 5; /* DEPRECIATED BUT NOT REPLACED */
    public int MOTION_CONTROL_FRAME_PERIOD_MS = 100;
    public int GENERAL_STATUS_FRAME_RATE_MS = 5;
    public int FEEDBACK_STATUS_FRAME_RATE_MS = 100;
    //public int QUAD_ENCODER_STATUS_FRAME_RATE_MS = 100; /* DEPRECIATED BUT NOT REPLACED */
    public int ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS = 100;
    //public int PULSE_WIDTH_STATUS_FRAME_RATE_MS = 100; /* DEPRECIATED BUT NOT REPLACED */
    public VelocityMeasPeriod VELOCITY_MEASUREMENT_PERIOD = VelocityMeasPeriod.Period_100Ms;
    public int VELOCITY_MEASUREMENT_ROLLING_AVERAGE_WINDOW = 64;

    public double OPEN_LOOP_RAMP_RATE_SEC = 0.0;
    public double CLOSED_LOOP_RAMP_RATE_SEC = 0.0;
}
