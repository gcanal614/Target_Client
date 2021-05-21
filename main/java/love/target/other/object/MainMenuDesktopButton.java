package love.target.other.object;

import love.target.Wrapper;
import love.target.other.PressEvent;
import love.target.render.font.FontManager;
import love.target.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class MainMenuDesktopButton {
    private final String name;
    private final ResourceLocation icon;
    private final PressEvent pressEvent;
    private float x,y;

    private boolean leftMouseDown;

    public MainMenuDesktopButton(float x, float y, String name, ResourceLocation icon, PressEvent pressEvent) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.icon = icon;
        this.pressEvent = pressEvent;
    }

    public MainMenuDesktopButton(String name, ResourceLocation icon, PressEvent pressEvent) {
        this.name = name;
        this.icon = icon;
        this.pressEvent = pressEvent;
    }

    public void draw(int mouseX, int mouseY) {
        RenderUtils.drawImage(getIcon(),x,y,30,25,new Color(95,158,160).getRGB());
        FontManager.yaHei16.drawCenteredString(name,x + 17,y + 27,-1);

        if (GuiMainMenu.selectedMainMenuDesktopButton == this) {
            RenderUtils.drawBorderedRect(x - 3,y - 3,x + 34,y + 40,1,new Color(150,150,150).getRGB(),new Color(0,0,0,50).getRGB());
        }

        if (Wrapper.isHovered(x - 3,y - 3,x + 34,y + 40,mouseX,mouseY)) {
            RenderUtils.drawBorderedRect(x - 3,y - 3,x + 34,y + 40,1,new Color(150,150,150).getRGB(),new Color(0,0,0,50).getRGB());

            if (Mouse.isButtonDown(0) && !leftMouseDown) {
                if (GuiMainMenu.selectedMainMenuDesktopButton != this) {
                    GuiMainMenu.selectedMainMenuDesktopButton = this;
                } else {
                    pressEvent.pressed();
                }
                leftMouseDown = true;
            }
        }

        if (!Mouse.isButtonDown(0)) {
            leftMouseDown = false;
        }
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
