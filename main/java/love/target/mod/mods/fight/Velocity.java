package love.target.mod.mods.fight;

import love.target.eventapi.EventTarget;
import love.target.events.EventPacket;
import love.target.mod.Mod;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.NumberValue;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Mod {
    private final NumberValue vertical = new NumberValue("Vertical",0.0, 0.0, 100.0, 5.0);
    private final NumberValue horizontal = new NumberValue("Horizontal",0.0, 0.0, 100.0, 5.0);
    private final BooleanValue explosionPacket = new BooleanValue("ExplosionPacket",true);

    public Velocity() {
        super("Velocity", Category.FIGHT);
        addValues(vertical,horizontal,explosionPacket);
    }

    @EventTarget
    public void onPacket(EventPacket e) {
        if (e.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) e.getPacket();
            if (packet.getEntityID() == mc.player.getEntityId()) {
                if (vertical.getValue().equals(0.0) && horizontal.getValue().equals(0.0)) {
                    e.setCancelled(true);
                } else {
                    packet.motionX *= horizontal.getValue() / 100.0;
                    packet.motionY *= vertical.getValue() / 100.0;
                    packet.motionZ *= horizontal.getValue() / 100.0;
                }
            }
        }

        if (explosionPacket.getValue()) {
            if (e.getPacket() instanceof S27PacketExplosion) {
                e.setCancelled(true);
            }
        }
    }
}
