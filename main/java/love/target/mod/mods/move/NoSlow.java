package love.target.mod.mods.move;

import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow extends Mod {
    public NoSlow() {
        super("NoSlow", Category.MOVE);
    }

    @EventTarget
    public void onUpdate(EventPreUpdate e) {
        if (mc.player.isRiding() || !mc.player.onGround) {
            return;
        }
        if (mc.player.isMoving() && mc.player.isBlocking()) {
            mc.getNetHandler().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }
}
