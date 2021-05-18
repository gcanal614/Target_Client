package love.target.mod.mods.fight;

import love.target.Wrapper;
import love.target.eventapi.EventTarget;
import love.target.events.EventPostUpdate;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.mod.mods.other.Teams;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.ModeValue;
import love.target.mod.value.values.NumberValue;
import love.target.utils.RotationUtil;
import love.target.utils.TimerUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class KillAura extends Mod {
    private final TimerUtil timer = new TimerUtil();
    private final TimerUtil SwitchTimer = new TimerUtil();
    public static EntityLivingBase curtarget;
    public static boolean isBlockReach;
    private final org.lwjgl.util.vector.Vector2f lastAngles = new Vector2f(0.0f, 0.0f);
    private List<EntityLivingBase> targets = new CopyOnWriteArrayList<>();
    private int index;
    public static float sYaw;
    public static float sPitch;
    public int xd;
    private static boolean isBlocking;
    public static float Where;
    private boolean isAttacking;
    private final TimerUtil rotationTimerUtil = new TimerUtil();
    private final ModeValue mode = new ModeValue("Mode", "Single");
    private final ModeValue prmode = new ModeValue("TargetMode", "Range");
    private final NumberValue maxCPS = new NumberValue("MaxCPS", 14, 1, 20, 1);
    private final NumberValue minCPS = new NumberValue("MinCPS", 10, 1, 20, 1);
    private final NumberValue hurtTime = new NumberValue("HurtTime", 10, 1, 20, 1);
    private final NumberValue fov = new NumberValue("FOV", 360, 1, 360, 1);
    private final NumberValue reach = new NumberValue("AttackRange", 4.1, 1.0, 8.0, 0.1);
    private final NumberValue throughWallReach = new NumberValue("ThroughWallRange", 3.0, 0.0, 8.0, 0.1);
    private final NumberValue switchDelay = new NumberValue("SwitchDelay", 800, 0, 2000, 1);
    private final BooleanValue blocking = new BooleanValue("AutoBlock", true);
    private final BooleanValue players = new BooleanValue("Players", true);
    private final BooleanValue animals = new BooleanValue("Animals", false);
    private final BooleanValue villager = new BooleanValue("Villager", false);
    private final BooleanValue invis = new BooleanValue("Invisibles", false);
    private final BooleanValue mobs = new BooleanValue("Mobs", true);
    private final BooleanValue died = new BooleanValue("DeadCheck", true);

    public KillAura() {
        super("KillAura", Category.FIGHT);
        this.mode.addModes("Single", "Switch");
        this.prmode.addModes("Range", "Fov", "Health", "Angle");
        this.addValues(this.mode, this.prmode, this.maxCPS, this.minCPS, this.hurtTime, this.fov, this.reach, this.throughWallReach, this.switchDelay, this.blocking, this.players, this.animals, this.mobs, this.villager, this.invis, this.died);
    }

    private void startBlocking() {
        if (mc.player.getHeldItem().getItem() instanceof ItemSword) {
            isBlocking = true;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            mc.playerController.sendUseItem(mc.player, mc.world, mc.player.inventory.getCurrentItem());
        }
    }

    private void stopBlocking() {
        if (isBlocking && !Mouse.isButtonDown(1)) {
            isBlocking = false;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            mc.playerController.onStoppedUsingItem(mc.player);
            mc.player.itemInUseCount = 0;
        }
    }

    @Override
    public void onDisable() {
        isBlockReach = true;
        this.isAttacking = false;
        if (isBlocking && this.blocking.getValue()) {
            this.stopBlocking();
        }
        this.targets.clear();
        curtarget = null;
        Where = 0.0f;
        this.lastAngles.x = mc.player.rotationYaw;
    }

    @Override
    public void onEnable() {
        isBlockReach = true;
        this.lastAngles.x = mc.player.rotationYaw;
        Where = 0.0f;
        curtarget = null;
        this.index = 0;
        this.xd = 0;
    }

    private boolean canBlock() {
        return mc.player.inventory.getCurrentItem() != null && mc.player.inventory.getCurrentItem().getItem() instanceof ItemSword;
    }

    private boolean shouldAttack() {
        int APS = 20 / randomNumbercheck(this.maxCPS.getValue().intValue(), this.minCPS.getValue().intValue());
        if (this.timer.hasReached(50 * APS)) {
            this.timer.reset();
            return true;
        }
        return false;
    }

    private void SwitchTarget() {
        if ((double)mc.player.getDistanceToEntity(this.targets.get(this.index)) > this.reach.getValue()) {
            ++this.index;
        }
        if (this.mode.isCurrentValue("Switch") && this.index < this.targets.size()) {
            ++this.index;
        }
    }

    public static int randomNumbercheck(int max, int min) {
        return (int)(Math.random() * (double)(max - min)) + min;
    }

    @EventTarget
    public void onUpdate(EventPreUpdate e) {
        this.targets = this.sortList(this.loadTargets());
        if (curtarget != null && curtarget instanceof EntityPlayer || curtarget instanceof EntityMob || curtarget instanceof EntityAnimal || curtarget instanceof EntityGolem || curtarget instanceof EntityDragon || curtarget instanceof EntitySlime || curtarget instanceof EntityGhast || curtarget instanceof EntitySquid || curtarget instanceof EntityBat || curtarget instanceof EntityVillager) {
            curtarget = null;
        }
        if (this.mode.isCurrentValue("Switch")) {
            if (this.targets.size() > 1 && this.SwitchTimer.hasReached(this.switchDelay.getValue().longValue())) {
                this.SwitchTarget();
                this.SwitchTimer.reset();
            }
        } else if (this.targets.size() > 1 && mc.player.ticksExisted % 2 == 0 && this.mode.isCurrentValue("Single")) {
            this.index = 0;
        }
        if (!this.targets.isEmpty()) {
            if (this.index >= this.targets.size()) {
                this.index = 0;
            }
            if ((curtarget = this.targets.get(this.index)) != null) {
                float[] rot = getNewRotations(curtarget);
                e.setYaw(rot[0]);
                e.setPitch(rot[1]);
                mc.player.rotationYawHead = rot[0];
                mc.player.renderYawOffset = rot[0];
                mc.player.rotationPitchHead = rot[1];
                if (curtarget.hurtTime >= this.hurtTime.getValue()) {
                    return;
                }
                sYaw = mc.player.rotationYaw;
                sPitch = mc.player.rotationPitch;
            }
        } else {
            Where = 0.0f;
        }
    }

    @EventTarget
    public void onPost(EventPostUpdate e) {
        if (curtarget != null) {
            if (this.blocking.getValue() && this.canBlock() && !this.isAttacking) {
                this.startBlocking();
            }
            if (curtarget.hurtTime >= this.hurtTime.getValue()) {
                return;
            }
            if (this.shouldAttack()) {
                this.attack();
            }
        } else {
            this.lastAngles.x = mc.player.rotationYaw;
            if (isBlocking && this.blocking.getValue()) {
                this.stopBlocking();
            }
        }
    }

    private List<EntityLivingBase> loadTargets() {
        return (List)mc.world.loadedEntityList.stream().filter((e2) -> {
            return this.qualifies(e2);
        }).collect(Collectors.toList());
    }

    private boolean isInFOV(Entity entity) {
        int fov1 = this.fov.getValue().intValue();
        return RotationUtil.getDistanceBetweenAngles((float)entity.posX, (float)entity.posZ) <= (float)fov1;
    }

    private boolean qualifies(Entity e2) {
        if (!((double)mc.player.getDistanceToEntity(e2) <= this.reach.getValue())) {
            return false;
        }
        if (!this.isInFOV(e2)) {
            return false;
        }
        if (!RotationUtil.canEntityBeSeen(e2) && (double)mc.player.getDistanceToEntity(e2) > this.throughWallReach.getValue()) {
            return false;
        }
        if (e2.isInvisible() && !this.invis.getValue()) {
            return false;
        }
        if (!e2.isEntityAlive()) {
            return false;
        }
        if (e2 == mc.player || this.died.getValue() && e2.isDead || mc.player.getHealth() == 0.0f) {
            return false;
        }
        if ((e2 instanceof EntityMob || e2 instanceof EntityGhast || e2 instanceof EntityGolem || e2 instanceof EntityDragon || e2 instanceof EntitySlime) && ((Boolean)this.mobs.getValue()).booleanValue()) {
            return true;
        }
        if (e2 instanceof EntityAnimal && this.animals.getValue()) {
            return true;
        }
        if (e2 instanceof EntityVillager && this.villager.getValue()) {
            return true;
        }
        return e2 instanceof EntityPlayer && this.players.getValue() && !Teams.isOnSameTeam(e2);
    }

    public void attack() {
        if ((double)mc.player.getDistanceToEntity(curtarget) > this.reach.getValue()) {
            isBlockReach = true;
            return;
        }
        this.isAttacking = true;
        if (this.blocking.getValue()) {
            this.stopBlocking();
        }
        mc.playerController.syncCurrentPlayItem();
        mc.player.swingItem();
        mc.getNetHandler().sendPacket(new C02PacketUseEntity((Entity)curtarget, C02PacketUseEntity.Action.ATTACK));
        if (this.blocking.getValue() && this.canBlock()) {
            this.startBlocking();
        }
        this.isAttacking = false;
    }

    private List<EntityLivingBase> sortList(List<EntityLivingBase> list) {
        if (this.prmode.isCurrentValue("Health")) {
            list.sort((o1, o2) -> (int)(o1.getHealth() - o2.getHealth()));
        }
        if (this.prmode.isCurrentValue("Angle")) {
            list.sort((o1, o2) -> {
                float[] rot1 = getNewRotations(o1);
                float[] rot2 = getNewRotations(o2);
                return (int)(mc.player.rotationYaw - rot1[0] - mc.player.rotationYaw - rot2[0]);
            });
        }
        if (this.prmode.isCurrentValue("Range")) {
            list.sort((o1, o2) -> (int)(o1.getDistanceToEntity(mc.player) - o2.getDistanceToEntity(mc.player)));
        }
        return list;
    }

    public static float[] getNewRotations(EntityLivingBase curTarget2) {
        double diffX = curTarget2.posX - mc.player.posX;
        double diffZ = curTarget2.posZ - mc.player.posZ;
        double diffY = curTarget2.posY - (mc.player.posY + (double)mc.player.getEyeHeight());
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.114514) - 90.0f;
        float pitch = (float)(-Math.atan2(diffY, dist) * 180.0 / 6.0);
        return new float[]{yaw, pitch};
    }
}
