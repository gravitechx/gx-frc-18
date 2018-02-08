package org.gravitechx.frc2018.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.utils.TalonSRXFactory;
import org.gravitechx.frc2018.utils.drivehelpers.DriveSignal;

public class Lift extends Subsystem {
    /* MOTOR CONTROLLERS */
    private LiftGearBox leftGearBox;
    private LiftGearBox rightGearBox;

    private double M_MAX_VOLTAGE = Constants.MAX_LIFT_VOLTAGE;

    /* SINGLETON */
    private static Lift mLift = new Lift();

    public static Lift getInstance(){
        return mLift;
    }

    public class LiftGearBox {
        private Spark mSlaveFront;
        private WPI_TalonSRX mMaster;
        private Spark mSlaveBack;

        public LiftGearBox(Spark mSlaveFront, WPI_TalonSRX mMaster, Spark mSlaveBack){
            this.mSlaveFront = mSlaveFront;
            this.mMaster = mMaster;
            this.mSlaveBack = mSlaveBack;
        }

        public void set(double speed){
            mMaster.set(-speed);
            mSlaveFront.set(speed);
            mSlaveBack.set(speed);
        }

        /* GETTERS AND SETTERS */
        public Spark getmSlaveFront() {
            return mSlaveFront;
        }

        public void setmSlaveFront(Spark mSlaveFront) {
            this.mSlaveFront = mSlaveFront;
        }

        public WPI_TalonSRX getmMaster() {
            return mMaster;
        }

        public void setmMaster(WPI_TalonSRX mMaster) {
            this.mMaster = mMaster;
        }

        public Spark getmSlaveBack() {
            return mSlaveBack;
        }

        public void setmSlaveBack(Spark mSlaveBack) {
            this.mSlaveBack = mSlaveBack;
        }
    }

    private Lift(){
        leftGearBox = new LiftGearBox(new Spark(Constants.LEFT_LIFT_FRONT_SPARK_PWM_CHANNEL),
                TalonSRXFactory.createDefaultTalon(Constants.LEFT_LIFT_TALON_CAN_CHANNEL),
                new Spark(Constants.LEFT_LIFT_BACK_SPARK_PWM_CHANNEL));

        rightGearBox = new LiftGearBox(new Spark(Constants.RIGHT_LIFT_FRONT_SPARK_PWM_CHANNEL),
                TalonSRXFactory.createDefaultTalon(Constants.RIGHT_LIFT_TALON_CAN_CHANNEL),
                new Spark(Constants.RIGHT_LIFT_BACK_SPARK_PWM_CHANNEL));

    }

    public void set(double speed){
        DriveSignal.limit(speed, 1.0);
        leftGearBox.set(M_MAX_VOLTAGE * speed / 12.0);
        rightGearBox.set(-M_MAX_VOLTAGE * speed / 12.0);
        System.out.println(M_MAX_VOLTAGE * speed / 12.0);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
