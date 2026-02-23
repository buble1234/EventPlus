package event.plus.mixin;

import event.plus.core.EventBus;
import event.plus.event.impl.JumpEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    private void onJump(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self != MinecraftClient.getInstance().player) return;

        JumpEvent event = EventBus.fire(new JumpEvent());
        if (event.isCancelled()) ci.cancel();
    }
}