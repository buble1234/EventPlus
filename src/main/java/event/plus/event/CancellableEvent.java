package event.plus.event;

public abstract class CancellableEvent extends Event {

    @Override
    public boolean isCancellable() {
        return true;
    }
}