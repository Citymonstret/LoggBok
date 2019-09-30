package com.github.sauilitired.loggbok;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Simple {@link Logger} implementation that
 * stores all incoming messages to a queue.
 */
@SuppressWarnings("WeakerAccess") public abstract class QueueLogger implements Logger {

    private final Queue<LogQueueEntry> logQueueEntries;

    public QueueLogger() {
        this.logQueueEntries = new LinkedBlockingQueue<>();
    }

    @Override public void log(int logLevel, String message, Object... args) {
        this.logQueueEntries.add(new LogQueueEntry(logLevel, message, args));
    }

    /**
     * Get a pending log entry. Will stall
     * the thread until a new message is available.
     *
     * @return Pending message
     */
    public LogQueueEntry getPending() {
        LogQueueEntry entry;
        //noinspection StatementWithEmptyBody
        while ((entry = logQueueEntries.poll()) == null)
            ;
        return entry;
    }

    public static class LogQueueEntry {

        private final int level;
        private final String message;
        private final Object[] args;

        private LogQueueEntry(final int level, final String message, final Object[] args) {
            this.level = level;
            this.message = message;
            this.args = args;
        }

        public int getLevel() {
            return this.level;
        }

        public String getMessage() {
            return this.message;
        }

        public Object[] getArgs() {
            return this.args;
        }

    }

}
