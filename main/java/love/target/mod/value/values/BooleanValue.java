package love.target.mod.value.values;

import love.target.mod.value.Value;
import love.target.mod.value.ValueType;

public class BooleanValue extends Value<Boolean> {
    public float anim;

    public BooleanValue(String name,boolean normalValue) {
        super(normalValue,name, ValueType.BOOLEAN_VALUE);
    }
}
