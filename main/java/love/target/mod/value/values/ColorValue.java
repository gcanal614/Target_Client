package love.target.mod.value.values;

import love.target.mod.value.Value;
import love.target.mod.value.ValueType;
import love.target.other.ColorPalette;

public class ColorValue extends Value<Integer> {
    private final ColorPalette colorPalette;

    public ColorValue(String name, int value) {
        super(value, name, ValueType.COLOR_VALUE);
        this.colorPalette = new ColorPalette(value);
        this.colorPalette.setNowRGB(value);
    }

    public ColorPalette getColorPalette() {
        return colorPalette;
    }
}
