package com.github.sauilitired.loggbok;

/**
 * {@link Logger} implementation that delegates
 * the incoming messages to several different loggers.
 */
@SuppressWarnings("unused") public class SplitLogger implements Logger {

    private final Logger[] loggers;

    public SplitLogger(final Logger... loggers) {
        this.loggers = loggers;
    }

    @Override public void log(final LogEntry logEntry) {
        for (final Logger logger : this.loggers) {
            logger.log(logEntry);
        }
    }

    @Override public void stop() throws Exception {
        for (final Logger logger : this.loggers) {
            logger.stop();
        }
    }

}
