package com.github.sauilitired.loggbok;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link QueueLogger} implementation that delegates messages to a separate logger.
 * The logging logic runs in a separate thread, and continuously polls the underlying
 * queue for new messages.
 */
@SuppressWarnings("WeakerAccess") public class ThreadedQueueLogger extends QueueLogger
    implements Runnable {

    private static final AtomicInteger threadCounter = new AtomicInteger(0);

    private final Thread thread;
    private final Logger internalLogger;

    public ThreadedQueueLogger(final Logger internalLogger) {
        this.internalLogger = internalLogger;
        this.thread = new Thread(this);
        this.thread.setDaemon(true);
        this.thread.setName(String.format("logthread-%d", threadCounter.getAndIncrement()));
        this.thread.start();
    }

    @Override public void run() {
        while (!this.thread.isInterrupted() && this.thread.isAlive()) {
            final LogQueueEntry pendingEntry = getPending();
            this.internalLogger
                .log(pendingEntry.getLevel(), pendingEntry.getMessage(), pendingEntry.getArgs());
        }
    }

    public Thread getThread() {
        return this.thread;
    }

    @Override public void stop() {
        this.getThread().interrupt();
    }

}
