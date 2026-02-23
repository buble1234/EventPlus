package event.plus.mixin;

import event.plus.core.EventBus;
import event.plus.event.impl.MouseClickEvent;
import event.plus.event.impl.MouseScrollEvent;
import net.minecraft.client.Mouse;
import net.minecraft.client.input.MouseInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, MouseInput input, int action, CallbackInfo ci) {
        MouseClickEvent.Button btn = MouseClickEvent.Button.fromCode(input.button());
        if (btn == null) return;

        MouseClickEvent.Action act = action == 1 ? MouseClickEvent.Action.PRESS : MouseClickEvent.Action.RELEASE;

        MouseClickEvent event = EventBus.fire(new MouseClickEvent(btn, act));
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void onMouseScroll(long window, double scrollX, double scrollY, CallbackInfo ci) {
        MouseScrollEvent event = EventBus.fire(new MouseScrollEvent(scrollX, scrollY));
        if (event.isCancelled()) ci.cancel();
    }
}