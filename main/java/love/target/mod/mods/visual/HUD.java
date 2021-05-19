package love.target.mod.mods.visual;

import love.target.Wrapper;
import love.target.designer.Designer;
import love.target.eventapi.EventTarget;
import love.target.events.Event2D;
import love.target.mod.Mod;
import love.target.render.font.FontManager;
import love.target.render.screen.designer.GuiDesigner;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HUD extends Mod {
    public HUD() {
        super("HUD", Category.VISUAL);
    }

    @EventTarget
    public void on2D(Event2D e) {
        for (Designer designer : GuiDesigner.designers) {
            if (designer.getDesignerType() != Designer.DesignerType.PLAYER_LIST && designer.getDesignerType() != Designer.DesignerType.SPEED_LIST) {
                designer.draw();
            }
        }

        int ychat = mc.ingameGUI.getChatGUI().getChatOpen() ? 4 : -10;
        String xyz = "X:" + (int)mc.player.posX + " Y:" + (int)mc.player.posY + " Z:" + (int)mc.player.posZ + " Blocks:" + Wrapper.speedBsString(mc.player,2);
        FontManager.yaHei16.drawStringWithShadow(xyz, 3, e.getScaledResolution().getScaledHeight() - FontManager.yaHei16.FONT_HEIGHT - 12 - ychat, -1);
        int potionTextY = 0;
        for (PotionEffect effect : mc.player.getActivePotionEffects()) {
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            String PType = I18n.format(potion.getName());
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
            FontManager.yaHei16.drawStringWithShadow(PType, e.getScaledResolution().getScaledWidth() - FontManager.yaHei16.getStringWidth(PType) + 15, e.getScaledResolution().getScaledHeight() - FontManager.yaHei16.FONT_HEIGHT + potionTextY - 12 - ychat, potion.getLiquidColor());
            potionTextY -= 10;
        }
    }
}
