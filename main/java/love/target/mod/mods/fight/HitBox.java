package love.target.mod.mods.fight;

import love.target.mod.Mod;
import love.target.mod.ModManager;
import love.target.mod.value.values.NumberValue;

/**
 * @see net.minecraft.entity.Entity -> getCollisionBorderSize()
 */

public class HitBox extends Mod {
    private static final NumberValue size = new NumberValue("Size", 0.1, 0.1, 1.0, 0.1);

    public HitBox() {
        super("HitBox",Category.FIGHT);
        addValues(size);
    }

    public static float getSize() {
        if (ModManager.getModEnableByName("HitBox")) {
            return size.getValue().floatValue();
        }

        return 0.1F;
    }
}
