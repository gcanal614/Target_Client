package love.target.command.commands;

import love.target.command.Command;

public class LoginCommand extends Command {
    public LoginCommand() {
        super("login","Auto login");
    }

    @Override
    public void run(String[] args) {
        mc.player.sendChatMessage("/login f144.577F..");
        super.run(args);
    }
}
