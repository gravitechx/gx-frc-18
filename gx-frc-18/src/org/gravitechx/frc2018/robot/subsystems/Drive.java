package org.gravitechx.frc2018.robot.subsystems;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.utils.drivehelpers.DifferentialDriveSignal;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;
import org.gravitechx.frc2018.utils.TalonSRXFactory;
import org.gravitechx.frc2018.utils.VictorSPFactory;

/**
 * Implements the drive subsystem. This contains the DriveTrain and primitive drive functions.
 */
public class Drive extends Subsystem implements TestableSystem {
    // Singleton Pattern
    private static Drive mInstance = new Drive();
    public static Drive getInstance(){
        return mInstance;
    }

    // Motor controllers (slaved)
    private WPI_TalonSRX leftDrive;
    private WPI_TalonSRX rightDrive;

    private DriveControlStates mCurrentState;

    public enum DriveControlStates {CLOSED_LOOP, AUTO, OPEN_LOOP}

    /**
     * Sets the up PID and drive train
     * @todo Refactor Drive to set the PID responce times in the motor config.
     */
    private Drive() {
        /* Initialize motor controllers */
        leftDrive = TalonSRXFactory.createDefaultSlaveTalon(
               Constants.leftTalonCanChannel, VictorSPFactory.createDefaultVictor(Constants.leftVictorSPPwmChannel));
        rightDrive = TalonSRXFactory.createDefaultSlaveTalon(
                Constants.rightTalonCanChannel, VictorSPFactory.createDefaultVictor(Constants.rightVictorSPPwmChannel));

        // Set up encoders
        leftDrive.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative,
                Constants.DRIVE_PID_CONFIG.PID_ID,
                0);

        rightDrive.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative,
                Constants.DRIVE_PID_CONFIG.PID_ID,
                0);

        leftDrive.setStatusFramePeriod(
                StatusFrameEnhanced.Status_2_Feedback0,
                5,
                0);

        rightDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0,
                5,
                0);

        // Configure PID
        TalonSRXFactory.configurePID(leftDrive, Constants.DRIVE_PID_CONFIG);
        TalonSRXFactory.configurePID(rightDrive, Constants.DRIVE_PID_CONFIG);

        mCurrentState = DriveControlStates.OPEN_LOOP;
    }

    /**
     * Lets the left and right motor values.
     * @param differentialDriveSignal
     */
    public void set(DifferentialDriveSignal differentialDriveSignal){
        leftDrive.set(differentialDriveSignal.leftMotorOutput);
        rightDrive.set(differentialDriveSignal.rightMotorOutput);
    }

    public void setControlState(DriveControlStates state){
        mCurrentState = state;
    }

    double lastZRotation = 0.0;
    double mNegInertiaScalar;
    double mNegInertiaAccumlator = 0.0;

    /**
     * This methods uses a version of cheesy drive for teleop.
     * @todo Make the rotationalScaler dependant on speed.
     * @param rotationalDriveSignal
     * @param isQuickTurn
     */
    public void curveDrive(RotationalDriveSignal rotationalDriveSignal, boolean isQuickTurn){
        rotationalDriveSignal.limit();
        rotationalDriveSignal.applyXZDeadband(Constants.THROTTLE_DEADBAND, Constants.WHEEL_DEADBAND);

        double dZ = rotationalDriveSignal.getZRoation() - lastZRotation;
        lastZRotation = rotationalDriveSignal.getZRoation();

        rotationalDriveSignal.transposeXSpeed(Constants.THROTTLE_TRANSPOSITION_OPERATION);
        rotationalDriveSignal.transposeZRotation(Constants.WHEEL_TRANSPOSITION_OPERATION);

        // If we are trying to turn left or right
        if(dZ * rotationalDriveSignal.getZRoation() > 0.0){
            mNegInertiaScalar = Constants.NEG_INERTIA_TURN_SCALAR;
        }else{
            // If we are trying to get to 0.0
            if(Math.abs(rotationalDriveSignal.getZRoation()) > Constants.NEG_INERTIA_THREASHOLD){
                // If we are outside the negative inertia threshold
                mNegInertiaScalar =  Constants.NEG_IRERTIA_FAR_SCALAR;
            }else{
                // If we are inside the negative inertia threshold
                mNegInertiaScalar = Constants.NEG_INERTIA_CLOSE_SCALAR;
            }
        }

        mNegInertiaAccumlator += mNegInertiaScalar * dZ;
        rotationalDriveSignal.setzRotation(rotationalDriveSignal.getZRoation() + mNegInertiaAccumlator);

        if(mNegInertiaAccumlator > 1.0){
            mNegInertiaAccumlator -= 1.0;
        }else if(mNegInertiaAccumlator < -1.0){
            mNegInertiaAccumlator += 1.0;
        }else {
            mNegInertiaAccumlator = 0.0;
        }

        set(rotationalDriveSignal.toDifferencialDriveSignal());
    }

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void test() {
        //DifferentialDrive d = new DifferentialDrive(leftDrive, rightDrive);
        Timer t = new Timer();
        t.start();
        while(t.get() < 5.0){
            //d.curvatureDrive(.2, 0.0, true);
            leftDrive.set(.3);
            rightDrive.set(.3);

            SmartDashboard.putNumber("Response Left: ", leftDrive.getSelectedSensorVelocity(Constants.DRIVE_PID_CONFIG.PID_ID));
            SmartDashboard.putNumber("Response Right: ", rightDrive.getSelectedSensorVelocity(Constants.DRIVE_PID_CONFIG.PID_ID));

            SmartDashboard.putString("Talons", TalonSRXFactory.getProperties(leftDrive));
        }
    }
}
