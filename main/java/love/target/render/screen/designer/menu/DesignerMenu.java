package love.target.render.screen.designer.menu;

import love.target.Wrapper;
import love.target.other.PressEvent;
import love.target.render.font.FontManager;
import love.target.utils.render.RenderUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class DesignerMenu {
    private final String title;
    private float x = 2.0f;
    private float y = 2.0f;
    private float dragX;
    private float dragY;
    private boolean mouseDown = false;
    private List<DesignerMenuButton> designerMenuButtons = new CopyOnWriteArrayList<DesignerMenuButton>();

    public DesignerMenu(String title) {
        this.title = title;
    }

    protected void addButton(DesignerMenuButton button) {
        this.designerMenuButtons.add(button);
    }

    public void draw(int mouseX, int mouseY) {
        RenderUtils.drawRect(this.x, this.y, this.x + 100.0f, this.y + 12.0f, -1);
        FontManager.yaHei18.drawString(this.title, this.x + 2.0f, this.y, new Color(0, 0, 0).getRGB());
        if (Wrapper.isHovered(this.x, this.y, this.x + 100.0f, this.y + 12.0f, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            if (this.dragX == 0.0f && this.dragY == 0.0f) {
                this.dragX = (float)mouseX - this.x;
                this.dragY = (float)mouseY - this.y;
            } else {
                this.x = (float)mouseX - this.dragX;
                this.y = (float)mouseY - this.dragY;
            }
        } else if (this.dragX != 0.0f || this.dragY != 0.0f) {
            this.dragX = 0.0f;
            this.dragY = 0.0f;
        }
        float buttonY = this.y + 12.0f;
        for (DesignerMenuButton button : this.designerMenuButtons) {
            if (Wrapper.isHovered(this.x, buttonY, this.x + 100.0f, buttonY + (float) FontManager.yaHei16.FONT_HEIGHT, mouseX, mouseY) && Mouse.isButtonDown(0) && !this.mouseDown) {
                button.onPress();
                this.mouseDown = true;
            }
            buttonY += 11.0f;
        }
        RenderUtils.drawRect(this.x, this.y + 12.0f, this.x + 100.0f, buttonY, new Color(0xBFBFBF).getRGB());
        float buttonY1 = this.y + 12.0f;
        for (DesignerMenuButton button : this.designerMenuButtons) {
            FontManager.yaHei16.drawString(button.getButtonText(), this.x + 3.0f, buttonY1, new Color(0, 0, 0).getRGB());
            if (Wrapper.isHovered(this.x, buttonY1, this.x + 100.0f, buttonY1 + (float)FontManager.yaHei16.FONT_HEIGHT, mouseX, mouseY)) {
                RenderUtils.drawRect(this.x, buttonY1, this.x + 100.0f, buttonY1 + (float)FontManager.yaHei16.FONT_HEIGHT, new Color(0, 0, 0, 50).getRGB());
            }
            buttonY1 += 11.0f;
        }
        if (!Mouse.isButtonDown(0)) {
            this.mouseDown = false;
        }
    }

    protected static class DesignerMenuButton {
        private final String buttonText;
        private final PressEvent pressEvent;

        public DesignerMenuButton(String buttonText, PressEvent pressEvent) {
            this.buttonText = buttonText;
            this.pressEvent = pressEvent;
        }

        public void onPress() {
            this.pressEvent.pressed();
        }

        public String getButtonText() {
            return this.buttonText;
        }
    }
}
