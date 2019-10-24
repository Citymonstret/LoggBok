package com.github.sauilitired.loggbok;

public class StandardFormatter implements LogFormatter {

    @Override public String format(final LogEntry logEntry) {
        return String.format(logEntry.getMessage(), logEntry.getArgs());
    }

}
