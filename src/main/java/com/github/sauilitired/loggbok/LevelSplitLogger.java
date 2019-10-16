package com.github.sauilitired.loggbok;

import java.util.HashMap;
import java.util.Map;

/**
 * Logger that allows different loggers to handle the log entries
 * depending on their log level
 */
public class LevelSplitLogger implements Logger {

    private final Map<Integer, Logger> loggerMap;
    private final Logger defaultLogger;

    /**
     * Construct a new LevelSplitLogger without a default logger.
     *
     * @param logLevels The log level instance to use in this particular logger.
     *                  May be null.
     */
    public LevelSplitLogger(final LogLevels logLevels) {
        this(null, logLevels);
    }

    /**
     * Construct a new LevelSplitLogger with a specified default logger.
     *
     * @param defaultLogger The default logger, which will be used if no other
     *                      mapping is found for the log level used.
     * @param logLevels     The log level instance to use in this particular logger.
     *                      May be null.
     */
    public LevelSplitLogger(final Logger defaultLogger, final LogLevels logLevels) {
        this.defaultLogger = defaultLogger;

        final int size;
        if (logLevels == null) {
            size = LogLevels.LEVEL_DEBUG + 1;
        } else {
            size = logLevels.getLevelCount();
        }

        this.loggerMap = new HashMap<>(size);
    }

    /**
     * Add a level-logger mapping.
     *
     * @param level  The level to map
     * @param logger The logger to use for the mapping. Should not be null.
     * @return The LevelSplitLogger instance.
     */
    public LevelSplitLogger split(final int level, final Logger logger) {
        this.loggerMap.put(level, logger);
        return this;
    }

    @Override public void log(final LogEntry logEntry) {
        Logger logger = loggerMap.get(logEntry.getLevel());
        if (logger == null) {
            logger = defaultLogger;
        }
        if (logger != null) {
            logger.log(logEntry);
        }
    }

}
