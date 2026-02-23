package event.plus.event.impl;

import event.plus.event.CancellableEvent;
import net.minecraft.util.Hand;

public class SwingHandEvent extends CancellableEvent {
    private final Hand hand;

    public SwingHandEvent(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }

    public boolean isMainHand() {
        return hand == Hand.MAIN_HAND;
    }

    public boolean isOffHand() {
        return hand == Hand.OFF_HAND;
    }
}