package com.github.sauilitired.loggbok;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PositionFormatter implements LogFormatter {

    private static final Pattern LOG_ARG_PATTERN = Pattern.compile("\\{(?<num>([0-9])+)}");;

    @Override public String format(final LogEntry logEntry) {
        final Matcher matcher = LOG_ARG_PATTERN.matcher(logEntry.getMessage());
        final Object[] args = logEntry.getArgs();
        String msg = logEntry.getMessage();

        int index = 0;
        boolean containsEmptyBrackets;
        while ((containsEmptyBrackets = msg.contains("{}")) || matcher.find()) {
            final int argumentNum;
            final String toReplace;
            if (containsEmptyBrackets) {
                argumentNum = index++;
                toReplace = "{}";
            } else {
                toReplace = matcher.group();
                argumentNum = Integer.parseInt(matcher.group("num"));
            }
            if (argumentNum < args.length) {
                final Object object = args[argumentNum];
                String objectString;
                if (object == null) {
                    objectString = "null";
                } else if (object instanceof LogFormatted) {
                    objectString = ((LogFormatted) object).getLogFormatted();
                } else {
                    objectString = object.toString();
                }
                msg = msg.replace(toReplace, objectString);
            } else {
                msg = msg.replace(toReplace, "");
            }
        }

        return msg;
    }

}
