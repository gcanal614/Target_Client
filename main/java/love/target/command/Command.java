package love.target.command;

import net.minecraft.client.Minecraft;

public class Command {
    protected Minecraft mc = Minecraft.getMinecraft();
    private final String name;
    private final String help;
    private final boolean showHelp;

    public Command(String name, String help) {
        this.name = name;
        this.help = help;
        this.showHelp = true;
    }

    public Command(String name, String help,boolean showHelp) {
        this.name = name;
        this.help = help;
        this.showHelp = showHelp;
    }

    public void run(String[] args) {

    }

    public String getName() {
        return this.name;
    }

    public String getHelp() {
        return this.help;
    }

    public boolean isShowHelp() {
        return this.showHelp;
    }
}
