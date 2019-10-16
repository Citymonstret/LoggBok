package com.github.sauilitired.loggbok;

public class ColorStripper implements Logger {

    private final Logger logger;

    public ColorStripper(final Logger logger) {
        this.logger = logger;
    }

    @Override public void log(LogEntry logEntry) {
        // Intercept message
        final String message = logEntry.getMessage();
        logEntry.setMessage(message.replaceAll("%c\\((?<color>[a-z]+)\\)", ""));
        this.logger.log(logEntry);
    }

}
