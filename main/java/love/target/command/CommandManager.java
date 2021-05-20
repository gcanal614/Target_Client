package love.target.command;

import love.target.Wrapper;
import love.target.command.commands.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandManager {
    private static final List<Command> commands = new CopyOnWriteArrayList<>();

    public static void init() {
        registerCommands(new HelpCommand(),
                new BindCommand(),
                new ToggleCommand(),
                new ReportCommand(),
                new RegisterCommand(),
                new LoginCommand()
        );
    }

    public static void registerCommands(Command... commands) {
        getCommands().addAll(Arrays.asList(commands));
    }

    public static boolean onChat(String message) {
        if (message.length() > 1 && message.startsWith("-")) {
            block2: {
                String[] args = message.trim().substring(1).split(" ");
                for (Command c : CommandManager.getCommands()) {
                    if (!args[0].equalsIgnoreCase(c.getName())) continue;
                    c.run(Arrays.copyOfRange(args, 1, args.length));
                    break block2;
                }
                Wrapper.sendMessage(String.format("Command not found Try '%shelp'", "-"));
            }
            return true;
        }
        return false;
    }

    public static List<Command> getCommands() {
        return commands;
    }
}
