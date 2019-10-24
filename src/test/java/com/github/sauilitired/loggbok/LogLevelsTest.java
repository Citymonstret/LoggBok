package com.github.sauilitired.loggbok;

import org.junit.Assert;
import org.junit.Test;

public class LogLevelsTest {

    @Test
    public void testEnabled() {
        final LogLevels logLevels = new LogLevels();
        Assert.assertTrue(logLevels.isEnabled(LogLevels.LEVEL_INFO));
        Assert.assertTrue(logLevels.isEnabled(LogLevels.LEVEL_WARNING));
        Assert.assertTrue(logLevels.isEnabled(LogLevels.LEVEL_ERROR));
        Assert.assertTrue(logLevels.isEnabled(LogLevels.LEVEL_DEBUG));
        logLevels.setDisplayLevel(0);
        Assert.assertFalse(logLevels.isEnabled(LogLevels.LEVEL_INFO));
        Assert.assertFalse(logLevels.isEnabled(LogLevels.LEVEL_WARNING));
        Assert.assertFalse(logLevels.isEnabled(LogLevels.LEVEL_ERROR));
        Assert.assertFalse(logLevels.isEnabled(LogLevels.LEVEL_DEBUG));
        logLevels.setEnabled(LogLevels.LEVEL_INFO, true);
        Assert.assertTrue(logLevels.isEnabled(LogLevels.LEVEL_INFO));
        Assert.assertFalse(logLevels.isEnabled(LogLevels.LEVEL_WARNING));
        Assert.assertFalse(logLevels.isEnabled(LogLevels.LEVEL_ERROR));
        Assert.assertFalse(logLevels.isEnabled(LogLevels.LEVEL_DEBUG));
        logLevels.setEnabled(LogLevels.LEVEL_INFO, false);
        Assert.assertFalse(logLevels.isEnabled(LogLevels.LEVEL_INFO));
        Assert.assertFalse(logLevels.isEnabled(LogLevels.LEVEL_WARNING));
        Assert.assertFalse(logLevels.isEnabled(LogLevels.LEVEL_ERROR));
        Assert.assertFalse(logLevels.isEnabled(LogLevels.LEVEL_DEBUG));
        logLevels.setEnabled(LogLevels.LEVEL_WARNING, true);
        logLevels.setEnabled(LogLevels.LEVEL_DEBUG, true);
        Assert.assertFalse(logLevels.isEnabled(LogLevels.LEVEL_INFO));
        Assert.assertTrue(logLevels.isEnabled(LogLevels.LEVEL_WARNING));
        Assert.assertFalse(logLevels.isEnabled(LogLevels.LEVEL_ERROR));
        Assert.assertTrue(logLevels.isEnabled(LogLevels.LEVEL_DEBUG));
    }

    @Test
    public void testLevels() {
        final LogLevels logLevels = new LogLevels();
        Assert.assertEquals("INFO", logLevels.getLevel(LogLevels.LEVEL_INFO));
        Assert.assertEquals("WARNING", logLevels.getLevel(LogLevels.LEVEL_WARNING));
        Assert.assertEquals("ERROR", logLevels.getLevel(LogLevels.LEVEL_ERROR));
        Assert.assertEquals("DEBUG", logLevels.getLevel(LogLevels.LEVEL_DEBUG));
    }

    @Test
    public void testAddLevel() {
        final LogLevels logLevels = new LogLevels();
        final String levelName = "TEST";
        Assert.assertEquals(levelName, logLevels.getLevel(logLevels.addLevel(levelName)));
    }

    @Test
    public void testNextLevel() {
        final LogLevels logLevels = new LogLevels();
        Assert.assertEquals(0x10, logLevels.getNextLevel());
        logLevels.addLevel("TEST1");
        Assert.assertEquals(0x20, logLevels.getNextLevel());
        final int level = logLevels.addLevel("TEST2");
        Assert.assertEquals(level << 1, logLevels.getNextLevel());
    }

}
