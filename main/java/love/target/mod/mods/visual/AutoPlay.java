package love.target.mod.mods.visual;

import love.target.Wrapper;
import love.target.eventapi.EventTarget;
import love.target.events.Event2D;
import love.target.events.EventTest;
import love.target.events.EventTitle;
import love.target.mod.Mod;
import love.target.mod.ModManager;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.ModeValue;
import love.target.mod.value.values.NumberValue;
import love.target.mod.value.values.TextValue;
import love.target.notification.Notification;
import love.target.notification.NotificationManager;
import love.target.render.font.FontManager;
import love.target.utils.TimerUtil;
import love.target.utils.render.RenderUtils;

import java.awt.*;

public class AutoPlay extends Mod {
    private final ModeValue mode = new ModeValue("RenderMode","Normal",new String[]{"Normal","Notification"});
    private final NumberValue delay = new NumberValue("Delay",1,0,5,1);
    private final TextValue ggText = new TextValue("GG_Text","gg");
    private final TextValue command = new TextValue("Command","/play solo_insane");
    private final BooleanValue autoGG = new BooleanValue("AutoGG",true);
    private final BooleanValue autoDisable = new BooleanValue("AutoDisable",false);
    private final TimerUtil timerUtil = new TimerUtil();
    private boolean canDrawAutoPlay = false;
    private double autoPlayY = -30;

    public AutoPlay() {
        super("AutoPlay",Category.OTHER);
        addValues(mode,delay,ggText,command,autoGG,autoDisable);
    }

    @Override
    protected void onDisable() {
        canDrawAutoPlay = false;
        autoPlayY = -30;
        timerUtil.reset();
        super.onDisable();
    }

    @EventTarget
    public void onTitle(EventTitle e) {
        if (e.getMessage().startsWith("§r§6§l")) {
            timerUtil.reset();
            canDrawAutoPlay = true;
            if (mode.isCurrentValue("Notification")) {
                NotificationManager.addNotification("AutoPlay","将你带到下一场游戏在", Notification.NotificationType.INFO,delay.getValue().longValue() * 1000L);
            }
            if (autoDisable.getValue()) {
                Mod killAura = ModManager.getModByName("KillAura");
                Mod speed = ModManager.getModByName("Speed");
                Mod scaffold = ModManager.getModByName("Scaffold");

                if (killAura != null) {
                    if (killAura.isEnabled()) {
                        NotificationManager.addNotification("AutoPlay","Disable KillAura", Notification.NotificationType.WARNING,2000);
                    }
                }

                if (speed != null) {
                    if (speed.isEnabled()) {
                        NotificationManager.addNotification("AutoPlay","Disable Speed", Notification.NotificationType.WARNING,2000);
                    }
                }

                if (scaffold != null) {
                    if (scaffold.isEnabled()) {
                        NotificationManager.addNotification("AutoPlay","Disable Scaffold", Notification.NotificationType.WARNING,2000);
                    }
                }
            }
            if (autoGG.getValue()) {
                mc.player.sendChatMessage(ggText.getValue());
            }
        }
    }

    @EventTarget
    public void on2D(Event2D e) {
        if (canDrawAutoPlay) {
            if (autoPlayY < 20) {
                autoPlayY++;
            }
            if (timerUtil.hasReached(delay.getValue().longValue() * 1000L)) {
                if (command.getValue() != null && !command.getValue().isEmpty()) {
                    mc.player.sendChatMessage(command.getValue());
                }
                timerUtil.reset();
                canDrawAutoPlay = false;
            }
        } else {
            if (autoPlayY > -30) {
                autoPlayY--;
            }
        }

        if (mode.isCurrentValue("Normal")) {
            RenderUtils.drawRect(e.getScaledResolution().getScaledWidth() / 2.0f - 60, autoPlayY, e.getScaledResolution().getScaledWidth() / 2.0f + 60, autoPlayY + 30, new Color(0, 0, 0, 150).getRGB());
            FontManager.yaHei18.drawString("AutoPlay", e.getScaledResolution().getScaledWidth() / 2.0f - 57, (float) autoPlayY, new Color(0xFFB700).getRGB());
            FontManager.yaHei16.drawString("将在" + (String.valueOf(((long) (timerUtil.getElapsedTime() / 1000.0f)) - delay.getValue().longValue()).split("-")[1]) + "秒之后开始游戏", e.getScaledResolution().getScaledWidth() / 2.0f - 57, (float) autoPlayY + 18, -1);
        }
    }
}
