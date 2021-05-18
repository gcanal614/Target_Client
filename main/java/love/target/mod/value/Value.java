package love.target.mod.value;

public class Value<T> {
    private final ValueType valueType;
    private final String name;
    private T value;

    public Value(T value, String name,ValueType valueType) {
        this.valueType = valueType;
        this.value = value;
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public ValueType getValueType() {
        return valueType;
    }
}
