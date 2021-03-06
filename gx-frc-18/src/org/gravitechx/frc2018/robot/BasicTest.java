package org.gravitechx.frc2018.robot;



import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.gravitechx.frc2018.utils.TalonSRXFactory;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class BasicTest extends IterativeRobot {

    public DifferentialDrive basicDrive;

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();
    Joystick joy = new Joystick(1);
    WPI_TalonSRX tleft, tright;


    /**
     * This function is run when the robot is first startedex up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        // chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
        tleft = TalonSRXFactory.createDefaultTalon(Constants.LEFT_TALON_CAN_CHANNEL);
        tright = TalonSRXFactory.createDefaultTalon(Constants.RIGHT_TALON_CAN_CHANNEL);

        WPI_VictorSPX vleft = new WPI_VictorSPX(Constants.LEFT_VICTOR_CAN_CHANNEL);
        WPI_VictorSPX vright = new WPI_VictorSPX(Constants.RIGHT_VICTOR_CAN_CHANNEL);

        vleft.follow(tleft);
        vright.follow(tright);

        basicDrive = new DifferentialDrive(tleft, tright);
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString code to get the auto name from the text box below the Gyro
     * <p>
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        autonomousCommand = chooser.getSelected();

        /*
         * String autoSelected = SmartDashboard.getString("Auto Selector",
         * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
         * = new MyAutoCommand(); break; case "Default Auto": default:
         * autonomousCommand = new ExampleCommand(); break; }
         */

        // schedule the autonomous command (example)
        if (autonomousCommand != null)
            autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null)
            autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        basicDrive.arcadeDrive(joy.getY(), joy.getX());

    }

    /**
     * This function is called periodically during test mode
     */

    @Override
    public void testPeriodic() {

    }
}