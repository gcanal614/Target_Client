package love.target.render.screen.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import love.target.Wrapper;
import love.target.mod.Mod;
import love.target.mod.ModManager;
import love.target.mod.value.Value;
import love.target.mod.value.ValueType;
import love.target.mod.value.values.*;
import love.target.render.font.FontManager;
import love.target.render.screen.designer.GuiDesigner;
import love.target.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Mouse;

/**
 * 由于我的硬盘坏了 所以只能找到反编译的源代码
 * 所以很杂很糟糕很鸡巴 XD
 */

public class ClickGui extends GuiScreen {
    private float dragX;
    private float dragY;
    private GuiTextField search;
    public static final String CLICK_GUI_VERSION = "TARGET_VERSION";
    public static float windowX = 200.0f;
    public static float windowY = 200.0f;
    float width = 600.0f;
    float height = 310.0f;
    static ClickType selectType = ClickType.HOME;
    static Mod.Category selectCategory = Mod.Category.FIGHT;
    static List<Value<?>> selectValue = new CopyOnWriteArrayList<>();
    static float modStart = 0.0f;
    static float valueStart = 0.0f;
    int whell = 0;
    int valueWhell = 0;
    static boolean modEnableDown = false;
    static boolean valueEnableDown = false;

    @Override
    public void initGui() {
        super.initGui();
        this.search = new GuiTextField(114514, this.mc.fontRenderer, (int)windowX + 10, (int)windowY + 250, 70, 20);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (Wrapper.isHovered(windowX, windowY, windowX + this.width, windowY + 50.0f, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            if (this.dragX == 0.0f && this.dragY == 0.0f) {
                this.dragX = (float)mouseX - windowX;
                this.dragY = (float)mouseY - windowY;
            } else {
                if (selectType == ClickType.HOME && this.search != null) {
                    String oldString = this.search.getText();
                    this.search = new GuiTextField(114514, this.mc.fontRenderer, (int)windowX + 10, (int)windowY + 250, 70, 20);
                    this.search.setText(oldString);
                    this.search.setEnableBackgroundDrawing(false);
                }
                windowX = (float)mouseX - this.dragX;
                windowY = (float)mouseY - this.dragY;
            }
        } else if (this.dragX != 0.0f || this.dragY != 0.0f) {
            this.dragX = 0.0f;
            this.dragY = 0.0f;
        }
        RenderUtils.drawRect(windowX, windowY, windowX + this.width, windowY + this.height, new Color(21, 22, 25).getRGB());
        if (selectType == ClickType.HOME) {
            this.search.drawTextBox();
            this.search.setMaxStringLength(60);
            this.search.setEnableBackgroundDrawing(false);
            RenderUtils.drawRect(windowX + 10.0f, windowY + 262.0f, windowX + 83.0f, windowY + 263.0f, new Color(0, 141, 255).getRGB());
            if (!this.search.isFocused() && this.search.getText().isEmpty()) {
                this.mc.fontRenderer.drawString("Search...", (int)windowX + 11, (int)windowY + 250, new Color(0x595959).getRGB());
            }
        }
        float typeX = windowX + 10.0f;
        for (ClickType e : ClickType.values()) {
            if (e == selectType) {
                FontManager.yaHei18.drawString(e.toString(), typeX, windowY + 3.0f, -1);
            } else {
                FontManager.yaHei18.drawString(e.toString(), typeX, windowY + 3.0f, new Color(61, 62, 65).getRGB());
            }
            if (Wrapper.isHovered(typeX, windowY + 5.0f, typeX + (float)FontManager.yaHei18.getStringWidth(e.toString()), windowY + 5.0f + (float)FontManager.yaHei18.FONT_HEIGHT, mouseX, mouseY) && Mouse.isButtonDown(0) && e != selectType) {
                selectType = e;
            }
            typeX += (float)(FontManager.yaHei18.getStringWidth(e + " ") + 10);
        }
        FontManager.yaHei16.drawString("Exit", windowX + this.width - (float)FontManager.yaHei18.getStringWidth("Exit") * 2.0f, windowY + 5.0f, Wrapper.isHovered(windowX + this.width - (float)FontManager.yaHei18.getStringWidth("Exit") * 2.0f, windowY + 6.0f, windowX + this.width - (float)FontManager.yaHei18.getStringWidth("Exit"), windowY + 5.0f + (float)FontManager.yaHei18.FONT_HEIGHT, mouseX, mouseY) ? -1 : new Color(61, 62, 65).getRGB());
        if (Mouse.isButtonDown(0) && Wrapper.isHovered(windowX + this.width - (float)FontManager.yaHei18.getStringWidth("Exit") * 2.0f, windowY + 6.0f, windowX + this.width - (float)FontManager.yaHei18.getStringWidth("Exit"), windowY + 5.0f + (float)FontManager.yaHei18.FONT_HEIGHT, mouseX, mouseY)) {
            this.mc.currentScreen = null;
        }
        if (selectType == ClickType.HOME) {
            float categoryY = windowY + 50.0f;
            for (Mod.Category c : Mod.Category.values()) {
                FontManager.yaHei16.drawString(c.toString(), windowX + 10.0f, categoryY, c == selectCategory ? -1 : new Color(61, 62, 65).getRGB());
                if (Wrapper.isHovered(windowX + 10.0f, categoryY, windowX + 10.0f + (float)FontManager.yaHei18.getStringWidth(c.name()), categoryY + (float)FontManager.yaHei18.FONT_HEIGHT, mouseX, mouseY) && Mouse.isButtonDown(0) && c != selectCategory) {
                    selectCategory = c;
                    modStart = 0.0f;
                }
                categoryY += 15.0f;
            }
            float modY = windowY + 50.0f - modStart;
            float modX = windowX + 100.0f;
            int scroll = Mouse.getDWheel();
            if (Wrapper.isHovered(windowX + 100.0f, windowY + 50.0f, windowX + 425.0f, windowY + 315.0f, mouseX, mouseY)) {
                if (scroll < 0 && modStart < (float)(ModManager.getModsByCategory(selectCategory).size() * 25 - 250)) {
                    this.whell = 25;
                }
                if (scroll > 0 && modStart > 0.0f) {
                    this.whell = -25;
                }
            }
            if (this.whell < 0) {
                if (modStart > 0.0f) {
                    modStart -= 2.0f;
                }
                ++this.whell;
            } else if (this.whell > 0) {
                if (modStart < (float)(ModManager.getModsByCategory(selectCategory).size() * 25 - 250)) {
                    modStart += 2.0f;
                }
                --this.whell;
            }
            float qwe = 0.0f;
            for (Value<?> value : selectValue) {
                if (value.getValueType() == ValueType.TEXT_VALUE) {
                    qwe += 30.0f;
                    continue;
                }
                if (value.getValueType() == ValueType.NUMBER_VALUE || value.getValueType() == ValueType.COLOR_VALUE) {
                    qwe += 25.0f;
                    continue;
                }
                qwe += 15.0f;
            }
            if (Wrapper.isHovered(windowX + 435.0f, windowY + 50.0f, windowX + 435.0f + 155.0f, windowY + this.height - 15.0f, mouseX, mouseY)) {
                if (scroll < 0 && valueStart < qwe - 200.0f) {
                    this.valueWhell += 10;
                }
                if (scroll > 0 && valueStart > 0.0f) {
                    this.valueWhell -= 10;
                }
            }
            if (this.valueWhell < 0) {
                if (valueStart > 0.0f) {
                    valueStart -= 1.0f;
                }
                ++this.valueWhell;
            } else if (this.valueWhell > 0) {
                if (valueStart < qwe) {
                    valueStart += 1.0f;
                }
                --this.valueWhell;
            }
            RenderUtils.startGlScissor((int)modX, (int)(windowY + 50.0f), (int)modX + 325, (int)this.height - 65);
            for (int i = 0; i < ModManager.getModsByCategory(selectCategory).size(); ++i) {
                Mod m = ModManager.getModsByCategory(selectCategory).get(i);
                if (this.search != null && !this.search.getText().isEmpty() && !m.getName().toLowerCase().contains(this.search.getText().toLowerCase())) continue;
                RenderUtils.drawRoundedRect2(modX, modY, modX + 325.0f, modY + 20.0f, 1.0f, new Color(32, 31, 35).getRGB());
                RenderUtils.drawRoundedRect2(modX, modY, modX + 8.0f, modY + 20.0f, 1.0f, m.isEnabled() ? new Color(41, 117, 219).getRGB() : new Color(39, 35, 39).getRGB());
                FontManager.yaHei16.drawString(m.getName(), modX + 30.0f, modY + 3.0f, m.isEnabled() ? -1 : new Color(61, 62, 65).getRGB());
                if (!m.getValues().isEmpty()) {
                    RenderUtils.drawRoundedRect2(modX + 305.0f, modY, modX + 325.0f, modY + 20.0f, 1.0f, new Color(39, 38, 42).getRGB());
                    if (selectValue == m.getValues()) {
                        FontManager.yaHei18.drawString(".", modX + 315.0f, modY - 3.0f, new Color(0, 250, 255).getRGB());
                        FontManager.yaHei18.drawString(".", modX + 315.0f, modY + 1.0f, new Color(0, 250, 255).getRGB());
                        FontManager.yaHei18.drawString(".", modX + 315.0f, modY + 5.0f, new Color(0, 250, 255).getRGB());
                    } else {
                        FontManager.yaHei18.drawString(".", modX + 315.0f, modY - 3.0f, new Color(255, 255, 255).getRGB());
                        FontManager.yaHei18.drawString(".", modX + 315.0f, modY + 1.0f, new Color(255, 255, 255).getRGB());
                        FontManager.yaHei18.drawString(".", modX + 315.0f, modY + 5.0f, new Color(255, 255, 255).getRGB());
                    }
                }
                if (Wrapper.isHovered(modX, windowY + 50.0f, modX + 325.0f, windowY + this.height - 15.0f, mouseX, mouseY) && Wrapper.isHovered(modX, modY, modX + 325.0f, modY + 20.0f, mouseX, mouseY)) {
                    RenderUtils.drawRoundedRect(modX, modY, modX + 325.0f, modY + 20.0f, 1, new Color(32, 31, 35, 100).getRGB());
                    if (Mouse.isButtonDown(0) && !modEnableDown) {
                        m.toggle();
                        modEnableDown = true;
                    }
                    if (Mouse.isButtonDown(1)) {
                        selectValue = m.getValues();
                        valueStart = 0.0f;
                    }
                }
                modY += 25.0f;
            }
            RenderUtils.stopGlScissor();
            if (!Mouse.isButtonDown(0)) {
                modEnableDown = false;
                valueEnableDown = false;
            }
            float valueX = windowX + 435.0f;
            float valueY = windowY + 50.0f;
            float valueYT = windowY + 55.0f - valueStart;
            RenderUtils.drawRoundedRect2(valueX, valueY, valueX + 155.0f, windowY + this.height - 15.0f, 1.0f, new Color(32, 31, 37).getRGB());
            if (selectValue.isEmpty()) {
                FontManager.yaHei16.drawString("No Value", valueX + 5.0f, valueY + 2.0f, new Color(61, 62, 65).getRGB());
            } else {
                RenderUtils.startGlScissor((int)valueX, (int)valueY, 155, (int)this.height - 65);
                boolean isIn = Wrapper.isHovered(valueX, valueY, valueX + 155.0f, windowY + this.height - 15.0f, mouseX, mouseY);
                for (Value<?> value : selectValue) {
                    double val;
                    double valRel;
                    double perc;
                    double valAbs;
                    double inc;
                    double max;
                    Value vn;
                    Value<?> v = value;
                    if (v.getValueType() == ValueType.BOOLEAN_VALUE) {
                        BooleanValue vo = (BooleanValue) v;
                        if (vo.getValue()) {
                            FontManager.yaHei16.drawString(vo.getName(), (int) valueX + 10, (int) valueYT, -1);
                            RenderUtils.drawRoundedRect2(valueX + 125.0f, valueYT, valueX + 150.0f, valueYT + 12.0f, 5.0f, new Color(34, 94, 181).getRGB());
                            RenderUtils.drawCircle(valueX + 131.0f + vo.anim, valueYT + 6.0f, 4.0f, 0.1f, true, -1);
                        } else {
                            FontManager.yaHei16.drawString(vo.getName(), (int) valueX + 10, (int) valueYT, new Color(61, 62, 65).getRGB());
                            RenderUtils.drawRoundedRect2(valueX + 125.0f, valueYT, valueX + 150.0f, valueYT + 12.0f, 5.0f, new Color(50, 49, 53).getRGB());
                            RenderUtils.drawRoundedRect2(valueX + 126.0f, valueYT + 1.0f, valueX + 149.0f, valueYT + 11.0f, 4.0f, new Color(31, 27, 31).getRGB());
                            RenderUtils.drawCircle(valueX + 131.0f + vo.anim, valueYT + 6.0f, 4.0f, 0.1f, true, new Color(50, 49, 53).getRGB());
                        }
                        if (Wrapper.isHovered(valueX + 125.0f, valueYT, valueX + 150.0f, valueYT + 12.0f, mouseX, mouseY) && Mouse.isButtonDown(0) && !valueEnableDown && isIn) {
                            vo.setValue(!((Boolean) vo.getValue()));
                            valueEnableDown = true;
                        }
                        vo.anim = (Boolean) vo.getValue() ? (float) RenderUtils.getAnimationState(vo.anim, 13.0, 50.0) : (float) RenderUtils.getAnimationState(vo.anim, 0.0, 50.0);
                        valueYT += 15.0f;
                    }
                    if (v.getValueType() == ValueType.MODE_VALUE) {
                        int next2;
                        int current;
                        ModeValue vm = (ModeValue) v;
                        FontManager.yaHei16.drawString(vm.getName(), (int) valueX + 10, (int) valueYT, -1);
                        RenderUtils.drawRect(valueX + 77.0f, valueYT + 1.0f, valueX + 138.0f, valueYT + 11.0f, new Color(21, 22, 25).getRGB());
                        RenderUtils.drawRect(valueX + 71.0f, valueYT + 1.0f, valueX + 77.0f, valueYT + 11.0f, new Color(Short.MAX_VALUE).getRGB());
                        RenderUtils.drawRect(valueX + 138.0f, valueYT + 1.0f, valueX + 144.0f, valueYT + 11.0f, new Color(Short.MAX_VALUE).getRGB());
                        FontManager.yaHei16.drawCenteredString((String) vm.getValue() + "", valueX + 108.0f, valueYT + 1.0f, -1);
                        FontManager.yaHei16.drawCenteredString("<", valueX + 75.0f, valueYT + 1.0f, -1);
                        FontManager.yaHei16.drawCenteredString(">", valueX + 142.0f, valueYT + 1.0f, -1);
                        if (Wrapper.isHovered(valueX + 71.0f, valueYT + 1.0f, valueX + 77.0f, valueYT + 11.0f, mouseX, mouseY) && Mouse.isButtonDown(0) && !valueEnableDown && isIn) {
                            try {
                                current = vm.getModes().indexOf(vm.getValue());
                                next2 = current - 1 >= vm.getModes().size() ? 0 : current - 1;
                                vm.setValue(vm.getModes().get(next2));
                            } catch (Exception e) {
                                try {
                                    vm.setValue(vm.getModes().get(vm.getModes().size() - 1));
                                } catch (Exception ignored) {
                                }
                            }
                            valueEnableDown = true;
                        }
                        if (Wrapper.isHovered(valueX + 138.0f, valueYT + 1.0f, valueX + 144.0f, valueYT + 11.0f, mouseX, mouseY) && Mouse.isButtonDown(0) && !valueEnableDown && isIn) {
                            current = vm.getModes().indexOf(vm.getValue());
                            next2 = current + 1 >= vm.getModes().size() ? 0 : current + 1;
                            vm.setValue(vm.getModes().get(next2));
                            valueEnableDown = true;
                        }
                        valueYT += 15.0f;
                    }
                    if (v.getValueType() == ValueType.NUMBER_VALUE) {
                        vn = (NumberValue) v;
                        double render = 68.0 * ((double) ((Number) ((NumberValue) vn).getValue()).floatValue() - ((NumberValue) vn).getMinValue()) / (((NumberValue) vn).getMaxValue() - ((NumberValue) vn).getMinValue()) * 2.0;
                        FontManager.yaHei16.drawString(vn.getName(), (int) valueX + 10, (int) valueYT - 3, new Color(61, 62, 65).getRGB());
                        FontManager.yaHei16.drawString(((NumberValue) vn).getValue().toString(), (int) (windowX + this.width - 28.0f - (float) FontManager.yaHei16.getStringWidth(((NumberValue) vn).getValue().toString())), (int) valueYT - 3, -1);
                        RenderUtils.drawRect(valueX + 12.0f, valueYT + 14.0f, windowX + this.width - 18.0f, valueYT + 17.0f, new Color(74, 73, 77).getRGB());
                        RenderUtils.drawRect(valueX + 12.0f, valueYT + 14.0f, (double) (valueX + 12.0f) + render, valueYT + 17.0f, new Color(44, 106, 206).getRGB());
                        if (Wrapper.isHovered(valueX + 12.0f, valueYT + 14.0f, windowX + this.width - 18.0f, valueYT + 17.0f, mouseX, mouseY) && Mouse.isButtonDown(0) && isIn) {
                            render = ((NumberValue) vn).getMinValue();
                            max = ((NumberValue) vn).getMaxValue();
                            inc = ((NumberValue) vn).getIncreaseValue();
                            valAbs = (double) mouseX - ((double) windowX + (double) this.width - 100.0);
                            perc = valAbs / 68.0;
                            perc = Math.min(Math.max(0.0, perc), 1.0);
                            valRel = (max - render) * perc;
                            val = render + valRel;
                            val = (double) Math.round(val * (1.0 / inc)) / (1.0 / inc);
                            vn.setValue(val);
                        }
                        valueYT += 25.0f;
                    }

                    if (v.getValueType() == ValueType.COLOR_VALUE) {
                        ColorValue cv = (ColorValue) v;
                        FontManager.yaHei16.drawString(cv.getName(),valueX + 5,valueYT,new Color(61, 62, 65).getRGB());
                        cv.getColorPalette().setX(valueX + 10);
                        cv.getColorPalette().setY(valueYT + 13);
                        cv.getColorPalette().draw(mouseX,mouseY);
                        cv.setValue(cv.getColorPalette().getNowRGB());
                        RenderUtils.drawRect(valueX + 130,valueYT + 13,valueX + 150,valueYT + 23,cv.getColorPalette().getNowRGB());
                        valueYT += 25.0f;
                    }

                    if (v.getValueType() == ValueType.TEXT_VALUE) {
                        TextValue textValue = (TextValue) v;
                        FontManager.yaHei16.drawString(textValue.getName(),valueX + 5,valueYT,new Color(61, 62, 65).getRGB());
                        RenderUtils.drawBorderedRect(valueX + 10,valueYT + 12,valueX + 150,valueYT + 24,2,Wrapper.isHovered(valueX + 10,valueYT + 12,valueX + 150,valueYT + 24,mouseX,mouseY) ? new Color(0x3145D7).getRGB() : new Color(0x0000FF).getRGB(),new Color(0,0,0).getRGB());
                        textValue.setTextFieldX((int) valueX + 12);
                        textValue.setTextFieldY((int) valueYT + 14);
                        textValue.setTextFieldWidth(139);
                        textValue.setTextFieldHeight(10);
                        textValue.getTextField().drawTextBox();
                        textValue.getTextField().setEnableBackgroundDrawing(false);
                        textValue.setValue(textValue.getTextField().getText());
                        valueYT += 30.0f;
                    }
                }
                RenderUtils.stopGlScissor();
            }
        } else if (selectType == ClickType.DESIGNER) {
            mc.displayGuiScreen(new GuiDesigner());
            selectType = ClickType.HOME;
        }

        //this.drawCursor(mouseX,mouseY);
    }

    private void drawCursor(int mouseX,int mouseY) {
        FontManager.cursor.drawString("a",mouseX,mouseY,new Color(0xFFFF00FF, true).getRGB());
    }

    private void hideMouse() {
        Mouse.setGrabbed(true);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (selectType == ClickType.HOME && this.search != null) {
            this.search.textboxKeyTyped(typedChar, keyCode);
        }

        if (selectType == ClickType.HOME && selectValue != null) {
            for (Value<?> value : selectValue) {
                if (value.getValueType() == ValueType.TEXT_VALUE) {
                    if (typedChar != ':') {
                        TextValue textValue = (TextValue) value;
                        textValue.getTextField().textboxKeyTyped(typedChar, keyCode);
                    }
                }
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (selectType == ClickType.HOME && this.search != null) {
            this.search.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (selectType == ClickType.HOME && selectValue != null) {
            for (Value<?> value : selectValue) {
                if (value.getValueType() == ValueType.TEXT_VALUE) {
                    TextValue textValue = (TextValue) value;
                    textValue.getTextField().mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void updateScreen() {
        //hideMouse();
        super.updateScreen();
    }

    @Override
    public void onGuiClosed() {
        //Mouse.setGrabbed(false);
        super.onGuiClosed();
    }
}
