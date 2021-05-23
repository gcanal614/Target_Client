package love.target.mod.mods.fight;

import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.utils.TimerUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class AutoSword extends Mod {
    public TimerUtil timer = new TimerUtil();

    //Andúril

    public AutoSword() {
        super("AutoSword", Category.ITEM);
    }

    @EventTarget
    private void onUpdate(EventPreUpdate event) {
        if (!this.timer.hasReached(100L) || mc.currentScreen != null && !(mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        int best = -1;
        float swordDamage = 0.0f;
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            float swordD;
            if (!mc.player.inventoryContainer.getSlot(i).getHasStack() || !((is = mc.player.inventoryContainer.getSlot(i).getStack()).getItem() instanceof ItemSword) || (swordD = this.getSharpnessLevel(is)) <= swordDamage) continue;
            swordDamage = swordD;
            best = i;
        }
        ItemStack current = mc.player.inventoryContainer.getSlot(36).getStack();
        if (!(best == -1 || current != null && current.getItem() instanceof ItemSword && swordDamage <= this.getSharpnessLevel(current))) {
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, best, 0, 2, mc.player);
            this.timer.reset();
        }
    }

    protected void swap(int slot, int hotbarNum) {
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, hotbarNum, 2, mc.player);
    }

    private float getSharpnessLevel(ItemStack stack) {
        float damage = ((ItemSword)stack.getItem()).getDamageVsEntity();
        damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f;
        return damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01f;
    }
}
