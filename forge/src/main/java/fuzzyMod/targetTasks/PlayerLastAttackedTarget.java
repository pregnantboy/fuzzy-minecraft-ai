package fuzzyMod.targetTasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.item.Item;
import net.minecraft.util.MovingObjectPosition;

public class PlayerLastAttackedTarget 
{
    private static EntityLivingBase theTarget;
    private static Item lastAttackedItem;
    private static boolean activated;

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public static boolean shouldExecute(EntityMobWithInventory mob)
    {
  
    	if (theTarget == null) {
    		return false;
    	} 
    	if (theTarget.getEntityId() == mob.getEntityId()) {  
    		mob.setAttackTarget(null);
    		return false;
    	}
    	if (mob.getDistanceToEntity(theTarget) > 30.0D) {
    		return false;
    	}
    	return true;
    }
    
    public static void updateLastTarget () {
    	
    	EntityLivingBase player = Minecraft.getMinecraft().thePlayer;

        if (player == null)
        {
            return;
        }
        else 
        {
        	Minecraft mc = Minecraft.getMinecraft();       	
        	if (player.getHeldItem()!= null) {
        		if (player.isSwingInProgress) {
		        	MovingObjectPosition objectMouseOver = mc.objectMouseOver;
		        	if(objectMouseOver != null && objectMouseOver.entityHit != null) {
		        		Entity targetCandidate = mc.objectMouseOver.entityHit;
		        		if (mc.objectMouseOver.typeOfHit ==MovingObjectPosition.MovingObjectType.ENTITY && targetCandidate instanceof EntityLivingBase) {
		        			theTarget = (EntityLivingBase) player.getEntityWorld().getEntityByID(targetCandidate.getEntityId());
		        			lastAttackedItem = player.getHeldItem().getItem();
		        		}
		        	}
        		}
        	}
        }
    }
    
    public static EntityMobWithInventory getLastTarget(EntityMobWithInventory mob) {
    	 if (theTarget != null && theTarget instanceof EntityMobWithInventory) {
    		 if (theTarget.getEntityId() == mob.getEntityId()) {  
    			 return null;
    		 }
    		 if (mob.getDistanceToEntity(theTarget) > 50.0D) {
    	    	return null;
    	     }
    		 return (EntityMobWithInventory)theTarget;
    	 }
    	 return null;
    }
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
  
}