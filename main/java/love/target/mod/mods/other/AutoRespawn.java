package love.target.mod.mods.other;

import love.target.eventapi.EventTarget;
import love.target.events.EventTick;
import love.target.mod.Mod;

public class AutoRespawn extends Mod {
    public AutoRespawn() {
        super("AutoRespawn", Category.OTHER);
    }

    @EventTarget
    public void onUpdate(EventTick e) {
        if (!mc.player.isEntityAlive()) {
            mc.player.respawnPlayer();
        }
    }
}

