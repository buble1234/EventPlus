package event.plus.mixin;

import event.plus.core.EventBus;
import event.plus.event.impl.PacketEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class PacketEventMixin {

    // Исходящий пакет
    @Inject(
            method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onSendPacket(Packet<?> packet, @Nullable PacketCallbacks callbacks, CallbackInfo ci) {
        PacketEvent event = EventBus.fire(new PacketEvent(PacketEvent.Direction.SEND, packet));
        if (event.isCancelled()) ci.cancel();
    }

    // Входящий пакет
    @Inject(
            method = "handlePacket",
            at = @At("HEAD"),
            cancellable = true
    )
    private static <T extends PacketListener> void onReceivePacket(Packet<T> packet, PacketListener listener, CallbackInfo ci) {
        PacketEvent event = EventBus.fire(new PacketEvent(PacketEvent.Direction.RECEIVE, packet));
        if (event.isCancelled()) ci.cancel();
    }
}