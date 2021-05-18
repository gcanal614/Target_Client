package love.target.command;

public class Command {
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
