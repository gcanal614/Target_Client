package love.target.mod.mods.fight;

import love.target.eventapi.EventTarget;
import love.target.events.EventTick;
import love.target.mod.Mod;
import net.minecraft.item.*;

public class FastThrow extends Mod {
    public FastThrow() {
        super("FastThrow", Category.FIGHT);
    }

    @EventTarget
    public void onTick(EventTick e) {
        Item item = mc.player.getHeldItem().getItem();
        if ((item instanceof ItemSnowball || item instanceof ItemPotion || item instanceof ItemEgg || item instanceof ItemExpBottle || item instanceof ItemFishingRod) && FastThrow.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            mc.rightClickDelayTimer = 0;
        }
    }
}
