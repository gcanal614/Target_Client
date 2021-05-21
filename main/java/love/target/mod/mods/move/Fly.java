package love.target.mod.mods.move;

import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.utils.MoveUtils;

public class Fly extends Mod {
    public Fly() {
        super("Fly",Category.MOVE);
    }

    @Override
    protected void onDisable() {
        MoveUtils.setSpeed(0);
        super.onDisable();
    }

    @EventTarget
    public void onPre(EventPreUpdate e) {
        mc.player.motionY = mc.gameSettings.keyBindJump.isKeyDown() ? 1 : mc.gameSettings.keyBindSneak.isKeyDown() ? -1 : 0;

        if (mc.player.isMoving()) {
            MoveUtils.setSpeed(1);
        } else {
            MoveUtils.setSpeed(0);
        }
    }
}
