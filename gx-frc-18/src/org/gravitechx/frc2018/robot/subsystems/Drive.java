package org.gravitechx.frc2018.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.utils.PIDFController;
import org.gravitechx.frc2018.utils.SchuhPDFController;
import org.gravitechx.frc2018.utils.TalonSRXFactory;
import org.gravitechx.frc2018.utils.drivehelpers.DifferentialDriveSignal;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;
import org.gravitechx.frc2018.utils.looping.Timestamp;
import org.gravitechx.frc2018.utils.motorconfigs.PIDFConfig;
import org.gravitechx.frc2018.utils.motorconfigs.TalonConfig;
import org.gravitechx.frc2018.utils.wrappers.GravAHRS;

/**
 * Implements the drive subsystem. This contains the DriveTrain and primitive drive functions.
 */
public class Drive extends Subsystem implements TestableSystem {
    private static Drive mInstance = new Drive();
    public static Drive getInstance(){
        return mInstance;
    }

    public GravAHRS ahrs;

    // Motor controllers
    private WPI_TalonSRX leftDrive;
    private WPI_TalonSRX rightDrive;

    private WPI_VictorSPX leftSlave;
    private WPI_VictorSPX rightSlave;

    // Drive state modeling
    private DriveControlStates mCurrentState;

    public enum DriveControlStates {CLOSED_LOOP, AUTO, OPEN_LOOP};

    private SchuhPDFController autoControllerLeft , autoControllerRight;

    /**
     * Sets the up PID and drive train
     */
    private Drive() {
        /* Initialize motor controllers */
        leftDrive = TalonSRXFactory.createTalon(Constants.LEFT_TALON_CAN_CHANNEL, new Constants.DriveTalonConfig());
        rightDrive = TalonSRXFactory.createTalon(Constants.RIGHT_TALON_CAN_CHANNEL, new Constants.DriveTalonConfig());

        leftSlave = new WPI_VictorSPX(Constants.LEFT_VICTOR_CAN_CHANNEL);
        rightSlave = new WPI_VictorSPX(Constants.RIGHT_VICTOR_CAN_CHANNEL);

        leftSlave.follow(leftDrive);
        rightSlave.follow(rightDrive);

        /* Set up encoders */
        leftDrive.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative,
                Constants.DRIVE_PID_CONFIG.PID_ID,
                0);

        rightDrive.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative,
                Constants.DRIVE_PID_CONFIG.PID_ID,
                0);

        leftDrive.setSensorPhase(true);
        rightDrive.setSensorPhase(true);

        rightDrive.setInverted(Constants.RIGHT_DRIVE_MOTOR_REVERSED);
        leftDrive.setInverted(Constants.LEFT_DRIVE_MOTOR_REVERSED);

        // Configure PID
        leftDrive = TalonSRXFactory.configurePID(leftDrive, Constants.DRIVE_PID_CONFIG);
        rightDrive = TalonSRXFactory.configurePID(rightDrive, Constants.DRIVE_PID_CONFIG);

        ahrs = GravAHRS.getInstance();

        ahrs.reset();

        mCurrentState = DriveControlStates.CLOSED_LOOP;

        autoControllerLeft = new SchuhPDFController(Constants.AUTON_DRIVE_CONFIG);
        autoControllerRight = new SchuhPDFController(Constants.AUTON_DRIVE_CONFIG);
    }

    /**
     * Lets the left and right motor values.
     * @param differentialDriveSignal The drive signal to which we will set the motors.
     * @see DifferentialDriveSignal
     */
    public void set(DifferentialDriveSignal differentialDriveSignal){
        if(mCurrentState == DriveControlStates.CLOSED_LOOP || mCurrentState == DriveControlStates.AUTO) {
            leftDrive.set(ControlMode.Velocity, differentialDriveSignal.getLeftMotorOutput() * Constants.DRIVE_ENCODER_MOTIFIER);
            rightDrive.set(ControlMode.Velocity, -differentialDriveSignal.getRightMotorOutput() * Constants.DRIVE_ENCODER_MOTIFIER);
        }else{
            leftDrive.set(ControlMode.PercentOutput, differentialDriveSignal.getLeftMotorOutput());
            rightDrive.set(ControlMode.PercentOutput, -differentialDriveSignal.getRightMotorOutput());
        }
    }

    public void set(DifferentialDriveSignal velocity, DifferentialDriveSignal acceleration){
        // Set setpoints
        autoControllerLeft.setSetpoints(velocity.getLeftMotorOutput(),
                velocity.getLeftMotorOutput(),
                acceleration.getLeftMotorOutput());
        autoControllerRight.setSetpoints(velocity.getRightMotorOutput(),
                velocity.getRightMotorOutput(),
                acceleration.getRightMotorOutput());

        // Run
        autoControllerLeft.run(
                leftDrive.getSelectedSensorVelocity(Constants.DRIVE_PID_CONFIG.PID_ID),
                (new Timestamp()).get());
        autoControllerRight.run(
                rightDrive.getSelectedSensorVelocity(Constants.DRIVE_PID_CONFIG.PID_ID),
                (new Timestamp()).get());

        // Set the drive to PID outs
        leftDrive.set(ControlMode.PercentOutput, autoControllerLeft.get());
        rightDrive.set(ControlMode.PercentOutput, autoControllerRight.get());
    }

    /**
     * Sets the subsystems control state.
     * @param state The current DriveControlState of the drive system
     */
    public void setControlState(DriveControlStates state){
        mCurrentState = state;
    }

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void initializeTest(){

    }

    public double getRightEncoderPosition() {
        return rightDrive.getSelectedSensorPosition(Constants.DRIVE_PID_CONFIG.PID_ID);
    }

    public double getLeftEncoderPosition() {
        return leftDrive.getSelectedSensorPosition(Constants.DRIVE_PID_CONFIG.PID_ID);
    }

    public double getRightEncoderPositionM(){
        return getRightEncoderPosition() * Constants.DRIVE_TO_M_ENCODER_MOTIFIER;
    }

    public double getLeftEncoderPositionM(){
        return getLeftEncoderPosition() * Constants.DRIVE_TO_M_ENCODER_MOTIFIER;
    }

    public int getLeftEncoderVelocity() {
        return leftDrive.getSelectedSensorVelocity(Constants.DRIVE_PID_CONFIG.PID_ID);
    }

    public int getRightEncoderVelocity() {
        return rightDrive.getSelectedSensorVelocity(Constants.DRIVE_PID_CONFIG.PID_ID);
    }

    public int getLeftPIDError() {
        return leftDrive.getClosedLoopError(Constants.DRIVE_PID_CONFIG.PID_ID);
    }

    public int getRightPIDError() {
        return rightDrive.getClosedLoopError(Constants.DRIVE_PID_CONFIG.PID_ID);
    }

    @Override
    public void test() {

    }

    /**
     * Creates a graph of the left and right encoder output and errors for debug.
     */
    public void graphEncodersToConsole(){
        SmartDashboard.putNumber("Left Encoder: ", getLeftEncoderVelocity());
        SmartDashboard.putNumber("Left Error: ", getLeftPIDError());
        SmartDashboard.putNumber("Right Encoder: ", getRightEncoderVelocity());
        SmartDashboard.putNumber("Right Error: ", getRightPIDError());
    }
}
