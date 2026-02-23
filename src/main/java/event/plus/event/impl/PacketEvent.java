package event.plus.event.impl;

import event.plus.event.CancellableEvent;
import net.minecraft.network.packet.Packet;

public class PacketEvent extends CancellableEvent {
    public enum Direction {
        SEND, RECEIVE
    }

    private final Direction direction;
    private final Packet<?> packet;

    public PacketEvent(Direction direction, Packet<?> packet) {
        this.direction = direction;
        this.packet = packet;
    }

    public Direction getDirection() {
        return direction;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public boolean isSend() {
        return direction == Direction.SEND;
    }

    public boolean isReceive() {
        return direction == Direction.RECEIVE;
    }

    @SuppressWarnings("unchecked")
    public <T extends Packet<?>> T getPacketAs(Class<T> type) {
        return type.isInstance(packet) ? (T) packet : null;
    }
}