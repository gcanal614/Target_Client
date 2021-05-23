package love.target.mod.mods.visual;

import love.target.eventapi.EventTarget;
import love.target.events.Event2D;
import love.target.events.EventTest;
import love.target.events.EventTitle;
import love.target.mod.Mod;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.NumberValue;
import love.target.mod.value.values.TextValue;
import love.target.render.font.FontManager;
import love.target.utils.TimerUtil;
import love.target.utils.render.RenderUtils;

import java.awt.*;

public class AutoPlay extends Mod {
    private final NumberValue delay = new NumberValue("Delay",1,0,5,1);
    private final TextValue ggText = new TextValue("GG_Text","gg");
    private final TextValue command = new TextValue("Command","/play solo_insane");
    private final BooleanValue autoGG = new BooleanValue("AutoGG",true);
    private final TimerUtil timerUtil = new TimerUtil();
    private boolean canDrawAutoPlay = false;
    private double autoPlayY = -30;

    public AutoPlay() {
        super("AutoPlay",Category.OTHER);
        addValues(delay,ggText,command,autoGG);
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
            if (autoGG.getValue()) {
                mc.player.sendChatMessage(ggText.getValue());
            }
        }
    }

    @EventTarget
    public void on2D(Event2D e) {
        boolean needReset = false;
        if (canDrawAutoPlay) {
            if (autoPlayY < 20) {
                autoPlayY++;
            }
            if (timerUtil.hasReached(delay.getValue().longValue() * 1000L)) {
                mc.player.sendChatMessage(command.getValue());
                timerUtil.reset();
                canDrawAutoPlay = false;
            }
        } else {
            if (autoPlayY > -30) {
                autoPlayY--;
            }
        }

        RenderUtils.drawRect(e.getScaledResolution().getScaledWidth() / 2.0f - 60,autoPlayY,e.getScaledResolution().getScaledWidth() / 2.0f + 60,autoPlayY + 30,new Color(0,0,0,150).getRGB());
        FontManager.yaHei18.drawString("AutoPlay",e.getScaledResolution().getScaledWidth() / 2.0f - 57,(float) autoPlayY,new Color(0xFFB700).getRGB());
        FontManager.yaHei16.drawString("将在" + (String.valueOf(((long) (timerUtil.getElapsedTime() / 1000.0f)) - delay.getValue().longValue()).split("-")[1]) + "秒之后开始游戏",e.getScaledResolution().getScaledWidth() / 2.0f - 57,(float) autoPlayY + 18,-1);
    }

    @EventTarget
    public void onTest(EventTest e) {
        timerUtil.reset();
        canDrawAutoPlay = true;
    }
}
