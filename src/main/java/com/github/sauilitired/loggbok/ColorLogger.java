package com.github.sauilitired.loggbok;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorLogger implements Logger {

    public static final String COLOR_BLACK = "\u001b[30m";
    public static final String COLOR_RED = "\u001b[31m";
    public static final String COLOR_GREEN = "\u001b[32m";
    public static final String COLOR_YELLOW = "\u001b[33m";
    public static final String COLOR_BLUE = "\u001b[34m";
    public static final String COLOR_MAGENTA = "\u001b[35m";
    public static final String COLOR_CYAN = "\u001b[36m";
    public static final String COLOR_WHITE = "\u001b[37m";
    public static final String COLOR_RESET = "\u001b[0m";
    private static final Map<String, String> colors;
    private static final Pattern colorPattern = Pattern.compile("%c\\((?<color>[a-z]+)\\)");

    static {
        colors = new HashMap<>(9);
        try {
            for (final Field field : ColorLogger.class.getFields()) {
                if (field.getName().startsWith("COLOR_")) {
                    colors.put(field.getName().substring(6).toLowerCase(),
                        field.get(null).toString());
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private final Logger logger;

    public ColorLogger(final Logger logger) {
        this.logger = logger;
    }

    @Override public void log(LogEntry logEntry) {
        final String message = logEntry.getMessage();
        final Matcher matcher = colorPattern.matcher(message);
        final StringBuffer stringBuilder = new StringBuffer(message.length());
        boolean matched = false;
        while (matcher.find()) {
            final String colorCode = colors.get(matcher.group("color"));
            if (colorCode != null) {
                matcher.appendReplacement(stringBuilder, Matcher.quoteReplacement(colorCode));
                matched = true;
            }
        }
        matcher.appendTail(stringBuilder);
        // Always reset the color in the end if a color has been written
        if (matched) {
            stringBuilder.append(COLOR_RESET);
        }
        logEntry.setMessage(stringBuilder.toString());
        this.logger.log(logEntry);
    }

}
