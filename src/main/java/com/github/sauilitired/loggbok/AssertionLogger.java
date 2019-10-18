package com.github.sauilitired.loggbok;

public class AssertionLogger implements Logger {

    private final Object targetLock = new Object();
    private String target;

    public AssertionLogger(final String target) {
        this.target = target;
    }

    public String getTarget() {
        synchronized (targetLock) {
            return this.target;
        }
    }

    public void setTarget(final String target) {
        synchronized (targetLock) {
            this.target = target;
        }
    }

    @Override public void log(LogEntry logEntry) {
        synchronized (targetLock) {
            if (logEntry == null) {
                throw new NullPointerException("logEntry is null");
            }
            final String message = logEntry.getMessage();
            if (message == null) {
                throw new NullPointerException("logEntry#getMessage is null");
            }
            if (message.equalsIgnoreCase(target) && !message.equals(this.target)) {
                throw new IllegalArgumentException(String
                    .format("Expected '%s' but got '%s' (Incorrect casing)", this.target, message));
            } else if (!message.equalsIgnoreCase(target)) {
                throw new IllegalArgumentException(
                    String.format("Expected '%s' but got '%s'", this.target, message));
            }
        }
    }

}
