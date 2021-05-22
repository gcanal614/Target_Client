package love.target.mod.mods.visual;

import love.target.designer.Designer;
import love.target.eventapi.EventTarget;
import love.target.events.Event2D;
import love.target.mod.Mod;
import love.target.render.screen.designer.GuiDesigner;

public class InventoryHUD extends Mod {
    public InventoryHUD() {
        super("InventoryHUD", Category.VISUAL);
    }

    @EventTarget
    public void on2D(Event2D e) {
        for (Designer designer : GuiDesigner.designers) {
            if (designer.getDesignerType() != Designer.DesignerType.INVENTORY_HUD) continue;
            designer.draw();
        }
    }
}
