package fuzzyMod.targetTasks;

import java.util.Iterator;
import java.util.List;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerLastAttackerTarget extends EntityAITarget{
	private EntityLivingBase theTarget;
	public PlayerLastAttackerTarget(EntityMobWithInventory mob) {
		super(mob, false);
	}

	@Override
	public boolean shouldExecute() {
	
		EntityLivingBase player = Minecraft.getMinecraft().thePlayer;

        if (player == null) {
            return false;
        } 
        else { 
        	theTarget = getPlayerAttacker();
        	if (theTarget != null) {
        		return true;
        	}
        }
        return false;
	}
	
	public void startExecuting() {
		this.taskOwner.setAttackTarget(this.theTarget);
        super.startExecuting();
	}
	
	public EntityLivingBase getPlayerAttacker() {
		Minecraft mc = Minecraft.getMinecraft();
//		System.out.println("tring to get entities");
		List entities;
		if (mc.thePlayer.getEntityBoundingBox() != null) {
			entities = this.taskOwner.getEntityWorld().getEntitiesWithinAABBExcludingEntity(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().expand(10.0D, 10.0D, 10.0D));
//			System.out.println("found entities");
		} else {
			return null;
		}
		Entity entity;
		Iterator<Entity> iterator = entities.iterator();
		while(iterator.hasNext()){
			entity = (Entity)iterator.next();
			if (entity instanceof EntityCreature) {
//				System.out.println("checking: " + entity);
				if (((EntityCreature) entity).getAttackTarget()!= null){
			
					if (((EntityCreature) entity).getAttackTarget().getUniqueID() == mc.thePlayer.getUniqueID()) {
//						System.out.println("found target");
						return (EntityCreature) entity;
					}
				}
			}
		}
		return null;
	}
}
