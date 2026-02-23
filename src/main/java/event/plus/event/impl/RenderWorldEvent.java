package event.plus.event.impl;

import event.plus.core.EventPhase;
import event.plus.event.Event;
import net.minecraft.client.render.WorldRenderer;
import org.joml.Matrix4f;

public class RenderWorldEvent extends Event {
    private final EventPhase phase;
    private final WorldRenderer levelRenderer;
    private final Matrix4f modelView;
    private final Matrix4f projection;
    private final float tickDelta;

    public RenderWorldEvent(EventPhase phase, WorldRenderer levelRenderer, Matrix4f modelView, Matrix4f projection, float tickDelta) {
        this.phase = phase;
        this.levelRenderer = levelRenderer;
        this.modelView = modelView;
        this.projection = projection;
        this.tickDelta = tickDelta;
    }

    public EventPhase getPhase() {
        return phase;
    }

    public WorldRenderer getLevelRenderer() {
        return levelRenderer;
    }

    public Matrix4f getModelView() {
        return modelView;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public float getTickDelta() {
        return tickDelta;
    }
}