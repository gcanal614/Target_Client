package love.target.designer.designers;

import com.utils.ObjectUtils;
import love.target.Wrapper;
import love.target.designer.Designer;
import love.target.other.rightclickmenu.NormalRightClickMenu;
import love.target.other.rightclickmenu.RightClickMenu;
import love.target.mod.Mod;
import love.target.mod.ModManager;
import love.target.render.font.FontManager;
import love.target.render.screen.designer.GuiDesigner;
import love.target.utils.render.RenderUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.List;

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
            List<Mod> sort = ModManager.getMods();
            sort.sort((o1, o2) -> FontManager.yaHei16.getStringWidth(o2.getName()) - FontManager.yaHei16.getStringWidth(o1.getName()));
            float textY = this.y;
            for (Mod m : sort) {
                if (!m.isEnabled()) continue;
                FontManager.yaHei16.drawStringWithShadow(m.getName(), (float)(x - FontManager.yaHei16.getStringWidth(m.getName()) - 1), textY, -1);
                textY += 10.0f;
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
