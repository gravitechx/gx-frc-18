package org.gravitechx.frc2018.robot.subsystems;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.utils.CANTalonFactory;
import org.gravitechx.frc2018.utils.VictorSPFactory;
import org.gravitechx.frc2018.utils.wrappers.MasterTalon;


public class Drive extends Subsystem {
    /* CONTINUING DEVELOPMENT */
    private CANTalon leftDrive;
    private CANTalon rightDrive;

    private Drive mInstance = new Drive();

    private Drive() {
        /* Initialize motor controllers */
        leftDrive = CANTalonFactory.createDefaultSlaveTalon(
                0, VictorSPFactory.createDefaultVictor(0));
        rightDrive = CANTalonFactory.createDefaultSlaveTalon(
                1, VictorSPFactory.createDefaultVictor(1));
    }

    public Drive getInstance(){
        return mInstance;
    }
}
