package love.target.command.commands;

import love.target.command.Command;
import love.target.render.screen.clickgui.ClickGui;

public class ClickGuiCommand extends Command {
    public ClickGuiCommand() {
        super("clickgui","Open ClickGui");
    }

    @Override
    public void run(String[] args) {
        super.run(args);
        new Thread(() -> {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mc.displayGuiScreen(new ClickGui());
        },"Open ClickGui Thread").start();
    }
}
