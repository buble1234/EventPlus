package event.plus.mixin;

import event.plus.core.EventBus;
import event.plus.event.impl.PacketEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        PacketEvent event = EventBus.fire(new PacketEvent(PacketEvent.Direction.SEND, packet));
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onReceivePacket(io.netty.channel.ChannelHandlerContext ctx, Packet<?> packet, CallbackInfo ci) {
        PacketEvent event = EventBus.fire(new PacketEvent(PacketEvent.Direction.RECEIVE, packet));
        if (event.isCancelled()) ci.cancel();
    }
}