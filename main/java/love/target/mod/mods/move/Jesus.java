package love.target.mod.mods.move;

import love.target.eventapi.EventTarget;
import love.target.events.EventCollideWithBlock;
import love.target.events.EventPacket;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.mod.value.values.ModeValue;
import love.target.mod.value.values.NumberValue;
import love.target.utils.MoveUtils;
import net.minecraft.block.BlockLiquid;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;

public class Jesus extends Mod {
	private final ModeValue mode = new ModeValue("Mode", "Solid", new String[]{"Solid", "Dolphin", "Motion"});
	public final NumberValue jumpHeight = new NumberValue("JumpHeight", 1.0, 0.0, 10.0, 0.1);

	private boolean wasWater = false;
	private int ticks = 0;

	public Jesus() {
		super("Jesus", Category.MOVE);
		this.addValues(mode, jumpHeight);
	}

	@Override
	public void onEnable() {
		this.wasWater = false;
		super.onEnable();
	}

	private boolean canJeboos() {
		return !(mc.player.fallDistance >= 3.0f || mc.gameSettings.keyBindJump.isPressed() || MoveUtils.isInLiquid() || mc.player.isSneaking());
	}

	boolean shouldJesus() {
		double x = mc.player.posX;
		double y = mc.player.posY;
		double z = mc.player.posZ;
		ArrayList<BlockPos> pos = new ArrayList<>(Arrays.asList(new BlockPos(x + 0.3, y, z + 0.3), new BlockPos(x - 0.3, y, z + 0.3), new BlockPos(x + 0.3, y, z - 0.3), new BlockPos(x - 0.3, y, z - 0.3)));
		for (BlockPos po : pos) {
			if (!(mc.world.getBlockState(po).getBlock() instanceof BlockLiquid))
				continue;
			if (mc.world.getBlockState(po).getProperties().get(BlockLiquid.LEVEL) instanceof Integer) {
				if ((int) mc.world.getBlockState(po).getProperties().get(BlockLiquid.LEVEL) <= 4) {
					return true;
				}
			}
		}
		return false;
	}

	@EventTarget
	public void onPre(EventPreUpdate e) {
		if (mode.isCurrentValue("Dolphin")) {
			if (mc.player.isInWater() && !mc.player.isSneaking() && shouldJesus()) {
				mc.player.motionY = 0.09;
			}

			if (mc.player.onGround || mc.player.isOnLadder()) {
				wasWater = false;
			}
			if (mc.player.motionY > 0.0 && this.wasWater) {
				if (mc.player.motionY <= 0.11) {
					mc.player.motionY *= 1.2671;
				}
				mc.player.motionY += 0.05172;
			}
			if (MoveUtils.isInLiquid() && !mc.player.isSneaking()) {
				if (this.ticks < 3) {
					mc.player.motionY = 0.13;
					++this.ticks;
					this.wasWater = false;
				} else {
					mc.player.motionY = 0.5;
					this.ticks = 0;
					this.wasWater = true;
				}
			}
		} else if (mode.isCurrentValue("Solid")) {
			if (MoveUtils.isInLiquid() && !mc.player.isSneaking() && !mc.gameSettings.keyBindJump.isPressed()) {
				mc.player.motionY = 0.05;
				mc.player.onGround = true;
			}
		} else if (mode.isCurrentValue("Motion")) {
			if (MoveUtils.isInLiquid() && !mc.player.isSneaking() && !mc.gameSettings.keyBindJump.isKeyDown()) {
				mc.player.motionY = jumpHeight.getValue();
			}
			if (MoveUtils.isOnLiquid() && mc.player.isMoving()) {
				MoveUtils.setSpeed(0.6);
			}
		}
	}

	@EventTarget
	public void onPacket(EventPacket e) {
		if (mode.isCurrentValue("Solid")) {
			if (e.getPacket() instanceof C03PacketPlayer && this.canJeboos() && MoveUtils.isOnLiquid()) {
				C03PacketPlayer packet = (C03PacketPlayer) e.getPacket();
				packet.setY_JESUS0(mc.player.ticksExisted % 2 == 0 ? packet.getPositionY() + 0.01 : packet.getPositionY() - 0.01);
			}
		}
	}

	@EventTarget
	public void onBB(EventCollideWithBlock e) {
		if (mode.isCurrentValue("Solid")) {
			if (e.getBlock() instanceof BlockLiquid && this.canJeboos()) {
				e.setBoundingBox(new AxisAlignedBB(e.getBlockPos().getX(), e.getBlockPos().getY(), e.getBlockPos().getZ(), e.getBlockPos().getX() + 1.0, e.getBlockPos().getY() + 1.0, e.getBlockPos().getZ() + 1.0));
			}
		}
	}
}
