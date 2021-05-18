package love.target.utils;

import love.target.events.EventMove;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class MoveUtils {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static void setSpeed(double speed) {
        mc.player.motionX = -Math.sin(getDirection()) * speed;
        mc.player.motionZ = Math.cos(getDirection()) * speed;
    }

    public static int getJump() {
        if (mc.player.isPotionActive(Potion.jump)) {
            return mc.player.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        }
        return 0;
    }

    public static double getSpeed() {
        double baseSpeed = 0.2873;
        if (mc.player.isPotionActive(Potion.moveSpeed)) {
            int amplifier = mc.player.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }

    public static boolean isOnGround(double height) {
        return !mc.world.getCollidingBoundingBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }

    public static boolean isOnLiquid() {
        AxisAlignedBB boundingBox = mc.player.getEntityBoundingBox();
        if (boundingBox == null) {
            return false;
        }
        boundingBox = boundingBox.contract(0.01, 0.0, 0.01).offset(0.0, -0.01, 0.0);
        boolean onLiquid = false;
        int y = (int)boundingBox.minY;
        for (int x = MathHelper.floor_double(boundingBox.minX); x < MathHelper.floor_double(boundingBox.maxX + 1.0); ++x) {
            for (int z = MathHelper.floor_double(boundingBox.minZ); z < MathHelper.floor_double(boundingBox.maxZ + 1.0); ++z) {
                Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block == Blocks.air) continue;
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
                onLiquid = true;
            }
        }
        return onLiquid;
    }

    public static boolean isInLiquid() {
        if (mc.player == null) {
            return false;
        }
        if (mc.player.isInWater()) {
            return true;
        }
        boolean var1 = false;
        int var2 = (int)mc.player.getEntityBoundingBox().minY;
        for (int var3 = MathHelper.floor_double(mc.player.getEntityBoundingBox().minX); var3 < MathHelper.floor_double(mc.player.getEntityBoundingBox().maxX) + 1; ++var3) {
            for (int var4 = MathHelper.floor_double(mc.player.getEntityBoundingBox().minZ); var4 < MathHelper.floor_double(mc.player.getEntityBoundingBox().maxZ) + 1; ++var4) {
                Block var5 = mc.world.getBlockState(new BlockPos(var3, var2, var4)).getBlock();
                if (var5 == null || var5.getMaterial() == Material.air) continue;
                if (!(var5 instanceof BlockLiquid)) {
                    return false;
                }
                var1 = true;
            }
        }
        return var1;
    }

    public static void setSpeedEvent(EventMove event, double speed) {
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }

    public static float getDirection() {
        float yaw = mc.player.rotationYaw;
        if (mc.player.movementInput.moveForward < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        if (mc.player.movementInput.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (mc.player.movementInput.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (mc.player.movementInput.moveStrafe > 0.0f) {
            yaw -= 90.0f * forward;
        }
        if (mc.player.movementInput.moveStrafe < 0.0f) {
            yaw += 90.0f * forward;
        }
        return yaw *= (float)Math.PI / 180;
    }
}
