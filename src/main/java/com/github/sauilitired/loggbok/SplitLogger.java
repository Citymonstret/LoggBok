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

    @Override public void log(int logLevel, String message, Object... args) {
        for (final Logger logger : this.loggers) {
            logger.log(logLevel, message, args);
        }
    }

    @Override public void stop() throws Exception {
        for (final Logger logger : this.loggers) {
            logger.stop();
        }
    }

}
