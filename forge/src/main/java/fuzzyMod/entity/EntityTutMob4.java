package fuzzyMod.entity;

import com.mojang.authlib.GameProfile;

import fuzzyMod.Reference;
import fuzzyMod.tasks.HarvestCrops;
import fuzzyMod.tasks.MineOres;
import fuzzyMod.tasks.SowSeeds;
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

public class EntityTutMob4 extends EntityMob {
	// harvester (but currently miner)
	
	private EntityAIAttackOnCollide meleeAttack = new EntityAIAttackOnCollide(this, EntityTutMob.class, 1.5D, false);

	private HarvestCrops harvester;
	private boolean startHarvest;
	
	private MineOres miner;
	
	private int lastArrowCount;

	public EntityTutMob4(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub


		this.tasks.addTask(1, new EntityAISwimming(this));
		
		harvester = new HarvestCrops(this, 10);
		startHarvest = false;
		
		miner = new MineOres(this,10);
		
		this.setCurrentItemOrArmor(0, new ItemStack(Items.wooden_hoe));
		this.setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots));
		this.setCurrentItemOrArmor(2, new ItemStack(Items.leather_leggings));
		this.setCurrentItemOrArmor(3, new ItemStack(Items.leather_chestplate));
		this.setSize(0.9F, 2.0F);
		this.getInventory();
		this.setCanPickUpLoot(true);
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.2);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2);

	}

	public boolean isAIEnabled() {
		return true;
	}

	public void onUpdate()
	{
		super.onUpdate();
		if (this.getArrowCountInEntity() > lastArrowCount) {
			if (!startHarvest) {
				startHarvest = true;
				System.out.println("Harvesting!");
			} else {
				startHarvest = false;
				System.out.println("Mining!");

			}
		}
		if (startHarvest) {
			harvester.nextStep();
		} else {
			miner.nextStep();
		}
		
		lastArrowCount = this.getArrowCountInEntity();
	}
	
}
