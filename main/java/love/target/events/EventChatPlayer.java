package love.target.events;

import love.target.eventapi.events.callables.EventCancellable;

public class EventChatPlayer extends EventCancellable {
    private String message;

    public EventChatPlayer(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
