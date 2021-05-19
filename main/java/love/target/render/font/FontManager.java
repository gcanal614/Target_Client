package love.target.render.font;

import com.utils.ObjectUtils;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.IOException;

public class FontManager {
    public static FontDrawer yaHei16;
    public static FontDrawer yaHei18;
    public static FontDrawer yaHei20;
    public static FontDrawer yaHei24;

    public static FontDrawer cursor;

    public static void init() {
        yaHei16 = FontManager.getFont("msyh", 16.0f, FontType.TTF);
        yaHei18 = FontManager.getFont("msyh", 18.0f, FontType.TTF);
        yaHei20 = FontManager.getFont("msyh", 20.0f, FontType.TTF);
        yaHei24 = FontManager.getFont("msyh", 24.0f, FontType.TTF);
        cursor = FontManager.getFont("cursor",20.0f,FontType.TTF);
    }

    private static FontDrawer getFont(String fontName, float size, FontType type) {
        try {
            String typeString;
            switch (type) {
                case TTF:
                    typeString = ".ttf";
                    break;
                case OTF:
                    typeString = ".otf";
                    break;
                default:
                    typeString = "";
                    break;
            }
            Font font = Font.createFont(0, ObjectUtils.getResource("target_resources/fonts/" + fontName + typeString));
            FontDrawer fontDrawer = new FontDrawer(font.deriveFont(size));
            fontDrawer.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());
            return fontDrawer;
        }
        catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public enum FontType {
        TTF,
        OTF
    }
}
