package love.target.mod.value.values;

import love.target.mod.value.Value;
import love.target.mod.value.ValueType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

public class TextValue extends Value<String> {
    private final GuiTextField textField = new GuiTextField(0, Minecraft.getMinecraft().fontRenderer,0,0,50,25);

    public TextValue(String name,String value) {
        super(value, name, ValueType.TEXT_VALUE);
    }

    public GuiTextField getTextField() {
        return textField;
    }

    public void setTextFieldX(int x) {
        textField.xPosition = x;
    }

    public void setTextFieldY(int y) {
        textField.yPosition = y;
    }

    public void setTextFieldWidth(int width) {
        textField.setWidth(width);
    }

    public void setTextFieldHeight(int height) {
        textField.setHeight(height);
    }
}
