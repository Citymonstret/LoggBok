package com.github.sauilitired.loggbok;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileLoggerTest {

    private Path path;

    @Before
    public void before() throws Exception {
        this.path = new File("test.log").toPath();
        if (Files.exists(this.path)) {
            throw new IllegalStateException("test.log already exists");
        } else {
            Files.createFile(this.path);
        }
    }

    @After
    public void after() throws Exception {
        if (this.path != null && Files.exists(this.path)) {
            Files.delete(this.path);
        }
    }

    @Test
    public void testFileLogger() throws Exception {
        final String format = "[%thread%][%level%]: %message%\n";
        final LogLevels logLevels = new LogLevels();
        final FileLogger fileLogger = new FileLogger(this.path, format, logLevels);
        final StringBuilder expectedString =
            new StringBuilder("[").append(Thread.currentThread().getName()).append("][INFO]: Test Message");
        fileLogger.log(LogLevels.LEVEL_INFO, "Test Message");
        final List<String> lines = Files.readAllLines(this.path);
        if (lines.size() != 1) {
            Assert.fail(String.format("Expected 1 line to be found in test.log, got %d.", lines.size()));
        } else {
            Assert.assertEquals(expectedString.toString(), lines.get(0));
        }
    }

}
