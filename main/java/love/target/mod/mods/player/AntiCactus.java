package love.target.mod.mods.player;

import love.target.eventapi.EventTarget;
import love.target.events.EventCollideWithBlock;
import love.target.mod.Mod;
import net.minecraft.block.BlockCactus;
import net.minecraft.util.AxisAlignedBB;

public class AntiCactus extends Mod {
    public AntiCactus() {
        super("AntiCactus", Category.PLAYER);
    }

    @EventTarget
    public void onBoundingBox(EventCollideWithBlock event) {
        if (event.getBlock() instanceof BlockCactus) event.setBoundingBox(new AxisAlignedBB(event.getBlockPos().getX(),event.getBlockPos().getY(),event.getBlockPos().getZ(),event.getBlockPos().getX() + 1,event.getBlockPos().getY() + 1,event.getBlockPos().getZ() + 1));
    }
}

