package love.target.mod.value.values;

import love.target.mod.value.Value;
import love.target.mod.value.ValueType;

public class ColorValue extends Value<Integer> {
    public ColorValue(String name, int value) {
        super(value, name, ValueType.COLOR_VALUE);
    }
}
