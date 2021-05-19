package love.target.mod.mods.player;

import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.NumberValue;
import love.target.utils.RotationUtil;
import love.target.utils.TimerUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ok我是州长粉丝
 */

public class Terminator extends Mod {
    private final NumberValue range = new NumberValue("Range",5,0,10,0.1);
    private final NumberValue cps = new NumberValue("CPS",5,1,20,1);
    private final BooleanValue player = new BooleanValue("Player",true);
    private final BooleanValue monster = new BooleanValue("Monster",false);
    private final BooleanValue animal = new BooleanValue("Animal",false);

    private final TimerUtil cpsTimerUtil = new TimerUtil();

    public Terminator() {
        super("Terminator", Category.PLAYER);
        addValues(range,cps,player,monster,animal);
    }

    @EventTarget
    public void onPre(EventPreUpdate e) {
        List<EntityLivingBase> inRangeEntities = new CopyOnWriteArrayList<>();

        for (Entity entity : mc.world.loadedEntityList) {
            if (mc.player.getDistanceToEntity(entity) <= range.getValue() && shouldAdd(entity) && entity instanceof EntityLivingBase) {
                inRangeEntities.add((EntityLivingBase) entity);
            }
        }

        inRangeEntities.sort((e1,e2) -> (int) ((mc.player.getDistanceToEntity(e1) - mc.player.getDistanceToEntity(e2))));

        if (!inRangeEntities.isEmpty()) {
            EntityLivingBase currentEntity = inRangeEntities.get(0);

            if (currentEntity.isDead) {
                inRangeEntities.clear();
                return;
            }

            float[] rotationValue = RotationUtil.getPredictedRotations(currentEntity);

            mc.player.rotationYaw = rotationValue[0];
            mc.player.rotationPitch = rotationValue[1];

            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);

            if (shouldAttack()) {
                mc.clickMouse();
            }
        }
    }

    @Override
    protected void onDisable() {
        super.onDisable();
    }

    private boolean shouldAttack() {
        int APS = 20 / cps.getValue().intValue();
        if (cpsTimerUtil.hasReached(50 * APS)) {
            cpsTimerUtil.reset();
            return true;
        }
        return false;
    }

    private boolean shouldAdd(Entity entity) {
        if (entity == mc.player) {
            return false;
        }

        if (entity.isDead) {
            return false;
        }

        if (entity instanceof EntityPlayer && player.getValue()) {
            return true;
        }

        if (entity instanceof EntityMob && monster.getValue()) {
            return true;
        }

        return entity instanceof EntityAnimal && animal.getValue();
    }
}
