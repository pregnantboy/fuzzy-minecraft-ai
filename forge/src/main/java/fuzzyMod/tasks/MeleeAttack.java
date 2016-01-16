package fuzzyMod.tasks;

import com.sun.prism.impl.Disposer.Target;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;

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
		        boolean attack = this.mob.attackEntityAsMob(attackTarget);
		        this.mob.swingItem();
		        this.mob.playSound("game.hostile.hurt", 1.0F, 1.0F / (this.mob.getRNG().nextFloat() * 0.4F + 0.8F));
		    }
		}
		
	    this.mob.getNavigator().tryMoveToEntityLiving(attackTarget, 1.0D);
	}
}
