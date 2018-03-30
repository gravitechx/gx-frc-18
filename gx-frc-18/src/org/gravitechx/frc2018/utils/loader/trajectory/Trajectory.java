package org.gravitechx.frc2018.utils.loader.trajectory;

import org.gravitechx.frc2018.utils.loader.file.FileHandeler;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import org.gravitechx.frc2018.utils.loader.file.*;

public class Trajectory {
    private Queue<TrajectoryPoint> feed;

    public Trajectory(){
        feed = new LinkedList<>();
    }

    public void add(TrajectoryPoint p) {
        feed.add(p);
    }

    public void add(TrajectoryPoint[] p){
        for (int i = 0; i < p.length; i++){
            add(p[i]);
        }
    }

    public TrajectoryPoint next(){
        return feed.remove();
    }

    public boolean isEmpty(){
        return feed.isEmpty();
    }

    public static Trajectory load(File file){
        FileHandeler fileHandeler = new FileHandeler(file);
        TableCSVConsumer csv = new TableCSVConsumer();
        Trajectory trajectory = new Trajectory();

        fileHandeler.read(csv);

        String[] tokens;
        while((tokens = csv.returnNextRow(3)) != null){
            trajectory.add(
                    new TrajectoryPoint(
                            Float.parseFloat(tokens[0]),
                            Float.parseFloat(tokens[1]),
                            Integer.parseInt(tokens[2])
                    )
            );
        }

        return trajectory;
    }
}
