package love.target.notification;

import love.target.Wrapper;
import love.target.render.font.FontManager;
import love.target.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationManager {
    private static final List<Notification> notifications = new CopyOnWriteArrayList<>();

    public static void draw(ScaledResolution scaledResolution, Minecraft mc) {
        float notificationY = scaledResolution.getScaledHeight() - 100;

        for (Notification notification : notifications) {
            //if (notification.timerUtil.hasReached(notification.getTime())) {
            //    notifications.remove(notification);
            //    continue;
            //}

            double timeX = ((float) notification.timerUtil.getElapsedTime() / (float) notification.getTime()) * 100;

            RenderUtils.drawRect(scaledResolution.getScaledWidth() - Math.max(FontManager.yaHei16.getStringWidth(notification.getMessage()),100) - 5,notificationY,scaledResolution.getScaledWidth(),notificationY + 25,new Color(0,0,0,50).getRGB());
            FontManager.yaHei18.drawString(notification.getTitle(),scaledResolution.getScaledWidth() - Math.max(FontManager.yaHei16.getStringWidth(notification.getMessage()),76),notificationY,-1);
            FontManager.yaHei16.drawString(notification.getMessage(),scaledResolution.getScaledWidth() - Math.max(FontManager.yaHei16.getStringWidth(notification.getMessage()),76),notificationY + 15,-1);
            notificationY -= 35;
        }
    }

    public static void addNotification(Notification notification) {
        notifications.add(notification);
    }
}
