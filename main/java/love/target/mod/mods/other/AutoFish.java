package love.target.mod.mods.other;

import love.target.eventapi.EventTarget;
import love.target.events.EventPacket;
import love.target.mod.Mod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class AutoFish extends Mod {
    public AutoFish() {
        super("AutoFish", Category.OTHER);
    }

    @EventTarget
    public void onUpdate(EventPacket e) {
        if (e.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) e.getPacket();
            Entity ent = mc.world.getEntityByID(packet.getEntityID());
            if (ent instanceof EntityFishHook) {
                EntityFishHook fishHook = (EntityFishHook) ent;
                int entityId = fishHook.angler.getEntityId();
                if (entityId == mc.player.getEntityId()) {
                    if (mc.player.inventory.currentItem != this.grabRodSlot()) {
                        return;
                    }
                    if (packet.getMotionX() == 0 && packet.getMotionY() != 0 && packet.getMotionZ() == 0) {
                        mc.rightClickMouse();
                        mc.rightClickMouse();
                    }
                }
            }
        }
    }
    
    private int grabRodSlot() {
        for (int i2 = 0; i2 < 9; ++i2) {
             ItemStack itemStack = mc.player.inventory.mainInventory[i2];
            if (itemStack != null && itemStack.getItem() instanceof ItemFishingRod) {
                return i2;
            }
        }
        return -1;
    }
}
