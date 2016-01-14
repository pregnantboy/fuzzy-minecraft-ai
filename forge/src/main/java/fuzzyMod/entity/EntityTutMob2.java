package fuzzyMod.entity;

import com.mojang.authlib.GameProfile;

import fuzzyMod.Reference;
import fuzzyMod.tasks.BuildFarm;
import fuzzyMod.tasks.BuildHouse;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityTutMob2 extends EntityMobWithInventory {
	// farm builder
	
	private EntityAIAttackOnCollide meleeAttack = new EntityAIAttackOnCollide(this, EntityTutMob.class, 1.5D, false);
	BuildHouse buildHouse;
	BuildFarm buildFarm;
	boolean isBuildingHouse, isBuildingFarm;
	private int lastArrowCount;
	
	public EntityTutMob2(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
		
		//this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityTutMob.class, false));
		
		//this.tasks.addTask(0,  meleeAttack);
		
		this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_hoe));

		this.setCurrentItemOrArmor(1, new ItemStack(Items.diamond_boots));
		this.setCurrentItemOrArmor(2, new ItemStack(Items.diamond_leggings));
		this.setCurrentItemOrArmor(4, new ItemStack(Items.diamond_helmet));
		this.setCurrentItemOrArmor(3, new ItemStack(Items.diamond_chestplate));
		
		this.setSize(0.9F, 2.0F);
		this.setCanPickUpLoot(true);
		isBuildingHouse = false;
		isBuildingFarm = false;
	}
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
				.setBaseValue(20);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
				.setBaseValue(0.25);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
				.setBaseValue(0.2);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
				.setBaseValue(2);

	}
	public boolean isAIEnabled() {
		return true;
	}
	
	public void onUpdate() {
		super.onUpdate();
		if (this.getArrowCountInEntity() > lastArrowCount) {
			if (!isBuildingHouse) {
				buildHouse = new BuildHouse(this, 10, 10, 10);
				isBuildingHouse = true;
				buildHouse.init();
			}
		
		}
		if (isBuildingFarm) {
			isBuildingFarm = !buildFarm.attemptBuildBlock(1);
		} else if (isBuildingHouse) {
			isBuildingHouse = !buildHouse.attemptBuildBlock(5);
		}
		lastArrowCount = this.getArrowCountInEntity();
	}
	
	public void someLogic() {
		if (this.getHealth() < (this.getMaxHealth()/2)) {
			
		}
	}
	

}
