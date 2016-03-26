package fuzzyMod.tasks;

import fuzzyMod.entity.EntityMobWithInventory;
/**
 * Combat task that attacks the target with a held item.
 */
public class MeleeAttack extends GenericAttack{
	/**
	 * Constructor class
	 * @param mob The AI executing this task.
	 */
	public MeleeAttack (EntityMobWithInventory mob) {
		super (mob, 20);	
	}
	/**
	 * Constructor class
	 * @param mob The AI executing this task.
	 * @param cooldown The time interval between each swing.
	 */
	public MeleeAttack (EntityMobWithInventory mob, int cooldown) {
		super (mob, cooldown);
	}
		
	/**
	 * Sets the next action of the task. Either moving towards the target or attacking the target with the equipped item.
	 */
	public void nextStep () {
		if (!hasAttackTarget()) {
			return;
		}
		currCooldown--;
		
        double d0 = this.getDistanceSqFromTarget();
        this.mob.getLookHelper().setLookPositionWithEntity(attackTarget, 10.0F, 10.0F);
		if (d0 < 8.0D){
	            
			if (this.currCooldown <= 0)
		    {
		        this.currCooldown = cooldown;
		        boolean attack = this.mob.attackEntityAsMob(attackTarget);
		        this.mob.swingItem();
		        this.mob.playSound("game.hostile.hurt", 1.0F, 1.0F / (this.mob.getRNG().nextFloat() * 0.4F + 0.8F));
		    }
		}
		
	    this.moveToTarget(1.0D);
	}
}
