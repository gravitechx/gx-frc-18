package org.gravitechx.frc2018.utils.loader.trajectory;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.gravitechx.frc2018.DataConst.PATH_TO_EXAMPLE_FILE;

class TrajectoryTest {
    @Test
    void trajectoryLoads(){
        Trajectory generated = Trajectory.load(new File(PATH_TO_EXAMPLE_FILE));
        while(!generated.isEmpty()){
            System.out.println(generated.next());
        }
    }
}