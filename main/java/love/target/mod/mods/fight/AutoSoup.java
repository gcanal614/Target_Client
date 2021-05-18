package love.target.mod.mods.fight;

import love.target.eventapi.EventTarget;
import love.target.events.EventTick;
import love.target.mod.Mod;
import love.target.mod.value.values.NumberValue;
import love.target.utils.TimerUtil;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class AutoSoup extends Mod {
    private final NumberValue health = new NumberValue("Health", 10.0, 1.0, 20.0, 0.5);
    private final NumberValue delay = new NumberValue("Delay", 200.0, 0.0, 8000.0, 1.0);
    private final TimerUtil timerUtil = new TimerUtil();

    public AutoSoup() {
        super("AutoSoup",Category.FIGHT);
        addValues(health,delay);
    }

    @EventTarget
    public void onTick(EventTick e) {
        if ((double)mc.player.getHealth() <= this.health.getValue() && this.timerUtil.hasReached(this.delay.getValue())) {
            for (int i = 0;i < mc.player.inventory.mainInventory.length;i++) {
                ItemStack itemStack = mc.player.inventory.mainInventory[i];

                if (!(itemStack.getItem() instanceof ItemSoup)) continue;
                int oldSlot = mc.player.inventory.currentItem;
                mc.getNetHandler().sendPacket(new C09PacketHeldItemChange(i));
                mc.getNetHandler().sendPacket(new C08PacketPlayerBlockPlacement(mc.player.inventory.getCurrentItem()));
                mc.getNetHandler().sendPacket(new C09PacketHeldItemChange(oldSlot));
                break;
            }
            this.timerUtil.reset();
        }
    }
}
