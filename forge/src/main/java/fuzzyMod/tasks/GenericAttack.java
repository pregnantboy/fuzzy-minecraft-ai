package fuzzyMod.tasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
/**
 * Abstract task for combat task.
 */
public abstract class GenericAttack {
	
	protected EntityMobWithInventory mob;
	protected int cooldown, currCooldown;
	protected EntityLivingBase attackTarget;
	protected World world;
	/**
	 * Constructor for abstract class
	 * @param mob The referenced mob
	 * @param cooldown The time interval between each attack.
	 */
	public GenericAttack (EntityMobWithInventory mob, int cooldown) {
		this.mob = mob;
		this.cooldown = cooldown;
		currCooldown = cooldown;
		world = this.mob.worldObj;
	}
	/**
	 * Returns true of the AI has an attack target set.
	 */
	protected boolean hasAttackTarget () {
		 attackTarget = this.mob.getAttackTarget();
	     return attackTarget != null && attackTarget.isEntityAlive();
	}
	/**
	 * Abstract method that sets what action to take for the next tick.
	 */
	public abstract void nextStep (); 
	
	/**
	 * Returns the squared distance from the attack target.
	 */
	public double getDistanceSqFromTarget () {
		if (hasAttackTarget()) {
			if (attackTarget instanceof EntityMobWithInventory) {
				((EntityMobWithInventory)attackTarget).setAttacker(this.mob);
			}
			return this.mob.getDistanceSqToEntity(attackTarget);
		} else {
			return -1;
		}
	}
	/**
	 * Attempts to move the AI to the attack target.
	 */
	public void moveToTarget (double moveSpeed) {
		this.mob.getNavigator().tryMoveToEntityLiving(attackTarget, moveSpeed);
	}
}
