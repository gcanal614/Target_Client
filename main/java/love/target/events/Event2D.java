package love.target.events;

import love.target.eventapi.events.Event;
import net.minecraft.client.gui.ScaledResolution;

public class Event2D implements Event {
    private final ScaledResolution scaledResolution;
    private final float partialTicks;

    public Event2D(ScaledResolution scaledResolution, float partialTicks) {
        this.scaledResolution = scaledResolution;
        this.partialTicks = partialTicks;
    }

    public ScaledResolution getScaledResolution() {
        return this.scaledResolution;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}
