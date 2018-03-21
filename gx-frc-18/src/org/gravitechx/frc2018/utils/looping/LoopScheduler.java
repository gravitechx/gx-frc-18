package org.gravitechx.frc2018.utils.looping;

import edu.wpi.first.wpilibj.Notifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Schedules and keeps a record of all active loops.
 */
public class LoopScheduler {
    private List<Loop> mLoops;
    private Notifier mNotifier;
    private boolean mRunning;
    private double mPeriod;
    private RemoteTimestamp ts;

    /**
     * Constructs a loop scheduler using the run period
     * @param runPeriod
     */
    private LoopScheduler(double runPeriod) {
        mLoops = new ArrayList<>();
        ts = new RemoteTimestamp();
        mNotifier = new Notifier(
                () -> {
                    if(mRunning){
                        ts.update();
                        for (Loop loop : mLoops) {
                            loop.update(ts);
                        }
                    }
                }
        );
        mRunning = false;
        mPeriod = runPeriod;
    }

    // Singleton
    private static LoopScheduler mThreadRegistry = new LoopScheduler(.05);
    public static LoopScheduler getInstance() {
        return mThreadRegistry;
    }

    /**
     * Add a loop to the loop scheduler.
     * @param lt
     */
    public void add(Loop lt) {
        mLoops.add(lt);
    }

    /**
     * Start all loops in the scheduler.
     */
    public void startAll(){
        for (Loop loop : mLoops) {
           loop.initalize();
        }
        mNotifier.startPeriodic(mPeriod);
        System.out.print("Looping");
    }

    /**
     * Stop all loops in the sceduler.
     */
    public void stopAll() {
        mNotifier.stop();
        for(Loop loop : mLoops){
            loop.deinitalize();
        }
    }
}
