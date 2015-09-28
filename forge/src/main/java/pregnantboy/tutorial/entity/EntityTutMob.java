package pregnantboy.tutorial.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityTutMob extends EntityMob {
	
	private EntityAIBase runAway =  new EntityAIAvoidEntity(this, EntityTutMob2.mobSelector , 5.0F, 2.0, 0.2);
	
	public EntityTutMob(World worldIn) {
		super(worldIn);
		this.tasks.addTask(0, new EntityAIWander(this, 0.3D));
		this.tasks.addTask(1, new EntityAILookIdle(this));
		
		this.setSize(0.9F, 2.0F);
		this.setCanPickUpLoot(true);		
	}
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
		.setBaseValue(3);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}
	public boolean isAIEnabled() {
		return true;
	}
	
	public void onUpdate() {
		super.onUpdate();
		if (this.isEntityAlive()) {
			if (this.getHealth() < (this.getMaxHealth()*0.8)) {
				this.tasks.addTask(0, runAway);
				System.out.println("running away!");
			}
		}
	}
	 public boolean attackEntityAsMob(Entity p_70652_1_) {
		 spawnExplosionParticle();
		 return super.attackEntityAsMob(p_70652_1_);
		 
	 }
	 @Override
	 public void onItemPickup(Entity p_71001_1_, int p_71001_2_) {
		 System.out.println("picked up shit");
		 System.out.println(p_71001_1_.getEntityId());
		 super.onItemPickup(p_71001_1_, p_71001_2_);
	 }
	 
}
