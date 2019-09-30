package com.github.sauilitired.loggbok;

@SuppressWarnings("unused") public interface Logger extends AutoCloseable {

    void log(LogEntry logEntry);

    default void log(int logLevel, String message, Object... args) {
        log(new LogEntry(logLevel, message, args));
    }

    default void info(String message, Object... args) {
        log(LogLevels.LEVEL_INFO, message, args);
    }

    default void warning(String message, Object... args) {
        log(LogLevels.LEVEL_WARNING, message, args);
    }

    default void error(String message, Object... args) {
        log(LogLevels.LEVEL_ERROR, message, args);
    }

    default void debug(String message, Object... args) {
        log(LogLevels.LEVEL_DEBUG, message, args);
    }

    default void stop() throws Exception {
    }

    @Override default void close() {
        try {
            this.stop();
        } catch (final Exception e) {
            throw new IllegalStateException("Failed to close the logger.", e);
        }
    }

}
