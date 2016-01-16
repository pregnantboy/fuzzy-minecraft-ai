package fuzzyMod.tasks;

import com.sun.prism.impl.Disposer.Target;

import fuzzyMod.entity.EntityMobWithInventory;

public class MeleeAttack extends GenericAttack{
	
	public MeleeAttack (EntityMobWithInventory mob) {
		super (mob, 20);	
	}
	
	public MeleeAttack (EntityMobWithInventory mob, int cooldown) {
		super (mob, cooldown);
	}
		
	
	public void nextStep () {
		if (!hasAttackTarget()) {
			return;
		}
		currCooldown--;
		
        double d0 = this.mob.getDistanceSqToEntity(attackTarget);
        this.mob.getLookHelper().setLookPositionWithEntity(attackTarget, 10.0F, 10.0F);
		if (d0 < 8.0D){
	            
			if (this.currCooldown <= 0)
		    {
		        this.currCooldown = cooldown;
		        this.mob.attackEntityAsMob(attackTarget);
		        System.out.println("attacking "+ attackTarget);
		        this.mob.swingItem();
		    }
		}
		
	    this.mob.getMoveHelper().setMoveTo(attackTarget.posX, attackTarget.posY, attackTarget.posZ, 1.0D);
	}
}
