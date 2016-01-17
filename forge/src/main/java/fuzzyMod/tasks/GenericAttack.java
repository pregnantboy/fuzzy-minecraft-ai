package fuzzyMod.tasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public abstract class GenericAttack {
	
	protected EntityMobWithInventory mob;
	protected int cooldown, currCooldown;
	protected EntityLivingBase attackTarget;
	protected World world;
	
	public GenericAttack (EntityMobWithInventory mob, int cooldown) {
		this.mob = mob;
		this.cooldown = cooldown;
		currCooldown = cooldown;
		world = this.mob.worldObj;
	}
	
	protected boolean hasAttackTarget () {
		 attackTarget = this.mob.getAttackTarget();
	     return attackTarget != null && attackTarget.isEntityAlive();
	}
	
	public abstract void nextStep (); 
	
	public double getDistanceSqFromTarget () {
		if (hasAttackTarget()) {
			return this.mob.getDistanceSqToEntity(attackTarget);
		} else {
			return -1;
		}
	}
	
	public void moveToTarget (double moveSpeed) {
		this.mob.getNavigator().tryMoveToEntityLiving(attackTarget, moveSpeed);
	}
}
