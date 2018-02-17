package org.gravitechx.frc2018.robot.subsystems;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.gravitechx.frc2018.robot.Constants;
import org.gravitechx.frc2018.utils.PIDController;
import org.gravitechx.frc2018.utils.PIDFController;
import org.gravitechx.frc2018.utils.TalonSRXFactory;
import org.gravitechx.frc2018.utils.drivehelpers.DriveSignal;
import org.gravitechx.frc2018.utils.lifthelpers.ElevatorHallEffect;

public class Lift extends Subsystem {
    /* MOTOR CONTROLLERS */
    private LiftGearBox leftGearBox;
    private LiftGearBox rightGearBox;

    private double outputScalar = 1.7;

    private ElevatorHallEffect mTopHall;
    private ElevatorHallEffect mMidHall;
    private ElevatorHallEffect mBottomHall;

    private SensorCollection encoder;

    private double M_MAX_VOLTAGE = Constants.MAX_LIFT_VOLTAGE;

    /* SINGLETON */
    private static Lift mLift = new Lift();

    private PIDFController liftController;

    enum ControlState {NEUTRAL, HOLDING, LIFTING};

    private ControlState mControlMode;

    public static Lift getInstance(){
        return mLift;
    }

    public void clearQuadature(){
        encoder.setQuadraturePosition(0, 0);
        liftController.reset(Timer.getFPGATimestamp());
    }

    public double getPosition(){
        return encoder.getQuadraturePosition() * Constants.QUADRATURE_TO_METER_LIFT;
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
            mSlaveFront.set(speed);
            mSlaveBack.set(speed);
            mMaster.set(-speed);
            //System.out.print("TOP HALL: " + mTopHall.isPressed() + "MID HALL: " + mMidHall.isPressed() + " BASE HALL" + mBottomHall.isPressed() + "\n");
            //mMaster.set(-speed);
            //System.out.println("SPEED: " + mMaster.getMotorOutputVoltage());
            //mSlaveFront.set(-mMaster.getMotorOutputVoltage() / 12.0);
            //mSlaveBack.set(-mMaster.getMotorOutputVoltage() / 12.0);
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
        WPI_TalonSRX leftTalon = TalonSRXFactory.createDefaultTalon(Constants.LEFT_LIFT_TALON_CAN_CHANNEL);
        encoder = leftTalon.getSensorCollection();
        leftGearBox = new LiftGearBox(new Spark(Constants.LEFT_LIFT_FRONT_SPARK_PWM_CHANNEL),
                leftTalon,
                new Spark(Constants.LEFT_LIFT_BACK_SPARK_PWM_CHANNEL));

        rightGearBox = new LiftGearBox(new Spark(Constants.RIGHT_LIFT_FRONT_SPARK_PWM_CHANNEL),
                TalonSRXFactory.createDefaultTalon(Constants.RIGHT_LIFT_TALON_CAN_CHANNEL),
                new Spark(Constants.RIGHT_LIFT_BACK_SPARK_PWM_CHANNEL));
        liftController = new PIDFController(Constants.LIFT_PIDF_CONFIG);

        mTopHall = new ElevatorHallEffect(Constants.HALL_TOP_DIO_CHANNEL);
        mMidHall = new ElevatorHallEffect(Constants.HALL_MID_DIO_CHANNEL);
        mBottomHall = new ElevatorHallEffect(Constants.HALL_BOTTOM_DIO_CHANNEL);

        mControlMode = ControlState.LIFTING;
    }

    public void set(double speed){
        System.out.println("SPEED: " + speed);

        if(mControlMode != ControlState.NEUTRAL) {
            speed = DriveSignal.limit(speed, 1.0);
            speed += Constants.NOMINAL_UP_VOLTAGE / 12.0;
            setDirect(speed);
        }
    }

    public void setDirect(double speed){
        speed = DriveSignal.limit(speed, 1.0);
        leftGearBox.set(M_MAX_VOLTAGE * speed / 12.0);
        rightGearBox.set(-M_MAX_VOLTAGE * speed / 12.0);
    }

    public void setSetPoint(double position, double velocity, double acceleration){
        liftController.setSetpoints(position, velocity, acceleration);
        liftController.run(getPosition(), Timer.getFPGATimestamp());
        set(-liftController.get() * outputScalar);
        System.out.print("SET POINT: " + position + "Lift Controller: " + liftController.get() + " DISTANCE: " + getPosition() + "\n");
    }

    public void setRelitivePosition(double position, double velocity, double acceleration){
        setSetPoint(position * Constants.LIFT_MAX_TRAVEL_M, velocity, acceleration);
    }

    @Override
    protected void initDefaultCommand() {

    }

    public double getOutputScalar() {
        return outputScalar;
    }

    public void setOutputScalar(double outputScalar) {
        this.outputScalar = outputScalar;
    }
}
