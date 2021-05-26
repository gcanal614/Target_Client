package love.target.mod.mods.item;

import love.target.Wrapper;
import love.target.eventapi.EventTarget;
import love.target.events.EventTick;
import love.target.mod.Mod;
import love.target.mod.value.values.BooleanValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class AutoTool extends Mod {
    private final BooleanValue sword = new BooleanValue("SwordCheck", false);
    private final BooleanValue autoBack = new BooleanValue("AutoCutBack", false);
    private int oldSlot = -888;
    private boolean pressing = false;

    public AutoTool() {
        super("AutoTool", Category.ITEM);
        addValues(sword, autoBack);
    }

    @EventTarget
    public void onTick(EventTick e) {
        if (mc.gameSettings.keyBindAttack.isKeyDown() && mc.objectMouseOver != null) {
            if (sword.getValue() && mc.player.getHeldItem().getItem() instanceof ItemSword) {
                return;
            }
            Block block = mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
            if (block instanceof BlockAir) {
                return;
            }
            float strength = 1.0f;
            int bestItemIndex = -1;
            for (int i = 0; i < 8; i++) {
                ItemStack itemStack = mc.player.inventory.mainInventory[i];
                if (itemStack == null) continue;
                if (!(itemStack.getStrVsBlock(block) > strength)) continue;
                strength = itemStack.getStrVsBlock(block);
                bestItemIndex = i;
            }
            if (bestItemIndex != -1) {
                if (mc.player.inventory.currentItem != oldSlot && !pressing && autoBack.getValue()) {
                    oldSlot = mc.player.inventory.currentItem;
                    pressing = true;
                }
                mc.player.inventory.currentItem = bestItemIndex;
            }
        }
        if (!mc.gameSettings.keyBindAttack.isKeyDown() && pressing && autoBack.getValue()) {
            mc.player.inventory.currentItem = oldSlot;
            oldSlot = -888;
            pressing = false;
        }
    }
}
