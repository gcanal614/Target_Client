package love.target.mod.value;

public enum ValueType {
    BOOLEAN_VALUE,
    NUMBER_VALUE,
    MODE_VALUE,
    TEXT_VALUE,
    COLOR_VALUE,
    NULL;

    public static ValueType get(String str) {
        for (ValueType type : values()) {
            if (type.toString().toLowerCase().equalsIgnoreCase(str)) {
                return type;
            }
        }

        return NULL;
    }
}
