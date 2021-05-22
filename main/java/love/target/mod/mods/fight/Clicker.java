package love.target.mod.mods.fight;

import love.target.Wrapper;
import love.target.eventapi.EventTarget;
import love.target.events.EventTick;
import love.target.mod.Mod;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.NumberValue;
import love.target.utils.TimerUtil;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Mouse;

public class Clicker extends Mod {
    private final NumberValue cpsValue = new NumberValue("CPS",8,1,20,1);
    private final BooleanValue blockHit = new BooleanValue("BlockHit",false);
    private final BooleanValue autoUnBlock = new BooleanValue("AutoUnblock",false);

    private final TimerUtil timerUtil = new TimerUtil();

    public Clicker() {
        super("Clicker",Category.FIGHT);
        addValues(cpsValue,blockHit,autoUnBlock);
    }

    @EventTarget
    public void onTick(EventTick e) {
        if (mc.currentScreen == null && Mouse.isButtonDown(0)) {
            if (!blockHit.getValue() && mc.player.isBlocking()) return;

            if (shouldAttack()) {
                boolean canBlock = false;
                if (autoUnBlock.getValue() && mc.player.isBlocking()) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                    mc.playerController.onStoppedUsingItem(mc.player);
                    mc.player.itemInUseCount = 0;
                    canBlock = true;
                }

                mc.leftClickCounter = 0;
                mc.clickMouse();

                if (canBlock && autoUnBlock.getValue() && !mc.player.isBlocking()) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    mc.playerController.sendUseItem(mc.player, mc.world, mc.player.inventory.getCurrentItem());
                }
            }
        }
    }

    private boolean shouldAttack() {
        int APS = 20 / cpsValue.getValue().intValue();
        if (timerUtil.hasReached(50 * APS)) {
            timerUtil.reset();
            return true;
        }
        return false;
    }
}
