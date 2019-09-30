package com.github.sauilitired.loggbok;

import java.time.LocalTime;
import java.time.temporal.Temporal;

public final class LogEntry {

    private final int level;
    private final String message;
    private final Object[] args;
    private final Temporal timestamp = LocalTime.now();
    private final Thread thread = Thread.currentThread();

    public LogEntry(final int level, final String message, final Object[] args) {
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

    public Temporal getTimestamp() {
        return this.timestamp;
    }

    public Thread getThread() {
        return this.thread;
    }

}
