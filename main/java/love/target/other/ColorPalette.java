package love.target.other;

import love.target.Wrapper;
import love.target.utils.render.RenderUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class ColorPalette {
    private float x,y;
    private int nowRGB;

    public ColorPalette(int nowRGB) {
        this.nowRGB = nowRGB;
    }

    public ColorPalette(float x, float y,int nowRGB) {
        this.x = x;
        this.y = y;
        this.nowRGB = nowRGB;
    }

    public void draw(int mouseX, int mouseY) {
        float colorX = x,colorY = y;
        for (int i = 0;i < 115;i += 1) {
            Color color = new Color(Color.HSBtoRGB((float) (i / 115.0 + Math.sin(1.6)) % 1.0f, 1.0f, 1.0f));
            RenderUtils.drawRect(colorX,colorY,colorX + 1,colorY + 10, color.getRGB());
            if (nowRGB == color.getRGB()) {
                RenderUtils.drawRect(colorX,colorY,colorX + 1,colorY + 10, new Color(0x6A6A6A).getRGB());
            }
            if (Mouse.isButtonDown(0) && Wrapper.isHovered(colorX,colorY,colorX + 1,colorY + 10,mouseX,mouseY)) {
                nowRGB = color.getRGB();
            }
            colorX += 1;
        }
    }

    public int getNowRGB() {
        return nowRGB;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setNowRGB(int nowRGB) {
        this.nowRGB = nowRGB;
    }
}
