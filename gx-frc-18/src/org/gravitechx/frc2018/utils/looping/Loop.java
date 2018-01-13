package org.gravitechx.frc2018.utils.looping;

/**
 * Contructs a loop that will run concurrently on a runnable thread.
 */
abstract public class Loop {
    /**
     * Initialize any values prior to looping
     */
    void initalize() {
        // Overload me
    }

    /**
     * Constant updating function. Time can be received from @see {Timestamp}
     * @param timestamp
     */
    abstract void update(Timestamp timestamp);

    /**
     * Denitializes or logs values.
     */
    void deinitalize() {}
}