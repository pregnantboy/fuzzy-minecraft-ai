package fuzzyMod.targetTasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.item.Item;

public class PlayerTarget extends EntityAITarget {
	Item itemTrigger;
	public PlayerTarget(EntityMobWithInventory mob, Item itemTrigger) {
		super(mob, false);
		this.itemTrigger = itemTrigger;
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase player = Minecraft.getMinecraft().thePlayer;

        if (player != null) {
        	if (player.getHeldItem() != null) {
        		if (player.getHeldItem().getItem() == itemTrigger) {
	        		return true;
	        	}
        	}
        }
        if (this.taskOwner.getAttackTarget()!= null) {
        	if (this.taskOwner.getAttackTarget().getUniqueID() == player.getUniqueID()) {
        		this.taskOwner.setLastAttacker(null);
        	}
        }
        return false;
	}
	
	public void startExecuting() {
//		System.out.println("will target player");
		this.taskOwner.setAttackTarget(Minecraft.getMinecraft().thePlayer);
        super.startExecuting();
	}
	
}
