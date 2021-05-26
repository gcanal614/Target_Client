package love.target.mod.mods.move;

import love.target.eventapi.EventTarget;
import love.target.events.EventPostUpdate;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.mod.value.values.ModeValue;
import love.target.utils.MoveUtils;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.RandomUtils;

/**
 * Watchdog mode by spicy client -> status.spicyclient.info
 * @see net.minecraft.client.entity.EntityPlayerSP -> onLivingUpdate()
 */

public class NoSlow extends Mod {
    public static final ModeValue mode = new ModeValue("Mode","Vanilla",new String[]{"Vanilla","NCP","Watchdog","AAC","Matrix"});

    public NoSlow() {
        super("NoSlow", Category.MOVE);
        addValues(mode);
    }

    @EventTarget
    public void onUpdate(EventPreUpdate e) {
        if (mode.isCurrentValue("Watchdog")) {
            if (mc.player.isBlocking() && mc.player.isMoving() && MoveUtils.isOnGround(0.42D)) {
                mc.getNetHandler().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(RandomUtils.nextDouble(4.9E-324D, 1.7976931348623157E308D), RandomUtils.nextDouble(4.9E-324D, 1.7976931348623157E308D), RandomUtils.nextDouble(4.9E-324D, 1.7976931348623157E308D)), EnumFacing.DOWN));
            }
        } else if (mode.isCurrentValue("NCP")) {
            if (mc.player.isRiding() || !mc.player.onGround) {
                return;
            }

            if (mc.player.isMoving() && mc.player.isBlocking()) {
                mc.getNetHandler().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
        } else if (mode.isCurrentValue("AAC")) {
            if (mc.player.isMoving() && mc.player.isBlocking()) {
                mc.getNetHandler().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
        } else if (mode.isCurrentValue("Matrix")) {
            if (mc.player.isBlocking() && mc.player.isMoving()) {
                mc.getNetHandler().sendPacket(new C08PacketPlayerBlockPlacement(mc.player.getCurrentEquippedItem()));
            }
        }
    }

    @EventTarget
    public void onPost(EventPostUpdate e) {
        if (mode.isCurrentValue("Watchdog")) {
            if (mc.player.isBlocking() && mc.player.isMoving() && MoveUtils.isOnGround(0.42D)) {
                mc.getNetHandler().sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.player.getHeldItem(), 0.0F, 0.0F, 0.0F));
            }
        }
    }
}
