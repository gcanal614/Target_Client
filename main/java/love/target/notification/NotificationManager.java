package love.target.notification;

import love.target.render.font.FontManager;
import love.target.utils.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationManager {
    private static final List<Notification> notifications = new CopyOnWriteArrayList<>();

    public static void draw(ScaledResolution scaledResolution) {
        float notificationY = scaledResolution.getScaledHeight() - 60;
        for (Notification notification : notifications) {
            float secondFloat = (notification.timerUtil.getElapsedTime() / 1000.0f) - (notification.getTime() / 1000.0f);
            String second = (String.valueOf((String.format("%." + 1 + "f", secondFloat))));
            try {
                second = second.split("-")[1];
            } catch (Exception ignored) { }
            if (!notification.setY) {
                notification.animationY = notificationY;
                notification.setY = true;
            }
            if (notification.canRemove) {
                notification.animationX = RenderUtils.getAnimationState(notification.animationX,scaledResolution.getScaledWidth() + 22,50);
                if (notification.animationX == scaledResolution.getScaledWidth() + 22) {
                    notifications.remove(notification);
                }
            } else {
                notification.animationX = RenderUtils.getAnimationState(notification.animationX,scaledResolution.getScaledWidth() - FontManager.yaHei16.getStringWidth(notification.getMessage() + " (" + second + ")"),50);
            }
            if (notification.timerUtil.hasReached(notification.getTime())) {
                notification.canRemove = true;
                notification.timerUtil.reset();
            } else {
                notification.animationY = (float) RenderUtils.getAnimationState(notification.animationY,notificationY,50);
            }
            double timeX = (scaledResolution.getScaledWidth() - notification.animationX + 22) * ((double)(notification.getTime() - notification.timerUtil.getElapsedTime()) / (double)notification.getTime());
            RenderUtils.drawRect(notification.animationX - 22,notification.animationY,scaledResolution.getScaledWidth(),notification.animationY + 20,new Color(0,0,0,50).getRGB());
            int imageColor;
            String iconName;
            switch (notification.getType()) {
                case WARNING:
                    imageColor = new Color(255,215,100).getRGB();
                    iconName = "textures/notification_icons/warning.png";
                    break;
                case ERROR:
                    imageColor = new Color(255, 100,100).getRGB();
                    iconName = "textures/notification_icons/error.png";
                    break;
                case SUCCESS:
                    iconName = "textures/notification_icons/success.png";
                    imageColor = new Color(0, 255, 0).getRGB();
                    break;
                case DEBUG:
                    iconName = "textures/notification_icons/debug.png";
                    imageColor = new Color(0, 255, 0).getRGB();
                    break;
                case INFO:
                default:
                    iconName = "textures/notification_icons/info.png";
                    imageColor = -1;
                    break;
            }
            if (!notification.canRemove) {
                RenderUtils.drawRect(notification.animationX - 22, notification.animationY + 19, scaledResolution.getScaledWidth() - timeX, notification.animationY + 20, imageColor);
            }
            GlStateManager.color(1,1,1,1);
            RenderUtils.drawImage(new ResourceLocation(iconName), notification.animationX - 20, notification.animationY + 2,16,16,imageColor);
            GlStateManager.color(1,1,1,1);
            FontManager.yaHei18.drawStringWithShadow(notification.getTitle(),(float) notification.animationX,notification.animationY - 1,new Color(251,251,251).getRGB());
            FontManager.yaHei16.drawStringWithShadow(notification.getMessage() + " (" + second + ")",(float) notification.animationX,notification.animationY + 8,new Color(184,184,184).getRGB());
            notificationY -= 32;
        }
    }

    public static void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public static void addNotification(String title, String message, Notification.NotificationType type, long time) {
        notifications.add(new Notification(title, message, type, time));
    }
}
