package love.target.mod.mods.move;

import love.target.eventapi.EventTarget;
import love.target.events.EventTick;
import love.target.mod.Mod;
import love.target.mod.value.values.BooleanValue;

public class Sprint extends Mod {
    private final BooleanValue allSprint = new BooleanValue("All",false);

    public Sprint() {
        super("Sprint",Category.MOVE);
        addValues(allSprint);
    }

    @EventTarget
    public void onTick(EventTick e) {
        if (allSprint.getValue()) {
            if (!mc.player.isMoving()) return;
        } else {
            if (!mc.player.isForwardMove()) return;
        }

        if (mc.player.isCollidedHorizontally) return;

        if (!mc.player.isSprinting()) {
            mc.player.setSprinting(true);
        }
    }
}
