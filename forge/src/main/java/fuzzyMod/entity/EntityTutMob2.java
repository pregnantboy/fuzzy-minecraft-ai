package fuzzyMod.entity;

import com.mojang.authlib.GameProfile;

import fuzzyMod.Reference;
import fuzzyMod.targetTasks.PlayerTarget;
import fuzzyMod.tasks.BuildFarm;
import fuzzyMod.tasks.BuildHouse;
import fuzzyMod.tasks.BuildMine;
import fuzzyMod.tasks.MeleeAttack;
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
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHealth;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityTutMob2 extends EntityMobWithInventory {
	// farm builder
	
	private EntityAIAttackOnCollide meleeAttack = new EntityAIAttackOnCollide(this, EntityTutMob.class, 1.5D, false);
	private BuildHouse buildHouse;
	private BuildFarm buildFarm;
	private BuildMine buildMine;
	private boolean isBuildingHouse, isBuildingFarm, isBuildingMine;
	private boolean isUnderPotion;
	private int lastArrowCount;
	MeleeAttack melee;
	
	public EntityTutMob2(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
		
		this.targetTasks.addTask(0, new PlayerTarget(this, Items.bone));
		
		this.tasks.addTask(0,  meleeAttack);
		
		this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_hoe));
		Potion p;
		this.setCurrentItemOrArmor(1, new ItemStack(Items.diamond_boots));
		this.setCurrentItemOrArmor(2, new ItemStack(Items.diamond_leggings));
		this.setCurrentItemOrArmor(4, new ItemStack(Items.diamond_helmet));
		this.setCurrentItemOrArmor(3, new ItemStack(Items.diamond_chestplate));
		
		this.setSize(0.9F, 2.0F);
		this.setCanPickUpLoot(true);
		isBuildingHouse = false;
		isBuildingFarm = false;
		isBuildingMine = false;
		isUnderPotion = false;
		
		buildHouse = new BuildHouse(this, 10, 10, 10);
		buildMine = new BuildMine(this, 10);
		buildFarm = new BuildFarm (this, 10, 10);
		melee = new MeleeAttack(this);

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
		if (!isBuildingFarm && !isBuildingHouse && !isBuildingMine) {
			melee.nextStep();
		}
		if (this.getArrowCountInEntity() > lastArrowCount) {
			if (!isBuildingHouse) {
				System.out.println("building House");
				isBuildingHouse = true;
				buildHouse.init();
			} 
		}
		
		if (this.isPotionActive(Potion.fireResistance)) {
			this.clearActivePotions();
			if (!isBuildingMine) {
				System.out.println("building Mine");
				buildMine.init();
				isBuildingMine = true;
			}
		}
		
		if (this.isPotionActive(Potion.jump)) {
			this.clearActivePotions();
			if (!isBuildingFarm) {
				System.out.println("building Farm");
				buildFarm.init();
				isBuildingFarm = true;
			}
		}
		
		if (isBuildingFarm) {
			isBuildingFarm = !buildFarm.attemptBuildBlock(5);
		} else if (isBuildingMine) {
			isBuildingMine = !buildMine.attemptBuildBlock(5);
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
