package love.target.mod.mods.move;

import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.mod.value.values.BooleanValue;
import love.target.utils.MoveUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class LongJump extends Mod {
    private final BooleanValue toggle = new BooleanValue("Toggle",true);
    private int airTicks = 0;

    public LongJump() {
        super("LongJump",Category.MOVE);
        addValues(toggle);
    }

    @EventTarget
    public void onPre(EventPreUpdate e) {
        if (airTicks > 0 && mc.player.onGround) {
            airTicks = 0;
            if (toggle.getValue()) {
                toggle();
                return;
            }
        }
        if (mc.player.isMoving()) {
            if (mc.player.onGround) {
                int i = 0;
                while ((double) i < (double) getMaxFallDist() / 0.05510000046342611 + 1.0) {
                    mc.getNetHandler().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.player.posX, mc.player.posY + 0.060100000351667404, mc.player.posZ, false));
                    mc.getNetHandler().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.player.posX, mc.player.posY + 5.000000237487257E-4, mc.player.posZ, false));
                    mc.getNetHandler().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.player.posX, mc.player.posY + 0.004999999888241291 + 6.01000003516674E-8, mc.player.posZ, false));
                    ++i;
                }
                mc.getNetHandler().sendPacket(new C03PacketPlayer(true));
                MoveUtils.setSpeed(0.4);
                mc.player.motionY = 0.55;
            } else {
                MoveUtils.setSpeed(Math.max(0.39,MoveUtils.getSpeed()));
                airTicks++;
            }
        }
    }

    @Override
    protected void onDisable() {
        airTicks = 0;
        super.onDisable();
    }

    private float getMaxFallDist() {
        PotionEffect potioneffect = mc.player.getActivePotionEffect(Potion.jump);
        int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
        return mc.player.getMaxFallHeight() + f;
    }
}
