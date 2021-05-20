package love.target.mod.mods.player;

import love.target.eventapi.EventTarget;
import love.target.events.EventPacket;
import love.target.mod.Mod;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class ChatBypass extends Mod {
    public ChatBypass() {
        super("ChatBypass",Category.PLAYER);
    }

    @EventTarget
    public void onPacket(EventPacket e) {
        if (e.getPacket() instanceof C01PacketChatMessage && e.isClientPacket()) {
            C01PacketChatMessage packet = (C01PacketChatMessage) e.getPacket();

            if (packet.getMessage().startsWith("/") || packet.getMessage().startsWith("-")) {
                return;
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (char c : packet.getMessage().toCharArray()) {
                stringBuilder.append(c).append("\u0898");
            }
            packet.setMessage(stringBuilder.toString());
        }
    }
}
