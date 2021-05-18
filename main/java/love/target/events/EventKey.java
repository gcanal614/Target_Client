package love.target.events;

import love.target.eventapi.events.Event;

public class EventKey implements Event {
    private final int keyCode;

    public EventKey(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
