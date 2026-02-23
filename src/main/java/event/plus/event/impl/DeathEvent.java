package event.plus.event.impl;

import event.plus.event.Event;
import net.minecraft.entity.damage.DamageSource;

public class DeathEvent extends Event {
    private final DamageSource source;

    public DeathEvent(DamageSource source) {
        this.source = source;
    }

    public DamageSource getSource() {
        return source;
    }
}