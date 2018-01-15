package org.usfirst.frc.team6619.robot.io;


import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardA {
	
	public static double[] amps = new double[16];
	public static SmartDashboard SmartDash;
	public static PowerDistributionPanel pdp;
	
	public static void run() {
		SmartDash = new SmartDashboard();
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
	}
}
