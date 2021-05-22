package love.target.mod.mods.player;

import love.target.eventapi.EventTarget;
import love.target.events.EventTick;
import love.target.mod.Mod;
import love.target.mod.value.values.NumberValue;

public class FastPlace extends Mod {
    private final NumberValue speed = new NumberValue("Speed",0,0,4,1);

    public FastPlace() {
        super("FastPlace",Category.PLAYER);
        addValues(speed);
    }

    @EventTarget
    public void onTick(EventTick e) {
        if (mc.rightClickDelayTimer <= speed.getValue().intValue()) {
            mc.rightClickDelayTimer = 0;
        }
    }
}
