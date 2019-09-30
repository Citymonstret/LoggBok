package com.github.sauilitired.loggbok;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings({"WeakerAccess", "unused"}) public abstract class SimpleLogger
    implements Logger, AutoCloseable {

    private final String logFormat;
    private final LogLevels logLevels;
    private String name;
    private DateTimeFormatter dateTimeFormatter;

    public SimpleLogger(final String logFormat, final LogLevels logLevels) {
        this(Thread.currentThread().getName(), logFormat, logLevels);
    }

    public SimpleLogger(final String name, final String logFormat, final LogLevels logLevels) {
        this(name, logFormat, logLevels, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public SimpleLogger(final String name, final String logFormat, final LogLevels logLevels,
        final DateTimeFormatter dateTimeFormatter) {
        this.name = name;
        this.logFormat = logFormat;
        this.logLevels = logLevels;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public String getLogFormatted(final int level, final String message, final Object[] args) {
        return getLogFormat().replace("%level%", this.getLogLevels().getLevel(level))
            .replace("%name%", this.getName())
            .replace("%time%", this.dateTimeFormatter.format(LocalTime.now()))
            .replace("%thread%", Thread.currentThread().getName())
            .replace("%message%", String.format(message, args));
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return this.dateTimeFormatter;
    }

    public void setDateTimeFormatter(final DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public String getLogFormat() {
        return this.logFormat;
    }

    public LogLevels getLogLevels() {
        return this.logLevels;
    }

    @Override public final void close() {
        try {
            this.stop();
        } catch (final Exception e) {
            throw new IllegalStateException("Failed to close the logger.", e);
        }
    }

}
