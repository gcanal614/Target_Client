package love.target.designer.designers;

import com.mojang.realmsclient.gui.ChatFormatting;
import love.target.Wrapper;
import love.target.designer.Designer;
import love.target.other.rightclickmenu.NormalRightClickMenu;
import love.target.other.rightclickmenu.RightClickMenu;
import love.target.other.object.PlayerListObject;
import love.target.render.font.FontManager;
import love.target.render.screen.designer.GuiDesigner;
import love.target.utils.render.RenderUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerListDesigner extends Designer {
    private static final String[] killMessage = new String[]{"was killed by ","was thrown into the void by ","was thrown off a cliff by ","was deleted by ","was purified by "};
    private List<PlayerListObject> players = new CopyOnWriteArrayList<>();
    private PlayerListObject no1Player;

    public PlayerListDesigner() {
        this.x = 2;
        this.y = 100;
        this.designerType = Designer.DesignerType.PLAYER_LIST;
    }

    public PlayerListDesigner(int x, int y) {
        this.x = x;
        this.y = y;
        this.designerType = Designer.DesignerType.PLAYER_LIST;
    }

    public void clearPlayers() {
        this.players.clear();
        this.no1Player = null;
    }

    public void onChat(String chatMessage) {
        block14:
        {
            if (this.mc.player == null) {
                return;
            }
            String playerName = null;
            try {
                for (String s : killMessage) {
                    if (chatMessage.contains(s)) {
                        playerName = chatMessage.split(s)[1];
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (playerName == null || playerName.isEmpty()) {
                return;
            }

            if (!this.players.isEmpty()) {
                for (PlayerListObject player : this.players) {
                    if (!player.name.equals(playerName)) continue;
                    ++player.kills;
                    break block14;
                }
            }
            this.players.add(new PlayerListObject(playerName, 1));
        }
    }

    @Override
    public void draw() {
        if (this.canDrawDesignerInfo()) {
            RenderUtils.drawBorderedRect(this.x - 3, this.y - 3, this.x + FontManager.yaHei16.getStringWidth("PlayerList") + 83, this.y + this.mc.fontRenderer.FONT_HEIGHT + 6, 1.0f, new Color(0, 0, 255).getRGB(), new Color(0, 0, 0, 0).getRGB());
            FontManager.yaHei16.drawString("PlayerList X:" + this.x + " Y:" + this.y, this.x - 3, this.y - 13, -1);
        }
        if (this.mc.player.isDead) {
            this.players.clear();
            this.no1Player = null;
        }
        float textY = this.y;
        RenderUtils.drawRect(this.x, this.y, this.x + FontManager.yaHei16.getStringWidth("PlayerList") + 80, this.y + this.mc.fontRenderer.FONT_HEIGHT + 3, new Color(21, 19, 23).getRGB());
        FontManager.yaHei18.drawString("PlayerList", this.x + 3, this.y - 1, new Color(255, 255, 255).getRGB());
        if (this.mc.player == null || this.mc.currentScreen instanceof GuiDesigner) {
            return;
        }
        this.players.sort((o1, o2) -> o2.kills - o1.kills);
        for (PlayerListObject player : this.players) {
            if (player == this.players.get(0)) {
                this.no1Player = player;
            }
            RenderUtils.drawRect(this.x, textY + (float)this.mc.fontRenderer.FONT_HEIGHT + 3.0f, this.x + FontManager.yaHei16.getStringWidth("PlayerList") + 80, textY + (float)this.mc.fontRenderer.FONT_HEIGHT + 13.0f, new Color(30, 30, 35, 240).getRGB());
            if (player == this.no1Player) {
                FontManager.yaHei18.drawString(ChatFormatting.YELLOW + "★", this.x + 2, this.mc.fontRenderer.FONT_HEIGHT + 1.5f + (int) textY, -1);
            }
            FontManager.yaHei16.drawString(player.name, (float)(this.x + (player == this.no1Player ? 12 : 3)), (float)this.mc.fontRenderer.FONT_HEIGHT + 2f + textY, -1);
            String killString;
            switch (player.kills) {
                case 0:
                case 1:
                case -1:
                    killString = "kill";
                    break;
                default:
                    killString = "kills";
                    break;
            }
            FontManager.yaHei16.drawString(player.kills + " " + killString, (float)(this.x + FontManager.yaHei16.getStringWidth("PlayerList") + 83 - FontManager.yaHei18.getStringWidth(player.kills + killString) - 2 - (killString.equalsIgnoreCase("kill") ? 3 : 1)), (float)this.mc.fontRenderer.FONT_HEIGHT + 2.5f + textY, -1);
            textY += 10.0f;
        }
    }

    @Override
    public boolean canDrag(int mouseX, int mouseY) {
        return Wrapper.isHovered(this.x, this.y, this.x + FontManager.yaHei16.getStringWidth("PlayerList") + 80, this.y + this.mc.fontRenderer.FONT_HEIGHT + 3, mouseX, mouseY) && Mouse.isButtonDown(0);
    }

    @Override
    public boolean canOpenMenu(int mouseX, int mouseY) {
        return Wrapper.isHovered(this.x, this.y, this.x + FontManager.yaHei16.getStringWidth("PlayerList") + 80, this.y + this.mc.fontRenderer.FONT_HEIGHT + 3, mouseX, mouseY) && Mouse.isButtonDown(1);
    }

    @Override
    public RightClickMenu getRightClickMenu(int mouseX, int mouseY) {
        return new NormalRightClickMenu(mouseX, mouseY);
    }
}
