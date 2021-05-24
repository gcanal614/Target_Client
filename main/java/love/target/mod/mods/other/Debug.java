package love.target.mod.mods.other;

import love.target.Wrapper;
import love.target.eventapi.EventTarget;
import love.target.events.Event2D;
import love.target.mod.Mod;
import love.target.render.font.FontManager;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
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
        debugInfos.add(new DebugInfo("MotionX",mc.player.motionX));
        debugInfos.add(new DebugInfo("MotionY",mc.player.motionY));
        debugInfos.add(new DebugInfo("MotionZ",mc.player.motionZ));
        debugInfos.add(new DebugInfo("PosX",mc.player.posX));
        debugInfos.add(new DebugInfo("PosY",mc.player.posY));
        debugInfos.add(new DebugInfo("PosZ",mc.player.posZ));
        debugInfos.add(new DebugInfo("BPS", Wrapper.speedBsDouble(mc.player)));
        debugInfos.add(new DebugInfo("Yaw", mc.player.rotationYaw));
        debugInfos.add(new DebugInfo("YawOffset", mc.player.renderYawOffset));
        debugInfos.add(new DebugInfo("YawHead", mc.player.rotationYawHead));
        debugInfos.add(new DebugInfo("Pitch", mc.player.rotationPitch));
        debugInfos.add(new DebugInfo("RenderPitch", mc.player.rotationPitchHead));
        debugInfos.add(new DebugInfo("FallDistance", mc.player.fallDistance));
        debugInfos.add(new DebugInfo("HurtResistantTime", mc.player.hurtResistantTime));
        debugInfos.add(new DebugInfo("HurtTime", mc.player.hurtTime));
        debugInfos.add(new DebugInfo("TimerSpeed", mc.timer.timerSpeed));
        debugInfos.add(new DebugInfo("RenderPosX", mc.getRenderManager().renderPosX));
        debugInfos.add(new DebugInfo("RenderPosY", mc.getRenderManager().renderPosY));
        debugInfos.add(new DebugInfo("RenderPosZ", mc.getRenderManager().renderPosZ));
        debugInfos.add(new DebugInfo("PlayerName", mc.session.getProfile().getName()));
        debugInfos.add(new DebugInfo("SKIP",null));
        debugInfos.add(new DebugInfo("InLiquid",mc.player.isInLiquid()));
        debugInfos.add(new DebugInfo("OnGround",mc.player.onGround));
        debugInfos.add(new DebugInfo("Sprinting", mc.player.isSprinting()));
        debugInfos.add(new DebugInfo("CollidedHorizontally", mc.player.isCollidedHorizontally));
        debugInfos.add(new DebugInfo("Sneaking", mc.player.isSneaking()));

        float textY = 115f;
        for (DebugInfo debugInfo : debugInfos) {
            if (!debugInfo.getName().equals("SKIP") && debugInfo.getValue() != null) {
                GlStateManager.pushMatrix();
                GlStateManager.scale(1.0F,0.9F,1.0F);
                FontManager.zpix16.drawStringWithShadow(debugInfo.getName() + " : " + debugInfo.getValue(),5,textY,-1);
                GlStateManager.popMatrix();
            }

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
