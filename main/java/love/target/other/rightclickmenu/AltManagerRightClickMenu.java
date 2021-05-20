package love.target.other.rightclickmenu;

import love.target.Wrapper;
import love.target.render.font.FontManager;
import love.target.render.screen.altlogin.Alt;
import love.target.render.screen.altlogin.AltLoginThread;
import love.target.render.screen.altlogin.GuiAltManager;
import love.target.utils.render.RenderUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class AltManagerRightClickMenu extends RightClickMenu {
    private final Alt alt;
    private boolean leftClickDown = false;

    public AltManagerRightClickMenu(int x, int y, Alt alt) {
        super(x, y);
        this.alt = alt;
    }

    @Override
    public void onOpen() {
        this.buttons.add(new RightClickMenu.RightClickMenuButton("登录", () -> {
            GuiAltManager.displayingMenu = null;
            String cachePassword = alt.getPassword().equals("NULL_PASSWORD") ? "" : alt.getPassword();
            GuiAltManager.altLoginThread = new AltLoginThread(alt.getUserName(),cachePassword);
            GuiAltManager.altLoginThread.start();
        }));
        this.buttons.add(new RightClickMenu.RightClickMenuButton("删除", () -> {
            try {
                GuiAltManager.getAlts().remove(alt);
                GuiAltManager.selectedAlt = null;
                GuiAltManager.displayingMenu = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        this.buttons.add(new RightClickMenu.RightClickMenuButton("取消", () -> GuiAltManager.displayingMenu = null));
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        RenderUtils.resetColor();
        RenderUtils.drawRect(x, y, x + 100, y + 25 + (buttons.size() * 10.0f), -1);

        if (!Wrapper.isHovered(x, y, x + 100, y + 25 + (buttons.size() * 10.0f), mouseX,mouseY) && (Mouse.isButtonDown(0) || Mouse.isButtonDown(1))) {
            GuiAltManager.displayingMenu = null;
        }

        alt.loadHead();
        RenderUtils.drawImage(alt.getHead(),x + 1,y + 1,15,15);
        FontManager.yaHei16.drawString(alt.getUserName(),x + 20,y + 3,new Color(0, 0, 0).getRGB());

        float buttonY = y + 20;
        for (RightClickMenu.RightClickMenuButton button : buttons) {
            FontManager.yaHei16.drawString(button.getButtonText(),x + 2, buttonY, new Color(0, 0, 0).getRGB());
            if (Wrapper.isHovered(x, buttonY, x + 100, buttonY + FontManager.yaHei16.FONT_HEIGHT, mouseX,mouseY)) {
                RenderUtils.drawRect(x, buttonY, x + 100, buttonY + FontManager.yaHei16.FONT_HEIGHT, new Color(0, 0, 0, 50).getRGB());
                if (Mouse.isButtonDown(0) && !leftClickDown) {
                    button.onPress();
                    leftClickDown = true;
                }
            }
            buttonY += 12.0f;
        }

        if (!Mouse.isButtonDown(0)) {
            leftClickDown = false;
        }
    }
}
