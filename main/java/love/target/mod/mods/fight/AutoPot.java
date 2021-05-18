package love.target.mod.mods.fight;

import love.target.eventapi.EventTarget;
import love.target.events.EventPreUpdate;
import love.target.mod.Mod;
import love.target.mod.value.values.BooleanValue;
import love.target.mod.value.values.NumberValue;
import love.target.utils.RotationUtil;
import love.target.utils.skid.Timer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.PotionEffect;

/**
 * I LOVE SKID :D
 * @author LeakedPvP
 */
public class AutoPot extends Mod {
	public static final BooleanValue SPEED = new BooleanValue("SpeedPotion",true);
	public static final BooleanValue REGEN = new BooleanValue("RegenPotion",true);
	public static final NumberValue HEALTH = new NumberValue("Health",6,0.5,20.0,0.5);
	public static final BooleanValue PREDICT = new BooleanValue("Predict",false);
    public static boolean potting;
    Timer timer = new Timer();

    public AutoPot() {
        super("AutoPot",Category.FIGHT);
        addValues(SPEED,REGEN,HEALTH,PREDICT);
    }

    @EventTarget
    public void onPre(EventPreUpdate event) {
        final boolean speed = SPEED.getValue();
        final boolean regen = REGEN.getValue();
        if (timer.check(200)) {
            if (potting)
                potting = false;
        }
        int spoofSlot = getBestSpoofSlot();
        int pots[] = {6, -1, -1};
        if (regen)
            pots[1] = 10;
        if (speed)
            pots[2] = 1;

        for (int i = 0; i < pots.length; i++) {
            if (pots[i] == -1)
                continue;
            if (pots[i] == 6 || pots[i] == 10) {
                if (timer.check(900) && !mc.player.isPotionActive(pots[i])) {
                    if (mc.player.getHealth() < (HEALTH.getValue())) {
                        getBestPot(spoofSlot, pots[i]);
                    }
                }
            } else if (timer.check(1000) && !mc.player.isPotionActive(pots[i])) {
                getBestPot(spoofSlot, pots[i]);
            }
        }
    }
  
    public void swap(int slot1, int hotbarSlot){
    	mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot1, hotbarSlot, 2, mc.player);
    }
    float[] getRotations(){    	
        double movedPosX = mc.player.posX + mc.player.motionX * 26.0D;
        double movedPosY = mc.player.getEntityBoundingBox().minY - 3.6D;
        double movedPosZ = mc.player.posZ + mc.player.motionZ * 26.0D;	
        if(PREDICT.getValue())
        	return RotationUtil.getRotationFromPosition(movedPosX, movedPosZ, movedPosY);
        else
        	return new float[]{mc.player.rotationYaw, 90};
    }
    int getBestSpoofSlot(){  	
    	int spoofSlot = 5;
    	for (int i = 36; i < 45; i++) {       		
    		if (!mc.player.inventoryContainer.getSlot(i).getHasStack()) {
     			spoofSlot = i - 36;
     			break;
            }else if(mc.player.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemPotion) {
            	spoofSlot = i - 36;
     			break;
            }
        }
    	return spoofSlot;
    }
    void getBestPot(int hotbarSlot, int potID){
    	for (int i = 9; i < 45; i++) {
            if (mc.player.inventoryContainer.getSlot(i).getHasStack() &&(mc.currentScreen == null || mc.currentScreen instanceof GuiInventory)) {
                ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                if(is.getItem() instanceof ItemPotion){
              	  ItemPotion pot = (ItemPotion)is.getItem();
              	  if(pot.getEffects(is).isEmpty())
              		  return;
              	  PotionEffect effect = pot.getEffects(is).get(0);
                  int potionID = effect.getPotionID();
                  if(potionID == potID)
              	  if(ItemPotion.isSplash(is.getItemDamage()) && isBestPot(pot, is)){
              		  if(36 + hotbarSlot != i)
              			  swap(i, hotbarSlot);
              		  timer.reset();
              		  boolean canpot = true;
              		  int oldSlot = mc.player.inventory.currentItem;
              		  mc.getNetHandler().sendPacket(new C09PacketHeldItemChange(hotbarSlot));
          			  mc.getNetHandler().sendPacket(new C03PacketPlayer.C05PacketPlayerLook(getRotations()[0], getRotations()[1], mc.player.onGround));
          			  mc.getNetHandler().sendPacket(new C08PacketPlayerBlockPlacement(mc.player.inventory.getCurrentItem()));
          			  mc.getNetHandler().sendPacket(new C09PacketHeldItemChange(oldSlot));
          			  potting = true;
          			  break;
              	  }               	  
                }              
            }
        }
    }
    
    boolean isBestPot(ItemPotion potion, ItemStack stack){
    	if(potion.getEffects(stack) == null || potion.getEffects(stack).size() != 1)
    		return false;
        PotionEffect effect = (PotionEffect) potion.getEffects(stack).get(0);
        int potionID = effect.getPotionID();
        int amplifier = effect.getAmplifier(); 
        int duration = effect.getDuration();
    	for (int i = 9; i < 45; i++) {    		
            if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {           	
                ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                if(is.getItem() instanceof ItemPotion){
                	ItemPotion pot = (ItemPotion)is.getItem();
                	 if (pot.getEffects(is) != null) {
                         for (Object o : pot.getEffects(is)) {
                             PotionEffect effects = (PotionEffect) o;
                             int id = effects.getPotionID();
                             int ampl = effects.getAmplifier(); 
                             int dur = effects.getDuration();
                             if (id == potionID && ItemPotion.isSplash(is.getItemDamage())){
                            	 if(ampl > amplifier){
                            		 return false;
                            	 }else if (ampl == amplifier && dur > duration){
                            		 return false;
                            	 }
                             }                            
                         }
                     }
                }
            }
        }
    	return true;
    }
}
