package love.target.mod.mods.world;

import love.target.eventapi.EventTarget;
import love.target.events.Event3D;
import love.target.mod.Mod;
import love.target.utils.MoveUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.util.BlockPos;

public class AutoDoor extends Mod {
    public AutoDoor() {
        super("AutoDoor",Category.WORLD);
    }

    @EventTarget
    public void onRender3D(Event3D e) {
        double yaw = MoveUtils.getDirection();
        double x = mc.player.posX + -Math.sin(yaw) * 1;
        double z = mc.player.posZ + Math.cos(yaw) * 1;
        double y = mc.player.posY;

        BlockPos pos = new BlockPos(x,y,z);
        Block b = mc.world.getBlockState(pos).getBlock();
        if (b instanceof BlockDoor) {
            if (!BlockDoor.isOpen(mc.world,pos)) {
                mc.player.swingItem();
                mc.playerController.onPlayerRightClick(mc.player, mc.world, mc.player.getHeldItem(), pos, mc.objectMouseOver.sideHit, mc.objectMouseOver.hitVec);
            }
        }
    }
}
