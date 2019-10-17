package com.github.sauilitired.loggbok;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class ErrorDigest {

    private final Logger logger;
    private final Object lock = new Object();
    private final PrintStream printStream;

    private int level;

    public ErrorDigest(final Logger logger) {
        this.logger = logger;
        this.printStream = new PrintStream(new ErrorOutputStream(), true);
    }

    public void digest(final Throwable throwable) {
        this.digest(LogLevels.LEVEL_ERROR, throwable);
    }

    public void digest(final int logLevel, final Throwable throwable) {
        synchronized (this.lock) {
            this.level = logLevel;
            throwable.printStackTrace(this.printStream);
        }
    }

    public PrintStream getPrintStream() {
        return this.printStream;
    }

    private final class ErrorOutputStream extends ByteArrayOutputStream {

        @Override public void flush() {
            String message = new String(toByteArray(), StandardCharsets.UTF_8);
            if (message.endsWith(System.lineSeparator())) {
                message = message.substring(0, message.length() - System.lineSeparator().length());
            }
            if (!message.isEmpty()) {
                logger.log(level, new String(toByteArray(), StandardCharsets.UTF_8));
            }
            super.reset();
        }

    }

}
