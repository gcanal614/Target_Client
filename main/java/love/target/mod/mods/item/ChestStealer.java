package love.target.mod.mods.item;

import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.mod.ModManager;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.NumberValue;
import love.target.utils.TimerUtil;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ChestStealer extends Mod {
    private final NumberValue delay = new NumberValue("Delay", 50, 0, 1000, 1);
    public final static BooleanValue menuCheck = new BooleanValue("MenuCheck", true);
    public final static BooleanValue silence = new BooleanValue("Silence", false);
    private final TimerUtil timer = new TimerUtil();

    public ChestStealer() {
        super("ChestStealer", Category.ITEM);
        this.addValues(this.delay, menuCheck, silence);
    }

    public static ChestStealer getInstance() {
        return (ChestStealer) ModManager.getModByName("ChestStealer");
    }

    @EventTarget
    private void onUpdate(EventPreUpdate e) {
        if (mc.player.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest)mc.player.openContainer;
            if (menuCheck.getValue() && !StatCollector.translateToLocal("container.chest").equalsIgnoreCase(container.getLowerChestInventory().getDisplayName().getUnformattedText())) {
                return;
            }
            for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
                if (container.getLowerChestInventory().getStackInSlot(i) == null || !this.timer.hasReached(this.delay.getValue().intValue())) continue;
                mc.playerController.windowClick(container.windowId, i, 0, 1, mc.player);
                this.timer.reset();
            }
            if (this.isEmpty()) {
                mc.player.closeScreen();
            }
        }
    }

    private boolean isEmpty() {
        if (mc.player.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest)mc.player.openContainer;
            for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
                ItemStack itemStack = container.getLowerChestInventory().getStackInSlot(i);
                if (itemStack == null || itemStack.getItem() == null) continue;
                return false;
            }
        }
        return true;
    }
}
