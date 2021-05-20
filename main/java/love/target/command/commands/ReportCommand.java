package love.target.command.commands;

import love.target.Wrapper;
import love.target.command.Command;

public class ReportCommand extends Command {
    public ReportCommand() {
        super("report","Report a player");
    }

    @Override
    public void run(String[] args) {
        if (args.length == 1) {
            mc.player.sendChatMessage("/report " + args[0] + " KillAura Speed Velocity Jesus Fly NoSlowDown AntiVoid NoFall Scaffold WallClimb Timer AutoArmor AutoPot Critical Teleport");
        } else {
            Wrapper.sendMessage("Try -report <player name>");
        }
        super.run(args);
    }
}
