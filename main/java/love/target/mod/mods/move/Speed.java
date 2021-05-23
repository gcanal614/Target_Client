package love.target.mod.mods.move;

import love.target.eventapi.EventTarget;
import love.target.events.EventMove;
import love.target.mod.Mod;
import love.target.mod.ModManager;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.ModeValue;
import love.target.mod.value.values.NumberValue;
import love.target.utils.MoveUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.util.List;

public class Speed extends Mod {
    private final ModeValue mode = new ModeValue("Mode","NCP",new String[]{"NCP","Test"});
    private final NumberValue moveBoost = new NumberValue("MoveBoost",1.0,1.0,10.0,0.1);
    private final NumberValue iceBoost = new NumberValue("IceBoost",1.2,1.0,10.0,0.1);
    private final NumberValue damageBoost = new NumberValue("DamageBoost",1.0,1.0,10.0,0.1);
    private final BooleanValue jumpTimer = new BooleanValue("JumpTimer",false);
    private final BooleanValue fallTimer = new BooleanValue("FallTimer",false);

    private int stage;
    public double movementSpeed;
    double distance = 0.0;

    public Speed() {
        super("Speed", Category.MOVE);
        addValues(mode,moveBoost,iceBoost,damageBoost,jumpTimer,fallTimer);
    }

    @EventTarget
    public void onMove(EventMove e) {
        if (mode.isCurrentValue("NCP")) {
            if (mc.player.isMoving() && mc.player.onGround && this.stage == 1) {
                this.movementSpeed = 1.66 * MoveUtils.getSpeed() - 0.01;
            } else if (mc.player.isMoving() && mc.player.onGround && this.stage == 2) {
                movementSpeed *= 1.05;
                if (jumpTimer.getValue()) {
                    mc.timer.timerSpeed = 1.06f;
                }
                mc.player.motionY = 0.40847965437456313;
                e.setY(0.40847965437456313);
            } else if (this.stage == 3) {
                if (jumpTimer.getValue()) {
                    mc.timer.timerSpeed = 1.0f;
                }
                double difference = 0.66 * (this.distance - MoveUtils.getSpeed());
                this.movementSpeed = this.distance - difference;
            } else {
                List<AxisAlignedBB> collidingList = mc.world.getCollidingBoundingBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0, mc.player.motionY, 0.0));
                if (collidingList.size() > 0 || mc.player.isCollidedVertically && this.stage > 0) {
                    this.stage = mc.player.isMoving() ? 1 : 0;
                }
                this.movementSpeed = this.distance - this.distance / 159.0;
            }

            this.movementSpeed = Math.max(this.movementSpeed, MoveUtils.getSpeed());

            double iceMoveSpeed = 1.0;

            for (double i = 0; i < 1.5; i += 0.1) {
                Block block = mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - i, mc.player.posZ)).getBlock();
                if (block instanceof BlockIce || block instanceof BlockPackedIce) {
                    iceMoveSpeed = iceBoost.getValue();
                }
            }

            this.movementSpeed *= iceMoveSpeed;
            this.movementSpeed *= moveBoost.getValue();
            if (mc.player.hurtTime != 0) {
                this.movementSpeed *= damageBoost.getValue();
            }
            if (mc.player.fallDistance > 0.5 && fallTimer.getValue()) {
                mc.timer.timerSpeed = 1.03f;
            } else if (!ModManager.getModEnableByName("Timer")) {
                mc.timer.timerSpeed = 1.0f;
            }
            MoveUtils.setSpeedEvent(e, this.movementSpeed);
            if (mc.player.isMoving()) {
                ++this.stage;
            }
        } else if (mode.isCurrentValue("Test")) {
            movementSpeed = MoveUtils.getSpeed() * moveBoost.getValue();
            if (mc.player.onGround && mc.player.isMoving() && !mc.player.isInWater() && !mc.player.isInLava()) {
                movementSpeed *= 1.05;
                if (jumpTimer.getValue()) {
                    mc.timer.timerSpeed = 1.06f;
                }
                mc.player.motionY = 0.40847965437456313;
                e.setY(0.40847965437456313);
            }

            if (jumpTimer.getValue()) {
                mc.timer.timerSpeed = 1f;
            }

            double iceMoveSpeed = 1.0;

            for (double i = 0; i < 1.5; i += 0.1) {
                Block block = mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - i, mc.player.posZ)).getBlock();
                if (block instanceof BlockIce || block instanceof BlockPackedIce) {
                    iceMoveSpeed = iceBoost.getValue();
                }
            }

            movementSpeed *= iceMoveSpeed;

            if (mc.player.hurtTime != 0) {
                this.movementSpeed *= damageBoost.getValue();
            }

            if (mc.player.fallDistance > 0.5 && fallTimer.getValue()) {
                mc.timer.timerSpeed = 1.03f;
            } else if (!ModManager.getModEnableByName("Timer")) {
                mc.timer.timerSpeed = 1f;
            }

            if (mc.player.isCollidedHorizontally) {
                movementSpeed = MoveUtils.getSpeed() * 0.5;
            }

            if (!mc.player.isInWater() && !mc.player.isInLava()) {
                MoveUtils.setSpeedEvent(e, this.movementSpeed);
            }

            if (mc.player.isMoving()) {
                ++this.stage;
            }
        }
    }

    @Override
    protected void onDisable() {
        mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }
}
