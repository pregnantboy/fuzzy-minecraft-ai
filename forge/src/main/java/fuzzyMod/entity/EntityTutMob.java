package fuzzyMod.entity;

import fuzzyMod.fuzzyLogic.FuzzyBrain;
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
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityTutMob extends EntityMobWithInventory {
	// house builder
	
	private EntityAIBase runAway = new EntityAIAvoidEntity(this, EntityTutMob2.mobSelector, 5.0F, 2.0, 0.2);
	FuzzyBrain brain;
	boolean isBuildingHouse, isBuildingFarm;
	private int lastArrowCount;
	
	public EntityTutMob(World worldIn) {
		super(worldIn);

	
		this.setSize(0.9F, 2.0F);
		this.setCanPickUpLoot(true);
		team = 1;
		brain = new FuzzyBrain(this, 1);
		
		this.setCurrentItemOrArmor(0, new ItemStack(Items.blaze_rod));
		this.setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots));
		this.setCurrentItemOrArmor(2, new ItemStack(Items.leather_leggings));
		this.setCurrentItemOrArmor(3, new ItemStack(Items.leather_chestplate));
		
		this.mana = 50;
		
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
		super.onUpdate();
		brain.setInputs();
		brain.nextStep();
	}
}


