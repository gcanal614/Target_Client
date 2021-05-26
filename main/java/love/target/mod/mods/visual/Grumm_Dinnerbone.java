package love.target.mod.mods.visual;

import love.target.mod.Mod;
import love.target.mod.value.values.BooleanValue;

public class Grumm_Dinnerbone extends Mod {
    public static BooleanValue all = new BooleanValue("All",false);

    public Grumm_Dinnerbone() {
        super("Grumm",Category.VISUAL);
        addValues(all);
    }
}
