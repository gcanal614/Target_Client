package love.target.mod.mods.visual;

import love.target.designer.Designer;
import love.target.designer.designers.PlayerListDesigner;
import love.target.eventapi.EventTarget;
import love.target.events.Event2D;
import love.target.events.EventChatPrint;
import love.target.mod.Mod;
import love.target.render.screen.designer.GuiDesigner;

public class PlayerList extends Mod {
    public PlayerList() {
        super("PlayerList",Category.VISUAL);
    }

    @EventTarget
    public void on2D(Event2D e) {
        for (Designer designer : GuiDesigner.designers) {
            if (designer.getDesignerType() == Designer.DesignerType.PLAYER_LIST) {
                designer.draw();
            }
        }
    }

    @EventTarget
    public void onChat(EventChatPrint e) {
        for (Designer designer : GuiDesigner.designers) {
            if (designer.getDesignerType() == Designer.DesignerType.PLAYER_LIST) {
                ((PlayerListDesigner) designer).onChat(e.getMessage());
            }
        }
    }

    @Override
    protected void onDisable() {
        for (Designer designer : GuiDesigner.designers) {
            if (designer.getDesignerType() == Designer.DesignerType.PLAYER_LIST) {
                ((PlayerListDesigner) designer).clearPlayers();
            }
        }
        super.onDisable();
    }
}
