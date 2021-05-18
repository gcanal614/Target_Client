package love.target.command.commands;

import love.target.Wrapper;
import love.target.command.Command;
import love.target.mod.Mod;
import love.target.mod.ModManager;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle", "Toggle Mod");
    }

    @Override
    public void run(String[] args) {
        if (args.length == 1) {
            String name = args[0];
            Mod mod = ModManager.getModByName(name);
            if (mod != null) {
                mod.toggle();
                Wrapper.sendMessage("Toggle Mod:" + mod.getName() + " To:" + mod.isEnabled());
            } else {
                Wrapper.sendMessage("No Found Mod");
            }
        } else {
            Wrapper.sendMessage("Try -toggle <module>");
        }
    }
}
