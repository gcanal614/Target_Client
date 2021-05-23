package love.target.command.commands;

import love.target.command.Command;
import love.target.render.screen.clickgui.ClickGui;

public class ClickGuiCommand extends Command {
    public ClickGuiCommand() {
        super("clickgui","Open ClickGui");
    }

    @Override
    public void run(String[] args) {
        mc.displayGuiScreen(new ClickGui());
        super.run(args);
    }
}
