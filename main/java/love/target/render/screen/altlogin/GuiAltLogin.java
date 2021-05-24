package love.target.render.screen.altlogin;

import love.target.Wrapper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;

public class GuiAltLogin extends GuiScreen implements LoginOrAdd {
    private GuiTextField password;
    private final GuiScreen previousScreen;
    private AltLoginThread thread;
    private GuiTextField username;
    private GuiTextField combined;
    private final AltEnum altEnum;
    private String ssss;

    public GuiAltLogin(GuiScreen previousScreen,AltEnum altEnum) {
        this.previousScreen = previousScreen;
        this.altEnum = altEnum;
    }

    protected void actionPerformed(GuiButton button) {
        String data;
        switch (button.id) {
            case 0:
                this.onLogin();
                break;
            case 1:
                this.mc.displayGuiScreen(this.previousScreen);
                break;
            case 2:
                try {
                    data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                } catch (Exception var4) {
                    return;
                }

                if (data.contains(":")) {
                    String[] credentials = data.split(":");
                    this.username.setText(credentials[0]);
                    this.password.setText(credentials[1]);
                }
                break;
            case 1145:
                this.username.setText(Wrapper.getRandomString(7));
                this.password.setText("");
        }
    }

    @Override
    public void onLogin() {
        String data;

        switch (altEnum) {
            case LOGIN:
                if (this.combined.getText().isEmpty()) {
                    this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
                } else if (!this.combined.getText().isEmpty() && this.combined.getText().contains(":")) {
                    data = this.combined.getText().split(":")[0];
                    String p = this.combined.getText().split(":")[1];
                    this.thread = new AltLoginThread(data.replaceAll(" ", ""), p.replaceAll(" ", ""));
                } else {
                    this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
                }

                this.thread.start();
                break;
            case ADD:
                ssss = EnumChatFormatting.YELLOW + "添加中";
                new Thread(() -> {
                    String data1;
                    String a;
                    String pa;

                    if (this.combined.getText().isEmpty()) {
                        a = username.getText();
                        pa = password.getText();
                    } else if (!this.combined.getText().isEmpty() && this.combined.getText().contains(":")) {
                        data1 = this.combined.getText().split(":")[0];
                        String p = this.combined.getText().split(":")[1];
                        a = data1.replaceAll(" ", "");
                        pa = p.replaceAll(" ", "");
                    } else {
                        a = username.getText();
                        pa = password.getText();
                    }

                    if (pa.isEmpty()) {
                        GuiAltManager.getAlts().add(new Alt(a, "NULL_PASSWORD", a));
                        ssss = EnumChatFormatting.GREEN + "添加成功! " + a;
                    } else {
                        Session s = AltLoginThread.createSession(a, pa);

                        if (s == null) {
                            ssss = EnumChatFormatting.RED + "添加失败!";
                        } else {
                            GuiAltManager.getAlts().add(new Alt(a, pa, s.getProfile().getName()));
                            ssss = EnumChatFormatting.GREEN + "添加成功! " + s.getProfile().getName();
                        }
                    }
                },("Add Alt Thread")).start();
                break;
        }
    }

    public void drawScreen(int x, int y, float z) {
        new ScaledResolution(this.mc);
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.combined.drawTextBox();
        Wrapper.mc.fontRenderer.drawCenteredString("Alt Login", (float)(this.width / 2), 20.0F, -1);
        if (altEnum == AltEnum.LOGIN) {
            Wrapper.mc.fontRenderer.drawCenteredString(this.thread == null ? "§eWaiting..." : this.thread.getStatus(), (float) (this.width / 2), 29.0F, -1);
        } else if (altEnum == AltEnum.ADD) {
            Wrapper.mc.fontRenderer.drawCenteredString(this.ssss == null ? "§eWaiting..." : ssss, (float) (this.width / 2), 29.0F, -1);
        }
        if (this.username.getText().isEmpty() && !this.username.isFocused()) {
            Wrapper.mc.fontRenderer.drawStringWithShadow("Username / E-Mail", (float)(this.width / 2 - 96), 66.0F, -7829368);
        }

        if (this.password.getText().isEmpty() && !this.password.isFocused()) {
            Wrapper.mc.fontRenderer.drawStringWithShadow("Password", (float)(this.width / 2 - 96), 106.0F, -7829368);
        }

        if (this.combined.getText().isEmpty() && !this.combined.isFocused()) {
            Wrapper.mc.fontRenderer.drawStringWithShadow("Email:Password", (float)(this.width / 2 - 96), 146.0F, -7829368);
        }

        super.drawScreen(x, y, z);
    }

    public void initGui() {
        int var3 = this.height / 4 + 24;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, var3 + 72 + 12 + 24, "Back"));
        this.buttonList.add(new GuiButton(1145, this.width / 2 - 100, var3 + 72 + 12 + 48, "Random User Name"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, var3 + 72 + 12 - 24, "Import user:pass"));
        this.username = new GuiTextField(1, this.mc.fontRenderer, this.width / 2 - 100, 60, 200, 20);
        this.password = new GuiTextField(11,this.mc.fontRenderer, this.width / 2 - 100, 100, 200, 20);
        this.combined = new GuiTextField(var3, this.mc.fontRenderer, this.width / 2 - 100, 140, 200, 20);
        this.username.setFocused(true);
        this.username.setMaxStringLength(200);
        this.password.setMaxStringLength(200);
        this.combined.setMaxStringLength(200);
        Keyboard.enableRepeatEvents(true);
    }

    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        if (character == '\t' && (this.username.isFocused() || this.combined.isFocused() || this.password.isFocused())) {
            this.username.setFocused(!this.username.isFocused());
            this.password.setFocused(!this.password.isFocused());
            this.combined.setFocused(!this.combined.isFocused());
        }

        if (character == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }

        this.username.textboxKeyTyped(character, key);
        this.password.textboxKeyTyped(character, key);
        this.combined.textboxKeyTyped(character, key);
    }

    protected void mouseClicked(int x, int y, int button) {
        try {
            super.mouseClicked(x, y, button);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        this.username.mouseClicked(x, y, button);
        this.password.mouseClicked(x, y, button);
        this.combined.mouseClicked(x, y, button);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
        this.combined.updateCursorCounter();
    }
}
