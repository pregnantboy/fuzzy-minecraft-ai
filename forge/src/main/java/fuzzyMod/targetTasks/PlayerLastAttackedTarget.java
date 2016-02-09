package fuzzyMod.targetTasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.item.Item;
import net.minecraft.util.MovingObjectPosition;

public class PlayerLastAttackedTarget extends EntityAITarget
{
    private EntityMobWithInventory mob;
    private static EntityLivingBase theTarget;
    private static Item lastAttackedItem;
    private Item signalItem;
    private static final String __OBFID = "CL_00003012";

    public PlayerLastAttackedTarget(EntityMobWithInventory mob, Item signalItem)
    {
        super(mob, false);
        this.mob = mob;
        this.signalItem = signalItem;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	if (theTarget == null) {
    		return false;
    	} if (signalItem != lastAttackedItem) {
    		return false;
    	}
    	if (theTarget.getEntityId() == this.mob.getEntityId()) {  
    		this.taskOwner.setAttackTarget(null);
    		return false;
    	}
    	if (this.mob.getDistanceToEntity(theTarget) > 30.0D) {
    		return false;
    	}
    	return true;
    }
    
    public static void updateLastTarget (EntityMobWithInventory mob) {
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
		        			theTarget = (EntityLivingBase)mob.getEntityWorld().getEntityByID(targetCandidate.getEntityId());
		        			lastAttackedItem = player.getHeldItem().getItem();
		        		}
		        	}
        		}
        	}
        }
    }
    
    public static EntityMobWithInventory getLastTarget() {
    	 if (theTarget != null && theTarget instanceof EntityMobWithInventory) {
    		 return (EntityMobWithInventory)theTarget;
    	 }
    	 return null;
    }
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
   	 	this.taskOwner.setAttackTarget(this.theTarget);
        super.startExecuting();
    }
  
}