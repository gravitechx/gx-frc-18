package org.usfirst.frc.team6619.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCommands extends CommandGroup {

    public AutoCommands() {
<<<<<<< HEAD
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	//THIS IS WHERE YOU COMMANDS LIKE SCRATCH BLOCK PROGRAMMING
    	addSequential(new DriveF (3));
    	addSequential(new DriveR (3));
=======
        /*Command calls are placed in this method in the order that they want to be called in.
         * There are two different ways commands can be called: with "addSequential(new commmand())" and with "addParallel(new command())".
         * addSequential's have commands execute one after another.
         * addParallel's have commands executed at the same time.
         */
    	addSequential(new DriveF(3));
>>>>>>> a9da5c1ab61d99c8f21e41a544f574d657627637
    }
}
