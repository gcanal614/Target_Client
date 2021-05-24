package love.target.designer.designers;

import love.target.Wrapper;
import love.target.designer.Designer;
import love.target.mod.Mod;
import love.target.mod.ModManager;
import love.target.other.rightclickmenu.NormalRightClickMenu;
import love.target.other.rightclickmenu.RightClickMenu;
import love.target.render.font.FontManager;
import love.target.utils.render.RenderUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.List;

public class TabGuiDesigner extends Designer {
    private Mod.Category selectedCategory = Mod.Category.FIGHT;
    private Mod selectedMod = ModManager.getModsByCategory(selectedCategory).get(0);
    private Status status = Status.CATEGORY;

    private float animationY;
    private float animationYForMod;

    public TabGuiDesigner() {
        this.x = 3;
        this.y = 20;
        this.animationY = 20;
        this.animationYForMod = 20;
        this.designerType = DesignerType.TAB_GUI;
    }

    public TabGuiDesigner(int x,int y) {
        this.x = x;
        this.y = y;
        this.animationY = y;
        this.animationYForMod = y;
        this.designerType = DesignerType.TAB_GUI;
    }

    public void onKeyPress(int key) {
        if (status == Status.CATEGORY) {
            if (key == 205) {
                status = Status.MOD;
            }
            if (selectedCategory.ordinal() + 1 < Mod.Category.values().length && key == 208) {
                selectedCategory = Mod.Category.values()[selectedCategory.ordinal() + 1];
            } else if (selectedCategory.ordinal() - 1 >= 0 && key == 200) {
                selectedCategory = Mod.Category.values()[selectedCategory.ordinal() - 1];
            }
            selectedMod = ModManager.getModsByCategory(selectedCategory).get(0);
            animationYForMod = animationY;
        } else if (status == Status.MOD) {
            if (key == 203) {
                status = Status.CATEGORY;
                return;
            }

            List<Mod> mods = ModManager.getModsByCategory(selectedCategory);
            if (key == 208 && (mods.indexOf(selectedMod) + 1) < mods.size()) {
                selectedMod = mods.get(mods.indexOf(selectedMod) + 1);
            } else if (key == 200 && (mods.indexOf(selectedMod) - 1) >= 0) {
                selectedMod = mods.get(mods.indexOf(selectedMod) - 1);
            }

            if (key == 28) {
                selectedMod.toggle();
            }
        }
    }

    @Override
    public void draw() {
        if (canDrawDesignerInfo()) {
            RenderUtils.drawBorderedRect(x - 3, y - 3, x + 53, y + (Mod.Category.values().length * 12) + 3, 1.0f, new Color(0, 0, 255).getRGB(), new Color(0, 0, 0, 0).getRGB());
            FontManager.yaHei16.drawString("TabGui X:" + x + " Y:" + y, x - 2, y - 13, -1);
        }
        RenderUtils.drawRect(x,y,x + 50,y + (Mod.Category.values().length * 12),new Color(0,0,0,50).getRGB());
        RenderUtils.drawRect(x,animationY,x + 50,animationY + 12,new Color(179, 0, 255).getRGB());
        float textY = y;
        float categoryY = y;
        for (Mod.Category category : Mod.Category.values()) {
            if (selectedCategory == category) {
                animationY = (float) RenderUtils.getAnimationState(animationY,textY,mc.timer.renderPartialTicks * 50);
                categoryY = textY;
            }
            FontManager.yaHei18.drawStringWithShadow(category.toString(),x + 3,textY,-1);

            textY+=12;
        }

        if (status == Status.MOD) {
            RenderUtils.drawRect(x + 52,categoryY,x + 135,categoryY + (ModManager.getModsByCategory(selectedCategory).size() * 12),new Color(0,0,0,50).getRGB());
            RenderUtils.drawRect(x + 52,animationYForMod,x + 135,animationYForMod + 12,new Color(179, 0, 255).getRGB());
            float modY = categoryY;
            for (Mod mod : ModManager.getModsByCategory(selectedCategory)) {
                if (selectedMod == mod) {
                    animationYForMod = (float) RenderUtils.getAnimationState(animationYForMod,modY,mc.timer.renderPartialTicks * 50);
                }
                FontManager.yaHei18.drawStringWithShadow(mod.getName(),x + 55,modY,mod.isEnabled() ? -1 : new Color(0x969696).getRGB());
                modY += 12;
            }
        }
    }

    @Override
    public boolean canDrag(int mouseX, int mouseY) {
        return Wrapper.isHovered(x,y,x + 50,y + (Mod.Category.values().length * 12),mouseX,mouseY) && Mouse.isButtonDown(0);
    }

    @Override
    public boolean canOpenMenu(int mouseX, int mouseY) {
        return Wrapper.isHovered(x,y,x + 50,y + (Mod.Category.values().length * 12),mouseX,mouseY) && Mouse.isButtonDown(1);
    }

    @Override
    public RightClickMenu getRightClickMenu(int mouseX, int mouseY) {
        return new NormalRightClickMenu(mouseX,mouseY);
    }

    enum Status {
        CATEGORY,
        MOD
    }
}
