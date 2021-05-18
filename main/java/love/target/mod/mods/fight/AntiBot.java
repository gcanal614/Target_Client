package love.target.mod.mods.fight;

import love.target.Wrapper;
import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AntiBot extends Mod{
    public static ArrayList<EntityPlayer> Bots = new ArrayList<>();
    public static List<EntityPlayer> invalid = new ArrayList<>();
    int bots = 0;

    public AntiBot() {
        super("AntiBot", Category.FIGHT);
    }

    @EventTarget
    public void onUpdate(EventPreUpdate event) {
        Iterator<Entity> var2 = mc.world.loadedEntityList.iterator();

        while(var2.hasNext()) {
            Entity e = var2.next();
            if (e != mc.player && e instanceof EntityPlayer && e.onGround && !e.isInvisible() && (int)e.posX == (int)mc.player.posX && (int)e.posZ == (int)mc.player.posZ && !getTabPlayerList().contains(e) && (int)e.posY != (int)mc.player.posY) {
                mc.world.removeEntity(e);
                Wrapper.sendMessage("Kill 1 Watchdog!!!");
            }
        }

        var2 = mc.world.loadedEntityList.iterator();

        while(true) {
            while(true) {
                if (!var2.hasNext()) {
                    return;
                }

                Object entities = var2.next();
                if (!(entities instanceof EntityPlayer)) {
                    break;
                }

                EntityPlayer entityPlayer2;
                EntityPlayer entity = entityPlayer2 = (EntityPlayer)entities;
                if (entityPlayer2 == mc.player) {
                    break;
                }

                if (!entity.getDisplayName().getFormattedText().contains("§") || entity.getDisplayName().getFormattedText().toLowerCase().contains("npc") || entity.getDisplayName().getFormattedText().toLowerCase().contains("§r") || entity.isInvisible()) {
                    Bots.add(entity);
                }

                if (Bots.contains(entity)) {
                    Bots.remove(entity);
                    break;
                }
            }

            this.bots = 0;
            Iterator var9 = mc.world.getLoadedEntityList().iterator();

            while(var9.hasNext()) {
                Entity entity2 = (Entity)var9.next();
                if (entity2 instanceof EntityPlayer) {
                    EntityPlayer entityPlayer;
                    EntityPlayer ent = entityPlayer = (EntityPlayer)entity2;
                    if (entityPlayer != mc.player && ent.isInvisible() && ent.ticksExisted > 25) {
                        if (getTabPlayerList().contains(ent)) {
                            if (ent.isInvisible()) {
                                ent.setInvisible(false);
                            }
                        } else {
                            ent.setInvisible(false);
                            ++this.bots;
                            mc.world.removeEntity(ent);
                        }
                    }
                }
            }

            if (this.bots != 0) {
                Wrapper.sendMessage("Kill " + this.bots + " Bot!!");
            }
        }
    }

    public boolean isInGodMode(Entity entity) {
        return this.isEnabled() && entity.ticksExisted <= 25;
    }

    public void onEnable() {
        Bots.clear();
    }

    public void onDisable() {
        Bots.clear();
    }

    public static List<EntityPlayer> getTabPlayerList() {
        ArrayList<EntityPlayer> list = new ArrayList();
        List<NetworkPlayerInfo> players = GuiPlayerTabOverlay.field_175252_a.sortedCopy(mc.getNetHandler().getPlayerInfoMap());
        Iterator var2 = players.iterator();

        while(var2.hasNext()) {
            Object o = var2.next();
            NetworkPlayerInfo info = (NetworkPlayerInfo)o;
            if (info != null) {
                list.add(mc.world.getPlayerEntityByName(info.getGameProfile().getName()));
            }
        }

        return list;
    }
}
