
package org.usfirst.frc.team6619.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import org.usfirst.frc.team6619.robot.io.*;
import org.usfirst.frc.team6619.robot.commands.ExampleCommand;
import org.usfirst.frc.team6619.robot.subsystems.ExampleSubsystem;

import javax.swing.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public double[] amps;
	public static Joystick joy;
	public static OI oi;
	public static RobotMap rm;
	public static SmartDashboard SmartDash;
	public static PowerDistributionPanel pdp;
	
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		amps = new double[16];
		joy = new Joystick(0);		
		oi = new OI();
		rm = new RobotMap();
		SmartDash = new SmartDashboard();
		pdp = new PowerDistributionPanel();
		chooser.addDefault("Default Auto", new ExampleCommand());
		//chooser.addObject("My Auto", new MyAutoCommand());
		for (int x = 0; x <= 14; x++){
			amps[x] = 0;
		}
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
		SmartDash.putNumber("Total Voltage", pdp.getVoltage());
		for (int x = 0; x <= 15; x++){
			if (pdp.getCurrent(x) > amps[x]){
				amps[x] = pdp.getCurrent(x);
			}
			SmartDash.putNumber("Port " + x, pdp.getCurrent(x));
			for (int n = 12; n <= 15; n++){
			SmartDash.putNumber("Max Amps " + n, amps[n - 1]);
			}
		}
		SmartDash.putNumber("Total Current", pdp.getTotalCurrent());
		SmartDash.putNumber("Total Energy", pdp.getTotalEnergy());
		SmartDash.putNumber("Total Power", pdp.getTotalPower());
		SmartDash.putBoolean("  ON? OFF?", true);
		rm.myRobot.arcadeDrive(joy);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
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
		SmartDash.putNumber("Total Voltage", pdp.getVoltage());
		for (int x = 1; x <= 15; x++){
			if (pdp.getCurrent(x) > amps[x - 1]){
				amps[x - 1] = pdp.getCurrent(x);
			}
			SmartDash.putNumber("Port " + x, pdp.getCurrent(x));
			for (int n = 12; n <= 15; n++){
			SmartDash.putNumber("Max Amps " + n, amps[n - 1]);
			}
		}
		SmartDash.putNumber("Total Current", pdp.getTotalCurrent());
		SmartDash.putNumber("Total Energy", pdp.getTotalEnergy());
		SmartDash.putNumber("Total Power", pdp.getTotalPower());
		SmartDash.putBoolean("  ON? OFF?", true);
		rm.myRobot.arcadeDrive(joy);
	}

	@Override
	public void teleopInit() {
		pdp.resetTotalEnergy();
		for (int x = 0; x <= 14; x++){
			amps[x] = 0;
		}
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
		
		
		
		SmartDash.putNumber("Total Voltage", pdp.getVoltage());
		for (int x = 1; x <= 15; x++){
			if (pdp.getCurrent(x) > amps[x - 1]){
				amps[x - 1] = pdp.getCurrent(x);
			}
			SmartDash.putNumber("Port " + x, pdp.getCurrent(x));
			for (int n = 12; n <= 15; n++){
			SmartDash.putNumber("Max Amps " + n, amps[n - 1]);
			}
		}
		SmartDash.putNumber("Total Current", pdp.getTotalCurrent());
		SmartDash.putNumber("Total Energy", pdp.getTotalEnergy());
		SmartDash.putNumber("Total Power", pdp.getTotalPower());
		SmartDash.putBoolean("  ON? OFF?", true);
		rm.myRobot.arcadeDrive(joy);
		InternalButton() {
	}


	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}