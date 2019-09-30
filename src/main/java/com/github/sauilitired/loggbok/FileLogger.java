package com.github.sauilitired.loggbok;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;

/**
 * A {@link SimpleLogger} implementation that outputs to
 * a {@link Path}.
 * <p>
 * It is recommended to wrap this logger with a {@link ThreadedQueueLogger}.
 */
@SuppressWarnings("unused") public class FileLogger extends SimpleLogger {

    private final Writer writer;

    public FileLogger(final Path path, final String logFormat, final LogLevels logLevels) {
        super(logFormat, logLevels);
        if (!Files.exists(path) || Files.isDirectory(path)) {
            throw new IllegalArgumentException("Supplied path must be an existing file.");
        }
        try {
            this.writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
        } catch (final IOException exception) {
            throw new RuntimeException("Failed to open the path for writing.", exception);
        }
    }

    public FileLogger(final String name, final Path path, final String logFormat,
        final LogLevels logLevels) {
        super(name, logFormat, logLevels);
        if (!Files.exists(path) || Files.isDirectory(path)) {
            throw new IllegalArgumentException("Supplied path must be an existing file.");
        }
        try {
            this.writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
        } catch (final IOException exception) {
            throw new RuntimeException("Failed to open the path for writing.", exception);
        }
    }

    public FileLogger(final String name, final Path path, final String logFormat,
        final LogLevels logLevels, final DateTimeFormatter dateTimeFormatter) {
        super(name, logFormat, logLevels, dateTimeFormatter);
        if (!Files.exists(path) || Files.isDirectory(path)) {
            throw new IllegalArgumentException("Supplied path must be an existing file.");
        }
        try {
            this.writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
        } catch (final IOException exception) {
            throw new RuntimeException("Failed to open the path for writing.", exception);
        }
    }

    @Override public void log(int logLevel, String message, Object... args) {
        try {
            final String finalizedMessage = getLogFormatted(logLevel, message, args);
            this.writer.write(finalizedMessage);
            if (finalizedMessage.contains("\n") || finalizedMessage.contains("\r")) {
                this.writer.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override public void stop() throws Exception {
        this.writer.close();
    }

}
