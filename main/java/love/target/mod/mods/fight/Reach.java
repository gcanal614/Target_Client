package love.target.mod.mods.fight;

import love.target.mod.Mod;
import love.target.mod.value.values.NumberValue;

public class Reach extends Mod {
    private static final NumberValue reachValue = new NumberValue("Reach",3.0,3.0,8.0,0.1);

    public Reach() {
        super("Reach",Category.FIGHT);
        addValues(reachValue);
    }

    public static double getReachValue() {
        return reachValue.getValue();
    }
}
