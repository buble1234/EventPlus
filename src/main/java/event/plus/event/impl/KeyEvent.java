package event.plus.event.impl;

import event.plus.event.CancellableEvent;

public class KeyEvent extends CancellableEvent {

    public enum Action {
        PRESS, RELEASE, HOLD
    }

    private final int keyCode;
    private final Action action;

    public KeyEvent(int keyCode, Action action) {
        this.keyCode = keyCode;
        this.action = action;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public Action getAction() {
        return action;
    }

    public boolean isPress() {
        return action == Action.PRESS;
    }

    public boolean isRelease() {
        return action == Action.RELEASE;
    }

    public boolean isHold() {
        return action == Action.HOLD;
    }
}