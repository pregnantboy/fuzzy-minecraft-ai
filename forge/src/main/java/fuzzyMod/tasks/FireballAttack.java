package fuzzyMod.tasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

/**
 * Combat task that allows the AI to throw fireballs at a target, setting them ablaze.
 */
public class FireballAttack extends GenericAttack {

	private int charges;
	private int numBalls;
	/**
	 * Constructor class for fireball attack
	 * @param mob The referenced mob
	 * @param cooldown The time interval between each throw
	 * @param numBalls The number of fireballs that can be thrown at once.
	 */
	public FireballAttack(EntityMobWithInventory mob, int cooldown, int numBalls)
	{
		super (mob, cooldown);
		this.numBalls = numBalls + 1;
		this.charges = 0;
	}

	/**
	 * Sets the next action of the fireball attack task. If within range, it will spawn the fireballs with a directional heading towards the attack target. Or else, it will move into range.
	 */
	public void nextStep()
	{
		if (!hasAttackTarget()) {
			return;
		}
		currCooldown--;

		double d0 = getDistanceSqFromTarget();

		if (d0 < 256.0D)
		{
			double d1 = attackTarget.posX - this.mob.posX;
			double d2 = attackTarget.getEntityBoundingBox().minY + (double)(attackTarget.height / 2.0F) - (this.mob.posY + (double)(this.mob.height / 2.0F));
			double d3 = attackTarget.posZ - this.mob.posZ;

			if (this.currCooldown <= 0)
			{
				++this.charges;

				if (this.charges <= numBalls)
				{
					this.currCooldown = 2;
				}
				else
				{
					this.currCooldown = cooldown;
					this.charges = 0;
				}

				if (this.charges > 1)
				{
					float f = MathHelper.sqrt_float(MathHelper.sqrt_double(d0)) * 0.5F;
					this.mob.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, new BlockPos((int)this.mob.posX, (int)this.mob.posY, (int)this.mob.posZ), 0);

					for (int i = 0; i < 1; ++i)
					{
						EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.mob.worldObj, this.mob, d1 , d2, d3);
						entitysmallfireball.posY = this.mob.posY + (double)(this.mob.height / 2.0F) + 0.5D;
						this.mob.worldObj.spawnEntityInWorld(entitysmallfireball);
						this.mob.useMana(1);
					}
				}
			}

			this.mob.getLookHelper().setLookPositionWithEntity(attackTarget, 10.0F, 10.0F);
		}
		else
		{
			this.moveToTarget(1.0D);
		}
	}
}
