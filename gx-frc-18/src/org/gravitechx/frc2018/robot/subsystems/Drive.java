package org.gravitechx.frc2018.robot.subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.utils.drivehelpers.DifferentialDriveSignal;
import org.gravitechx.frc2018.utils.TalonSRXFactory;
import org.gravitechx.frc2018.utils.VictorSPFactory;

/**
 * Implements the drive subsystem. This contains the DriveTrain and primitive drive functions.
 */
public class Drive extends Subsystem implements TestableSystem {
    private static Drive mInstance = new Drive();
    public static Drive getInstance(){
        return mInstance;
    }

    // Motor controllers (slaved)
    private WPI_TalonSRX leftDrive;
    private WPI_TalonSRX rightDrive;

    // Drive state modeling
    private DriveControlStates mCurrentState;
    public enum DriveControlStates {CLOSED_LOOP, AUTO, OPEN_LOOP}

    /**
     * Sets the up PID and drive train
     */
    private Drive() {
        /* Initialize motor controllers */
        leftDrive = TalonSRXFactory.createDefaultSlaveTalon(
               Constants.leftTalonCanChannel, VictorSPFactory.createDefaultVictor(Constants.leftVictorSPPwmChannel));
        rightDrive = TalonSRXFactory.createDefaultSlaveTalon(
                Constants.rightTalonCanChannel, VictorSPFactory.createDefaultVictor(Constants.rightVictorSPPwmChannel));

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

        // Configure PID

        leftDrive = TalonSRXFactory.configurePID(leftDrive, Constants.DRIVE_PID_CONFIG);
        rightDrive = TalonSRXFactory.configurePID(rightDrive, Constants.DRIVE_PID_CONFIG);

        mCurrentState = DriveControlStates.CLOSED_LOOP;
    }

    /**
     * Lets the left and right motor values.
     * @param differentialDriveSignal The drive signal to which we will set the motors.
     * @see DifferentialDriveSignal
     */
    public void set(DifferentialDriveSignal differentialDriveSignal){
        leftDrive.set(ControlMode.Velocity, differentialDriveSignal.getLeftMotorOutput() * Constants.DRIVE_ENCODER_MOTIFIER);
        rightDrive.set(ControlMode.Velocity, -1 * differentialDriveSignal.getRightMotorOutput() * Constants.DRIVE_ENCODER_MOTIFIER);
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

    public int getLeftEncoder() {
        return leftDrive.getSelectedSensorVelocity(Constants.DRIVE_PID_CONFIG.PID_ID);
    }

    public int getRightEncoder() {
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
        graphEncoderOutput();
    }

    /**
     * Creates a graph of the left and right encoder output and errors for debug.
     */
    public void graphEncoderOutput(){
        SmartDashboard.putNumber("Left Encoder: ", getLeftEncoder());
        SmartDashboard.putNumber("Left Error: ", getLeftPIDError());
        SmartDashboard.putNumber("Right Encoder: ", getRightEncoder());
        SmartDashboard.putNumber("Left Error: ", getRightPIDError());
    }
}
