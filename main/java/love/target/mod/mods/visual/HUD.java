package love.target.mod.mods.visual;

import com.utils.string.StringUtils;
import love.target.Wrapper;
import love.target.designer.Designer;
import love.target.designer.designers.TabGuiDesigner;
import love.target.eventapi.EventTarget;
import love.target.events.Event2D;
import love.target.events.EventKey;
import love.target.mod.Mod;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.ColorValue;
import love.target.mod.value.values.ModeValue;
import love.target.mod.value.values.TextValue;
import love.target.render.font.FontManager;
import love.target.render.screen.designer.GuiDesigner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;

public class HUD extends Mod {
    private final TextValue clientName = new TextValue("ClientName","Target");
    public static final ModeValue arrayListColorMode = new ModeValue("ArrayListColor","White",new String[]{"White","Custom","Rainbow","Fade"});
    public static final ModeValue arrayListEdge = new ModeValue("ArrayListEdge","Right",new String[]{"Right","Left"});
    public static final ModeValue arrayListYWay = new ModeValue("ArrayListWay","Down",new String[]{"Down","Up"});
    public static final ColorValue arrayListCustomColor = new ColorValue("ArrayListCustomColor",-1);
    public static final ColorValue tabGuiColor = new ColorValue("TabGuiColor",new Color(179, 0, 255).getRGB());
    public static final BooleanValue arrayListSort = new BooleanValue("ArrayListSort",true);
    public static final BooleanValue info = new BooleanValue("Info",true);

    public static String clientNameRender;

    public HUD() {
        super("HUD", Category.VISUAL);
        addValues(arrayListColorMode,arrayListEdge,arrayListYWay,clientName,arrayListCustomColor,tabGuiColor,arrayListSort,info);
    }

    @EventTarget
    public void onKey(EventKey e) {
        for (Designer designer : GuiDesigner.designers) {
            if (designer.getDesignerType() == Designer.DesignerType.TAB_GUI) {
                ((TabGuiDesigner) designer).onKeyPress(e.getKeyCode());
            }
        }
    }

    @EventTarget
    public void on2D(Event2D e) {
        if (clientName.getValue().contains("%time%")) {
            clientNameRender = clientName.getValue().replace("%time%", StringUtils.getTheTimeInSeconds());
        } else {
            clientNameRender = clientName.getValue();
        }

        for (EnumChatFormatting chatFormatting : EnumChatFormatting.values()) {
            if (clientName.getValue().contains("%" + chatFormatting.getFormattingCode() + "%")) {
                clientNameRender = clientNameRender.replace("%" + chatFormatting.getFormattingCode() + "%","\u00a7" + chatFormatting.getFormattingCode());
            }
        }

        if (!mc.gameSettings.showDebugInfo) {
            for (Designer designer : GuiDesigner.designers) {
                if (designer.getDesignerType() != Designer.DesignerType.PLAYER_LIST && designer.getDesignerType() != Designer.DesignerType.SPEED_LIST && designer.getDesignerType() != Designer.DesignerType.INVENTORY_HUD) {
                    designer.draw();
                }
            }
        }
        int ychat = mc.ingameGUI.getChatGUI().getChatOpen() ? 4 : -10;
        String xyz = "X:" + (int)mc.player.posX + " Y:" + (int)mc.player.posY + " Z:" + (int)mc.player.posZ + " Blocks:" + Wrapper.speedBsString(mc.player,2);

        if (info.getValue()) {
            if (mc.ingameGUI.getChatGUI().getChatOpen()) {
                FontManager.yaHei16.drawStringWithShadow(xyz + " FPS:" + Minecraft.getDebugFPS(), 3, e.getScaledResolution().getScaledHeight() - FontManager.yaHei16.FONT_HEIGHT - 12 - ychat, -1);
            } else {
                FontManager.yaHei16.drawStringWithShadow(xyz, 3, e.getScaledResolution().getScaledHeight() - FontManager.yaHei16.FONT_HEIGHT - 12 - ychat, -1);
                FontManager.yaHei16.drawStringWithShadow("FPS:" + Minecraft.getDebugFPS(), 3, e.getScaledResolution().getScaledHeight() - (FontManager.yaHei16.FONT_HEIGHT * 2) - 12 - ychat, -1);
            }
        }
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
