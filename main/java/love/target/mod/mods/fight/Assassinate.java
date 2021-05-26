package love.target.mod.mods.fight;

import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.mod.value.values.NumberValue;

public class Assassinate extends Mod {
    private final NumberValue distance = new NumberValue("Distance", 2.0, 0.0, 10.0, 1.0);
    private final NumberValue y1 = new NumberValue("Y",2.0, 0.0, 10.0, 1.0);

    public Assassinate() {
        super("Assassinate", Category.FIGHT);
        addValues(distance, y1);
    }

    @EventTarget
    public void onUpdate(EventPreUpdate e) {
        if (KillAura.curtarget != null) {
            double yaw = Math.toRadians(KillAura.curtarget.rotationYaw);
            double x = -Math.sin(yaw) * distance.getValue();
            double y = KillAura.curtarget.posY + y1.getValue();
            double z = Math.cos(yaw) * distance.getValue();
            mc.player.setPositionAndRotation(KillAura.curtarget.posX - x, y, KillAura.curtarget.posZ - z, mc.player.rotationYaw, mc.player.rotationPitch);
        }
    }
}
