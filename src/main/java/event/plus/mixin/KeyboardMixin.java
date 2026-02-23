package event.plus.mixin;

import event.plus.core.EventBus;
import event.plus.event.impl.KeyEvent;
import net.minecraft.client.Keyboard;
import net.minecraft.client.input.KeyInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    private void onKey(long window, int action, KeyInput input, CallbackInfo ci) {
        KeyEvent.Action keyAction = switch (action) {
            case 1 -> KeyEvent.Action.PRESS;
            case 0 -> KeyEvent.Action.RELEASE;
            case 2 -> KeyEvent.Action.HOLD;
            default -> null;
        };
        if (keyAction == null) return;
        KeyEvent event = EventBus.fire(new KeyEvent(input.key(), keyAction));
        if (event.isCancelled()) ci.cancel();
    }
}