package love.target.render.font;

import java.awt.Color;
import java.awt.Font;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class FontDrawer extends FontRenderer {
    private final UnicodeFont unicodeFont;

    @SuppressWarnings("unchecked")
    public FontDrawer(Font fontIn) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), true);
        this.unicodeFont = new UnicodeFont(fontIn);
        this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
        System.out.println("Adding glyphs " + unicodeFont.getFont().getFontName() + unicodeFont.getFont().getSize());
        this.unicodeFont.addGlyphs(0, 65535);
        try {
            this.unicodeFont.loadGlyphs();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
        this.FONT_HEIGHT = this.unicodeFont.getHeight("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789") / 2;
    }

    public int drawStringDirectly(String string, float x, float y, int color) {
        if (string == null) {
            return 0;
        }
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        boolean blend = GL11.glIsEnabled(3042);
        boolean lighting = GL11.glIsEnabled(2896);
        boolean texture = GL11.glIsEnabled(3553);
        if (!blend) {
            GL11.glEnable(3042);
        }
        if (lighting) {
            GL11.glDisable(2896);
        }
        if (texture) {
            GL11.glDisable(3553);
        }
        this.unicodeFont.drawString(x *= 2.0f, y *= 2.0f, string, new org.newdawn.slick.Color(color));
        if (texture) {
            GL11.glEnable(3553);
        }
        if (lighting) {
            GL11.glEnable(2896);
        }
        if (!blend) {
            GL11.glDisable(3042);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glPopMatrix();
        GlStateManager.bindTexture(0);
        return (int)x;
    }

    @Override
    public int drawString(String text, int x, int y, int color) {
        return this.drawString(text, (float)x, (float)y, color, new Color(color).getAlpha());
    }

    public int drawString(String text, float x, float y, int color) {
        return this.drawString(text, x, y, color, new Color(color).getAlpha());
    }

    public int drawString(String text, float x, float y, int color, int alpha) {
        text = "§r" + text;
        float len = -1.0f;
        for (String str : text.split("§")) {
            if (str.length() < 1) continue;
            switch (str.charAt(0)) {
                case '0': {
                    color = new Color(0, 0, 0).getRGB();
                    break;
                }
                case '1': {
                    color = new Color(0, 0, 170).getRGB();
                    break;
                }
                case '2': {
                    color = new Color(0, 170, 0).getRGB();
                    break;
                }
                case '3': {
                    color = new Color(0, 170, 170).getRGB();
                    break;
                }
                case '4': {
                    color = new Color(170, 0, 0).getRGB();
                    break;
                }
                case '5': {
                    color = new Color(170, 0, 170).getRGB();
                    break;
                }
                case '6': {
                    color = new Color(255, 170, 0).getRGB();
                    break;
                }
                case '7': {
                    color = new Color(170, 170, 170).getRGB();
                    break;
                }
                case '8': {
                    color = new Color(85, 85, 85).getRGB();
                    break;
                }
                case '9': {
                    color = new Color(85, 85, 255).getRGB();
                    break;
                }
                case 'a': {
                    color = new Color(85, 255, 85).getRGB();
                    break;
                }
                case 'b': {
                    color = new Color(85, 255, 255).getRGB();
                    break;
                }
                case 'c': {
                    color = new Color(255, 85, 85).getRGB();
                    break;
                }
                case 'd': {
                    color = new Color(255, 85, 255).getRGB();
                    break;
                }
                case 'e': {
                    color = new Color(255, 255, 85).getRGB();
                    break;
                }
                case 'f': {
                    color = new Color(255, 255, 255).getRGB();
                }
            }
            Color col = new Color(color);
            str = str.substring(1);
            this.drawStringDirectly(str, x + len, y, new Color(col.getRed(), col.getGreen(), col.getBlue(), alpha).getRGB());
            len += (float)(this.getStringWidth(str) + 1);
        }
        return (int)len;
    }

    @Override
    public int drawStringWithShadow(String text, float x, float y, int color) {
        return this.drawStringWithShadow(text, x, y, color, new Color(color).getAlpha());
    }

    public int drawStringWithShadow(String text, int x, int y, int color) {
        return this.drawStringWithShadow(text, x, y, color, new Color(color).getAlpha());
    }

    public int drawStringWithShadow(String text, float x, float y, int color, int alpha) {
        text = "§r" + text;
        float len = -1.0f;
        for (String str : text.split("§")) {
            if (str.length() < 1) continue;
            switch (str.charAt(0)) {
                case '0': {
                    color = new Color(0, 0, 0).getRGB();
                    break;
                }
                case '1': {
                    color = new Color(0, 0, 170).getRGB();
                    break;
                }
                case '2': {
                    color = new Color(0, 170, 0).getRGB();
                    break;
                }
                case '3': {
                    color = new Color(0, 170, 170).getRGB();
                    break;
                }
                case '4': {
                    color = new Color(170, 0, 0).getRGB();
                    break;
                }
                case '5': {
                    color = new Color(170, 0, 170).getRGB();
                    break;
                }
                case '6': {
                    color = new Color(255, 170, 0).getRGB();
                    break;
                }
                case '7': {
                    color = new Color(170, 170, 170).getRGB();
                    break;
                }
                case '8': {
                    color = new Color(85, 85, 85).getRGB();
                    break;
                }
                case '9': {
                    color = new Color(85, 85, 255).getRGB();
                    break;
                }
                case 'a': {
                    color = new Color(85, 255, 85).getRGB();
                    break;
                }
                case 'b': {
                    color = new Color(85, 255, 255).getRGB();
                    break;
                }
                case 'c': {
                    color = new Color(255, 85, 85).getRGB();
                    break;
                }
                case 'd': {
                    color = new Color(255, 85, 255).getRGB();
                    break;
                }
                case 'e': {
                    color = new Color(255, 255, 85).getRGB();
                    break;
                }
                case 'f': {
                    color = new Color(255, 255, 255).getRGB();
                }
            }
            Color col = new Color(color);
            str = str.substring(1);
            this.drawStringDirectly(str, x + len + 0.5f, y + 0.5f, new Color(0, 0, 0, 80).getRGB());
            this.drawStringDirectly(str, x + len, y, new Color(col.getRed(), col.getGreen(), col.getBlue(), alpha).getRGB());
            len += (float)(this.getStringWidth(str) + 1);
        }
        return (int)len;
    }

    @Override
    public int getCharWidth(char c) {
        return this.getStringWidth(Character.toString(c));
    }

    @Override
    public int getStringWidth(String string) {
        return this.unicodeFont.getWidth(string) / 2;
    }

    @Override
    public void drawCenteredString(String text, float x, float y, int color) {
        this.drawString(text, x - (float)(this.getStringWidth(text) / 2), y, color);
    }

    @Override
    public void drawCenteredStringWithShadow(String text, float x, float y, int color) {
        this.drawStringWithShadow(text, x - (float)(this.getStringWidth(text) / 2), y, color);
    }

    public void drawCenteredString(String text, int x, int y, int color) {
        this.drawString(text, (float)x - (float)(this.getStringWidth(text) / 2), (float)y, color);
    }
}
