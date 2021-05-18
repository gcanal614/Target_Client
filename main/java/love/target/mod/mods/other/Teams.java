package love.target.mod.mods.other;

import love.target.mod.Mod;
import love.target.mod.ModManager;
import net.minecraft.entity.Entity;

public class Teams extends Mod {
    public Teams() {
        super("Teams", Category.OTHER);
    }

    public static boolean isOnSameTeam(Entity entity) {
        if (!ModManager.getModByName("Teams").isEnabled()) {
            return false;
        }
        if (mc.player.getDisplayName().getUnformattedText().startsWith("§")) {
            if (mc.player.getDisplayName().getUnformattedText().length() <= 2 || entity.getDisplayName().getUnformattedText().length() <= 2) {
                return false;
            }
            return mc.player.getDisplayName().getUnformattedText().substring(0, 2).equals(entity.getDisplayName().getUnformattedText().substring(0, 2));
        }
        return false;
    }
}
