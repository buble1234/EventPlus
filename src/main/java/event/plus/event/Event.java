package event.plus.event;

public abstract class Event {

    private boolean cancelled;

    public boolean isCancellable() {
        return false;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        if (!isCancellable()) {
            throw new UnsupportedOperationException("Event " + getClass().getSimpleName() + " is not cancellable");
        }
        this.cancelled = true;
    }

    public void uncancel() {
        this.cancelled = false;
    }
}