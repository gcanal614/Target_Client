package love.target.mod.mods.move;

import love.target.eventapi.EventTarget;
import love.target.events.EventMove;
import love.target.mod.Mod;
import love.target.utils.MoveUtils;

public class NoWaterPush extends Mod {
    public NoWaterPush() {
        super("NoWaterPush",Category.MOVE);
    }

    @EventTarget
    public void onMove(EventMove e) {
        if (!mc.player.isMoving() && mc.player.isInLiquid()) {
            MoveUtils.setSpeed(0);
            MoveUtils.setSpeedEvent(e,0);
        }
    }
}
