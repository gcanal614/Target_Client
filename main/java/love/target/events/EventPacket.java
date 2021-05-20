package love.target.events;

import love.target.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

public class EventPacket extends EventCancellable {
    private final Packet<?> packet;
    private final boolean isClientPacket;

    public EventPacket(Packet<?> packet,boolean isClientPacket) {
        this.packet = packet;
        this.isClientPacket = isClientPacket;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public boolean isClientPacket() {
        return isClientPacket;
    }
}
