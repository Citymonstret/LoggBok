package com.github.sauilitired.loggbok;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Simple {@link Logger} implementation that
 * stores all incoming messages to a queue.
 */
@SuppressWarnings("WeakerAccess") public abstract class QueueLogger implements Logger {

    private final Queue<LogEntry> logQueueEntries;

    public QueueLogger() {
        this.logQueueEntries = new LinkedBlockingQueue<>();
    }

    @Override public void log(final int logLevel, final String message, final Object... args) {
        this.logQueueEntries.add(new LogEntry(logLevel, message, args));
    }

    @Override public void log(final LogEntry logEntry) {
        this.logQueueEntries.add(logEntry);
    }

    /**
     * Get a pending log entry. Will stall
     * the thread until a new message is available.
     *
     * @return Pending message
     */
    public final LogEntry getPending() {
        LogEntry entry;
        //noinspection StatementWithEmptyBody
        while ((entry = logQueueEntries.poll()) == null)
            ;
        return entry;
    }

}
