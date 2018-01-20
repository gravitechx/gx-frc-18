
package org.gravitechx.frc2018.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.buttons.Button;

import org.gravitechx.frc2018.robot.commands.ExampleCommand;
import org.gravitechx.frc2018.robot.io.controlschemes.DefaultControlScheme;
import org.gravitechx.frc2018.robot.io.controlschemes.ControlScheme;
import org.gravitechx.frc2018.robot.subsystems.Drive;
import org.gravitechx.frc2018.robot.subsystems.ExampleSubsystem;
import org.gravitechx.frc2018.robot.subsystems.TestableSystem;
import java.util.ArrayList;
import java.util.List;

import org.gravitechx.frc2018.utils.drivehelpers.DifferentialDriveSignal;
import org.gravitechx.frc2018.utils.drivehelpers.DrivePipeline;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;

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

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	private ControlScheme mControlScheme;
	//DifferentialDrive difDrive;
	//public Joystick mStick = new Joystick(0);
	DrivePipeline pipe = new DrivePipeline();



	/**
	 * This function is run when the robot is first startedex up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);

		drive = Drive.getInstance();

		mControlScheme = DefaultControlScheme.getInstance();
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
		// PID test
		DifferentialDriveSignal signal = pipe.apply(mControlScheme.getRotationalDriveSignal(), mControlScheme.getQuickTurnButton());
		drive.set(signal);
	}

	/**
	 * This function is called periodically during test mode
	 */

	@Override
	public void testPeriodic() {
		drive.test();
	}
}
