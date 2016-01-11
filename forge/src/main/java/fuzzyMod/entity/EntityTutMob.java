package fuzzyMod.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import fuzzyMod.tasks.BuildFarm;
import fuzzyMod.tasks.BuildHouse;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityTutMob extends EntityMob {
	// house builder
	
	private EntityAIBase runAway = new EntityAIAvoidEntity(this, EntityTutMob2.mobSelector, 5.0F, 2.0, 0.2);
	BuildHouse buildHouse;
	BuildFarm buildFarm;
	boolean isBuildingHouse, isBuildingFarm;
	private int lastArrowCount;
	
	public EntityTutMob(World worldIn) {
		super(worldIn);

		this.tasks.addTask(0, new EntityAITempt(this, 1.2D, Items.apple, false));
		this.setSize(0.9F, 2.0F);
		this.setCanPickUpLoot(true);

		isBuildingHouse = false;
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}

	public boolean isAIEnabled() {
		return true;
	}

	public void onUpdate() {
		super.onUpdate();
		if (this.getArrowCountInEntity() > lastArrowCount) {
//			if (!isBuildingHouse) {
//				buildHouse = new BuildHouse(this, 10, 10, 10);
//				isBuildingHouse = true;
//				buildHouse.init();
//			}
			if (!isBuildingFarm) {
				buildFarm = new BuildFarm (this, 10,20);
				isBuildingFarm = true;
				buildFarm.init();
			}
		}
		if (isBuildingFarm) {
			isBuildingFarm = !buildFarm.attemptBuildBlock(1);
		} else if (isBuildingHouse) {
			isBuildingHouse = !buildHouse.attemptBuildBlock(10);
		}
		lastArrowCount = this.getArrowCountInEntity();
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