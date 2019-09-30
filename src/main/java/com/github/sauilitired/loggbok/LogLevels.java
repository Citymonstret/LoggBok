package com.github.sauilitired.loggbok;

@SuppressWarnings({"WeakerAccess", "unused"}) public class LogLevels {

    public static final int LEVEL_INFO = 0;
    public static final int LEVEL_WARNING = 1;
    public static final int LEVEL_ERROR = 2;
    public static final int LEVEL_DEBUG = 3;
    private final Object levelLock = new Object();
    private final Object displayLock = new Object();
    private int displayLevel = Integer.MAX_VALUE;
    private String[] levels;

    public LogLevels() {
        // Default levels
        this.levels = new String[] {"INFO", "WARNING", "ERROR", "DEBUG"};
    }

    public int setLevel(final int index, final String level) {
        if (index < 0) {
            throw new IllegalArgumentException("The level index must be positive.");
        }
        if (level == null || level.isEmpty()) {
            throw new IllegalArgumentException("Level must be non-null and non-empty.");
        }
        synchronized (levelLock) {
            if (index >= this.levels.length) {
                // We need to resize the array
                final String[] strings = new String[index + 1];
                System.arraycopy(this.levels, 0, strings, 0, this.levels.length);
                strings[index] = level;
                this.levels = strings;
            } else {
                this.levels[index] = level;
            }
        }
        return index;
    }

    public int addLevel(final String level) {
        return this.setLevel(this.levels.length, level);
    }

    public String getLevel(final int index) {
        if (index < 0 || index > this.getMaxIndex()) {
            throw new IllegalArgumentException("Index must be in the range [0, max index]");
        }
        synchronized (levelLock) {
            return this.levels[index];
        }
    }

    public int getMaxIndex() {
        synchronized (levelLock) {
            return this.levels.length - 1;
        }
    }

    public int getDisplayLevel() {
        synchronized (displayLock) {
            return this.displayLevel;
        }
    }

    public int setDisplayLevel(final int newLevel) {
        synchronized (displayLock) {
            this.displayLevel = newLevel;
        }
        return getDisplayLevel();
    }

}
