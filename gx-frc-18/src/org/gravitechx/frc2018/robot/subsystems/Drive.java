package org.gravitechx.frc2018.robot.subsystems;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.gravitechx.frc2018.robot.Constants;


public class Drive extends Subsystem {
    /* CONTINUING DEVELOPMENT */
    private CANTalon mMasterTalonLeft, mMasterTalonRight;
    private VictorSP mSlaveVictorLeft, mSlaveVictorRight;

    private Drive mInstance = new Drive();

    private Drive() {
        /* Initialize motor controllers */
        mMasterTalonLeft = new CANTalon(Constants.leftTalonCanChannel);
        mMasterTalonRight = new CANTalon(Constants.rightTalonCanChannel);
        mSlaveVictorLeft = new VictorSP(Constants.leftVictorSPPwmChannel);
        mSlaveVictorRight = new VictorSP(Constants.rightVictorSPPwmChannel);

    }

    public Drive getInstance(){
        return mInstance;
    }

    public void updateSlaves(){
        mSlaveVictorLeft.set(mMasterTalonLeft.get());
        mSlaveVictorRight.set(mMasterTalonRight.get());
    }




}
