
package org.gravitechx.frc2018.robot;

import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.gravitechx.frc2018.robot.io.controlschemes.ControlScheme;
import org.gravitechx.frc2018.robot.io.controlschemes.DefaultControlScheme;
import org.gravitechx.frc2018.robot.subsystems.BIO;
import org.gravitechx.frc2018.robot.subsystems.Drive;
import org.gravitechx.frc2018.robot.subsystems.Lift;
import org.gravitechx.frc2018.utils.UsbLifeCam;
import org.gravitechx.frc2018.utils.drivehelpers.DrivePipeline;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;
import org.gravitechx.frc2018.utils.looping.Loop;
import org.gravitechx.frc2018.utils.looping.LoopScheduler;
import org.gravitechx.frc2018.utils.wrappers.GravAHRS;

import static org.gravitechx.frc2018.utils.drivehelpers.DriveSignal.limit;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Drive drive;
	public static DrivePipeline dPipe;
	public static Lift lift;
	public static BIO bio;
	public static GravAHRS ahrs;
	public boolean isGrabbing;
	public VideoSink cameraServer;
	public UsbLifeCam topCam;

	//public static PowerDistributionPanel pdp;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	private ControlScheme mControlScheme;



	/**
	 * This function is run when the robot is first startedex up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);

		drive = Drive.getInstance();
		lift = Lift.getInstance();
		dPipe = new DrivePipeline();
		bio = BIO.getInstance();
		//pdp  = new PowerDistributionPanel(0);
		ahrs = new GravAHRS(SPI.Port.kMXP);

		mControlScheme = DefaultControlScheme.getInstance();

		cameraServer = CameraServer.getInstance().getServer();

		topCam = new UsbLifeCam(Constants.TOP_CAM);

		cameraServer.setSource(topCam.getCamera());
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
		drive.set(dPipe.apply(
						new RotationalDriveSignal(mControlScheme.getThrottle(), mControlScheme.getWheel()),
						mControlScheme.getQuickTurnButton())
		);

		drive.graphEncodersToConsole();

		//lift.setDirect(-mControlScheme.getFusedAxis());

		lift.set(-mControlScheme.getFusedAxis());

		//lift.setRelitivePosition(-mControlScheme.getFusedAxis());

		if(mControlScheme.getInhalingButton() && bio.getControlState() != BIO.ControlState.EXHALING){
			bio.set(BIO.ControlState.INHALING);
		}else if (mControlScheme.getExhalingButton()){
			bio.set(BIO.ControlState.EXHALING);
		}else{
			bio.set(BIO.ControlState.NEUTRAL);
		}

		if(bio.getControlState() == BIO.ControlState.EXHALING && mControlScheme.getInhalingButton()){
			bio.setShouldExhale(true);
		}else{
			bio.setShouldExhale(false);
		}

		if(mControlScheme.getGrabbingButton()){
			bio.grasp(BIO.GraspingStatus.CLOSED);
		}else{
			bio.grasp(BIO.GraspingStatus.OPEN);
		}

		//System.out.printf("DISTANCE TRAVELED: " + lift.getPosition() + "\n");
		/*System.out.print(
				"AHRS Yaw: " + ahrs.getYawDegrees()
				+ "AHRS Yaw / Sec: " + ahrs.getYawRateDegreesPerSecond()
				+ "AHRS Pitch: " + ahrs.getPitchDegrees()
				+ "AHRS Pitch / Sec: " + ahrs.getPitchDegreesPerSecond()
		);*/

		bio.update();
		mControlScheme.update();
	}

	/**
	 * This function is called periodically during test mode
	 */

	@Override
	public void testPeriodic() {
		drive.test();
	}
}
