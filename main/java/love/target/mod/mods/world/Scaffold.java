package love.target.mod.mods.world;

import love.target.Wrapper;
import love.target.eventapi.EventTarget;
import love.target.eventapi.types.Priority;
import love.target.events.Event2D;
import love.target.events.EventPostUpdate;
import love.target.events.EventPreUpdate;
import love.target.events.EventTick;
import love.target.mod.Mod;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.NumberValue;
import love.target.render.font.FontManager;
import love.target.utils.MoveUtils;
import love.target.utils.TimerUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;

public class Scaffold extends Mod {
    private final NumberValue towerBoostValue = new NumberValue("TowerBoost",1.0,1.0,5.0,0.1);
    private final BooleanValue tower = new BooleanValue("Tower", true);
    private final BooleanValue swing = new BooleanValue("Swing", false);
    private final BooleanValue movetower = new BooleanValue("MoveTower", true);
    public final BooleanValue noSprint = new BooleanValue("NoSprint", false);
    public final BooleanValue fixedSpeed = new BooleanValue("FixedSpeed", false);
    public final BooleanValue keepYValue = new BooleanValue("KeepY", false);
    public static final List<Block> invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.furnace, Blocks.carpet, Blocks.crafting_table, Blocks.trapped_chest, Blocks.chest, Blocks.dispenser, Blocks.air, Blocks.water, Blocks.lava, Blocks.flowing_water, Blocks.flowing_lava, Blocks.sand, Blocks.snow_layer, Blocks.torch, Blocks.anvil, Blocks.jukebox, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.noteblock, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.wooden_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_slab, Blocks.wooden_slab, Blocks.stone_slab2, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.yellow_flower, Blocks.red_flower, Blocks.anvil, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.cactus, Blocks.ladder, Blocks.web, Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest);
    private final List<Block> validBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava);
    private final BlockPos[] blockPositions = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1)};
    private final EnumFacing[] facings = new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.NORTH};
    private final TimerUtil towerStopwatch = new TimerUtil();
    private static float qwerty;
    private ItemStack currentblock;
    private float jumpGround = 0.0f;
    private float rotationYawSave;
    private float rotationPitchSave;

    private int keepY;

    public Scaffold() {
        super("Scaffold", Category.WORLD);
        this.addValues(towerBoostValue,this.tower, this.movetower, this.swing,this.noSprint,fixedSpeed,keepYValue);
    }

    @Override
    public void onEnable() {
        this.towerStopwatch.reset();
        this.jumpGround = (int)mc.player.posY;

        if (MoveUtils.isOnGround(0.00001)) {
            keepY = ((int) mc.player.posY);
        }
    }

    @Override
    public void onDisable() {
        mc.player.stepHeight = 0.6f;
        mc.timer.timerSpeed = 1.0f;
    }

    @EventTarget(value = Priority.LOWEST)
    public void onTick(EventTick e) {
        if (noSprint.getValue()) {
            mc.player.setSprinting(false);
        }
    }

    @EventTarget
    private void render(Event2D e) {
        ScaledResolution sr = new ScaledResolution(mc);
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        int middleX = width / 2 + 15;
        int middleY = height / 2 - 12;
        int block = this.getBlockCount() + this.getallBlockCount();
        if (block != 0) {
            FontManager.yaHei16.drawStringWithShadow((block >= 64 ? EnumChatFormatting.YELLOW : EnumChatFormatting.RED) + "Blocks:" + block + EnumChatFormatting.WHITE + " (" + EnumChatFormatting.GRAY + this.currentblock.getDisplayName() + EnumChatFormatting.WHITE + ")", middleX - 50, middleY + 20, 0, 255);
        } else {
            FontManager.yaHei16.drawStringWithShadow((block >= 64 ? EnumChatFormatting.YELLOW : EnumChatFormatting.RED) + "Blocks:" + block + EnumChatFormatting.WHITE + " (" + EnumChatFormatting.GRAY + "No hava LOL" + EnumChatFormatting.WHITE + ")", middleX - 50, middleY + 20, 0, 255);
        }
    }

    @EventTarget
    private void onUpdate(EventPreUpdate event) {
        if (mc.player.isMoving() && fixedSpeed.getValue()) {
            MoveUtils.setSpeed(0.1);
        }
        mc.player.stepHeight = 0.5f;
        qwerty = mc.player.rotationYaw;
        event.setYaw(rotationYawSave);
        mc.player.rotationYawHead = rotationYawSave;
        mc.player.renderYawOffset = rotationYawSave;
        mc.player.rotationPitchHead = 79.44f;
        event.setPitch(79.44f);

        if (mc.player.onGround) {
            mc.timer.timerSpeed = 1.0f;
        }

        if (this.getBlockCount() <= 0) {
            int spoofSlot = this.getBestSpoofSlot();
            this.getBlock(spoofSlot);
        }

        double yDif = 1.0;
        BlockData data = null;
        for (double posY = mc.player.posY - 1.0; posY > 0.0; posY -= 1.0) {
            BlockData newData = this.getBlockData(new BlockPos(mc.player.posX, posY, mc.player.posZ));
            if (newData == null || !((yDif = mc.player.posY - posY) <= 3.0)) continue;
            data = newData;
            break;
        }
        int slot = -1;
        int blockCount = 0;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
            if (itemStack == null) continue;
            int stackSize = itemStack.stackSize;
            if (!this.isValidItem(itemStack.getItem()) || stackSize <= blockCount) continue;
            blockCount = stackSize;
            slot = i;
            this.currentblock = itemStack;
        }
        if (data != null && slot != -1) {
            BlockPos pos = data.pos;
            Block block = mc.world.getBlockState(pos.offset(data.face)).getBlock();
            if (!this.validBlocks.contains(block) || this.isBlockUnder(yDif)) {
                return;
            }
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()) && this.tower.getValue()) {
                if (!this.movetower.getValue()) {
                    if (mc.player.isMoving()) {
                        return;
                    }
                    mc.player.motionX = 0.0;
                    mc.player.motionZ = 0.0;
                }
                if (mc.player.isMoving()) {
                    if ((float)((int)mc.player.posY) > this.jumpGround) {
                        mc.player.motionY = 0.40982;
                        this.jumpGround = (int)mc.player.posY;
                        mc.player.motionY -= 0.028;
                    }
                } else {
                    mc.player.motionX = 0.0;
                    mc.player.motionZ = 0.0;
                    mc.player.motionY = 0.41982;
                }

                if (!mc.player.onGround) {
                    mc.timer.timerSpeed = towerBoostValue.getValue().floatValue();
                }
            }
        }
        if (data != null) {
            float[] rot = this.getRotationsBlock(data.pos, data.face);
            this.rotationYawSave = rot[0];
            this.rotationPitchSave = rot[1];
        }
    }

    public float[] getRotationsBlock(BlockPos block, EnumFacing face) {
        double x = (double)block.getX() + 0.5 - mc.player.posX + (double)face.getFrontOffsetX() / 2.0;
        double z = (double)block.getZ() + 0.5 - mc.player.posZ + (double)face.getFrontOffsetZ() / 2.0;
        double y = (double)block.getY() + 0.5;
        double d1 = mc.player.posY + (double)mc.player.getEyeHeight() - y;
        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(Math.atan2(d1, d3) * 180.0 / Math.PI);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[]{yaw, pitch};
    }

    @EventTarget
    private void onPostUpdate(EventPostUpdate event) {
        if (this.getBlockCount() <= 0) {
            int spoofSlot = this.getBestSpoofSlot();
            this.getBlock(spoofSlot);
        }
        double yDif = 1.0;
        BlockData data = null;
        for (double posY = mc.player.posY - 1.0; posY > 0.0; posY -= 1.0) {
            BlockData newData = this.getBlockData(new BlockPos(mc.player.posX, posY, mc.player.posZ));
            if (newData == null || !((yDif = mc.player.posY - posY) <= 3.0)) continue;
            data = newData;
            break;
        }
        int slot = -1;
        int blockCount = 0;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
            if (itemStack == null) continue;
            int stackSize = itemStack.stackSize;
            if (!this.isValidItem(itemStack.getItem()) || stackSize <= blockCount) continue;
            blockCount = stackSize;
            slot = i;
            this.currentblock = itemStack;
        }
        if (data != null && slot != -1) {
            BlockPos pos = data.pos;
            Block block = mc.world.getBlockState(pos.offset(data.face)).getBlock();
            Vec3 hitVec = this.getVec3(data);
            if (!this.validBlocks.contains(block) || this.isBlockUnder(yDif)) {
                return;
            }
            if (keepYValue.getValue() && !(mc.player.posY - 1 < keepY)) {
                pos.y = keepY;
                return;
            }
            int last = mc.player.inventory.currentItem;
            mc.player.inventory.currentItem = slot;
            if (mc.playerController.onPlayerRightClick(mc.player, mc.world, mc.player.getCurrentEquippedItem(), pos, data.face, hitVec)) {
                if (this.swing.getValue()) {
                    mc.player.swingItem();
                } else {
                    mc.getNetHandler().sendPacket(new C0APacketAnimation());
                }
            }
            mc.player.inventory.currentItem = last;
        }
    }

    public int getBlockCount() {
        int n = 0;
        for (int i = 36; i < 45; ++i) {
            if (!mc.player.inventoryContainer.getSlot(i).getHasStack()) continue;
            ItemStack stack = mc.player.inventoryContainer.getSlot(i).getStack();
            Item item = stack.getItem();
            if (!(stack.getItem() instanceof ItemBlock) || !this.isValid(item)) continue;
            n += stack.stackSize;
        }
        return n;
    }

    public int getallBlockCount() {
        int n = 0;
        for (int i = 0; i < 36; ++i) {
            if (!mc.player.inventoryContainer.getSlot(i).getHasStack()) continue;
            ItemStack stack = mc.player.inventoryContainer.getSlot(i).getStack();
            Item item = stack.getItem();
            if (!(stack.getItem() instanceof ItemBlock) || !this.isValid(item)) continue;
            n += stack.stackSize;
        }
        return n;
    }

    private boolean isValid(Item item) {
        return item instanceof ItemBlock && !invalidBlocks.contains(((ItemBlock)item).getBlock());
    }

    private boolean isBlockUnder(double yOffset) {
        EntityPlayerSP player = mc.player;
        return !this.validBlocks.contains(mc.world.getBlockState(new BlockPos(player.posX, player.posY - yOffset, player.posZ)).getBlock());
    }

    public void swap(int slot1, int hotbarSlot) {
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot1, hotbarSlot, 2, mc.player);
    }

    void getBlock(int hotbarSlot) {
        for (int i = 9; i < 45; ++i) {
            ItemBlock block;
            Minecraft var10000 = mc;
            if (!mc.player.inventoryContainer.getSlot(i).getHasStack() || mc.currentScreen != null && !(mc.currentScreen instanceof GuiInventory)) continue;
            var10000 = mc;
            ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
            if (!(is.getItem() instanceof ItemBlock) || !this.isValidItem(block = (ItemBlock)is.getItem())) continue;
            if (36 + hotbarSlot == i) break;
            this.swap(i, hotbarSlot);
            break;
        }
    }

    int getBestSpoofSlot() {
        int spoofSlot = 5;
        for (int i = 36; i < 45; ++i) {
            if (mc.player.inventoryContainer.getSlot(i).getHasStack()) continue;
            spoofSlot = i - 36;
            break;
        }
        return spoofSlot;
    }

    private Vec3 getVec3(BlockData data) {
        BlockPos pos = data.pos;
        EnumFacing face = data.face;
        double x = (double)pos.getX() + 0.5;
        double y = (double)pos.getY() + 0.5;
        double z = (double)pos.getZ() + 0.5;
        x += (double)face.getFrontOffsetX() / 2.0;
        z += (double)face.getFrontOffsetZ() / 2.0;
        y += (double)face.getFrontOffsetY() / 2.0;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += this.randomNumber(0.3, -0.3);
            z += this.randomNumber(0.3, -0.3);
        } else {
            y += this.randomNumber(0.49, 0.5);
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += this.randomNumber(0.3, -0.3);
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += this.randomNumber(0.3, -0.3);
        }
        return new Vec3(x, y, z);
    }

    private double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    private BlockData getBlockData(BlockPos pos) {
        BlockPos[] blockPositions = this.blockPositions;
        EnumFacing[] facings = this.facings;
        List<Block> validBlocks = this.validBlocks;
        WorldClient world = mc.world;
        double playerYaw = MoveUtils.getDirection();
        double x = mc.player.posX;
        double y = mc.player.posY;
        double z = mc.player.posZ;
        double x2 = -Math.sin(playerYaw);
        double z2 = Math.cos(playerYaw);
        BlockPos posBelow = new BlockPos(0, -1, 0);
        if (!validBlocks.contains(world.getBlockState(pos.add(posBelow)).getBlock())) {
            return new BlockData(pos.add(posBelow), EnumFacing.UP);
        }
        int blockPositionsLength = blockPositions.length;
        for (int i = 0; i < blockPositionsLength; ++i) {
            BlockPos blockPos = pos.add(blockPositions[i]);
            if (!validBlocks.contains(world.getBlockState(blockPos).getBlock())) {
                return new BlockData(blockPos, facings[i]);
            }
            for (int i1 = 0; i1 < blockPositionsLength; ++i1) {
                BlockPos blockPos1 = pos.add(blockPositions[i1]);
                BlockPos blockPos2 = blockPos.add(blockPositions[i1]);
                if (!validBlocks.contains(world.getBlockState(blockPos1).getBlock())) {
                    return new BlockData(blockPos1, facings[i1]);
                }
                if (validBlocks.contains(world.getBlockState(blockPos2).getBlock())) continue;
                return new BlockData(blockPos2, facings[i1]);
            }
        }
        return null;
    }

    private EnumFacing getClosestEnum(BlockPos pos) {
        EnumFacing closestEnum = EnumFacing.UP;
        float rotations = MathHelper.wrapAngleTo180_float(getRotations(pos, EnumFacing.UP)[0]);
        if (rotations >= 45.0f && rotations <= 135.0f) {
            closestEnum = EnumFacing.EAST;
        } else if (rotations >= 135.0f && rotations <= 180.0f || rotations <= -135.0f && rotations >= -180.0f) {
            closestEnum = EnumFacing.SOUTH;
        } else if (rotations <= -45.0f && rotations >= -135.0f) {
            closestEnum = EnumFacing.WEST;
        } else if (rotations >= -45.0f && rotations <= 0.0f || rotations <= 45.0f && rotations >= 0.0f) {
            closestEnum = EnumFacing.NORTH;
        }
        if (MathHelper.wrapAngleTo180_float(getRotations(pos, EnumFacing.UP)[1]) > 75.0f || MathHelper.wrapAngleTo180_float(getRotations(pos, EnumFacing.UP)[1]) < -75.0f) {
            closestEnum = EnumFacing.UP;
        }
        return closestEnum;
    }

    public static float[] getRotations(BlockPos block, EnumFacing face) {
        double x = (double)block.getX() + 0.5 - mc.player.posX + (double)face.getFrontOffsetX() / 2.0;
        double z = (double)block.getZ() + 0.5 - mc.player.posZ + (double)face.getFrontOffsetZ() / 2.0;
        double d1 = mc.player.posY + (double)mc.player.getEyeHeight() - ((double)block.getY() + 0.5);
        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(Math.atan2(d1, d3) * 180.0 / Math.PI);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[]{yaw, pitch};
    }

    private boolean isValidItem(Item item) {
        if (item instanceof ItemBlock) {
            ItemBlock iBlock = (ItemBlock)item;
            Block block = iBlock.getBlock();
            return !invalidBlocks.contains(block);
        }
        return false;
    }

    private float getRotationWhole(float yaw, float playerYaw) {
        if (yaw > 0.0f && yaw < 45.0f) {
            return 45.0f;
        }
        if (yaw > 45.0f && yaw < 90.0f) {
            return 90.0f;
        }
        if (yaw > 90.0f && yaw < 135.0f) {
            return 135.0f;
        }
        if (yaw > 135.0f && yaw < 180.0f) {
            return 180.0f;
        }
        if (yaw > 180.0f && yaw < 225.0f) {
            return 225.0f;
        }
        if (yaw > 225.0f && yaw < 270.0f) {
            return 270.0f;
        }
        if (yaw > 270.0f && yaw < 315.0f) {
            return 315.0f;
        }
        if (yaw > 315.0f && yaw < 360.0f) {
            return 360.0f;
        }
        if (yaw < 0.0f && yaw > -45.0f) {
            return -45.0f;
        }
        if (yaw < -45.0f && yaw > -90.0f) {
            return -90.0f;
        }
        if (yaw < -90.0f && yaw > -135.0f) {
            return -135.0f;
        }
        if (yaw < -135.0f && yaw > -180.0f) {
            return -180.0f;
        }
        if (yaw < -180.0f && yaw > -225.0f) {
            return -225.0f;
        }
        if (yaw < -225.0f && yaw > -270.0f) {
            return -270.0f;
        }
        if (yaw < -270.0f && yaw > -315.0f) {
            return -315.0f;
        }
        if (yaw < -315.0f && yaw > -360.0f) {
            return -360.0f;
        }
        return playerYaw + 180.0f;
    }

    private static class BlockData {
        public final BlockPos pos;
        public final EnumFacing face;

        private BlockData(BlockPos pos, EnumFacing face) {
            this.pos = pos;
            this.face = face;
        }
    }
}
