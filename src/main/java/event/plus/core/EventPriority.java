package event.plus.core;

public enum EventPriority {
    LOW(0),
    NORMAL(1),
    HIGH(2),
    CRITICAL(3);

    private final int level;

    EventPriority(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}