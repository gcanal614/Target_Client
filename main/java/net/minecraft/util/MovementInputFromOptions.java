package net.minecraft.util;

import com.utils.ObjectUtils;
import love.target.mod.ModManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.input.Keyboard;

public class MovementInputFromOptions extends MovementInput
{
    private final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn)
    {
        this.gameSettings = gameSettingsIn;
    }

    public void updatePlayerMoveState()
    {

        if (ModManager.getModEnableByName("ScreenMove") && ObjectUtils.reverse(Minecraft.getMinecraft().currentScreen instanceof GuiChat) && Minecraft.getMinecraft().currentScreen != null) {
            this.moveStrafe = 0.0F;
            this.moveForward = 0.0F;

            if (Keyboard.isKeyDown(gameSettings.keyBindForward.getKeyCode())) {
                ++this.moveForward;
            }

            if (Keyboard.isKeyDown(gameSettings.keyBindBack.getKeyCode())) {
                --this.moveForward;
            }

            if (Keyboard.isKeyDown(gameSettings.keyBindLeft.getKeyCode())) {
                ++this.moveStrafe;
            }

            if (Keyboard.isKeyDown(gameSettings.keyBindRight.getKeyCode())) {
                --this.moveStrafe;
            }

            this.jump = Keyboard.isKeyDown(gameSettings.keyBindJump.getKeyCode());
            this.sneak = this.gameSettings.keyBindSneak.isKeyDown();

            if (this.sneak) {
                this.moveStrafe = (float) ((double) this.moveStrafe * 0.3D);
                this.moveForward = (float) ((double) this.moveForward * 0.3D);
            }
        } else {
            this.moveStrafe = 0.0F;
            this.moveForward = 0.0F;

            if (this.gameSettings.keyBindForward.isKeyDown()) {
                ++this.moveForward;
            }

            if (this.gameSettings.keyBindBack.isKeyDown()) {
                --this.moveForward;
            }

            if (this.gameSettings.keyBindLeft.isKeyDown()) {
                ++this.moveStrafe;
            }

            if (this.gameSettings.keyBindRight.isKeyDown()) {
                --this.moveStrafe;
            }

            this.jump = this.gameSettings.keyBindJump.isKeyDown();
            this.sneak = this.gameSettings.keyBindSneak.isKeyDown();

            if (this.sneak) {
                this.moveStrafe = (float) ((double) this.moveStrafe * 0.3D);
                this.moveForward = (float) ((double) this.moveForward * 0.3D);
            }
        }
    }
}
