package event.plus.event.impl;

import event.plus.core.EventPhase;
import event.plus.event.Event;

public class TickEvent extends Event {
    private final EventPhase phase;

    public TickEvent(EventPhase phase) {
        this.phase = phase;
    }

    public EventPhase getPhase() {
        return phase;
    }
}