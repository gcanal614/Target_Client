package love.target.mod.mods.move;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import love.target.eventapi.EventTarget;
import love.target.events.Event3D;
import love.target.events.EventMove;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.mod.ModManager;
import love.target.mod.mods.fight.KillAura;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.ModeValue;
import love.target.mod.value.values.NumberValue;
import love.target.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TargetStrafe extends Mod {
    public ModeValue mode = new ModeValue("RenderMode", "Null", new String[]{"Round", "Pentagon", "Null"});
    public NumberValue range = new NumberValue("Range", 1.7, 0.1, 10.0, 0.1);
    public NumberValue spaceRange = new NumberValue("HightRange", 5.0, 0.1, 5.0, 0.1);
    private final BooleanValue onlyInRange = new BooleanValue("Only In Range", false);
    private final BooleanValue check = new BooleanValue("Check", true);
    private final BooleanValue auto = new BooleanValue("Auto", true);
    private final BooleanValue key = new BooleanValue("onJumpKey", true);
    public Entity target;
    private double degree = 0.0;
    private float groundY;
    private boolean left = true;
    private List<Entity> targets = new ArrayList<>();

    public TargetStrafe() {
        super("TargetStrafe", Category.MOVE);
        this.addValues(this.mode, this.range, this.spaceRange, this.check, this.auto, this.onlyInRange, this.key);
    }

    @Override
    public void onEnable() {
        this.targets.clear();
        this.degree = 0.0;
        this.left = true;
        this.target = null;
    }

    @EventTarget
    public void onRender(Event3D event) {
        if (this.mode.isCurrentValue("Round")) {
            if (KillAura.curtarget == null) {
                return;
            }
            if (TargetStrafe.mc.player.onGround) {
                this.groundY = (float)TargetStrafe.mc.player.posY;
            }
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0f);
            GL11.glDisable(3553);
            GL11.glEnable(2884);
            GL11.glDisable(2929);
            AxisAlignedBB Item2 = new AxisAlignedBB(-0.175, 0.0, -0.175, 0.175, 0.35, 0.175);
            GL11.glPushMatrix();
            GL11.glTranslated(RenderUtils.getEntityRenderX(KillAura.curtarget), RenderUtils.getEntityRenderY(KillAura.curtarget), RenderUtils.getEntityRenderZ(KillAura.curtarget));
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            RenderUtils.drawCircle((int)Item2.minX, (int)Item2.minY, this.range.getValue().floatValue(), 1.0f, false, this.canStrafe() ? new Color(255, 255, 0).getRGB() : new Color(255, 255, 255).getRGB());
            GL11.glPopMatrix();
            GL11.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
            GL11.glEnable(2929);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glDisable(2848);
            GL11.glPopMatrix();
        } else if (this.mode.isCurrentValue("Pentagon")) {
            double rad = this.range.getValue();
            if (KillAura.curtarget == null) {
                return;
            }
            if (TargetStrafe.mc.player.onGround) {
                this.groundY = (float)TargetStrafe.mc.player.posY;
            }
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            RenderUtils.startDrawing();
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glLineWidth(1.0f);
            GL11.glBegin(3);
            double x = KillAura.curtarget.lastTickPosX + (KillAura.curtarget.posX - KillAura.curtarget.lastTickPosX) * (double)event.getTicks() - TargetStrafe.mc.getRenderManager().viewerPosX;
            double y = KillAura.curtarget.lastTickPosY + (KillAura.curtarget.posY - KillAura.curtarget.lastTickPosY) * (double)event.getTicks() - TargetStrafe.mc.getRenderManager().viewerPosY;
            double z = KillAura.curtarget.lastTickPosZ + (KillAura.curtarget.posZ - KillAura.curtarget.lastTickPosZ) * (double)event.getTicks() - TargetStrafe.mc.getRenderManager().viewerPosZ;
            int color = Color.WHITE.getRGB();
            for (int i = 0; i <= 10; ++i) {
                RenderUtils.glColor(color);
                if (this.range.getValue() < 0.8 && this.range.getValue() > 0.0) {
                    GL11.glVertex3d(x + rad * Math.cos((double)i * (Math.PI * 2) / 3.0), y, z + rad * Math.sin((double)i * (Math.PI * 2) / 3.0));
                }
                if (this.range.getValue() < 1.5 && this.range.getValue() > 0.7) {
                    GL11.glVertex3d(x + rad * Math.cos((double)i * (Math.PI * 2) / 4.0), y, z + rad * Math.sin((double)i * (Math.PI * 2) / 4.0));
                }
                if (this.range.getValue() < 2.0 && this.range.getValue() > 1.4) {
                    GL11.glVertex3d(x + rad * Math.cos((double)i * (Math.PI * 2) / 5.0), y, z + rad * Math.sin((double)i * (Math.PI * 2) / 5.0));
                }
                if (this.range.getValue() < 2.4 && this.range.getValue() > 1.9) {
                    GL11.glVertex3d(x + rad * Math.cos((double)i * (Math.PI * 2) / 6.0), y, z + rad * Math.sin((double)i * (Math.PI * 2) / 6.0));
                }
                if (this.range.getValue() < 2.7 && this.range.getValue() > 2.3) {
                    GL11.glVertex3d(x + rad * Math.cos((double)i * (Math.PI * 2) / 7.0), y, z + rad * Math.sin((double)i * (Math.PI * 2) / 7.0));
                }
                if (this.range.getValue() < 6.0 && this.range.getValue() > 2.6) {
                    GL11.glVertex3d(x + rad * Math.cos((double)i * (Math.PI * 2) / 8.0), y, z + rad * Math.sin((double)i * (Math.PI * 2) / 8.0));
                }
                if (this.range.getValue() < 7.0 && this.range.getValue() > 5.9) {
                    GL11.glVertex3d(x + rad * Math.cos((double)i * (Math.PI * 2) / 9.0), y, z + rad * Math.sin((double)i * (Math.PI * 2) / 9.0));
                }
                if (!(this.range.getValue() < 11.0) || !(this.range.getValue() > 6.9)) continue;
                GL11.glVertex3d(x + rad * Math.cos((double)i * (Math.PI * 2) / 10.0), y, z + rad * Math.sin((double)i * (Math.PI * 2) / 10.0));
            }
            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            RenderUtils.stopDrawing();
            GL11.glEnable(3553);
            GL11.glPopMatrix();
        }
    }

    @EventTarget
    private void onUpdate(EventPreUpdate e) {
        if (((Boolean)this.key.getValue()).booleanValue()) {
            if (TargetStrafe.mc.gameSettings.keyBindJump.isKeyDown()) {
                TargetStrafe.mc.gameSettings.keyBindForward.Doing = this.target != null && ModManager.getModByName("Speed").isEnabled() && ModManager.getModByName("KillAura").isEnabled() && (Boolean)this.auto.getValue() != false;
                KillAura ka = (KillAura)ModManager.getModByName("KillAura");
                this.target = ka.isEnabled() ? KillAura.curtarget : null;
            } else {
                TargetStrafe.mc.gameSettings.keyBindForward.Doing = false;
                this.target = null;
            }
        } else {
            TargetStrafe.mc.gameSettings.keyBindForward.Doing = this.target != null && ModManager.getModByName("Speed").isEnabled() && ModManager.getModByName("KillAura").isEnabled() && (Boolean)this.auto.getValue() != false;
            KillAura ka = (KillAura)ModManager.getModByName("KillAura");
            this.target = ka.isEnabled() ? KillAura.curtarget : null;
        }
    }

    @EventTarget(value=3)
    private void onMove(EventMove e) {
        if (this.canStrafe()) {
            Speed speedM = (Speed)ModManager.getModByName("Speed");
            double speed = speedM.movementSpeed * 0.99;
            this.degree = Math.atan2(TargetStrafe.mc.player.posZ - this.target.posZ, TargetStrafe.mc.player.posX - this.target.posX);
            this.degree += this.left ? speed / (double)TargetStrafe.mc.player.getDistanceToEntity(this.target) : -(speed / (double)TargetStrafe.mc.player.getDistanceToEntity(this.target));
            double x = this.target.posX + this.range.getValue() * Math.cos(this.degree);
            double z = this.target.posZ + this.range.getValue() * Math.sin(this.degree);
            if (((Boolean)this.check.getValue()).booleanValue() && this.needToChange(x, z)) {
                boolean bl = this.left = !this.left;
                this.degree += 2.0 * (this.left ? speed / (double)TargetStrafe.mc.player.getDistanceToEntity(this.target) : -(speed / (double)TargetStrafe.mc.player.getDistanceToEntity(this.target)));
                x = this.target.posX + this.range.getValue() * Math.cos(this.degree);
                z = this.target.posZ + this.range.getValue() * Math.sin(this.degree);
            }
            e.setX(speed * -Math.sin((float)Math.toRadians(TargetStrafe.toDegree(x, z))));
            TargetStrafe.mc.player.motionX = speed * -Math.sin((float)Math.toRadians(TargetStrafe.toDegree(x, z)));
            TargetStrafe.mc.player.motionZ = speed * Math.cos((float)Math.toRadians(TargetStrafe.toDegree(x, z)));
            e.setZ(speed * Math.cos((float)Math.toRadians(TargetStrafe.toDegree(x, z))));
        }
    }

    public boolean canStrafe() {
        KillAura ka = (KillAura)ModManager.getModByName("KillAura");
        Speed speed = (Speed)ModManager.getModByName("Speed");
        return this.target != null && mc.player.isMoving() && ka.isEnabled() && speed.isEnabled() && this.isEnabled() && (!(Boolean)this.onlyInRange.getValue() || this.target.getDistanceSqToEntity(mc.player) < this.range.getValue() + this.spaceRange.getValue());
    }

    public static float toDegree(double x, double z) {
        return (float)(Math.atan2(z - Minecraft.getMinecraft().player.posZ, x - Minecraft.getMinecraft().player.posX) * 180.0 / Math.PI) - 90.0f;
    }

    public boolean needToChange(double x, double z) {
        if (TargetStrafe.mc.player.isCollidedHorizontally) {
            if (TargetStrafe.mc.player.ticksExisted % 2 == 0) {
                return true;
            }
        }
        for (int i = (int)(TargetStrafe.mc.player.posY + 4.0); i >= 0; --i) {
            BlockPos playerPos;
            block7: {
                block6: {
                    playerPos = new BlockPos(x, (double)i, z);
                    if (TargetStrafe.mc.world.getBlockState(playerPos).getBlock().equals(Blocks.lava)) break block6;
                    if (!TargetStrafe.mc.world.getBlockState(playerPos).getBlock().equals(Blocks.fire)) break block7;
                }
                return true;
            }
            if (TargetStrafe.mc.world.isAirBlock(playerPos)) continue;
            return false;
        }
        return true;
    }
}
