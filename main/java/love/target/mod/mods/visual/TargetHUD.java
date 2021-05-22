package love.target.mod.mods.visual;

import com.utils.color.ColorUtils;
import love.target.Wrapper;
import love.target.eventapi.EventTarget;
import love.target.events.Event2D;
import love.target.mod.Mod;
import love.target.mod.mods.fight.KillAura;
import love.target.render.font.FontManager;
import love.target.utils.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class TargetHUD extends Mod {
    private float nowHealth = 0.0f;
    private EntityLivingBase lastEntity;

    public TargetHUD() {
        super("TargetHUD",Category.VISUAL);
    }

    @EventTarget
    public void on2D(Event2D e) {
        EntityLivingBase targetEntity = KillAura.curtarget;
        float x = e.getScaledResolution().getScaledWidth() / 2.0f + 50;
        float y = e.getScaledResolution().getScaledHeight() / 2.0f + 50;
        if (targetEntity != null) {
            float percentageOfHealth = Math.min(100,(targetEntity.getHealth() / targetEntity.getMaxHealth()) * 100);
            if (lastEntity == targetEntity) {
                nowHealth = (float) RenderUtils.getAnimationState(nowHealth, percentageOfHealth, 15);
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
            FontManager.yaHei16.drawCenteredString("HP:" + (int) targetEntity.getHealth(),x + 70,y + 12,-1);
            lastEntity = targetEntity;
        }
    }
}
