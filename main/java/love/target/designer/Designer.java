package love.target.designer;

import love.target.other.rightclickmenu.RightClickMenu;
import love.target.render.screen.designer.GuiDesigner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public abstract class Designer {
    protected int x = 2;
    protected int y = 2;
    protected int dragX;
    protected int dragY;
    protected boolean focusing;
    protected DesignerType designerType = DesignerType.NULL;
    protected Minecraft mc = Minecraft.getMinecraft();
    protected ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

    public abstract void draw();

    public abstract boolean canDrag(int mouseX, int mouseY);

    public abstract boolean canOpenMenu(int mouseX, int mouseY);

    public abstract RightClickMenu getRightClickMenu(int mouseX, int mouseY);

    public void doDrag(int mouseX, int mouseY) {
        if (this.dragX == 0 && this.dragY == 0) {
            this.dragX = mouseX - this.x;
            this.dragY = mouseY - this.y;
        } else {
            this.x = mouseX - this.dragX;
            this.y = mouseY - this.dragY;
        }
        this.dragging();
    }

    public void resetDrag() {
        if (this.dragX != 0 || this.dragY != 0) {
            this.dragX = 0;
            this.dragY = 0;
        }
    }

    protected void dragging() { }

    public boolean isFocusing() {
        return this.focusing;
    }

    public void setFocusing(boolean focusing) {
        this.focusing = focusing;
    }

    public DesignerType getDesignerType() {
        return this.designerType;
    }

    protected boolean canDrawDesignerInfo() {
        if (GuiDesigner.selectedDesigner != null) {
            return this.focusing && GuiDesigner.selectedDesigner == this && this.mc.currentScreen instanceof GuiDesigner;
        }
        return false;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public enum DesignerType {
        LOGO,
        ARRAY_LIST,
        PLAYER_LIST,
        SPEED_LIST,
        NULL;

        public static DesignerType toDesignerTypeByString(String s) {
            for (DesignerType designerType : DesignerType.values()) {
                if (!designerType.name().equalsIgnoreCase(s)) continue;
                return designerType;
            }
            return NULL;
        }
    }
}
