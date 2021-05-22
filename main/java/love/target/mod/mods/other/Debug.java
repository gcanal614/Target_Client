package love.target.mod.mods.other;

import love.target.eventapi.EventTarget;
import love.target.events.Event2D;
import love.target.mod.Mod;
import love.target.render.font.FontManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Debug extends Mod {
    public Debug() {
        super("Debug",Category.OTHER);
    }

    @EventTarget
    public void on2D(Event2D e) {
        List<DebugInfo> debugInfos = new CopyOnWriteArrayList<>();
        debugInfos.add(new DebugInfo("LeftClickCounter",mc.leftClickCounter));
        debugInfos.add(new DebugInfo("RightClickDelayTimer",mc.rightClickDelayTimer));
        float textY = 150f;
        for (DebugInfo debugInfo : debugInfos) {
            FontManager.yaHei16.drawStringWithShadow(debugInfo.getName() + " : " + debugInfo.getValue(),2,textY,-1);

            textY += 12f;
        }
    }

    private static class DebugInfo {
        private final String name;
        private final Object value;

        public DebugInfo(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }
    }
}
