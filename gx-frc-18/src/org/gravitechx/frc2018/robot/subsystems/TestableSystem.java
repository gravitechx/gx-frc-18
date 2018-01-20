package org.gravitechx.frc2018.robot.subsystems;

/**
 * Start of a "unit testing" like framework for the robot.
 * The idea is that the robot could go through a set of mechanical tests to verify that it works correctly.
 * @todo finish implementation
 */
public interface TestableSystem {
    /**
     * Tests the subsystem.
     */
    public void initializeTest();
    public void test();
}
