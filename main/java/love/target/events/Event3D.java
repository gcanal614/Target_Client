package love.target.events;

import love.target.eventapi.events.Event;

public class Event3D implements Event {
    private float ticks;

    public Event3D(float ticks) {
        this.ticks = ticks;
    }

    public float getTicks() {
        return this.ticks;
    }
}
