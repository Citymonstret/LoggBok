package com.github.sauilitired.loggbok;

import org.junit.Test;

public class ColorLoggerTest {

    private ColorLogger createColorLogger() {
        final StringBuilder expectedString = new StringBuilder("Hello ");
        expectedString.append(ColorLogger.COLOR_RED).append("World!")
            .append(ColorLogger.COLOR_RESET);
        final AssertionLogger assertionLogger = new AssertionLogger(expectedString.toString());
        return new ColorLogger(assertionLogger);
    }

    private Logger createColorStripper() {
        final AssertionLogger assertionLogger = new AssertionLogger("Hello World!");
        return new ColorLogger(new ColorStripper(assertionLogger));
    }

    @Test
    public void testColorLogger() {
        createColorLogger().log(LogLevels.LEVEL_INFO, "Hello %c(red)World!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testColorLoggerIllegalArgument() {
        createColorLogger().log(LogLevels.LEVEL_INFO, "Hello %c(green)World!");
    }

    @Test
    public void testColorStripper() {
        createColorStripper().log(LogLevels.LEVEL_INFO, "Hello World!");
    }

}
