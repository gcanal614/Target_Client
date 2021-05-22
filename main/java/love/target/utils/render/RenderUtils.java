package love.target.utils.render;

import java.awt.Color;

import love.target.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    public static void drawRect(double d, double e, double g, double h, int color) {
        if (d < g) {
            int i = (int)d;
            d = g;
            g = i;
        }
        if (e < h) {
            int j = (int)e;
            e = h;
            h = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(d, h, 0.0).endVertex();
        worldrenderer.pos(g, h, 0.0).endVertex();
        worldrenderer.pos(g, e, 0.0).endVertex();
        worldrenderer.pos(d, e, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        RenderUtils.resetColor();
    }

    public static void drawBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        tessellator.draw();
    }

    public static double getAnimationState(double animation, double finalState, double speed) {
        float add = (float)(0.01 * speed);
        animation = animation < finalState ? (animation + (double)add < finalState ? (animation += (double)add) : finalState) : (animation - (double)add > finalState ? (animation -= (double)add) : finalState);
        return animation;
    }

    public static void resetColor() {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
        RenderUtils.drawRect(x + 0.5f, y, x1 - 0.5f, y + 0.5f, insideC);
        RenderUtils.drawRect(x + 0.5f, y1 - 0.5f, x1 - 0.5f, y1, insideC);
        RenderUtils.drawRect(x, y + 0.5f, x1, y1 - 0.5f, insideC);
    }

    public static void drawRoundedRect2(float x, float y, float x2, float y2, float round, int color) {
        Gui.drawRect((int)(x += (float)((double)(round / 2.0f) + 0.5)), (int)(y += (float)((double)(round / 2.0f) + 0.5)), (int)(x2 -= (float)((double)(round / 2.0f) + 0.5)), (int)(y2 -= (float)((double)(round / 2.0f) + 0.5)), color);
        RenderUtils.circle(x2 - round / 2.0f, y + round / 2.0f, round, color);
        RenderUtils.circle(x + round / 2.0f, y2 - round / 2.0f, round, color);
        RenderUtils.circle(x + round / 2.0f, y + round / 2.0f, round, color);
        RenderUtils.circle(x2 - round / 2.0f, y2 - round / 2.0f, round, color);
        Gui.drawRect((int)(x - round / 2.0f - 0.5f), (int)(y + round / 2.0f), (int)x2, (int)(y2 - round / 2.0f), color);
        Gui.drawRect((int)x, (int)(y + round / 2.0f), (int)(x2 + round / 2.0f + 0.5f), (int)(y2 - round / 2.0f), color);
        Gui.drawRect((int)(x + round / 2.0f), (int)(y - round / 2.0f - 0.5f), (int)(x2 - round / 2.0f), (int)(y2 - round / 2.0f), color);
        Gui.drawRect((int)(x + round / 2.0f), (int)y, (int)(x2 - round / 2.0f), (int)(y2 + round / 2.0f + 0.5f), color);
    }

    public static void circle(float x, float y, float radius, int fill) {
        RenderUtils.arc(x, y, 0.0f, 360.0f, radius, fill);
    }

    public static void arc(float x, float y, float start, float end, float radius, int color) {
        RenderUtils.arcEllipse(x, y, start, end, radius, radius, color);
    }

    public static void arcEllipse(float x, float y, float start, float end, float w, float h, int color) {
        float ldy;
        float ldx;
        float i;
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        float temp = 0.0f;
        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }
        float var11 = (float)(color >> 24 & 0xFF) / 255.0f;
        float var12 = (float)(color >> 16 & 0xFF) / 255.0f;
        float var13 = (float)(color >> 8 & 0xFF) / 255.0f;
        float var14 = (float)(color & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var12, var13, var14, var11);
        if (var11 > 0.5f) {
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0f);
            GL11.glBegin(3);
            for (i = end; i >= start; i -= 4.0f) {
                ldx = (float)Math.cos((double)i * Math.PI / 180.0) * w * 1.001f;
                ldy = (float)Math.sin((double)i * Math.PI / 180.0) * h * 1.001f;
                GL11.glVertex2f(x + ldx, y + ldy);
            }
            GL11.glEnd();
            GL11.glDisable(2848);
        }
        GL11.glBegin(6);
        for (i = end; i >= start; i -= 4.0f) {
            ldx = (float)Math.cos((double)i * Math.PI / 180.0) * w;
            ldy = (float)Math.sin((double)i * Math.PI / 180.0) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawCircle(float x, float y, float r, float lineWidth, boolean isFull, int color) {
        RenderUtils.drawCircle(x, y, r, 10, lineWidth, 360, isFull, color);
    }

    public static void drawCircle(float cx, float cy, double r, int segments, float lineWidth, int part, boolean isFull, int c) {
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        r *= 2.0;
        cx *= 2.0f;
        cy *= 2.0f;
        float f2 = (float)(c >> 24 & 0xFF) / 255.0f;
        float f3 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f4 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f5 = (float)(c & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glLineWidth(lineWidth);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f3, f4, f5, f2);
        GL11.glBegin(3);
        for (int i = segments - part; i <= segments; ++i) {
            double x = Math.sin((double)i * Math.PI / 180.0) * r;
            double y = Math.cos((double)i * Math.PI / 180.0) * r;
            GL11.glVertex2d((double)cx + x, (double)cy + y);
            if (!isFull) continue;
            GL11.glVertex2d(cx, cy);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }

    public static void startGlScissor(int x, int y, int width, int height) {
        int scaleFactor = new ScaledResolution(Wrapper.mc).getScaleFactor();
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        GL11.glScissor(x * scaleFactor, Wrapper.mc.displayHeight - (y + height) * scaleFactor, width * scaleFactor, height * scaleFactor);
    }

    public static void stopGlScissor() {
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    public static void drawBorderedRect(double x2, double d2, double x22, double e2, float l1, int col1, int col2) {
        RenderUtils.drawRect(x2, d2, x22, e2, col2);
        float f2 = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f22 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(col1 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f22, f3, f4, f2);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d(x2, d2);
        GL11.glVertex2d(x2, e2);
        GL11.glVertex2d(x22, e2);
        GL11.glVertex2d(x22, d2);
        GL11.glVertex2d(x2, d2);
        GL11.glVertex2d(x22, d2);
        GL11.glVertex2d(x2, e2);
        GL11.glVertex2d(x22, e2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        RenderUtils.resetColor();
    }

    public static void drawImage(ResourceLocation image, double x, double y, double width, double height) {
        RenderUtils.drawImage(image, x, y, width, height, -1);
    }

    public static void drawImage(ResourceLocation image, double x, double y, double width, double height, int color) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        Color color1 = new Color(color);
        GL11.glColor4f((float)color1.getRed() / 255.0f, (float)color1.getGreen() / 255.0f, (float)color1.getBlue() / 255.0f, (float)color1.getAlpha() / 255.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        RenderUtils.drawModalRectWithCustomSizedTexture((float)x, (float)y, 0.0f, 0.0f, (float)width, (float)height, (float)width, (float)height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        float f = 1.0f / textureWidth;
        float f1 = 1.0f / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0).tex(u * f, (v + height) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0).tex((u + width) * f, (v + height) * f1).endVertex();
        worldrenderer.pos(x + width, y, 0.0).tex((u + width) * f, v * f1).endVertex();
        worldrenderer.pos(x, y, 0.0).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static void glColor(int hex) {
        float alpha = (float)(hex >> 24 & 0xFF) / 255.0f;
        float red = (float)(hex >> 16 & 0xFF) / 255.0f;
        float green = (float)(hex >> 8 & 0xFF) / 255.0f;
        float blue = (float)(hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void startDrawing() {
        GL11.glEnable(3042);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        Minecraft.getMinecraft().entityRenderer.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 0);
    }

    public static void stopDrawing() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void pre3D() {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
    }

    public static void post3D() {
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
    }

    public static double getEntityRenderX(Entity entity) {
        return entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)Minecraft.getMinecraft().timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().renderPosX;
    }

    public static double getEntityRenderY(Entity entity) {
        return entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)Minecraft.getMinecraft().timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().renderPosY;
    }

    public static double getEntityRenderZ(Entity entity) {
        return entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)Minecraft.getMinecraft().timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().renderPosZ;
    }
}
