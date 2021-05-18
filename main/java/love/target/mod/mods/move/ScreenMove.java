package love.target.mod.mods.move;

import com.utils.ObjectUtils;
import love.target.eventapi.EventTarget;
import love.target.events.EventTick;
import love.target.mod.Mod;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class ScreenMove extends Mod {
    private static final int[] keys = new int[]{
            mc.gameSettings.keyBindForward.getKeyCode(),
            mc.gameSettings.keyBindBack.getKeyCode(),
            mc.gameSettings.keyBindLeft.getKeyCode(),
            mc.gameSettings.keyBindRight.getKeyCode(),
            mc.gameSettings.keyBindJump.getKeyCode()
    };

    public ScreenMove() {
        super("ScreenMove",Category.MOVE);
    }

    @EventTarget
    public void onTick(EventTick e) {
        if (mc.currentScreen != null && ObjectUtils.reverse(mc.currentScreen instanceof GuiChat)) {
            for (int k : keys) {
                KeyBinding.setKeyBindState(k, Keyboard.isKeyDown(k));
            }
        }
    }
}
