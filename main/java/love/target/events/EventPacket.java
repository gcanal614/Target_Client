package love.target.events;

import love.target.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

public class EventPacket extends EventCancellable {
    private final Packet<?> packet;

    public EventPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
