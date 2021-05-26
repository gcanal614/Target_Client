package love.target.mod.mods.move;

import com.mojang.authlib.GameProfile;
import love.target.eventapi.EventTarget;
import love.target.events.EventPacket;
import love.target.events.EventTick;
import love.target.mod.Mod;
import love.target.mod.value.values.NumberValue;
import love.target.utils.TimerUtil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Blink extends Mod {
    private final NumberValue delay = new NumberValue("Delay", 0.0, 0.0, 3000.0, 1.0);
    private EntityOtherPlayerMP blinkEntity;
    private final List<Packet<?>> packetList = new CopyOnWriteArrayList<>();
    private boolean blinking = false;
    private final TimerUtil timerUtil = new TimerUtil();

    public Blink() {
        super("Blink", Category.MOVE);
        addValues(delay);
    }

    @Override
    public void onEnable() {
        startBlink();
    }

    @EventTarget
    public void onTick(EventTick e) {
        if (delay.getValue() != 0.0 && timerUtil.hasReached(delay.getValue().longValue()) && blinking) {
            try {
                blinking = false;
                stopBlink();
                startBlink();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            timerUtil.reset();
        }
    }

    @EventTarget
    private void onPacketSend(EventPacket event) {
        if (blinking && (event.getPacket() instanceof C0BPacketEntityAction || event.getPacket() instanceof C03PacketPlayer || event.getPacket() instanceof C02PacketUseEntity || event.getPacket() instanceof C0APacketAnimation || event.getPacket() instanceof C08PacketPlayerBlockPlacement)) {
            packetList.add(event.getPacket());
            event.setCancelled(true);
        }
    }

    @Override
    public void onDisable() {
        if (blinking) {
            stopBlink();
        }
        timerUtil.reset();
    }

    private void startBlink() {
        blinkEntity = new EntityOtherPlayerMP(mc.world, new GameProfile(new UUID(69L, 96L), "Blink"));
        blinkEntity.inventory = mc.player.inventory;
        blinkEntity.inventoryContainer = mc.player.inventoryContainer;
        blinkEntity.setPositionAndRotation(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.rotationYaw, mc.player.rotationPitch);
        blinkEntity.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(blinkEntity.getEntityId(), blinkEntity);
        blinking = true;
    }

    private void stopBlink() {
        for (Packet<?> packet : packetList) {
            mc.getNetHandler().sendPacket(packet);
        }
        packetList.clear();
        mc.world.removeEntityFromWorld(blinkEntity.getEntityId());
        blinking = false;
    }
}
