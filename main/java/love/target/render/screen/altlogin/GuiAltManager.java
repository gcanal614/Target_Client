package love.target.render.screen.altlogin;

import com.utils.file.FileUtils;
import love.target.Wrapper;
import love.target.other.rightclickmenu.AltManagerRightClickMenu;
import love.target.other.rightclickmenu.RightClickMenu;
import love.target.render.font.FontManager;
import love.target.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Mouse;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GuiAltManager extends GuiScreen {
    private static final List<Alt> alts = new CopyOnWriteArrayList<>();
    private final GuiScreen screen;
    private float wheel;
    private static float listHeight;
    private static float selectedAltY;
    public static Alt selectedAlt;
    public static AltLoginThread altLoginThread;
    private boolean leftMouseDown = false;
    private boolean rightMouseDown = false;
    private String status = "§eWaiting...";
    public static RightClickMenu displayingMenu;

    public GuiAltManager(GuiScreen screen) {
        this.screen = screen;
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0,this.width / 2 + 4 + 76, this.height - 48, 75, 20,"Add alt"));
        this.buttonList.add(new GuiButton(1,this.width / 2 - 74, this.height - 48, 70, 20,"Login alt"));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 4 + 76, this.height - 24, 75, 20, "Back"));
        this.buttonList.add(new GuiButton(3, 20, this.height - 24, 75, 20, "Import"));
        displayingMenu = null;
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new GuiAltLogin(this, AltEnum.ADD));
                break;
            case 1:
                mc.displayGuiScreen(new GuiAltLogin(this, AltEnum.LOGIN));
                break;
            case 2:
                mc.displayGuiScreen(screen);
                break;
            case 3:
                new Thread(() -> {
                    try {
                        for (String s : FileUtils.readLine(new FileInputStream(FileUtils.browseFile(JFileChooser.FILES_ONLY, "请选择一个文件")))) {
                            String a = s.split(":")[0];
                            String p = s.split(":")[1];
                            String u = s.split(":")[2];

                            getAlts().add(new Alt(a, p, u));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },"Import alts Thread").start();
                System.gc();
                break;
        }
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        if (Mouse.hasWheel()) {
            int cacheWheel = Mouse.getDWheel();
            if (cacheWheel < 0) {
                if (listHeight < alts.size() * 46 - 460) {
                    this.wheel = 46;
                }
                if (listHeight < 0) {
                    listHeight = 0;
                }
            } else if (cacheWheel > 0) {
                this.wheel = -46;
                if (listHeight < 0) {
                    listHeight = 0;
                }
            }

            if(cacheWheel != 0) {
                displayingMenu = null;
            }
        }

        if (this.wheel < 0) {
            if (listHeight > 0) {
                --listHeight;
            }
            ++this.wheel;
        } else if (this.wheel > 0) {
            if (listHeight < alts.size() * 46 - 480) {
                ++listHeight;
            }
            --this.wheel;
        }

        RenderUtils.drawRect(20,2,150,48,new Color(0,0,0,150).getRGB());
        mc.session.loadHead();
        RenderUtils.drawImage(mc.session.getHead(),22,4,42,42,-1);
        FontManager.yaHei20.drawString(mc.session.getProfile().getName(),68,33 - FontManager.yaHei20.FONT_HEIGHT,-1);
        FontManager.yaHei20.drawString(status,160,32 - FontManager.yaHei20.FONT_HEIGHT,-1);
        FontManager.yaHei16.drawString(alts.size() + "alts",21,height - 50,new Color(0x5494747, true).getRGB());
        RenderUtils.drawRect(20,50,width - 20,height - 50,new Color(0,0,0,150).getRGB());
        if (alts.size() >= 11) {
            RenderUtils.drawRect(width - 24, listHeight + 51, width - 22, height - 51 + listHeight - alts.size() * 46 + 480, new Color(50, 50, 50).getRGB());
        }
        RenderUtils.startGlScissor(20,52,width - 20,height - 104);
        if (selectedAlt != null) {
            RenderUtils.drawBorderedRect(22,selectedAltY + 2,width - 26,selectedAltY + 34,1,-1,new Color(0,0,0,0).getRGB());
        }
        float altY = 50 - listHeight;
        for (Alt alt : alts) {
            alt.loadHead();
            RenderUtils.drawImage(alt.getHead(),23,altY + 3,30,30,-1);
            FontManager.yaHei18.drawString("账号: " + EnumChatFormatting.GRAY + alt.getAccount(),60,altY + 2,-1);
            FontManager.yaHei18.drawString("密码: " + (alt.getPassword().equals("NULL_PASSWORD") ? EnumChatFormatting.RED + "离线账户" : EnumChatFormatting.GRAY + alt.getPassword().replaceAll(".","*")),60,altY + 12,-1);
            FontManager.yaHei18.drawString("游戏名: " + EnumChatFormatting.GRAY + alt.getUserName(),60,altY + 22,-1);
            if (Wrapper.isHovered(22,altY + 2,width - 22,altY + 34,mouseX,mouseY)) {
                boolean isIn = Wrapper.isHovered(20,50,width - 20,height - 50,mouseX,mouseY);

                if (isIn) {
                    RenderUtils.drawBorderedRect(22, altY + 2, width - 26, altY + 34, 1, new Color(0, 0, 0).getRGB(), new Color(0, 0, 0, 100).getRGB());
                }
                if (Mouse.isButtonDown(0) && !leftMouseDown && isIn) {
                    if (alt != selectedAlt) {
                        selectedAlt = alt;
                    } else {
                        String cachePassword = alt.getPassword().equals("NULL_PASSWORD") ? "" : alt.getPassword();
                        altLoginThread = new AltLoginThread(alt.getAccount(),cachePassword);
                        altLoginThread.start();
                    }
                    leftMouseDown = true;
                }

                if (Mouse.isButtonDown(1) && !rightMouseDown && isIn) {
                    displayingMenu = new AltManagerRightClickMenu(mouseX,mouseY,alt);
                    displayingMenu.onOpen();
                    rightMouseDown = true;
                }
            }

            if (alt == selectedAlt) {
                selectedAltY = altY;
            }

            altY += 40;
        }
        RenderUtils.stopGlScissor();

        if (!Mouse.isButtonDown(0)) {
            leftMouseDown = false;
        }

        if (!Mouse.isButtonDown(1)) {
            rightMouseDown = false;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

        if (displayingMenu != null) {
            displayingMenu.draw(mouseX,mouseY);
        }

        if (altLoginThread != null) {
            status = altLoginThread.getStatus();
        }
    }

    public static List<Alt> getAlts() {
        return alts;
    }
}
