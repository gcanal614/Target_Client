package love.target.mod.mods.visual;

import love.target.eventapi.EventTarget;
import love.target.events.Event3D;
import love.target.mod.Mod;
import love.target.mod.ModManager;
import love.target.mod.mods.item.ChestStealer;
import love.target.utils.render.RenderUtils;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author Sigma客户端
 */

public class ChestESP extends Mod {
    public ChestESP() {
        super("ChestESP",Category.VISUAL);
    }

    @EventTarget
    public void on3D(Event3D e) {
        for (TileEntity tileEntity : mc.world.loadedTileEntityList) {
            if (tileEntity instanceof TileEntityChest) {
                TileEntityLockable storage = (TileEntityLockable) tileEntity;
                this.drawESPOnStorage(storage, storage.getPos().getX(), storage.getPos().getY(), storage.getPos().getZ());
            } else if (tileEntity instanceof TileEntityEnderChest) {
                float renderX = (float)((double)tileEntity.getPos().getX() - mc.getRenderManager().viewerPosX);
                float renderY = (float)((double)tileEntity.getPos().getY() - mc.getRenderManager().viewerPosY);
                float renderZ = (float)((double)tileEntity.getPos().getZ() - mc.getRenderManager().viewerPosZ);
                double minX;
                double minY;
                double minZ;
                double negXDoubleChest = 0.0;
                double negZDoubleChest = 0.0;
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDisable(3553);
                GL11.glEnable(2848);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                RenderUtils.glColor(new Color(187, 0, 255, 50).getRGB());
                minX = (double)renderX + 0.0625 - negXDoubleChest;
                minY = renderY;
                minZ = (double)renderZ + 0.0625 - negZDoubleChest;
                RenderUtils.drawBoundingBox(new AxisAlignedBB(minX, minY, minZ, minX + 0.9, minY + 0.9, minZ + 0.9));
                GL11.glLineWidth(0.5f);
                GL11.glDisable(2848);
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glPopMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }

    public void drawESPOnStorage(TileEntity storage, double x, double y, double z) {
        TileEntityChest chest = (TileEntityChest) storage;
        Vec3 vec = new Vec3(0, 0, 0);
        Vec3 vec2 = new Vec3(0, 0, 0);
        if (chest.adjacentChestZNeg != null) {
            vec = new Vec3(x + 0.0625, y, z - 0.9375);
            vec2 = new Vec3(x + 0.9375, y + 0.875, z + 0.9375);
        } else if (chest.adjacentChestXNeg != null) {
            vec = new Vec3(x + 0.9375, y, z + 0.0625);
            vec2 = new Vec3(x - 0.9375, y + 0.875, z + 0.9375);
        } else if (chest.adjacentChestXPos == null && chest.adjacentChestZPos == null) {
            vec = new Vec3(x + 0.0625, y, z + 0.0625);
            vec2 = new Vec3(x + 0.9375, y + 0.875, z + 0.9375);
        } else {
            return;
        }
        GL11.glPushMatrix();
        RenderUtils.pre3D();
        mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);

        if (chest.getChestType() == 1) {
            GL11.glColor4d(0.7, 0.1, 0.1, 0.5);
        } else {
            RenderUtils.glColor(new Color(255, 255, 0, 50).getRGB());
        }
        RenderUtils.drawBoundingBox(new AxisAlignedBB(vec.xCoord - mc.getRenderManager().renderPosX, vec.yCoord - mc.getRenderManager().renderPosY, vec.zCoord - mc.getRenderManager().renderPosZ, vec2.xCoord - mc.getRenderManager().renderPosX, vec2.yCoord - mc.getRenderManager().renderPosY, vec2.zCoord - mc.getRenderManager().renderPosZ));
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        RenderUtils.post3D();
        GL11.glPopMatrix();
    }
}
