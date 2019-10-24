package com.github.sauilitired.loggbok;

import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * {@link Logger} implementation that delegates
 * logging to a Java {@link java.util.logging.Logger}
 */
public class JavaDelegateLogger extends SimpleLogger {

    private final java.util.logging.Logger javaLogger;

    public JavaDelegateLogger(final java.util.logging.Logger logger,
        final String logFormat, final LogLevels logLevels) {
        super(logFormat, logLevels);
        this.javaLogger = logger;
    }

    @Override public void log(final LogEntry logEntry) {
        if (getLogLevels().isEnabled(logEntry.getLevel())) {
            final LogRecord logRecord = new LogRecord(getLevel(logEntry.getLevel()), getLogFormatted(logEntry));
            logRecord.setLoggerName(this.getName());
            logRecord.setParameters(logEntry.getArgs());
            logRecord.setThreadID((int) logEntry.getThreadId());
            this.javaLogger.log(logRecord);
        }
    }

    private static Level getLevel(final int level) {
        switch (level) {
            case LogLevels.LEVEL_WARNING:
                return Level.WARNING;
            case LogLevels.LEVEL_ERROR:
                return Level.SEVERE;
            default:
                return Level.INFO;
        }
    }

}
