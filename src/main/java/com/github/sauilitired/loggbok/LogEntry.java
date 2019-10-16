package com.github.sauilitired.loggbok;

import java.time.LocalTime;
import java.time.temporal.Temporal;

public final class LogEntry {

    private int level;
    private String message;
    private Object[] args;
    private Temporal timestamp = LocalTime.now();
    private long threadId = Thread.currentThread().getId();

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

    public long getThreadId() {
        return this.threadId;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public void setTimestamp(Temporal timestamp) {
        this.timestamp = timestamp;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

}
