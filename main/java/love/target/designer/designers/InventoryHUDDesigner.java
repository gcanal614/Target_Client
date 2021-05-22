package love.target.designer.designers;

import love.target.Wrapper;
import love.target.designer.Designer;
import love.target.other.rightclickmenu.NormalRightClickMenu;
import love.target.other.rightclickmenu.RightClickMenu;
import love.target.render.font.FontManager;
import love.target.render.screen.designer.GuiDesigner;
import love.target.utils.render.RenderUtils;
import net.minecraft.item.ItemStack;
import netscape.security.UserTarget;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class InventoryHUDDesigner extends Designer {
    public InventoryHUDDesigner() {
        this.x = 2;
        this.y = 190;
        this.designerType = DesignerType.INVENTORY_HUD;
    }

    public InventoryHUDDesigner(int x, int y) {
        this.x = x;
        this.y = y;
        this.designerType = DesignerType.INVENTORY_HUD;
    }

    @Override
    public void draw() {
        if (this.canDrawDesignerInfo()) {
            RenderUtils.drawBorderedRect(this.x - 3, this.y - 3, this.x + 166, this.y + 68, 1.0f, new Color(0, 0, 255).getRGB(), new Color(0, 0, 0, 0).getRGB());
            FontManager.yaHei16.drawString("InventoryHUD X:" + this.x + " Y:" + this.y, this.x - 2, this.y - 13, -1);
        }
        RenderUtils.drawRect(this.x, this.y, this.x + 163, this.y + 10, new Color(20, 19, 18).getRGB());
        RenderUtils.drawRect(this.x, this.y + 10, this.x + 163, this.y + 65, new Color(23, 25, 24).getRGB());
        FontManager.yaHei16.drawString("Inventory List", this.x + 3, this.y - 1, new Color(0x555555).getRGB());
        if (this.mc.currentScreen instanceof GuiDesigner || this.mc.player == null) {
            FontManager.yaHei16.drawCenteredString("Your inventory is empty...", (float)this.x + 78.5f, (float)this.y + 28.5f, new Color(80, 80, 80).getRGB());
        } else {
            int itemX = this.x + 1;
            int itemY = this.y + 11;
            int airs = 0;
            for (int i = 0; i < this.mc.player.inventory.mainInventory.length; i++) {
                if (i < 9) continue;
                ItemStack stack = this.mc.player.inventory.mainInventory[i];
                if (stack == null) {
                    airs++;
                }
                RenderUtils.enableGUIStandardItemLighting();
                this.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, itemX, itemY);
                this.mc.getRenderItem().renderItemOverlays(this.mc.fontRenderer, stack, itemX, itemY);
                RenderUtils.disableGUIStandardItemLighting();
                if (itemX < x + 1 + 144) {
                    itemX += 18;
                } else {
                    itemX = this.x + 1;
                    itemY += 18;
                }
            }

            if (airs == 27) {
                FontManager.yaHei16.drawCenteredString("Your inventory is empty...", (float)this.x + 78.5f, (float)this.y + 28.5f, new Color(80, 80, 80).getRGB());
            }
        }
    }

    @Override
    public boolean canDrag(int mouseX, int mouseY) {
        return Wrapper.isHovered(this.x, this.y, this.x + 163, this.y + 65, mouseX, mouseY) && Mouse.isButtonDown(0);
    }

    @Override
    public boolean canOpenMenu(int mouseX, int mouseY) {
        return Wrapper.isHovered(this.x, this.y, this.x + 163, this.y + 65, mouseX, mouseY) && Mouse.isButtonDown(1);
    }

    @Override
    public RightClickMenu getRightClickMenu(int mouseX, int mouseY) {
        return new NormalRightClickMenu(mouseX, mouseY);
    }
}
