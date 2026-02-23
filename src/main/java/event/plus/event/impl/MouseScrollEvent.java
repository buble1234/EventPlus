package event.plus.event.impl;

import event.plus.event.CancellableEvent;

public class MouseScrollEvent extends CancellableEvent {
    private final double scrollX;
    private final double scrollY;

    public MouseScrollEvent(double scrollX, double scrollY) {
        this.scrollX = scrollX;
        this.scrollY = scrollY;
    }

    public double getScrollX() {
        return scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }

    public boolean isScrollingUp() {
        return scrollY > 0;
    }

    public boolean isScrollingDown() {
        return scrollY < 0;
    }
}