package event.plus.mixin;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import event.plus.core.EventBus;
import event.plus.core.EventPhase;
import event.plus.event.impl.RenderWorldEvent;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.ObjectAllocator;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderPre(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, Matrix4f positionMatrix, Matrix4f matrix4f, Matrix4f projectionMatrix, GpuBufferSlice fogBuffer, Vector4f fogColor, boolean renderSky, CallbackInfo ci) {
        EventBus.fire(new RenderWorldEvent(
                EventPhase.PRE,
                (WorldRenderer) (Object) this,
                new Matrix4f(positionMatrix),
                new Matrix4f(projectionMatrix),
                tickCounter.getTickProgress(true)
        ));
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderPost(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, Matrix4f positionMatrix, Matrix4f matrix4f, Matrix4f projectionMatrix, GpuBufferSlice fogBuffer, Vector4f fogColor, boolean renderSky, CallbackInfo ci) {
        EventBus.fire(new RenderWorldEvent(
                EventPhase.POST,
                (WorldRenderer) (Object) this,
                new Matrix4f(positionMatrix),
                new Matrix4f(projectionMatrix),
                tickCounter.getTickProgress(true)
        ));
    }
}