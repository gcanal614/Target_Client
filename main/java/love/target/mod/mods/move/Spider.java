package love.target.mod.mods.move;

import love.target.eventapi.EventTarget;
import love.target.events.EventCollideWithBlock;
import love.target.events.EventPacket;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.mod.value.values.ModeValue;
import love.target.mod.value.values.NumberValue;
import love.target.utils.MoveUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class Spider extends Mod {
    private final ModeValue mode = new ModeValue("Mode", "Jump", new String[]{"Motion", "Jump"});
    private final NumberValue speed = new NumberValue("MotionSpeed", 0.1, 0.0, 0.5, 0.01);

    public Spider() {
        super("Spider", Category.MOVE);
        addValues(mode, speed);
    }

    @EventTarget
    public void onUpdate(EventPreUpdate e) {
        if (mode.isCurrentValue("Motion")) {
            if (mc.player.isCollidedHorizontally) {
                mc.player.motionY = speed.getValue();
            }
        } else if (mode.isCurrentValue("Jump")) {
            if (mc.player.isCollidedHorizontally && !mc.player.isOnLadder() && !mc.player.isInWater() && !mc.player.isInLava()) {
                if (mc.player.onGround) {
                    mc.player.motionY = 0.39;
                } else if (mc.player.motionY < 0.0) {
                    mc.player.motionY = -0.24;
                }
            }
        }
    }

    @EventTarget
    public void onBlockCollide(EventCollideWithBlock e) {
        if (mode.isCurrentValue("Jump")) {
            if (mc.player.isCollidedHorizontally && !mc.player.isOnLadder() && !mc.player.isInWater() && !mc.player.isInLava()) {
                e.getBoxes().add(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).offset(mc.player.posX, (int) mc.player.posY - 1, mc.player.posZ));
            }
        }
    }

    @EventTarget
    public void onPacket(EventPacket event) {
        if (mode.isCurrentValue("Jump")) {
            if (event.getPacket() instanceof C03PacketPlayer) {
                C03PacketPlayer p = (C03PacketPlayer) event.getPacket();
                if (mc.player.isCollidedHorizontally && !mc.player.isOnLadder() && !mc.player.isInWater() && !mc.player.isInLava()) {
                    double speed = 1.0E-10;
                    float f = MoveUtils.getDirection();
                    p.setX_SPIDER0(p.getPositionX() - MathHelper.sin(f) * speed);
                    p.setZ_SPIDER0(p.getPositionZ() + MathHelper.cos(f) * speed);
                }
            }
        }
    }
}
