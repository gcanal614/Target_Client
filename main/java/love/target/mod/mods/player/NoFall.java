package love.target.mod.mods.player;

import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;

/**
 * 绕过但是不绕过
 */

public class NoFall extends Mod {
    public NoFall() {
        super("NoFall",Category.PLAYER);
    }

    @EventTarget
    public void onPre(EventPreUpdate e) {
        if (mc.player.fallDistance > 2) {
            e.setOnGround(true);
        }
    }
}
