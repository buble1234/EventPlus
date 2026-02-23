package event.plus.mixin;

import event.plus.core.EventBus;
import event.plus.core.EventPhase;
import event.plus.event.impl.AttackEvent;
import event.plus.event.impl.TickEvent;
import event.plus.event.impl.WorldLoadEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTickPre(CallbackInfo ci) {
        EventBus.fire(new TickEvent(EventPhase.PRE));
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTickPost(CallbackInfo ci) {
        EventBus.fire(new TickEvent(EventPhase.POST));
    }

    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    private void onAttack(CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftClient.getInstance().targetedEntity == null) return;

        Entity target = MinecraftClient.getInstance().targetedEntity;
        AttackEvent event = EventBus.fire(new AttackEvent(target));
        if (event.isCancelled()) cir.cancel();
    }

    @Inject(method = "setWorld", at = @At("TAIL"))
    private void onSetWorld(@Nullable ClientWorld world, CallbackInfo ci) {
        EventBus.fire(new WorldLoadEvent(world));
    }
}