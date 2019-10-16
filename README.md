**W.I.P** Really simple Java logging framework.

### Example

```java
// Create a file to log to
final Path testFile = new File("./test.log").toPath();
if (!Files.exists(testFile)) {
    try {
        Files.createFile(testFile);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
// Specify a log format
final String logFormat = "[%thread%][%level%][%time%] %message%\n";
// The loggers are AutoCloseable
try (final Logger fileLogger = new ThreadedQueueLogger(
    new FileLogger(testFile, logFormat, new LogLevels()));
    final Logger threadedQueueLogger = new ThreadedQueueLogger(
        new PrintStreamLogger(System.out, logFormat, new LogLevels()));
    final Logger logger = new SplitLogger(fileLogger, threadedQueueLogger)) {
    logger.info("Hello World!");
    logger.error("Hello Error!");
}
```

There are also other utility wrappers, such as LevelSplitLogger, which
handles different logging levels using different loggers:

```java
final String logFormat = "[%thread%][%level%][%time%] %message%\n";
final LogLevels logLevels = new LogLevels();
try (final PrintStreamLogger stdout = new PrintStreamLogger(System.out, logFormat, logLevels);
     final PrintStreamLogger stderr = new PrintStreamLogger(System.err, logFormat, logLevels);
     final LevelSplitLogger levelSplitLogger = new LevelSplitLogger(stdout, logLevels).split(LogLevels.LEVEL_ERROR, stderr)) {
    levelSplitLogger.info("Hello World!");
    levelSplitLogger.error("Hello Error!");
}
```

LoggBok also allows you to interact with the Java logging API. This
can be done either by delegating logging to a Java logger, or by wrapping
a LoggBok logger in a Java logger, and thus using the Java logging API to log
to LoggBok. 

```java
final String logFormat = "[%thread%][%level%][%time%] %message%\n";
final LogLevels logLevels = new LogLevels();
try (final PrintStreamLogger stdout = new PrintStreamLogger(System.out, logFormat, logLevels);
    final PrintStreamLogger stderr = new PrintStreamLogger(System.err, logFormat, logLevels);
    final LevelSplitLogger levelSplitLogger = new LevelSplitLogger(stdout, logLevels).split(LogLevels.LEVEL_ERROR, stderr);
    final JavaDelegateLogger javaDelegateLogger = new JavaDelegateLogger(Logger.getLogger("Main"), logFormat, logLevels)) {
    // Using the java logging API
    final Logger javaLogger = JavaLogger.wrap(levelSplitLogger);
    javaLogger.log(Level.INFO, "Hello World!");
    javaLogger.log(Level.SEVERE, "Hello Error!");
    // Delegating to the Java logging API
    javaDelegateLogger.log(LogLevels.LEVEL_INFO, "Hello %s!", "world");
}
```
