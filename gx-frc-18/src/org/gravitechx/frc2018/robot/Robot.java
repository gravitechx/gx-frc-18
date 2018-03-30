
package org.gravitechx.frc2018.robot;

import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.*;
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
import org.gravitechx.frc2018.utils.drivehelpers.DifferentialDriveSignal;
import org.gravitechx.frc2018.utils.drivehelpers.DrivePipeline;
import org.gravitechx.frc2018.utils.drivehelpers.RotationalDriveSignal;
import org.gravitechx.frc2018.utils.looping.Loop;
import org.gravitechx.frc2018.utils.looping.LoopScheduler;
import org.gravitechx.frc2018.utils.looping.Timestamp;
import org.gravitechx.frc2018.utils.wrappers.GravAHRS;

import java.util.stream.StreamSupport;

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
	public boolean isGrabbing;

	public Timer autonTimer;

	//public static PowerDistributionPanel pdp;
	enum AutonOptions{
		LEFT(),
		GOFORWARD(),
		RIGHT()
	}

	SendableChooser<AutonOptions> chooser = new SendableChooser<>();
	private ControlScheme mControlScheme;

	String gameData;

	/**
	 * This function is run when the robot is first startedex up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Left", AutonOptions.LEFT);
		chooser.addObject("Go Forward", AutonOptions.GOFORWARD);
		chooser.addObject("Right", AutonOptions.RIGHT);
		SmartDashboard.putData("Auto mode", chooser);

		drive = Drive.getInstance();
		lift = Lift.getInstance();
		dPipe = new DrivePipeline();
		bio = BIO.getInstance();
		//pdp  = new PowerDistributionPanel(0);

		mControlScheme = DefaultControlScheme.getInstance();

		lift.zeroPosition();

		//cameraServer = CameraServer.getInstance().getServer();

		//topCam = new UsbLifeCam(Constants.TOP_CAM);

		//cameraServer.setSource(topCam.getCamera());
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
		AutonOptions selected;
		selected = chooser.getSelected();
		System.out.println("left: " + AutonOptions.LEFT + " right: "+ AutonOptions.RIGHT);
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)

		lift.zeroPosition();
		autonTimer = new Timer();
		autonTimer.start();

		String gameData;

		gameData = DriverStation.getInstance().getGameSpecificMessage();

		if(gameData != null && gameData.length() > 0){
			if(AutonOptions.LEFT == selected && gameData.charAt(0) == 'L'){
				autoIsAGo = true;
			}else if(AutonOptions.RIGHT == selected && gameData.charAt(0) == 'R'){
				autoIsAGo = true;
			}else{
				autoIsAGo = false;
			}
		}
	}

	boolean autoIsAGo = false;

	/**
	 * This function is called periodically during autonom  ous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		if (autonTimer.get() < 5.5) {
			drive.set(new RotationalDriveSignal(.2, 0.0).toDifferencialDriveSignal());
		} else if (autonTimer.get() >= 5.5 && autonTimer.get() < 6.5) {
			if(autoIsAGo)
			bio.setIntake(-0.25);
		} else {
			drive.set(new DifferentialDriveSignal(0.0, 0.0));
			bio.setIntake(0.0);
		}
	}

	@Override
	public void teleopInit() {
		lift.zeroPosition();
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

		//lift.set(mControlScheme.getLiftManualAxis());

		lift.setRelitivePosition(mControlScheme.getLiftManualAxis(), mControlScheme.getDAxis(), 0.0);


		SmartDashboard.putNumber("Axis", mControlScheme.getLiftManualAxis());

		lift.graphPIDOuts();

		if(mControlScheme.getInhalingButton() && bio.getControlState() != BIO.ControlState.EXHALING){
			bio.set(BIO.ControlState.INHALING);
		}else if (mControlScheme.getExhalingButton()){
			bio.set(BIO.ControlState.EXHALING);
		}else{
			bio.set(BIO.ControlState.NEUTRAL);
		}

		bio.setShouldExhale(
				bio.getControlState() == BIO.ControlState.EXHALING && mControlScheme.getInhalingButton()
		);

		if(mControlScheme.getGrabbingButton()){
			bio.grasp(BIO.GraspingStatus.CLOSED);
		}else{
			bio.grasp(BIO.GraspingStatus.OPEN);
		}

		lift.loop();

		bio.update();
		mControlScheme.update((new Timestamp()).get());
	}

	/**
	 * This function is called periodically during test mode
	 */

	@Override
	public void testPeriodic() {
		//lift.setDirect(.2);
	}
}
