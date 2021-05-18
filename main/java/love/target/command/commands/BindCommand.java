package love.target.command.commands;

import love.target.Wrapper;
import love.target.command.Command;
import love.target.mod.Mod;
import love.target.mod.ModManager;
import org.lwjgl.input.Keyboard;

public class BindCommand extends Command {
    public BindCommand() {
        super("bind", "Bind mod key");
    }

    @Override
    public void run(String[] args) {
        if (args.length == 2) {
            String name = args[0];
            int key = Keyboard.getKeyIndex(args[1].toUpperCase());
            Mod mod = ModManager.getModByName(name);
            if (mod != null) {
                if (key == 54) {
                    Wrapper.sendMessage("Cannot be RSHIFT");
                } else {
                    mod.setKeyCode(key);
                    Wrapper.sendMessage("Bind Mod:" + mod.getName() + " To:" + args[1]);
                }
            } else {
                Wrapper.sendMessage("No Found Mod");
            }
        } else {
            Wrapper.sendMessage("try -bind <module> <key>");
        }
        super.run(args);
    }
}
