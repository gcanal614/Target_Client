package love.target.mod.mods.other;

import love.target.eventapi.EventTarget;
import love.target.events.Event2D;
import love.target.mod.Mod;
import love.target.mod.value.values.*;
import love.target.render.font.FontManager;
import love.target.utils.render.RenderUtils;

import java.awt.*;

public class TestMod extends Mod {
    private final ModeValue modeValue = new ModeValue("TEST_MODE_VALUE","0",new String[]{"0","1","2"});
    private final ColorValue colorValue = new ColorValue("TEST_COLOR_VALUE",-1);
    private final BooleanValue booleanValue = new BooleanValue("TEST_BOOLEAN_VALUE0",false);
    private final NumberValue numberValue1 = new NumberValue("TEST_NUMBER_VALUE1",1.0,-100,210,1);
    private final TextValue textValue = new TextValue("TEXT_VALUE","");
    private final NumberValue numberValue = new NumberValue("TEST_NUMBER_VALUE0",1.0,-10,10,1);
    private final ColorValue colorValue1 = new ColorValue("TEST_COLOR_VALUE2",new Color(0x0000FF).getRGB());
    private final BooleanValue booleanValue1 = new BooleanValue("TEST_BOOLEAN_VALUE1",true);

    public TestMod() {
        super("TestMod",Category.OTHER);
        addValues(modeValue,colorValue,booleanValue,numberValue1,textValue,numberValue,colorValue1,booleanValue1);
    }

    @EventTarget
    public void on2D(Event2D e) {
        RenderUtils.drawRect(10,150,20,200,colorValue.getValue());
        RenderUtils.drawRect(10,200,20,250,colorValue1.getValue());
        FontManager.yaHei24.drawString(textValue.getValue(),10,250,-1);
    }
}
