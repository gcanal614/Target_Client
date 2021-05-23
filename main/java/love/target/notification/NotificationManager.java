package love.target.notification;

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
        float notificationY = scaledResolution.getScaledHeight() - 200;

        for (Notification notification : notifications) {
            if (notification.timerUtil.hasReached(notification.getTime())) {
                notifications.remove(notification);
                continue;
            }

            double timeX = ((float) notification.timerUtil.getElapsedTime() / (float) notification.getTime()) * 100;

            RenderUtils.drawRect(scaledResolution.getScaledWidth(),notificationY,scaledResolution.getScaledWidth() - FontManager.yaHei16.getStringWidth(notification.getMessage()),notificationY + 30,new Color(0,0,0,50).getRGB());
        }
    }

    public static void addNotification(Notification notification) {
        notifications.add(notification);
    }
}
