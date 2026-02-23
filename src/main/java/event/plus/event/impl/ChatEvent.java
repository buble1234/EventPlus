package event.plus.event.impl;

import event.plus.event.CancellableEvent;

public class ChatEvent extends CancellableEvent {
    public enum Direction {
        SEND, RECEIVE
    }

    private final Direction direction;
    private String message;

    public ChatEvent(Direction direction, String message) {
        this.direction = direction;
        this.message = message;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return direction == Direction.SEND;
    }

    public boolean isReceive() {
        return direction == Direction.RECEIVE;
    }
}