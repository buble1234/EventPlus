package event.plus.event.impl;

import event.plus.event.CancellableEvent;

public class MouseClickEvent extends CancellableEvent {
    public enum Button {
        LEFT(0), RIGHT(1), MIDDLE(2);

        private final int code;

        Button(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static Button fromCode(int code) {
            return switch (code) {
                case 0 -> LEFT;
                case 1 -> RIGHT;
                case 2 -> MIDDLE;
                default -> null;
            };
        }
    }

    public enum Action {PRESS, RELEASE}

    private final Button button;
    private final Action action;

    public MouseClickEvent(Button button, Action action) {
        this.button = button;
        this.action = action;
    }

    public Button getButton() {
        return button;
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

    public boolean isLeft() {
        return button == Button.LEFT;
    }

    public boolean isRight() {
        return button == Button.RIGHT;
    }

    public boolean isMiddle() {
        return button == Button.MIDDLE;
    }
}