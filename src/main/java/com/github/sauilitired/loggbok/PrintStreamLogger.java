package com.github.sauilitired.loggbok;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;

/**
 * {@link SimpleLogger} implementation that writes to a {@link PrintStream}.
 */
@SuppressWarnings("unused") public class PrintStreamLogger extends SimpleLogger {

    private final PrintStream printStream;

    public PrintStreamLogger(final PrintStream printStream, final String logFormat,
        final LogLevels logLevels) {
        super(logFormat, logLevels);
        this.printStream = printStream;
    }

    public PrintStreamLogger(final String name, final PrintStream printStream,
        final String logFormat, final LogLevels logLevels) {
        super(name, logFormat, logLevels);
        this.printStream = printStream;
    }

    public PrintStreamLogger(final String name, final PrintStream printStream,
        final String logFormat, final LogLevels logLevels,
        final DateTimeFormatter dateTimeFormatter) {
        super(name, logFormat, logLevels, dateTimeFormatter);
        this.printStream = printStream;
    }

    @Override public void log(final LogEntry logEntry) {
        if (this.getLogLevels().getDisplayLevel() < logEntry.getLevel()) {
            return;
        }
        printStream.print(getLogFormatted(logEntry));
    }

    public PrintStream getPrintStream() {
        return this.printStream;
    }

}
