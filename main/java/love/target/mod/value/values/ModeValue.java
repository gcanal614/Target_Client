package love.target.mod.value.values;

import love.target.mod.value.Value;
import love.target.mod.value.ValueType;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModeValue extends Value<String> {
    private final List<String> mods = new CopyOnWriteArrayList<>();

    public ModeValue(String name, String value) {
        super(value, name, ValueType.MODE_VALUE);
    }

    public ModeValue(String name, String value, String[] mods) {
        super(value, name, ValueType.MODE_VALUE);
        this.mods.addAll(Arrays.asList(mods));
    }

    public void addMode(String mode) {
        mods.add(mode);
    }

    public void addModes(String... mods) {
        this.mods.addAll(Arrays.asList(mods));
    }

    public boolean isCurrentValue(String value) {
        return this.getValue().equalsIgnoreCase(value);
    }

    public List<String> getModes() {
        return mods;
    }
}
