package com.github.sauilitired.loggbok;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Wrapper class that allows you to log to a LoggBok {@link com.github.sauilitired.loggbok.Logger logger}
 * using the Java {@link Logger logging} API
 */
public class JavaLogger extends Logger {

    private final com.github.sauilitired.loggbok.Logger logger;

    public static JavaLogger wrap(final com.github.sauilitired.loggbok.Logger logger) {
        final String name;
        if (logger instanceof SimpleLogger) {
            name = ((SimpleLogger) logger).getName();
        } else {
            name = Thread.currentThread().getName();
        }
        return wrap(logger, name);
    }

    public static JavaLogger wrap(final com.github.sauilitired.loggbok.Logger logger,
        final String loggerName) {
        return new JavaLogger(loggerName, logger);
    }

    private JavaLogger(final String name, final com.github.sauilitired.loggbok.Logger logger) {
        super(name, null);
        this.logger = logger;
    }

    @Override public void log(final LogRecord record) {
        final LogEntry logEntry = new LogEntry(getLevel(record.getLevel()),
            record.getMessage(), record.getParameters());
        logEntry.setThreadId(record.getThreadID());
        this.logger.log(logEntry);
    }

    private static int getLevel(final Level level) {
        switch (level.getName()) {
            case "WARNING":
                return LogLevels.LEVEL_WARNING;
            case "SEVERE":
                return LogLevels.LEVEL_ERROR;
            default:
                return LogLevels.LEVEL_INFO;
        }
    }

}
