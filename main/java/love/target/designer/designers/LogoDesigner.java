package love.target.designer.designers;

import com.mojang.realmsclient.gui.ChatFormatting;
import love.target.Wrapper;
import love.target.designer.Designer;
import love.target.other.rightclickmenu.NormalRightClickMenu;
import love.target.other.rightclickmenu.RightClickMenu;
import love.target.render.font.FontManager;
import love.target.utils.render.RenderUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class LogoDesigner extends Designer {

    public LogoDesigner() {
        this.x = 3;
        this.designerType = DesignerType.LOGO;
    }

    public LogoDesigner(int x,int y) {
        this.x = x;
        this.y = y;
        this.designerType = DesignerType.LOGO;
    }

    @Override
    public void draw() {
        if (this.canDrawDesignerInfo()) {
            RenderUtils.drawBorderedRect(this.x - 2, this.y, x + FontManager.yaHei20.getStringWidth("Target") + 2,y + FontManager.yaHei20.FONT_HEIGHT + 1, 1.0f, new Color(0, 0, 255).getRGB(), new Color(0, 0, 0, 0).getRGB());
            FontManager.yaHei16.drawString("Logo X:" + this.x + " Y:" + this.y, this.x - 2, this.y - 13, -1);
        }
        FontManager.yaHei20.drawStringWithShadow(ChatFormatting.YELLOW + "Target", x,y, -1);
    }

    @Override
    public boolean canDrag(int mouseX, int mouseY) {
        return Wrapper.isHovered(x,y,x + FontManager.yaHei20.getStringWidth("Target"),y + FontManager.yaHei20.FONT_HEIGHT,mouseX,mouseY) && Mouse.isButtonDown(0);
    }

    @Override
    public boolean canOpenMenu(int mouseX, int mouseY) {
        return Wrapper.isHovered(x,y,x + FontManager.yaHei20.getStringWidth("Target"),y + FontManager.yaHei20.FONT_HEIGHT,mouseX,mouseY) && Mouse.isButtonDown(1);
    }

    @Override
    public RightClickMenu getRightClickMenu(int mouseX, int mouseY) {
        return new NormalRightClickMenu(mouseX,mouseY);
    }
}
