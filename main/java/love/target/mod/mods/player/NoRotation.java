package love.target.mod.mods.player;

import love.target.eventapi.EventTarget;
import love.target.events.EventPacket;
import love.target.mod.Mod;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotation extends Mod {
    public NoRotation() {
        super("NoRotation", Category.PLAYER);
    }

    @EventTarget
    public void onPacket(EventPacket e) {
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)e.getPacket();
            packet.setYaw(mc.player.rotationYaw);
            packet.setPitch(mc.player.rotationPitch);
        }
    }
}
