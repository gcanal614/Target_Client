package love.target.designer.rightclickmenu;

import love.target.Wrapper;
import love.target.render.font.FontManager;
import love.target.render.screen.designer.GuiDesigner;
import love.target.utils.render.RenderUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class NormalRightClickMenu extends RightClickMenu {
    private boolean leftClickDown = false;

    public NormalRightClickMenu(int x, int y) {
        super(x, y);
    }

    @Override
    public void onOpen() {
        this.buttons.add(new RightClickMenu.RightClickMenuButton("删除", () -> {
            GuiDesigner.displayingMenu = null;
            GuiDesigner.designers.remove(GuiDesigner.selectedDesigner);
            GuiDesigner.selectedDesigner = null;
        }));
        this.buttons.add(new RightClickMenu.RightClickMenuButton("取消", () -> {
            GuiDesigner.displayingMenu = null;
        }));
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        float buttonY0 = this.y + 2;
        for (RightClickMenu.RightClickMenuButton button : this.buttons) {
            buttonY0 += 10.0f;
        }
        RenderUtils.drawRect(this.x, this.y, this.x + 100, buttonY0, -1);
        float buttonY = this.y;
        for (RightClickMenu.RightClickMenuButton button : this.buttons) {
            FontManager.yaHei16.drawString(button.getButtonText(), (float)(this.x + 2), buttonY, new Color(0, 0, 0).getRGB());
            if (Wrapper.isHovered(this.x, buttonY, this.x + 100, buttonY + (float) FontManager.yaHei16.FONT_HEIGHT, mouseX, mouseY)) {
                RenderUtils.drawRect(this.x, buttonY, this.x + 100, buttonY + (float)FontManager.yaHei16.FONT_HEIGHT, new Color(0, 0, 0, 50).getRGB());
                if (Mouse.isButtonDown(0) && !this.leftClickDown) {
                    button.onPress();
                    this.leftClickDown = true;
                }
            }
            buttonY += 12.0f;
        }
        if (!Wrapper.isHovered(this.x, this.y, this.x + 100, buttonY0, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            GuiDesigner.displayingMenu = null;
        }
        if (!Mouse.isButtonDown(0)) {
            this.leftClickDown = false;
        }
    }
}
