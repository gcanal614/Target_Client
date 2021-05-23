package love.target.mod.mods.player;

import love.target.eventapi.EventTarget;
import love.target.events.EventPacket;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;

public class SpeedMine extends Mod {
    private boolean bzs = false;
    private float bzx = 0.0f;
    public BlockPos blockPos;
    public EnumFacing facing;

    public SpeedMine() {
        super("SpeedMine",Category.PLAYER);
    }

    public Block getBlock(double x, double y, double z) {
        BlockPos bp = new BlockPos(x, y, z);
        return mc.world.getBlockState(bp).getBlock();
    }

    @EventTarget
    public void onPacket(EventPacket event) {
        if (event.getPacket() instanceof C07PacketPlayerDigging && !mc.playerController.extendedReach() && mc.playerController != null) {
            C07PacketPlayerDigging c07PacketPlayerDigging = (C07PacketPlayerDigging) event.getPacket();
            if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                this.bzs = true;
                this.blockPos = c07PacketPlayerDigging.getPosition();
                this.facing = c07PacketPlayerDigging.getFacing();
                this.bzx = 0.0f;
            } else if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK || c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                this.bzs = false;
                this.blockPos = null;
                this.facing = null;
            }
        }
    }

    @EventTarget
    public void onUpdate(EventPreUpdate event) {
        if (mc.playerController.extendedReach()) {
            mc.playerController.blockHitDelay = 0;
        } else if (this.bzs) {
            Block block = mc.world.getBlockState(this.blockPos).getBlock();
            this.bzx += (float) ((double) block.getPlayerRelativeBlockHardness(mc.player, mc.world, this.blockPos) * 1.4);
            if (this.bzx >= 1.0f) {
                mc.world.setBlockState(this.blockPos, Blocks.air.getDefaultState(), 11);
                mc.player.sendQueue.getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.blockPos, this.facing));
                this.bzx = 0.0f;
                this.bzs = false;
            }
        }
    }
}
