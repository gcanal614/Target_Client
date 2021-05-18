package love.target.events;

import love.target.eventapi.events.callables.EventCancellable;

public class EventPreUpdate extends EventCancellable {
    private boolean onGround;
    private float yaw;
    private float pitch;
    private double y;

    public EventPreUpdate(boolean onGround, float yaw, float pitch, double y) {
        this.onGround = onGround;
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
