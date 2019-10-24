package com.github.sauilitired.loggbok;

import org.junit.Assert;
import org.junit.Test;

public class PositionFormatterTest {

    @Test
    public void testPositionFormatter() {
        final LogFormatter positionFormatter = new PositionFormatter();
        final LogEntry logEntry = new LogEntry(LogLevels.LEVEL_INFO,
            "Hello {1}! {0} formatted this!", new Object[] {"LoggBok", "World"});
        Assert.assertEquals("Hello World! LoggBok formatted this!", positionFormatter.format(logEntry));
    }



}
