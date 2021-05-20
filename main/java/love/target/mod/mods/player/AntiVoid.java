package love.target.mod.mods.player;

import love.target.Wrapper;
import love.target.eventapi.EventTarget;
import love.target.events.EventPacket;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.utils.MoveUtils;
import love.target.utils.TimerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author SpicyClient
 */

public class AntiVoid extends Mod {
    private static final transient List<Packet<?>> packets = new CopyOnWriteArrayList<>();
    private static transient Data6d lastOnground = null;
    private static transient boolean antivoid = false, resumeCheckingAfterFall = false;
    private static transient TimerUtil noSpam = new TimerUtil();

    public AntiVoid() {
        super("AntiVoid", Category.PLAYER);
    }

    @Override
    public void onEnable() {
        lastOnground = null;
        antivoid = false;
        packets.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        packets.clear();
        super.onDisable();
    }

    @EventTarget
    public void onEvent(EventPreUpdate e) {
        if (resumeCheckingAfterFall) {
            lastOnground = null;
            if (MoveUtils.isOnGround(0.0001)) {
                resumeCheckingAfterFall = false;
            }
            return;
        }
        if (!isOverVoid()) {
            lastOnground = new Data6d(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.motionX, mc.player.motionY, mc.player.motionZ);
            for (Packet<?> p : packets) {
                mc.getNetHandler().sendPacketNoEvent(p);
            }
            packets.clear();
            antivoid = false;
        } else {
            if (mc.player.fallDistance >= 20 && antivoid && noSpam.hasReached(2000)) {
                packets.clear();
                try {
                    setPosAndMotionWithData6d(lastOnground);
                } catch (Exception ignored) { }
                antivoid = false;
                resumeCheckingAfterFall = true;
                noSpam.reset();
            }
        }
    }

    @EventTarget
    public void onPacket(EventPacket e) {
        if (isOverVoid() && e.isClientPacket()) {
            packets.add(e.getPacket());
            e.setCancelled(true);
            antivoid = true;
        }
    }

    private void setPosAndMotionWithData6d(Data6d posAndMotion) {
        mc.player.setPosition(posAndMotion.x, posAndMotion.y, posAndMotion.z);
        mc.player.motionX = posAndMotion.motionX;
        mc.player.motionY = posAndMotion.motionY;
        mc.player.motionZ = posAndMotion.motionZ;
    }

    private boolean isOverVoid() {
        boolean isOverVoid = true;
        BlockPos block = new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ);
        for (double i = mc.player.posY + 1; i > 0; i -= 0.5) {
            if (isOverVoid) {
                try {
                    if (mc.world.getBlockState(block).getBlock() != Blocks.air) {
                        isOverVoid = false;
                        break;
                    }
                } catch (Exception ignored) { }
            }
            block = block.add(0, -1, 0);
        }

        for (double i = 0; i < 10; i += 0.1) {
            if (MoveUtils.isOnGround(i) && isOverVoid) {
                isOverVoid = false;
                break;
            }
        }

        return isOverVoid;
    }

    private static class Data6d {
        public double x, y, z, motionX, motionY, motionZ;

        public Data6d(double x, double y, double z, double motionX, double motionY, double motionZ) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.motionX = motionX;
            this.motionY = motionY;
            this.motionZ = motionZ;
        }
    }
}
