package love.target.mod.mods.player;

import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Mod {
    public NoFall() {
        super("NoFall",Category.PLAYER);
    }

    @EventTarget
    public void onPre(EventPreUpdate e) {
        if (mc.player.fallDistance > 3 && !AntiVoid.isOverVoid()) {
            mc.getNetHandler().sendPacket(new C03PacketPlayer(true));
        }
    }
}
