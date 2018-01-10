package org.gravitechx.frc2018.utils;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import org.gravitechx.frc2018.utils.motorconfigs.TalonConfig;
import org.gravitechx.frc2018.utils.wrappers.EfficentCANTalon;
import org.gravitechx.frc2018.utils.wrappers.MasterTalon;

public class CANTalonFactory {

    /**
     * Creates a Talon using only the default settings and the PWM port.
     * @param pwmPort
     * @return
     */
    public static CANTalon createDefaultTalon(int pwmPort){
        return createTalon(new TalonConfig(pwmPort));
    }

    public static CANTalon createDefaultSlaveTalon(int canPort, SpeedController s){
        return createSlaveTalon(s, new TalonConfig(canPort));
    }

    /**
     * Create a slave talon and returns the master.
     */
    public static CANTalon createSlaveTalon(SpeedController s, TalonConfig config){
        return setTalon(
                new MasterTalon(config.PORT, s, config.CONTROL_FRAME_PERIOD_MS),
                config);
    }

    /**
     * Creates a CANTalon using a TalonConfig
     * @param config
     * @return
     */
    public static CANTalon createTalon(TalonConfig config){
        CANTalon talon = new EfficentCANTalon(config.PORT, config.CONTROL_FRAME_PERIOD_MS);
        return setTalon(talon, config);
    }

    private static CANTalon setTalon(CANTalon talon, TalonConfig config){
        talon.changeControlMode(CANTalon.TalonControlMode.Voltage);
        talon.changeMotionControlFramePeriod(config.MOTION_CONTROL_FRAME_PERIOD_MS);
        talon.clearIAccum();
        talon.ClearIaccum();
        talon.clearMotionProfileHasUnderrun();
        talon.clearMotionProfileTrajectories();
        talon.clearStickyFaults();
        talon.ConfigFwdLimitSwitchNormallyOpen(config.LIMIT_SWITCH_NORMALLY_OPEN);
        talon.configMaxOutputVoltage(config.MAX_OUTPUT_VOLTAGE);
        talon.configNominalOutputVoltage(config.NOMINAL_VOLTAGE, -config.NOMINAL_VOLTAGE);
        talon.configPeakOutputVoltage(config.PEAK_VOLTAGE, -config.PEAK_VOLTAGE);
        talon.ConfigRevLimitSwitchNormallyOpen(config.LIMIT_SWITCH_NORMALLY_OPEN);
        talon.enableBrakeMode(config.ENABLE_BRAKE);
        talon.EnableCurrentLimit(config.ENABLE_CURRENT_LIMIT);
        talon.enableForwardSoftLimit(config.ENABLE_SOFT_LIMIT);
        talon.enableLimitSwitch(config.ENABLE_LIMIT_SWITCH, config.ENABLE_LIMIT_SWITCH);
        talon.enableReverseSoftLimit(config.ENABLE_SOFT_LIMIT);
        talon.enableZeroSensorPositionOnForwardLimit(false);
        talon.enableZeroSensorPositionOnIndex(false, false);
        talon.enableZeroSensorPositionOnReverseLimit(false);
        talon.reverseOutput(false);
        talon.reverseSensor(false);
        talon.setAnalogPosition(0);
        talon.setCurrentLimit(config.CURRENT_LIMIT);
        talon.setExpiration(config.EXPIRATION_TIMEOUT_SECONDS);
        talon.setForwardSoftLimit(config.FORWARD_SOFT_LIMIT);
        talon.setInverted(config.INVERTED);
        talon.setNominalClosedLoopVoltage(config.NOMINAL_CLOSED_LOOP_VOLTAGE);
        talon.setPosition(0);
        talon.setProfile(0);
        talon.setPulseWidthPosition(0);
        talon.setReverseSoftLimit(config.REVERSE_SOFT_LIMIT);
        talon.setSafetyEnabled(config.SAFETY_ENABLED);
        talon.SetVelocityMeasurementPeriod(config.VELOCITY_MEASUREMENT_PERIOD);
        talon.SetVelocityMeasurementWindow(config.VELOCITY_MEASUREMENT_ROLLING_AVERAGE_WINDOW);
        talon.setVoltageCompensationRampRate(config.VOLTAGE_COMPENSATION_RAMP_RATE);
        talon.setVoltageRampRate(config.VOLTAGE_RAMP_RATE);

        talon.setStatusFrameRateMs(CANTalon.StatusFrameRate.General, config.GENERAL_STATUS_FRAME_RATE_MS);
        talon.setStatusFrameRateMs(CANTalon.StatusFrameRate.Feedback, config.FEEDBACK_STATUS_FRAME_RATE_MS);
        talon.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, config.QUAD_ENCODER_STATUS_FRAME_RATE_MS);
        talon.setStatusFrameRateMs(CANTalon.StatusFrameRate.AnalogTempVbat,
                config.ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS);
        talon.setStatusFrameRateMs(CANTalon.StatusFrameRate.PulseWidth, config.PULSE_WIDTH_STATUS_FRAME_RATE_MS);

        return talon;
    }
}
