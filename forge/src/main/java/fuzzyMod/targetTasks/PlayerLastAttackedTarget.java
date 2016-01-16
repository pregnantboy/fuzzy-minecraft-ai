package fuzzyMod.targetTasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class PlayerLastAttackedTarget extends EntityAITarget
{
    EntityMobWithInventory mob;
    EntityLivingBase theTarget;
    Item signalItem;
    private static final String __OBFID = "CL_00001625";

    public PlayerLastAttackedTarget(EntityMobWithInventory mob, Item signalItem)
    {
        super(mob, false);
        this.mob = mob;
        this.setMutexBits(1);
        this.signalItem = signalItem;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
       EntityLivingBase player = Minecraft.getMinecraft().thePlayer;

        if (player == null)
        {
            return false;
        }
        else 
        {
        	Minecraft mc = Minecraft.getMinecraft();
        	
        	if (player.getHeldItem()!= null) {
        		if (player.getHeldItem().getItem() == signalItem && player.isSwingInProgress) {
		        	MovingObjectPosition objectMouseOver = mc.objectMouseOver;
		        	// makes a variable for where you look
		        	if(mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null) {
		        		Entity targetCandidate = mc.objectMouseOver.entityHit;
		        		if (targetCandidate instanceof EntityLivingBase) {
		        			theTarget = (EntityLivingBase)targetCandidate;
		        			System.out.println("trying to set target " + theTarget);
		        			return theTarget != null && this.isSuitableTarget(theTarget, false) && theTarget.isEntityAlive();
		        		}
		        	}
        		}
        	}
        	return false;
        }
   
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.theTarget);
		System.out.println("target set as " + theTarget);
        super.startExecuting();
    }
}