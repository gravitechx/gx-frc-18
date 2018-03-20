package org.gravitechx.frc2018.robot.io.server;

import org.gravitechx.frc2018.robot.Robot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotServerTest {
    private static int PORT = 8423;

    @Test
    void serverStarts(){
        RobotServer rs = new RobotServer(PORT, 500);
        Thread t = new Thread(rs);
        t.start();
        try {
            Thread.sleep(
                    (long) 2000
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(t.isAlive(), true);
        t.interrupt();
    }
}