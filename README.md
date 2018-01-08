gx-frc-18
=========

GravitechX's 2018 code base for the 2018 FRC robot.

Structure
---------

The code base is structured for extendability. All code much follow a strict stucture
 be be integrated into the main branch. See below for more information.
 
 - com.team6619.frc2018.hardware
 
Contains hardware drivers, wrapper and helper classes.
 
 - com.team6619.frc2018.io
 
 Contains controller io and smart dashboard related classes.
 
  - com.team6619.frc2018.subsystems
 
 A subsystem is a partition of a robots function and contains one main class.
 
 - com.team6619.frc2018.util
 
 Contains utility or helper functions for the robot.
 
 Variable Naming Conventions
 ---------------------------
 
 Use descriptive, camel case names. 
 
 - UPPER_CASE for constants and underscores should be used between words.
    + Example: DISTANCE_TO_GOAL
 - m** for private or protected variables (for meta)
    + Example: mCurrentExtensionLength