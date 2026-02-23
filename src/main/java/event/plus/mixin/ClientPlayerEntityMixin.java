package event.plus.mixin;

import event.plus.core.EventBus;
import event.plus.event.impl.JumpEvent;
import event.plus.event.impl.MoveEvent;
import event.plus.event.impl.SwingHandEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @ModifyVariable(method = "move", at = @At("HEAD"), argsOnly = true, index = 2)
    private Vec3d onMove(Vec3d movement, MovementType type) {
        if (type != MovementType.SELF) return movement;
        MoveEvent event = EventBus.fire(new MoveEvent(movement));
        if (event.isCancelled()) return Vec3d.ZERO;
        return event.toVec3d();
    }

    @Inject(method = "swingHand", at = @At("HEAD"), cancellable = true)
    private void onSwingHand(Hand hand, CallbackInfo ci) {
        SwingHandEvent event = EventBus.fire(new SwingHandEvent(hand));
        if (event.isCancelled()) ci.cancel();
    }
}