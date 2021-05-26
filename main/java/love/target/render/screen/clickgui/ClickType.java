package love.target.render.screen.clickgui;

public enum ClickType {
    HOME("Home"),
    DESIGNER("Designer"),
    CONFIG("Config"),
    CLIENT_SETTING("Setting"),
    CONSOLE("Console");

    private final String name;

    ClickType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
