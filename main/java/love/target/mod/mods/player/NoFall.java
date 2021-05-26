package love.target.mod.mods.player;

import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.mod.value.values.ModeValue;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.world.gen.layer.GenLayerEdge;

public class NoFall extends Mod {
    private final ModeValue mode = new ModeValue("Mode","Watchdog",new String[]{"Watchdog","Matrix"});

    public NoFall() {
        super("NoFall",Category.PLAYER);
        addValues(mode);
    }

    @EventTarget
    public void onPre(EventPreUpdate e) {
        switch (mode.getValue()) {
            case "Watchdog":
                if (mc.player.fallDistance > 3 && !AntiVoid.isOverVoid()) {
                    mc.getNetHandler().sendPacket(new C03PacketPlayer(true));
                }
                break;
            case "Matrix":
                if (mc.player.fallDistance > 3.0f && !AntiVoid.isOverVoid()) {
                    mc.getNetHandler().sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.rotationYaw, mc.player.rotationPitch, true));
                    mc.player.fallDistance = 0.0f;
                }
                break;
        }

    }
}
