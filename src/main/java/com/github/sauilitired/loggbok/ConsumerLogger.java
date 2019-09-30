package com.github.sauilitired.loggbok;

import java.util.function.Consumer;

@SuppressWarnings("unused") public class ConsumerLogger implements Logger {

    private final Consumer<LogEntry> consumer;

    public ConsumerLogger(final Consumer<LogEntry> consumer) {
        this.consumer = consumer;
    }

    @Override public void log(final LogEntry logEntry) {
        this.consumer.accept(logEntry);
    }

}
