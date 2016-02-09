package fuzzyMod.entity;

import fuzzyMod.targetTasks.NearestTarget;
import fuzzyMod.targetTasks.PlayerLastAttackedTarget;
import fuzzyMod.targetTasks.PlayerLastAttackerTarget;
import fuzzyMod.tasks.ArrowAttack;
import fuzzyMod.tasks.BuildFarm;
import fuzzyMod.tasks.BuildHouse;
import fuzzyMod.tasks.FireballAttack;
import fuzzyMod.tasks.MeleeAttack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class EntityTutMob extends EntityMobWithInventory {
	// house builder
	
	private EntityAIBase runAway = new EntityAIAvoidEntity(this, EntityTutMob2.mobSelector, 5.0F, 2.0, 0.2);
	BuildHouse buildHouse;
	BuildFarm buildFarm;
	MeleeAttack melee;
	FireballAttack fireball;
	ArrowAttack arrow;
	
	boolean isBuildingHouse, isBuildingFarm;
	private int lastArrowCount;
	
	public EntityTutMob(World worldIn) {
		super(worldIn);

		this.tasks.addTask(1, new EntityAITempt(this, 1.2D, Items.apple, false));
		this.setSize(0.9F, 2.0F);
		this.setCanPickUpLoot(true);
		this.targetTasks.addTask(0, new PlayerLastAttackerTarget(this));
//		this.targetTasks.addTask(1, new PlayerLastAttackedTarget(this, Items.wooden_sword));
		// first false is checkSight, second false is isNearby
		isBuildingHouse = false;
		fireball = new FireballAttack(this, 30, 3);
		melee = new MeleeAttack(this);
		arrow = new ArrowAttack (this, 30);
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3);
	}

	public boolean isAIEnabled() {
		return true; 
	}
	
	public void onUpdate() {
		this.getStrength();
		super.onUpdate();
//		melee.nextStep();
		fireball.nextStep();
//		arrow.nextStep();
	}
	
	public boolean attackEntityAsMob(Entity p_70652_1_) { 
		return super.attackEntityAsMob(p_70652_1_);
	}
	
	
	@Override
	public void onItemPickup(Entity p_71001_1_, int p_71001_2_) {
		System.out.println("picked up shit");
		System.out.println(p_71001_1_.getEntityId());
		super.onItemPickup(p_71001_1_, p_71001_2_);
	}

	
}
	// second parameter is the velocity
//	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_) {
//		
//		EntityLivingBase target = p_82196_1_;
//		
//		EntitySnowball entitysnowball = new EntitySnowball(this.worldObj, this);
//	    EntityArrow entityarrow = new EntityArrow(this.worldObj, this, p_82196_2_);
//	    EntityLargeFireball entityfireball = new EntityLargeFireball(this.worldObj);
//	    double d0 = target.posX - this.posX;
//	    double d1 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D - entitysnowball.posY;
//	    double d2 = target.posZ - this.posZ;
//	    float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 0.2F;
//	    float f = MathHelper.sqrt_float(MathHelper.sqrt_double(d0)) * 0.5F;
//	    
//
////        for (int i = 0; i < 1; ++i)
////        {
////            EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.worldObj, this, d1 + this.getRNG().nextGaussian() * (double)f, d2, d3 + this.field_179469_a.getRNG().nextGaussian() * (double)f);
////            entitysmallfireball.posY = this.field_179469_a.posY + (double)(this.field_179469_a.height / 2.0F) + 0.5D;
////            this.field_179469_a.worldObj.spawnEntityInWorld(entitysmallfireball);
////        }
////	    
//	    entitysnowball.setThrowableHeading(d0, d1 + (double)f1, d2, 1.6F, 12.0F);
//	    entityarrow.setThrowableHeading(d0, d1 + (double)f1, d2, 1.6F, 12.0F);
//	    this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
//	   // this.worldObj.spawnEntityInWorld(entityarrow);
//	    this.worldObj.spawnEntityInWorld(entitysnowball);
//	}


