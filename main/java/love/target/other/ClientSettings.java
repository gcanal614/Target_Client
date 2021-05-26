package love.target.other;

import love.target.Wrapper;
import love.target.mod.value.values.BooleanValue;

public class ClientSettings {
    public static final BooleanValue MOD_ENABLE_NOTIFICATION_VALUE = new BooleanValue("ModEnableNotification",false);

    public static BooleanValue getValueByName(String name) {
        for (BooleanValue booleanValue : Wrapper.getClientSettingList()) {
            if (booleanValue.getName().equalsIgnoreCase(name)) return booleanValue;
        }

        return null;
    }
}
