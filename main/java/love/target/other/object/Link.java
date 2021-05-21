package love.target.other.object;

import net.minecraft.util.ResourceLocation;

public class Link {
    private final ResourceLocation icon;
    private final String name;
    private final String url;

    public Link(ResourceLocation icon,String name, String url) {
        this.icon = icon;
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public ResourceLocation getIcon() {
        return icon;
    }
}
