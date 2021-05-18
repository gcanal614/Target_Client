package love.target.mod.mods.visual;

import love.target.Wrapper;
import love.target.designer.Designer;
import love.target.designer.designers.SpeedListDesigner;
import love.target.eventapi.EventTarget;
import love.target.events.Event2D;
import love.target.events.EventTick;
import love.target.mod.Mod;
import love.target.render.screen.designer.GuiDesigner;

public class SpeedList extends Mod {
    public SpeedList() {
        super("SpeedList",Category.VISUAL);
    }

    @EventTarget
    public void onTick(EventTick e) {
        for (Designer designer : GuiDesigner.designers) {
            if (designer.getDesignerType() == Designer.DesignerType.SPEED_LIST) {
                SpeedListDesigner speedListDesigner = (SpeedListDesigner) designer;
                speedListDesigner.onTick();
                designer.draw();
            }
        }
    }

   @EventTarget
   public void on2D(Event2D e) {
       for (Designer designer : GuiDesigner.designers) {
           if (designer.getDesignerType() == Designer.DesignerType.SPEED_LIST) {
               designer.draw();
           }
       }
   }
}
