package love.target.mod.mods.fight;

import love.target.eventapi.EventTarget;
import love.target.events.EventPacket;
import love.target.mod.Mod;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Mod {
    public Velocity() {
        super("Velocity", Category.FIGHT);
    }

    @EventTarget
    public void onPacket(EventPacket e) {
        if (e.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) e.getPacket();
            if (packet.getEntityID() == mc.player.getEntityId()) {
                e.setCancelled(true);
            }
        }

        if (e.getPacket() instanceof S27PacketExplosion) {
            e.setCancelled(true);
        }
    }
}
