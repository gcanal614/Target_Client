package love.target.designer.designers;

import love.target.designer.Designer;
import love.target.other.rightclickmenu.NormalRightClickMenu;
import love.target.other.rightclickmenu.RightClickMenu;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class KeyboardDesigner extends Designer {
    private static final Key[] KEYS = new Key[]{
            new Key(KeyType.KEYBOARD, Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(),Keyboard.getKeyName(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode())),
            new Key(KeyType.KEYBOARD, Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode(),Keyboard.getKeyName(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode())),
            new Key(KeyType.KEYBOARD, Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(),Keyboard.getKeyName(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode())),
            new Key(KeyType.KEYBOARD, Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(),Keyboard.getKeyName(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode())),
            new Key(KeyType.KEYBOARD, Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(),Keyboard.getKeyName(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode())),

            new Key(KeyType.MOUSE,Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(),Keyboard.getKeyName(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode())),
            new Key(KeyType.MOUSE,Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(),Keyboard.getKeyName(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode()))
    };

    @Override
    public void draw() {

        for (Key key : KEYS) {
            if (key.getType() == KeyType.KEYBOARD) {
                if (key.getKeyCode() == Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode()) {

                }
            } else if (key.getType() == KeyType.MOUSE) {

            }
        }
    }

    @Override
    public boolean canDrag(int mouseX, int mouseY) {
        return false;
    }

    @Override
    public boolean canOpenMenu(int mouseX, int mouseY) {
        return false;
    }

    @Override
    public RightClickMenu getRightClickMenu(int mouseX, int mouseY) {
        return new NormalRightClickMenu(mouseX,mouseY);
    }

    private static class Key {
        private final KeyType type;
        private final int keyCode;
        private final String name;

        public Key(KeyType type, int keyCode, String name) {
            this.type = type;
            this.keyCode = keyCode;
            this.name = name;
        }

        public KeyType getType() {
            return type;
        }

        public int getKeyCode() {
            return keyCode;
        }

        public String getName() {
            return name;
        }
    }

    private enum KeyType {
        KEYBOARD,
        MOUSE
    }
}
