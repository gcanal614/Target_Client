package love.target.notification;

import love.target.utils.TimerUtil;

public class Notification {
    private final String title;
    private final String message;
    private final NotificationType type;
    private final long time;

    public final TimerUtil timerUtil = new TimerUtil();

    public Notification(String title, String message, NotificationType type, long time) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public NotificationType getType() {
        return type;
    }

    public long getTime() {
        return time;
    }

    public enum NotificationType {
        INFO,
        WARNING,
        ERROR,
        DEBUG
    }
}
