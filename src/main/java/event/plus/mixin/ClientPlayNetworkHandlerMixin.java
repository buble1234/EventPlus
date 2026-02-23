package event.plus.mixin;

import event.plus.core.EventBus;
import event.plus.event.impl.ChatEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onSendChat(String message, CallbackInfo ci) {
        ChatEvent event = EventBus.fire(new ChatEvent(ChatEvent.Direction.SEND, message));
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "onChatMessage", at = @At("HEAD"), cancellable = true)
    private void onReceiveChat(ChatMessageS2CPacket packet, CallbackInfo ci) {
        String message = packet.body().content();
        ChatEvent event = EventBus.fire(new ChatEvent(ChatEvent.Direction.RECEIVE, message));
        if (event.isCancelled()) ci.cancel();
    }
}