package event.plus.event.impl;

import event.plus.event.CancellableEvent;
import net.minecraft.entity.Entity;

public class AttackEvent extends CancellableEvent {
    private final Entity target;

    public AttackEvent(Entity target) {
        this.target = target;
    }

    public Entity getTarget() {
        return target;
    }
}