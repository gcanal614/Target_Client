package love.target.mod.mods.move;

import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.mod.value.values.ModeValue;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * @see net.minecraft.client.entity.EntityPlayerSP -> onLivingUpdate()
 */

public class NoSlow extends Mod {
    private final ModeValue mode = new ModeValue("Mode","Vanilla",new String[]{"Vanilla","Watchdog"});

    public NoSlow() {
        super("NoSlow", Category.MOVE);
        addValues(mode);
    }

    @EventTarget
    public void onUpdate(EventPreUpdate e) {
        if (mode.isCurrentValue("Watchdog")) {
            if (mc.player.isRiding() || !mc.player.onGround) {
                return;
            }
            if (mc.player.isMoving() && mc.player.isBlocking()) {
                mc.getNetHandler().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
        }
    }
}
