package love.target.mod.mods.visual;

import love.target.eventapi.EventTarget;
import love.target.events.Event2D;
import love.target.mod.Mod;
import love.target.mod.mods.fight.KillAura;
import love.target.mod.value.values.ModeValue;
import love.target.render.font.FontManager;
import love.target.utils.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class TargetHUD extends Mod {
    private final ModeValue mode = new ModeValue("Mode","Target",new String[]{"Target","Minecraft","Exhibition"});

    private float nowHealth = 0.0f;
    private EntityLivingBase lastEntity;

    public TargetHUD() {
        super("TargetHUD",Category.VISUAL);
        addValues(mode);
    }

    @EventTarget
    public void on2D(Event2D e) {
        switch (mode.getValue()) {
            case "Minecraft":
                renderMinecraftTargetHUD(e.getScaledResolution(),KillAura.curtarget);
                break;
            case "Exhibition":
                renderExhibitionTargetHUD(e.getScaledResolution(),KillAura.curtarget);
                break;
            case "Target":
            default:
                renderTargetHUD(e.getScaledResolution(),KillAura.curtarget);
                break;
        }
    }

    public void renderTargetHUD(ScaledResolution scaledResolution, EntityLivingBase targetEntity) {
        float x = scaledResolution.getScaledWidth() / 2.0f + 50;
        float y = scaledResolution.getScaledHeight() / 2.0f + 50;
        if (targetEntity != null) {
            float percentageOfHealth = Math.min(100,(targetEntity.getHealth() / targetEntity.getMaxHealth()) * 100);
            if (lastEntity == targetEntity) {
                nowHealth = (float) RenderUtils.getAnimationState(nowHealth, percentageOfHealth, 20);
            } else {
                nowHealth = percentageOfHealth;
            }
            RenderUtils.drawRect(x,y,x + 130,y + 27.5,new Color(66,66,66).getRGB());
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.bindTexture(0);
            if (targetEntity instanceof EntityPlayer) {
                NetworkPlayerInfo networkPlayerInfo = mc.getNetHandler().getPlayerInfo(targetEntity.getUniqueID());
                mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                Gui.drawScaledCustomSizeModalRect((int) x + 2, (int) y + 2, 8.0f, 8.0f, 8, 8, 24, 24, 64.0f, 64.0f);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.bindTexture(0);
            } else {
                RenderUtils.drawRect(x + 2,y + 2,x + 26,y + 26,new Color(0x744848).getRGB());
                FontManager.yaHei24.drawCenteredString("?",x + 14,y + 5,-1);
            }

            int healthColor = new Color(99, 173, 99).getRGB();

            if (percentageOfHealth < 60 && percentageOfHealth > 20) {
                healthColor = new Color(173, 166, 99).getRGB();
            } else if (percentageOfHealth <= 20) {
                healthColor = new Color(173, 99, 99).getRGB();
            }

            FontManager.yaHei16.drawString(targetEntity.getName(),x + 28,y,-1);
            RenderUtils.drawRect(x + 28,y + 12,x + 128,y + 25.5,new Color(70,70,70).getRGB());
            RenderUtils.drawRect(x + 28,y + 12,x + 28 + nowHealth,y + 25.5,healthColor);
            FontManager.yaHei16.drawCenteredString("HP:" + (int) targetEntity.getHealth() + " / " + (int) targetEntity.getMaxHealth(),x + 72.5f,y + 12,-1);
            lastEntity = targetEntity;
        }
    }

    private void renderMinecraftTargetHUD(ScaledResolution scaledResolution,EntityLivingBase entityLivingBase) {
        if (entityLivingBase != null) {
            GL11.glColor4f(1.0f, 1.0f,1.0f,1.0f);
            mc.fontRenderer.drawStringWithShadow(entityLivingBase.getName(), scaledResolution.getScaledWidth() / 2.0f - mc.fontRenderer.getStringWidth(entityLivingBase.getName()) / 2.0f, scaledResolution.getScaledHeight() / 2.0f - 30, 16777215);
            mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/icons.png"));

            int i = 0;
            while ((float) i < entityLivingBase.getMaxHealth() / 2.0f) {
                mc.ingameGUI.drawTexturedModalRect((float) (scaledResolution.getScaledWidth() / 2) - entityLivingBase.getMaxHealth() / 2.0f * 10.0f / 2.0f + (float) (i * 10), (float) (scaledResolution.getScaledHeight() / 2 - 20), 16, 0, 9, 9);
                ++i;
            }
            i = 0;
            while ((float) i < entityLivingBase.getHealth() / 2.0f) {
                mc.ingameGUI.drawTexturedModalRect((float) (scaledResolution.getScaledWidth() / 2) - entityLivingBase.getMaxHealth() / 2.0f * 10.0f / 2.0f + (float) (i * 10), (float) (scaledResolution.getScaledHeight() / 2 - 20), 52, 0, 9, 9);
                ++i;
            }
        }
    }

    private void renderExhibitionTargetHUD(ScaledResolution scaledResolution,EntityLivingBase entityLivingBase) {
        float width = scaledResolution.getScaledWidth();
        float height = scaledResolution.getScaledHeight();
        if (entityLivingBase != null && !entityLivingBase.isDead) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(width / 2.0f + 10.0f, height - 90.0f, 0.0f);
            RenderUtils.drawRect(0.0, 0.0, 125.0, 36.0, new Color(0,0,0, 150).getRGB());
            mc.fontRenderer.drawStringWithShadow(entityLivingBase.getName(), 38.0f, 2.0f, -1);
            float health = entityLivingBase.getHealth();
            float progress = health / entityLivingBase.getMaxHealth();
            Color customColor = (entityLivingBase.hurtTime > 5) ? Color.RED : ((health >= 0.0f) ? new Color(0,0,0).brighter() : Color.RED);
            double width2 = mc.fontRenderer.getStringWidth(entityLivingBase.getName());
            double one = 1.0 / 10.0;
            width2 = Math.round(width2 * one) / one;
            if (width2 < 50.0) {
                width2 = 50.0;
            }
            double healthLocation = width2 * progress;
            RenderUtils.drawRect(37.5, 11.5, 38.0 + healthLocation + 0.5, 14.5, customColor.getRGB());
            RenderUtils.drawBorderedRect(37.0, 11.0, 39.0 + width2, 15.0, 0.5f, new Color(0, 0, 0, 0).getRGB(), new Color(0, 0, 0, 150).getRGB());
            for (int i = 1; i < 10; ++i) {
                double dThing = width2 / 10.0 * i;
                RenderUtils.drawRect(38.0 + dThing, 11.0, 38.0 + dThing + 0.5, 15.0, new Color(0,0,0).getRGB());
            }
            GlStateManager.scale(0.5, 0.5, 0.5);
            String str = "HP: " + (int) health + " | Dist: " + (int) mc.player.getDistanceToEntity(entityLivingBase);
            mc.fontRenderer.drawStringWithShadow(str, 76.0f, 35.0f, -1);
            GlStateManager.scale(2.0f, 2.0f, 2.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            //mc.getRenderItem().zLevel = -147.0f;
            mc.getRenderItem().renderItemAndEffectIntoGUI(entityLivingBase.getEquipmentInSlot(4), 36, 20);
            mc.getRenderItem().renderItemAndEffectIntoGUI(entityLivingBase.getEquipmentInSlot(3), 52, 20);
            mc.getRenderItem().renderItemAndEffectIntoGUI(entityLivingBase.getEquipmentInSlot(2), 68, 20);
            mc.getRenderItem().renderItemAndEffectIntoGUI(entityLivingBase.getEquipmentInSlot(1), 84, 20);
            mc.getRenderItem().renderItemAndEffectIntoGUI(entityLivingBase.getEquipmentInSlot(0), 100, 20);
            RenderUtils.drawEntityOnScreen(18, 34, 16.0f, 0.0f, 9.0f, entityLivingBase);
            GlStateManager.popMatrix();
        }
    }
}
