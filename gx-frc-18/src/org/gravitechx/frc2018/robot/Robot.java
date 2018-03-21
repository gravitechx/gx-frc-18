
package org.gravitechx.frc2018.robot;

import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.gravitechx.frc2018.frames.AmpFrame;
import org.gravitechx.frc2018.frames.VisionFrame;
import org.gravitechx.frc2018.robot.commands.ExampleCommand;
import org.gravitechx.frc2018.robot.io.controlschemes.ControlScheme;
import org.gravitechx.frc2018.robot.io.controlschemes.DefaultControlScheme;
import org.gravitechx.frc2018.robot.subsystems.BIO;
import org.gravitechx.frc2018.robot.subsystems.Drive;
import org.gravitechx.frc2018.robot.subsystems.Lift;
import org.gravitechx.frc2018.robot.subsystems.ExampleSubsystem;
import org.gravitechx.frc2018.robot.io.server.RobotServer;
import org.gravitechx.frc2018.utils.drivehelpers.DrivePipeline;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;
import org.gravitechx.frc2018.utils.looping.RemoteTimestamp;

import javax.json.*;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import static org.gravitechx.frc2018.utils.drivehelpers.DriveSignal.limit;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

	public static Drive drive;
	public static DrivePipeline dPipe;
	public static Lift lift;
	public static BIO bio;
	public boolean isGrabbing;
	public VideoSink cameraServer;

	//public static PowerDistributionPanel pdp;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	private ControlScheme mControlScheme;
	DrivePipeline pipe = new DrivePipeline();
	RobotServer rs;
	Thread serverThread;



	/**
	 * This function is run when the robot is first startedex up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", new ExampleCommand());
		//chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		drive = Drive.getInstance();
		lift = Lift.getInstance();
		dPipe = new DrivePipeline();
		rs = new RobotServer(Constants.PORT, Constants.SERVER_WAIT_MS);
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
		
		//Initialize server code
		rs = new RobotServer(Constants.PORT, Constants.SERVER_WAIT_MS);
		serverThread = new Thread(rs);
		System.out.println("right before start");
		serverThread.start();
		mControlScheme = DefaultControlScheme.getInstance();
		
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
		serverThread = new Thread(rs);
		serverThread.start();
		mControlScheme = DefaultControlScheme.getInstance();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		drive.set(dPipe.apply(
						new RotationalDriveSignal(mControlScheme.getThrottle(), mControlScheme.getWheel()),
						mControlScheme.getQuickTurnButton())
		);
		drive.graphEncodersToConsole();

		bio.update();
		mControlScheme.update();
	}

	/**
	 * This function is called periodically during test mode
	 */

	@Override
	public void testPeriodic() {
		lift.setDirect(.2);
	}
}
