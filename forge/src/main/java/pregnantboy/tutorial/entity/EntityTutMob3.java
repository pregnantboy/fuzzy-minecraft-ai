package pregnantboy.tutorial.entity;

import com.mojang.authlib.GameProfile;

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
import pregnantboy.tutorial.Reference;
import pregnantboy.tutorial.tasks.SowSeeds;

public class EntityTutMob3 extends EntityMob {

	private EntityAIAttackOnCollide meleeAttack = new EntityAIAttackOnCollide(this, EntityTutMob.class, 1.5D, false);

	private SowSeeds sower;
	private boolean startPlanting;
	private int lastArrowCount;

	public EntityTutMob3(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub


		this.tasks.addTask(1, new EntityAISwimming(this));

		sower = new SowSeeds(this, 10);
		startPlanting = false;
		this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_hoe));
		//
		// this.setCurrentItemOrArmor(1, new ItemStack(Items.diamond_boots));
		// this.setCurrentItemOrArmor(2, new ItemStack(Items.diamond_leggings));
		// this.setCurrentItemOrArmor(4, new ItemStack(Items.diamond_helmet));
		// this.setCurrentItemOrArmor(3, new
		// ItemStack(Items.diamond_chestplate));
		//
		this.setSize(0.9F, 2.0F);
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
			if (!startPlanting) {
				startPlanting = true;
			}
		}
		if (startPlanting) {
			sower.nextStep();
		}
		lastArrowCount = this.getArrowCountInEntity();
	}
	
}
