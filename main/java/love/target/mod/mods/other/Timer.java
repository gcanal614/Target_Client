package love.target.mod.mods.other;

import love.target.eventapi.EventTarget;
import love.target.eventapi.types.Priority;
import love.target.events.EventTick;
import love.target.mod.Mod;
import love.target.mod.value.values.NumberValue;

public class Timer extends Mod {
    private final NumberValue speed = new NumberValue("Speed",1.0,0.01,10.0,0.01);

    public Timer() {
        super("Timer",Category.OTHER);
        addValues(speed);
    }

    @Override
    protected void onDisable() {
        mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    @EventTarget(value = Priority.LOWEST)
    public void onTick(EventTick e) {
        mc.timer.timerSpeed = speed.getValue().floatValue();
    }
}
