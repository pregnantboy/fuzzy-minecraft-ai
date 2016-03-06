package fuzzyMod.tasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ArrowAttack extends GenericAttack{

	public ArrowAttack(EntityMobWithInventory mob, int cooldown) {
		super(mob, cooldown);
		currCooldown = cooldown;
	}

	@Override
	public void nextStep() {
		if (!hasAttackTarget()) {
			return;
		}
		currCooldown -- ;
		 double d = getDistanceSqFromTarget();
	       
	     
		if (currCooldown < 20) {
			this.mob.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
		}
	    if (d < 256.0D)
        {	
			if (currCooldown == 0 && this.mob.getArrows()> 0){
				this.mob.useArrow(1);
				currCooldown = cooldown;
				EntityArrow entityarrow = new EntityArrow(world, this.mob, (float) 1.0);
				this.mob.playSound("random.bow", 1.0F, 1.0F / (this.mob.getRNG().nextFloat() * 0.4F + 0.8F));
				double d0 = attackTarget.posX - this.mob.posX;
				double d1 = attackTarget.posY + (double)attackTarget.getEyeHeight() - 1.100000023841858D - entityarrow.posY;
			    double d2 = attackTarget.posZ - this.mob.posZ;
			    float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 0.2F;
			    float f = MathHelper.sqrt_float(MathHelper.sqrt_double(d0)) * 0.5F;
			    entityarrow.setThrowableHeading(d0, d1 + (double)f1, d2, 1.6F, 12.0F);
			    world.spawnEntityInWorld(entityarrow);
			    this.mob.useArrow(2);
			}
			this.mob.getLookHelper().setLookPositionWithEntity(attackTarget, 10.0F, 10.0F);
        } else {
        	this.moveToTarget(1.0D);
        }
		   
		
	}
	
	
	
}
