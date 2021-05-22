package love.target.designer.designers;

import com.utils.ObjectUtils;
import com.utils.color.ColorUtils;
import love.target.Wrapper;
import love.target.designer.Designer;
import love.target.mod.mods.visual.HUD;
import love.target.other.rightclickmenu.NormalRightClickMenu;
import love.target.other.rightclickmenu.RightClickMenu;
import love.target.mod.Mod;
import love.target.mod.ModManager;
import love.target.render.font.FontManager;
import love.target.render.screen.designer.GuiDesigner;
import love.target.utils.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ArrayListDesigner extends Designer {
    private static final String[] exampleMods = new String[]{"I_LOVE_WATCHDOG","51LLA0ra","Ve10c1t9","Ant180t","N0S10w","4pr1nt","S6ee9","HU0","E4P"};

    public ArrayListDesigner() {
        this.x = scaledResolution.getScaledWidth();
        this.y = 2;
        this.designerType = DesignerType.ARRAY_LIST;
    }

    public ArrayListDesigner(int x,int y) {
        this.x = x;
        this.y = y;
        this.designerType = DesignerType.ARRAY_LIST;
    }

    @Override
    public void draw() {
        if (ObjectUtils.reverse(mc.currentScreen instanceof GuiDesigner)) {

            List<Mod> sort = new CopyOnWriteArrayList<>(ModManager.getMods());

            if (HUD.arrayListSort.getValue()) {
                sort.sort((o1, o2) -> FontManager.yaHei16.getStringWidth(o2.getName()) - FontManager.yaHei16.getStringWidth(o1.getName()));
            }

            float textY = this.y;
            int rainbowTick = 0;
            int fadeTicks = 1;
            for (Mod m : sort) {
                if (!m.isEnabled()) { continue; }
                int color;
                switch (HUD.arrayListColorMode.getValue()) {
                    case "Custom":
                        color = HUD.arrayListCustomColor.getValue();
                        break;
                    case "Rainbow":
                        color = ColorUtils.rainbowInt(rainbowTick);
                        break;
                    case "Fade":
                        Color normalColor = new Color(HUD.arrayListCustomColor.getValue());
                        color = fade(normalColor, fadeTicks).getRGB();
                        break;
                    case "White":
                    default:
                        color = -1;
                        break;
                }

                float renderX = Math.min(x, new ScaledResolution(mc).getScaledWidth());
                float fontWidth = HUD.arrayListEdge.isCurrentValue("Right") ? FontManager.yaHei16.getStringWidth(m.getName()) : HUD.arrayListEdge.isCurrentValue("Left") ? FontManager.yaHei16.getStringWidth(sort.get(0).getName()) :  FontManager.yaHei16.getStringWidth(m.getName());
                FontManager.yaHei16.drawStringWithShadow(m.getName(), (float)(renderX - fontWidth - 1), textY, color);
                if (HUD.arrayListYWay.isCurrentValue("Down")) {
                    textY += 10.0f;
                } else if (HUD.arrayListYWay.isCurrentValue("Up")) {
                    textY -= 10.0f;
                } else {
                    textY += 10.0f;
                }
                fadeTicks++;
                rainbowTick += 200;
            }
        } else {
            if (canDrawDesignerInfo()) {
                RenderUtils.drawBorderedRect(x - FontManager.yaHei16.getStringWidth("I_LOVE_WATCHDOG") - 3, this.y, x,y + 90, 1.0f, new Color(0, 0, 255).getRGB(), new Color(0, 0, 0, 0).getRGB());
                FontManager.yaHei16.drawString("ArrayList X:" + this.x + " Y:" + this.y, x - FontManager.yaHei16.getStringWidth("I_LOVE_WATCHDOG") - 2, this.y - 13, -1);
            }
            float textY = this.y;
            for (String s : exampleMods) {
                FontManager.yaHei16.drawStringWithShadow(s, (float)(x - FontManager.yaHei16.getStringWidth(s) - 1), textY, -1);
                textY += 10;
            }
        }
    }

    /**
     * SKID SUNNY CLIENT
     * BECAUSE I AM A SKIDDER :)
     */
    private Color fade(Color color, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 100.0f / count * 2.0f) % 2.0f - 1.0f);
        brightness = 0.5f + 0.5f * brightness;
        hsb[2] = brightness % 2.0f;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    @Override
    public boolean canDrag(int mouseX, int mouseY) {
        return Wrapper.isHovered(x - FontManager.yaHei16.getStringWidth("I_LOVE_WATCHDOG") - 3,y,x,y + 90,mouseX,mouseY) && Mouse.isButtonDown(0);
    }

    @Override
    public boolean canOpenMenu(int mouseX, int mouseY) {
        return Wrapper.isHovered(x - FontManager.yaHei16.getStringWidth("I_LOVE_WATCHDOG") - 3,y,x,y + 90,mouseX,mouseY) && Mouse.isButtonDown(1);
    }

    @Override
    public RightClickMenu getRightClickMenu(int mouseX, int mouseY) {
        return new NormalRightClickMenu(mouseX,mouseY);
    }
}
