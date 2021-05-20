package love.target.mod.mods.fight;

import love.target.Wrapper;
import love.target.eventapi.EventTarget;
import love.target.events.EventPacket;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.ModeValue;
import love.target.mod.value.values.NumberValue;
import love.target.utils.TimerUtil;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Critical extends Mod {
    private final ModeValue mode = new ModeValue("Mode","Vanilla",new String[]{"Vanilla","Packet","NoGround","LowHop","Jump"});
    private final NumberValue delayValue = new NumberValue("Delay",100,0,1000,1);
    private final BooleanValue onGroundCheck = new BooleanValue("OnGroundCheck",true);

    private final TimerUtil delayTimerUtil = new TimerUtil();

    private boolean canSetUpdateOnGround = false;

    public Critical() {
        super("Critical",Category.FIGHT);
        addValues(mode,delayValue,onGroundCheck);
    }

    @EventTarget
    public void onUpdate(EventPreUpdate e) {
        if (canSetUpdateOnGround) {
            if (mc.player.onGround) {
                e.setOnGround(false);
            }
            canSetUpdateOnGround = false;
        }
    }

    @EventTarget
    public void onPacket(EventPacket e) {
        if (e.getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity) e.getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK) {
            if (onGroundCheck.getValue() && !mc.player.onGround) {
                return;
            }

            if (delayTimerUtil.hasReached(delayValue.getValue().longValue())) {
                switch (mode.getValue()) {
                    case "Vanilla":
                        critical(new double[]{0.11D, 0.1100013579D, 1.3579E-6D});
                        break;
                    case "Packet":
                        critical(new double[]{0.04250000001304D, 0.00150000001304D, 0.01400000001304D, 0.00150000001304D});
                        break;
                    case "NoGround":
                        canSetUpdateOnGround = true;
                        break;
                    case "LowHop":
                        if (mc.player.onGround) {
                            mc.player.motionY = 0.2;
                        }
                        break;
                    case "Jump":
                        if (mc.player.onGround) {
                            mc.player.jump();
                        }
                        break;
                }
                delayTimerUtil.reset();
            }
        }
    }

    private void critical(double[] value) {
        for(double offset : value) {
            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.player.posX, mc.player.posY + offset, mc.player.posZ, false));
        }
    }
}
