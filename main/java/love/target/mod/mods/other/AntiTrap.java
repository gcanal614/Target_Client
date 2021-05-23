package love.target.mod.mods.other;

import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.*;

public class AntiTrap extends Mod {
    public AntiTrap() {
        super("AntiTrap",Category.OTHER);
    }

    @EventTarget
    public void OnUpdate(EventPreUpdate e) {
        double var9 = mc.player.posY + 3.0D;
        BlockPos sand = new BlockPos(new Vec3(mc.player.posX, var9, mc.player.posZ));
        Block sandblock = mc.world.getBlockState(sand).getBlock();
        var9 = mc.player.posY + 2.0D;
        BlockPos forge = new BlockPos(new Vec3(mc.player.posX, var9, mc.player.posZ));
        Block forgeblock = mc.world.getBlockState(forge).getBlock();
        var9 = mc.player.posY + 1.0D;
        BlockPos obsidianpos = new BlockPos(new Vec3(mc.player.posX, var9, mc.player.posZ));
        Block obsidianblock = mc.world.getBlockState(obsidianpos).getBlock();
        BlockPos downpos;
        if (obsidianblock == Block.getBlockById(49)) {
            this.bestTool(mc.objectMouseOver.getBlockPos().getX(), mc.objectMouseOver.getBlockPos().getY(), mc.objectMouseOver.getBlockPos().getZ());
            var9 = mc.player.posY - 1.0D;
            downpos = new BlockPos(new Vec3(mc.player.posX, var9, mc.player.posZ));
            mc.playerController.onPlayerDamageBlock(downpos, EnumFacing.UP);
        }


        if (forgeblock == Block.getBlockById(61)) {
            this.bestTool(mc.objectMouseOver.getBlockPos().getX(), mc.objectMouseOver.getBlockPos().getY(), mc.objectMouseOver.getBlockPos().getZ());
            var9 = mc.player.posY - 1.0D;
            downpos = new BlockPos(new Vec3(mc.player.posX, var9, mc.player.posZ));
            mc.playerController.onPlayerDamageBlock(downpos, EnumFacing.UP);
        }

        if (forgeblock == Block.getBlockById(12)) {
            this.bestTool(mc.objectMouseOver.getBlockPos().getX(), mc.objectMouseOver.getBlockPos().getY(), mc.objectMouseOver.getBlockPos().getZ());
            var9 = mc.player.posY - 1.0D;
            downpos = new BlockPos(new Vec3(mc.player.posX, var9, mc.player.posZ));
            mc.playerController.onPlayerDamageBlock(downpos, EnumFacing.UP);
        }
    }

    public void bestTool(int x, int y, int z) {
        int blockId = Block.getIdFromBlock(mc.world.getBlockState(new BlockPos(x, y, z)).getBlock());
        int bestSlot = 0;
        float f = -1.0F;

        for(int i1 = 36; i1 < 45; ++i1) {
            try {
                ItemStack curSlot = mc.player.inventoryContainer.getSlot(i1).getStack();
                if ((curSlot.getItem() instanceof ItemTool || curSlot.getItem() instanceof ItemSword || curSlot.getItem() instanceof ItemShears) && curSlot.getStrVsBlock(Block.getBlockById(blockId)) > f) {
                    bestSlot = i1 - 36;
                    f = curSlot.getStrVsBlock(Block.getBlockById(blockId));
                }
            } catch (Exception ignored) { }
        }

        if (f != -1.0F) {
            mc.player.inventory.currentItem = bestSlot;
            mc.playerController.updateController();
        }
    }
}
