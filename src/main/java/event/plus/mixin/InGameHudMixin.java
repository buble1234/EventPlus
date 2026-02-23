package event.plus.mixin;

import event.plus.core.EventBus;
import event.plus.core.EventPhase;
import event.plus.event.impl.RenderHudEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderPre(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        EventBus.fire(new RenderHudEvent(EventPhase.PRE, context, tickCounter.getTickProgress(true)));
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderPost(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        EventBus.fire(new RenderHudEvent(EventPhase.POST, context, tickCounter.getTickProgress(true)));
    }
}