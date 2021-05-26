package love.target.mod.mods.player;

import com.utils.ObjectUtils;
import love.target.eventapi.EventTarget;
import love.target.events.EventTick;
import love.target.mod.Mod;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastEat extends Mod {
    public FastEat() {
        super("FastEat", Category.PLAYER);
    }

    @EventTarget
    public void onTick(EventTick e) {
        if (mc.player.getHeldItem() != null) {
            if (mc.player.isUsingItem() && ObjectUtils.reverse(mc.player.getHeldItem().getItem() instanceof ItemSword) && ObjectUtils.reverse(mc.player.getHeldItem().getItem() instanceof ItemBow)) {
                for (int i = 0; i < 3; ++i) {
                    mc.getNetHandler().sendPacket(new C03PacketPlayer(!mc.player.onGround));
                    mc.getNetHandler().sendPacket(new C03PacketPlayer(mc.player.onGround));
                }
            }
        }
    }
}
