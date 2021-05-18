package love.target.events;

import love.target.eventapi.events.callables.EventCancellable;

public class EventChatPrint extends EventCancellable {
    private final String message;
    private final int chatLineId;

    public EventChatPrint(String message, int chatLineId) {
        this.message = message;
        this.chatLineId = chatLineId;
    }

    public String getMessage() {
        return this.message;
    }

    public int getChatLineId() {
        return chatLineId;
    }
}
