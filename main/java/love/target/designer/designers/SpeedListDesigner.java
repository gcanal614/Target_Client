package love.target.designer.designers;

import love.target.Wrapper;
import love.target.designer.Designer;
import love.target.other.rightclickmenu.NormalRightClickMenu;
import love.target.other.rightclickmenu.RightClickMenu;
import love.target.render.font.FontManager;
import love.target.utils.render.RenderUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SpeedListDesigner extends Designer {
    private final List<Block> blocks = new CopyOnWriteArrayList<>();

    public SpeedListDesigner() {
        this.x = 2;
        this.y = 100;
        this.designerType = DesignerType.SPEED_LIST;
    }

    public SpeedListDesigner(int x,int y) {
        this.x = x;
        this.y = y;
        this.designerType = DesignerType.SPEED_LIST;
    }

    public void onTick() {
        for (Block block : blocks) {
            block.x--;
        }

        addBlock(new SpeedListDesigner.Block(getX() + 189, Wrapper.speedBsInteger(mc.player) * 2));
    }

    @Override
    public void draw() {
        if (canDrawDesignerInfo()) {
            RenderUtils.drawBorderedRect(x - 3,y + 3,x + 193,y - 83,1.0f, new Color(0, 0, 255).getRGB(), new Color(0, 0, 0, 0).getRGB());
            FontManager.yaHei16.drawString("SpeedList X:" + this.x + " Y:" + this.y, this.x - 3, this.y - 96, -1);
        }
        RenderUtils.drawRect(x,y,x + 190,y - 80,new Color(0,0,0,80).getRGB());

        RenderUtils.startGlScissor(x,y - 83,193,83);
        for (Block block : blocks) {
            RenderUtils.drawRect(block.x,y - block.height,block.x + 1,y - block.height - 1,new Color(0, 255, 0,150).getRGB());

            try {
                Block lastBlock = blocks.get(blocks.indexOf(block) - 1);
                RenderUtils.drawRect(lastBlock.x,y - lastBlock.height,block.x + 1,y - block.height - 1,new Color(0, 255, 0,150).getRGB());
            } catch (Exception ignored) {}

            if (block.x < 0) {
                blocks.remove(block);
            }
        }
        RenderUtils.stopGlScissor();
        if (!blocks.isEmpty()) {
            RenderUtils.drawRect(x,y - blocks.get(blocks.size() - 1).height - 1,x + 190,y - blocks.get(blocks.size() - 1).height - 2,new Color(255,0,0).getRGB());
        }
    }

    @Override
    public boolean canDrag(int mouseX, int mouseY) {
        return Wrapper.isHovered(x,y - 80,x + 190,y,mouseX,mouseY) && Mouse.isButtonDown(0);
    }

    @Override
    public boolean canOpenMenu(int mouseX, int mouseY) {
        return Wrapper.isHovered(x,y - 80,x + 190,y,mouseX,mouseY) && Mouse.isButtonDown(1);
    }

    @Override
    public RightClickMenu getRightClickMenu(int mouseX, int mouseY) {
        return new NormalRightClickMenu(mouseX,mouseY);
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    @Override
    protected void dragging() {
        blocks.clear();
        super.dragging();
    }

    public static class Block {
        public int x;
        public final int height;

        public Block(int x, int height) {
            this.x = x;
            this.height = height;
        }
    }
}
