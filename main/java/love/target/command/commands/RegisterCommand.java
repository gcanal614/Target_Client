package love.target.command.commands;

import love.target.command.Command;

public class RegisterCommand extends Command {
    public RegisterCommand() {
        super("reg","Auto register");
    }

    @Override
    public void run(String[] args) {
        mc.player.sendChatMessage("/register f144577F f144577F");
        super.run(args);
    }
}
