package love.target.mod.mods.visual;

import com.mojang.realmsclient.gui.ChatFormatting;
import love.target.eventapi.EventTarget;
import love.target.events.Event3D;
import love.target.mod.Mod;
import love.target.mod.mods.other.Teams;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.NumberValue;
import love.target.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ESP extends Mod {
    private static final BooleanValue self = new BooleanValue("Self", false);

    public ESP() {
        super("ESP", Category.VISUAL);
        this.addValues(self);
    }

    @EventTarget
    public void on3D(Event3D e) {
        for (EntityPlayer entity : mc.world.playerEntities) {
            if (entity == mc.player && mc.gameSettings.thirdPersonView == 0 || !isValid(entity) || !entity.isEntityAlive()) continue;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.enableBlend();
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            double renderPosX = mc.getRenderManager().viewerPosX;
            double renderPosY = mc.getRenderManager().viewerPosY;
            double renderPosZ = mc.getRenderManager().viewerPosZ;
            float partialTicks = e.getTicks();
            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - renderPosX;
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - renderPosY;
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - renderPosZ;
            float DISTANCE = mc.player.getDistanceToEntity(entity);
            float SCALE = 0.035f;
            entity.isChild();
            GlStateManager.translate((float)x, (float)y + entity.height + 0.5f - (entity.isChild() ? entity.height / 2.0f : 0.0f), (float)z);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(-(SCALE /= 2.0f), -SCALE, -SCALE);
            float HEALTH = entity.getHealth();
            int COLOR = (double)HEALTH > 20.0 ? -65292 : ((double)HEALTH >= 10.0 ? -16711936 : ((double)HEALTH >= 3.0 ? -23296 : -65536));
            new Color(0, 0, 0);
            double thickness = 1.5f + DISTANCE * 0.01f;
            double xLeft = -20.0;
            double xRight = 20.0;
            double yUp = 27.0;
            double yDown = 130.0;
            Color color = new Color(255, 255, 255);
            if (entity.hurtTime > 0) {
                color = new Color(255, 0, 0);
            } else if (Teams.isOnSameTeam(entity)) {
                color = new Color(0, 255, 0);
            }
            RenderUtils.drawBorderedRect((float)xLeft, (float)yUp, (float)xRight, (float)yDown, (float)thickness + 0.5f, Color.BLACK.getRGB(), 0);
            RenderUtils.drawBorderedRect((float)xLeft, (float)yUp, (float)xRight, (float)yDown, (float)thickness, color.getRGB(), 0);
            RenderUtils.drawBorderedRect((float)xLeft - 3.0f - DISTANCE * 0.2f, (float)yDown - (float)(yDown - yUp), (float)xLeft - 2.0f, (float)yDown, 0.15f, Color.BLACK.getRGB(), new Color(100, 100, 100).getRGB());
            RenderUtils.drawBorderedRect((float)xLeft - 3.0f - DISTANCE * 0.2f, (float)yDown - (float)(yDown - yUp) * Math.min(1.0f, entity.getHealth() / 20.0f), (float)xLeft - 2.0f, (float)yDown, 0.15f, Color.BLACK.getRGB(), COLOR);
            int c = entity.getHealth() < 5.0f ? new Color(255, 20, 10).getRGB() : ((double)entity.getHealth() < 12.5 ? new Color(16774441).getRGB() : new Color(0, 255, 0).getRGB());
            mc.fontRenderer.drawStringWithShadow(entity.getName(), (int)(xLeft / 2.0 - (double)(mc.fontRenderer.getStringWidth(entity.getName()) / 2)) + 9, (int)yUp - 15, Teams.isOnSameTeam(entity) ? new Color(0, 255, 0).getRGB() : new Color(255, 0, 0).getRGB());
            mc.fontRenderer.drawStringWithShadow(String.valueOf((int)entity.getHealth()) + ChatFormatting.RED + " ♥", (float)xLeft - (float)mc.fontRenderer.getStringWidth(String.valueOf((int)entity.getHealth()) + ChatFormatting.RED + " ♥") - 10.0f, (float)yDown / 2.0f, c);
            mc.fontRenderer.drawStringWithShadow(entity.getHeldItem() == null ? "" : entity.getHeldItem().getDisplayName(), (int)(xLeft / 2.0 - (double)(mc.fontRenderer.getStringWidth(entity.getHeldItem() == null ? "" : entity.getHeldItem().getDisplayName()) / 2)), (int)yDown + 10, new Color(0, 255, 0).getRGB());
            String svar = "XYZ: " + (int)entity.posX + " " + (int)entity.posY + " " + (int)entity.posZ + " 距离: " + (int)DISTANCE + "m";
            int y2 = 0;
            for (PotionEffect effect : entity.getActivePotionEffects()) {
                Potion potion = Potion.potionTypes[effect.getPotionID()];
                String PType = I18n.format(potion.getName(), new Object[0]);
                switch (effect.getAmplifier()) {
                    case 1: {
                        PType = PType + " II";
                        break;
                    }
                    case 2: {
                        PType = PType + " III";
                        break;
                    }
                    case 3: {
                        PType = PType + " IV";
                    }
                }
                if (effect.getDuration() < 600 && effect.getDuration() > 300) {
                    PType = PType + "§7:§6 " + Potion.getDurationString(effect);
                } else if (effect.getDuration() < 300) {
                    PType = PType + "§7:§c " + Potion.getDurationString(effect);
                } else if (effect.getDuration() > 600) {
                    PType = PType + "§7:§7 " + Potion.getDurationString(effect);
                }
                mc.fontRenderer.drawStringWithShadow(PType, (float)xLeft - (float)mc.fontRenderer.getStringWidth(PType) - 5.0f, (float)yUp - (float)mc.fontRenderer.FONT_HEIGHT + (float)y2 + 20.0f, potion.getLiquidColor());
                y2 -= 10;
            }
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GlStateManager.disableBlend();
            GL11.glDisable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glNormal3f(1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
    }

    public static boolean isValid(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer && entity.getHealth() >= 0.0f) {
            return entity != mc.player || self.getValue();
        }
        return false;
    }
}
