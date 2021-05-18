package love.target.mod.mods.visual;

import love.target.eventapi.EventTarget;
import love.target.events.EventTick;
import love.target.mod.Mod;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class NightVision extends Mod {
    public NightVision() {
        super("NightVision", Category.VISUAL);
    }

    @EventTarget
    private void onTick(EventTick e) {
        mc.player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 6420, 1));
    }

    @Override
    public void onDisable() {
        mc.player.removePotionEffect(Potion.nightVision.getId());
    }
}
