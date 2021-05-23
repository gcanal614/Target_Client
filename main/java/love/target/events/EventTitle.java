package love.target.events;

import love.target.eventapi.events.Event;

public class EventTitle implements Event {
    private final String message;

    public EventTitle(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
