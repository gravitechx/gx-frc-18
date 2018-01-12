package org.gravitechx.frc2018.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.utils.TalonSRXFactory;
import org.gravitechx.frc2018.utils.VictorSPFactory;


public class Drive extends Subsystem implements Testable {
    /*
    * Singleton Pattern
    * Reference: https://www.tutorialspoint.com/design_pattern/singleton_pattern.htm
    * */
    private static Drive mInstance = new Drive();
    public static Drive getInstance(){
        return mInstance;
    }

    // Motor controllers (slaved)
    private WPI_TalonSRX leftDrive;
    private WPI_TalonSRX rightDrive;

    private DriveControlStates mCurrentState;

    public enum DriveControlStates {
        CLOSED_LOOP,
        AUTO,
        OPEN_LOOP
    }

    private Drive() {
        /* Initialize motor controllers */
        leftDrive = TalonSRXFactory.createDefaultSlaveTalon(
                Constants.leftTalonCanChannel, VictorSPFactory.createDefaultVictor(Constants.leftVictorSPPwmChannel));
        rightDrive = TalonSRXFactory.createDefaultSlaveTalon(
                Constants.rightTalonCanChannel, VictorSPFactory.createDefaultVictor(Constants.rightVictorSPPwmChannel));
        mCurrentState = DriveControlStates.OPEN_LOOP;
    }

    public void setControlState(DriveControlStates state){
        mCurrentState = state;
    }

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void test() {
        DifferentialDrive d = new DifferentialDrive(leftDrive, rightDrive);
        Timer t = new Timer();
        while(t.get() < 5.0){
            d.curvatureDrive(.5, 0.0, true);
        }
    }
}
