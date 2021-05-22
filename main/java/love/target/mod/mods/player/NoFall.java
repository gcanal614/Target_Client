package love.target.mod.mods.player;

import love.target.Wrapper;
import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * 绕过但是不绕过
 */

public class NoFall extends Mod {
    public NoFall() {
        super("NoFall",Category.PLAYER);
    }

    @EventTarget
    public void onPre(EventPreUpdate e) {
        if (mc.player.fallDistance > 1.5 && !AntiVoid.isOverVoid()) {
            Wrapper.getLogger().debug("Set on ground no fall");
            e.setOnGround(false);
            e.setOnGround(true);
        }
    }
}
