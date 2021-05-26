package love.target.mod;

import love.target.eventapi.EventManager;
import love.target.mod.value.Value;
import love.target.notification.Notification;
import love.target.notification.NotificationManager;
import love.target.other.ClientSettings;
import net.minecraft.client.Minecraft;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Mod {
    private boolean enabled;
    private int keyCode;

    private final String name;
    private final Category category;
    private final List<Value<?>> values = new CopyOnWriteArrayList<>();

    protected static Minecraft mc = Minecraft.getMinecraft();

    public double animation = 0.0f;

    public Mod(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    /**
     * 开启Mod
     */
    public void enableMod() {
        setEnabled(true);
    }

    /**
     * 关闭Mod
     */
    public void disableMod() {
        setEnabled(false);
    }

    /**
     * 切换Mod的enable
     * 如果enable == true 则setEnabled(false) 否则setEnabled(true)
     */
    public void toggle() {
        setEnabled(!enabled);
    }

    /**
     * 设置Mod的enable 并且在EventManager里register unregister 并且触发onEnable() onDisable()
     * @see EventManager
     */
    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) {
            return;
        }
        this.enabled = enabled;
        if (enabled) {
            EventManager.register(this);
            if (mc.world != null) {
                this.onEnable();
            }
            if (ClientSettings.MOD_ENABLE_NOTIFICATION_VALUE.getValue()) {
                NotificationManager.addNotification(getName(), "Enable " + getName(), Notification.NotificationType.SUCCESS, 1000);
            }
        } else {
            EventManager.unregister(this);
            if (mc.world != null) {
                this.onDisable();
            }
            if (ClientSettings.MOD_ENABLE_NOTIFICATION_VALUE.getValue()) {
                NotificationManager.addNotification(getName(), "Disable " + getName(), Notification.NotificationType.ERROR, 1000);
            }
        }
    }

    /**
     * 增加Values
     */
    public void addValues(Value<?>... values) {
        getValues().addAll(Arrays.asList(values));
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public Category getCategory() {
        return category;
    }

    public List<Value<?>> getValues() {
        return values;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    protected void onEnable() {}

    protected void onDisable() {}

    /**
     * 可供Mod选择的Categories
     */
    public enum Category {
        FIGHT("Fight"),
        VISUAL("Visual"),
        MOVE("Move"),
        PLAYER("Player"),
        ITEM("Item"),
        OTHER("Other"),
        WORLD("World");

        private final String name;

        Category(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
