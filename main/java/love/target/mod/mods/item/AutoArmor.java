package love.target.mod.mods.item;

import love.target.eventapi.EventTarget;
import love.target.events.EventTick;
import love.target.mod.Mod;
import love.target.mod.value.values.NumberValue;
import love.target.utils.TimerUtil;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class AutoArmor extends Mod {
    public final static NumberValue DELAY = new NumberValue("Delay", 1.0D, 0.0D, 10.0D, 1.0D);
    private final TimerUtil timer = new TimerUtil();

    public AutoArmor() {
        super("AutoArmor", Category.ITEM);
        super.addValues(DELAY);
    }

    @EventTarget
    public void onEvent(EventTick event) {
        long delay = DELAY.getValue().longValue() * 50L;
        if ((mc.currentScreen == null || mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChat) && this.timer.hasReached((double)delay)) {
            this.getBestArmor();
        }

    }

    public void getBestArmor() {
        for(int type = 1; type < 5; ++type) {
            if (mc.player.inventoryContainer.getSlot(4 + type).getHasStack()) {
                ItemStack is = mc.player.inventoryContainer.getSlot(4 + type).getStack();
                if (isBestArmor(is, type)) {
                    continue;
                }

                this.drop(4 + type);
            }

            for(int i = 9; i < 45; ++i) {
                if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                    if (isBestArmor(is, type) && getProtection(is) > 0.0F) {
                        this.shiftClick(i);
                        this.timer.reset();
                        if (DELAY.getValue().longValue() > 0L) {
                            return;
                        }
                    }
                }
            }
        }

    }

    public static boolean isBestArmor(ItemStack stack, int type) {
        float prot = getProtection(stack);
        String strType = "";
        if (type == 1) {
            strType = "helmet";
        } else if (type == 2) {
            strType = "chestplate";
        } else if (type == 3) {
            strType = "leggings";
        } else if (type == 4) {
            strType = "boots";
        }

        if (!stack.getUnlocalizedName().contains(strType)) {
            return false;
        } else {
            for(int i = 5; i < 45; ++i) {
                if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                    if (getProtection(is) > prot && is.getUnlocalizedName().contains(strType)) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    public void shiftClick(int slot) {
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, 1, mc.player);
    }

    public void drop(int slot) {
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 1, 4, mc.player);
    }

    public static float getProtection(ItemStack stack) {
        float prot = 0.0F;
        if (stack.getItem() instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor)stack.getItem();
            prot = (float)((double)prot + (double)armor.damageReduceAmount + (double)((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075D);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0D);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0D);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0D);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0D);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.featherFalling.effectId, stack) / 100.0D);
        }

        return prot;
    }
}
