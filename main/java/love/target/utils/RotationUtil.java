package love.target.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtil {
    static Minecraft mc = Minecraft.getMinecraft();

    public static float changeRotation(float p_706631, float p_706632, float p_706633) {
        float var4 = MathHelper.wrapAngleTo180_float(p_706632 - p_706631);
        if (var4 > p_706633) {
            var4 = p_706633;
        }
        if (var4 < -p_706633) {
            var4 = -p_706633;
        }
        return p_706631 + var4;
    }

    public static float[] getPredictedRotations(EntityLivingBase ent) {
        double x = ent.posX + (ent.posX - ent.lastTickPosX);
        double z = ent.posZ + (ent.posZ - ent.lastTickPosZ);
        double y = ent.posY + (double)(ent.getEyeHeight() / 2.0f);
        return getRotationFromPosition(x, z, y);
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - mc.player.posX;
        double zDiff = z - mc.player.posZ;
        double yDiff = y - mc.player.posY - 1.2;
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static float getDistanceBetweenAngles(float angle1, float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (angle3 > 180.0f) {
            angle3 = 0.0f;
        }
        return angle3;
    }

    public static boolean canEntityBeSeen(Entity e) {
        boolean see;
        Vec3 vec1 = new Vec3(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ);
        AxisAlignedBB box = e.getEntityBoundingBox();
        Vec3 vec2 = new Vec3(e.posX, e.posY + (double)(e.getEyeHeight() / 1.32f), e.posZ);
        double minx = e.posX - 0.25;
        double maxx = e.posX + 0.25;
        double miny = e.posY;
        double maxy = e.posY + Math.abs(e.posY - box.maxY);
        double minz = e.posZ - 0.25;
        double maxz = e.posZ + 0.25;
        boolean bl = see = mc.world.rayTraceBlocks(vec1, vec2) == null;
        if (see) {
            return true;
        }
        vec2 = new Vec3(maxx, miny, minz);
        boolean bl2 = see = mc.world.rayTraceBlocks(vec1, vec2) == null;
        if (see) {
            return true;
        }
        vec2 = new Vec3(minx, miny, minz);
        boolean bl3 = see = mc.world.rayTraceBlocks(vec1, vec2) == null;
        if (see) {
            return true;
        }
        vec2 = new Vec3(minx, miny, maxz);
        boolean bl4 = see = mc.world.rayTraceBlocks(vec1, vec2) == null;
        if (see) {
            return true;
        }
        vec2 = new Vec3(maxx, miny, maxz);
        boolean bl5 = see = mc.world.rayTraceBlocks(vec1, vec2) == null;
        if (see) {
            return true;
        }
        vec2 = new Vec3(maxx, maxy, minz);
        boolean bl6 = see = mc.world.rayTraceBlocks(vec1, vec2) == null;
        if (see) {
            return true;
        }
        vec2 = new Vec3(minx, maxy, minz);
        boolean bl7 = see = mc.world.rayTraceBlocks(vec1, vec2) == null;
        if (see) {
            return true;
        }
        vec2 = new Vec3(minx, maxy, maxz - 0.1);
        boolean bl8 = see = mc.world.rayTraceBlocks(vec1, vec2) == null;
        if (see) {
            return true;
        }
        vec2 = new Vec3(maxx, maxy, maxz);
        see = mc.world.rayTraceBlocks(vec1, vec2) == null;
        return see;
    }
}
