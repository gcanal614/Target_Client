package love.target.config;

public class Config {
    private final String name;
    private final String dir;

    public Config(String name) {
        this(name,false);
    }

    public Config(String name, boolean isDefault) {
        this.name = name;
        if (isDefault) {
            this.dir = "";
        } else {
            this.dir = "configs/start" + name + "/";
        }
    }

    public String getName() {
        return name;
    }

    public String getDir() {
        return dir;
    }
}
