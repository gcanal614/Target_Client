package love.target.mod.mods.fight;

import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.NumberValue;
import love.target.utils.RotationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AutoAim extends Mod {
    private final NumberValue range = new NumberValue("Range",5,0,10,0.1);
    private final BooleanValue silence = new BooleanValue("Silence",false);
    private final BooleanValue onMouseClick = new BooleanValue("OnMouseClick",true);
    private final BooleanValue player = new BooleanValue("Player",true);
    private final BooleanValue monster = new BooleanValue("Monster",false);
    private final BooleanValue animal = new BooleanValue("Animal",false);

    public AutoAim() {
        super("AutoAim",Category.FIGHT);
        addValues(range,silence,onMouseClick,player,monster,animal);
    }

    @EventTarget
    public void onPre(EventPreUpdate e) {
        if (onMouseClick.getValue() && !mc.gameSettings.keyBindAttack.isKeyDown()) return;

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

            if (silence.getValue()) {
                e.setYaw(rotationValue[0]);
                e.setPitch(rotationValue[1]);
                mc.player.rotationYawHead = rotationValue[0];
                mc.player.renderYawOffset = rotationValue[0];
                mc.player.rotationPitchHead = rotationValue[1];
            } else {
                mc.player.rotationYaw = rotationValue[0];
                mc.player.rotationPitch = rotationValue[1];
            }
        }
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
