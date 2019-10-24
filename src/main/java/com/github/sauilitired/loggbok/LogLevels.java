package com.github.sauilitired.loggbok;

@SuppressWarnings({"WeakerAccess", "unused"}) public class LogLevels {

    public static final int LEVEL_INFO      = 0x00000001;
    public static final int LEVEL_WARNING   = 0x00000002;
    public static final int LEVEL_ERROR     = 0x00000004;
    public static final int LEVEL_DEBUG     = 0x00000008;
    private static final int ALL_ENABLED    = 0x7FFFFFFF;
    private final Object levelLock = new Object();
    private final Object displayLock = new Object();

    private int displayLevel = ALL_ENABLED;
    private String[] levels;

    public LogLevels() {
        // Default levels
        this.levels = new String[] {"INFO", "WARNING", "ERROR", "DEBUG"};
    }

    private static int log2(int bits) {
        if (bits == 0) {
            throw new IllegalArgumentException("Cannot get log2 of 0");
        }
        return 31 - Integer.numberOfLeadingZeros(bits);
    }

    public int setLevel(final int index, final String level) {
        if (index < 0) {
            throw new IllegalArgumentException("The level index must be positive.");
        }
        if (level == null || level.isEmpty()) {
            throw new IllegalArgumentException("Level must be non-null and non-empty.");
        }
        synchronized (levelLock) {
            final int loggedIndex = log2(index);
            if (loggedIndex >= this.levels.length) {
                // We need to resize the array
                final String[] strings = new String[loggedIndex + 1];
                System.arraycopy(this.levels, 0, strings, 0, this.levels.length);
                strings[loggedIndex] = level;
                this.levels = strings;
            } else {
                this.levels[loggedIndex] = level;
            }
        }
        return index;
    }

    public int addLevel(final String level) {
        return this.setLevel(getNextLevel(), level);
    }

    public String getLevel(final int index) {
        final int loggedIndex = log2(index);
        if (loggedIndex< 0 || loggedIndex >= this.levels.length) {
            throw new IllegalArgumentException(
                String.format("Index must be in the range [0, %d), was %d.",
                    this.levels.length, loggedIndex));
        }
        synchronized (levelLock) {
            return this.levels[loggedIndex];
        }
    }

    public int getDisplayLevel() {
        synchronized (displayLock) {
            return this.displayLevel;
        }
    }

    public boolean isEnabled(int level) {
        synchronized (levelLock) {
            return (this.displayLevel & level) != 0;
        }
    }

    public void setDisplayLevel(int displayLevel) {
        synchronized (levelLock) {
            this.displayLevel = displayLevel;
        }
    }

    public void setEnabled(int level, boolean enabled) {
        synchronized (levelLock) {
            if (enabled) {
                this.displayLevel = this.displayLevel | level;
            } else {
                this.displayLevel = this.displayLevel ^ level;
            }
        }
    }

    public int getLevelCount() {
        return this.levels.length;
    }

    public int getNextLevel() {
        return 1 << this.levels.length;
    }

}
