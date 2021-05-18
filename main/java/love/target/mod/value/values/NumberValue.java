package love.target.mod.value.values;

import love.target.mod.value.Value;
import love.target.mod.value.ValueType;

public class NumberValue extends Value<Double> {
    private final double minValue;
    private final double maxValue;
    private final double increaseValue;

    public NumberValue(String name,double value, double minValue, double maxValue, double increaseValue) {
        super(value, name, ValueType.NUMBER_VALUE);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.increaseValue = increaseValue;
    }

    public NumberValue(String name,float value, float minValue, float maxValue, float increaseValue) {
        super((double) value, name, ValueType.NUMBER_VALUE);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.increaseValue = increaseValue;
    }

    public NumberValue(String name,long value, long minValue, long maxValue, long increaseValue) {
        super((double) value, name, ValueType.NUMBER_VALUE);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.increaseValue = increaseValue;
    }

    public NumberValue(String name,int value, int minValue, int maxValue, int increaseValue) {
        super((double) value, name, ValueType.NUMBER_VALUE);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.increaseValue = increaseValue;
    }

    public NumberValue(String name,short value, short minValue, short maxValue, short increaseValue) {
        super((double) value, name, ValueType.NUMBER_VALUE);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.increaseValue = increaseValue;
    }

    public NumberValue(String name,byte value, byte minValue, byte maxValue, byte increaseValue) {
        super((double) value, name, ValueType.NUMBER_VALUE);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.increaseValue = increaseValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getIncreaseValue() {
        return increaseValue;
    }
}
