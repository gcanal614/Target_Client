package love.target.render.screen.designer;

import love.target.designer.Designer;
import love.target.other.rightclickmenu.RightClickMenu;
import love.target.render.screen.designer.menu.DesignerMenu;
import love.target.render.screen.designer.menu.menus.DesignerMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GuiDesigner extends GuiScreen {
    public static List<Designer> designers = new CopyOnWriteArrayList<>();
    public static Designer selectedDesigner;
    private static final DesignerMenu designerMenu = new DesignerMainMenu();
    public static RightClickMenu displayingMenu;
    private boolean rightClickDown = false;

    public static void addDesigner(Designer designer) {
        designers.add(designer);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        designerMenu.draw(mouseX, mouseY);
        for (Designer designer : designers) {
            if (designer.canDrag(mouseX, mouseY)) {
                selectedDesigner = designer;
            }
            designer.setFocusing(designer == selectedDesigner);
            designer.draw();
        }
        if (selectedDesigner != null) {
            if (selectedDesigner.canDrag(mouseX, mouseY)) {
                if (displayingMenu == null) {
                    selectedDesigner.doDrag(mouseX, mouseY);
                }
            } else {
                selectedDesigner.resetDrag();
            }
            if (selectedDesigner.canOpenMenu(mouseX, mouseY) && !this.rightClickDown) {
                displayingMenu = selectedDesigner.getRightClickMenu(mouseX, mouseY);
                if (displayingMenu != null) {
                    displayingMenu.onOpen();
                }
                this.rightClickDown = true;
            }
        }
        if (!Mouse.isButtonDown(1)) {
            this.rightClickDown = false;
        }
        if (displayingMenu != null) {
            displayingMenu.draw(mouseX, mouseY);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
