package fuzzyMod.targetTasks;

import java.util.Iterator;
import java.util.List;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class PlayerLastAttackerTarget extends EntityAITarget{
	private static EntityLivingBase theTarget;
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
	
	
	
	public static void updatePlayerAttacker() {
		Minecraft mc = Minecraft.getMinecraft();
//		System.out.println("tring to get entities");
		List entities;
		if (mc.thePlayer.getEntityBoundingBox() != null) {
			entities = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().expand(16.0D, 4.0D, 16.0D));
//			System.out.println("found entities");
		} else {
			return;
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
						theTarget = (EntityCreature) entity;
					}
				}
			}
		}
	}
	
	public static EntityMobWithInventory getPlayerAttacker() {
		if (theTarget != null && theTarget instanceof EntityMobWithInventory) {
			return (EntityMobWithInventory)theTarget;
		}
		return null;	
	}
}
