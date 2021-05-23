package love.target.utils;

import love.target.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

public class BlockUtils {
    public static int getBlockID(Block block) {
        return Block.getIdFromBlock(block);
    }

    public static Block getBlock(BlockPos pos) {
        return Wrapper.mc.world.getBlockState(pos).getBlock();
    }
}
