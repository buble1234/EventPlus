package event.plus.event.impl;

import event.plus.core.EventPhase;
import event.plus.event.Event;
import net.minecraft.client.gui.DrawContext;

public class RenderHudEvent extends Event {
    private final EventPhase phase;
    private final DrawContext graphics;
    private final float tickDelta;

    public RenderHudEvent(EventPhase phase, DrawContext graphics, float tickDelta) {
        this.phase = phase;
        this.graphics = graphics;
        this.tickDelta = tickDelta;
    }

    public EventPhase getPhase() {
        return phase;
    }

    public DrawContext getGraphics() {
        return graphics;
    }

    public float getTickDelta() {
        return tickDelta;
    }
}