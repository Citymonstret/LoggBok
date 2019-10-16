package com.github.sauilitired.loggbok;

public class Main {

    public static void main(String[] args) {
        final String logFormat = "[%thread%][%level%][%time%] %message%\n";
        final LogLevels logLevels = new LogLevels();
        try (final PrintStreamLogger stdout = new PrintStreamLogger(System.out, logFormat, logLevels);
            final PrintStreamLogger stderr = new PrintStreamLogger(System.err, logFormat, logLevels);
            final LevelSplitLogger levelSplitLogger = new LevelSplitLogger(new ColorLogger(stdout),
                logLevels).split(LogLevels.LEVEL_ERROR, new ColorStripper(stderr))) {
            levelSplitLogger.info("%c(green)Hello %c(yellow)World!");
            levelSplitLogger.error("%c(green)Hello %c(yellow)Error!");
        }

    }

}
