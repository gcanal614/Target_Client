package love.target.command.commands;

import love.target.Wrapper;
import love.target.command.Command;
import love.target.command.CommandManager;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "List Commands");
    }

    @Override
    public void run(String[] args) {
        Wrapper.sendMessageNoClientName("   §b§lTarget Commands");
        for (Command c : CommandManager.getCommands()) {
            if (!c.isShowHelp()) continue;
            Wrapper.sendMessageNoClientName("§b -" + c.getName() + " >§7 " + c.getHelp());
        }

        super.run(args);
    }
}
