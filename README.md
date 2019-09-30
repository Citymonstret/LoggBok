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
