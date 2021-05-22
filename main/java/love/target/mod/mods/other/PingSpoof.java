package love.target.mod.mods.other;

import love.target.eventapi.EventTarget;
import love.target.events.EventPacket;
import love.target.events.EventTick;
import love.target.mod.Mod;
import love.target.mod.value.values.NumberValue;
import love.target.utils.TimerUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PingSpoof extends Mod {
    public final NumberValue ping = new NumberValue("Ping", 1000, 0, 5000, 1);
    private static final List<Packet<?>> packets = new CopyOnWriteArrayList<>();
    private final TimerUtil timerUtil = new TimerUtil();

    public PingSpoof() {
        super("PingSpoof", Category.OTHER);
        this.addValues(this.ping);
    }

    @EventTarget
    public void onSendPacket(EventPacket e) {
        if (mc.isSingleplayer()) {
            return;
        }
        if (e.getPacket() instanceof C00PacketKeepAlive) {
            if (packets.contains(e.getPacket())) {
                return;
            }
            e.setCancelled(true);
            packets.add(e.getPacket());
        }
    }

    @EventTarget
    public void onTick(EventTick e) {
        if (this.timerUtil.hasReached(this.ping.getValue().intValue())) {
            for (Packet<?> packet : packets) {
                if (!(packet instanceof C00PacketKeepAlive)) continue;
                mc.getNetHandler().sendPacketNoEvent(packet);
            }
            packets.clear();
            this.timerUtil.reset();
        }
    }
}
