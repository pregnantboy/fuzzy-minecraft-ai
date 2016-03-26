package fuzzyMod.entity;

import fuzzyMod.fuzzyLogic.FuzzyBrain;
import fuzzyMod.tasks.SowSeeds;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Entity for FuzzyMob. Follows the rules exported into Slot 3 by FuzzyAIBuilder.
 */
public class EntityTutMob3 extends EntityMobWithInventory {
	// planter


	private FuzzyBrain brain;

	public EntityTutMob3(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub


		this.tasks.addTask(0, new EntityAITempt(this, 1.2D, Items.apple, false));
		this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(2, new EntityAISwimming(this));

		brain = new FuzzyBrain (this,3);
		this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_hoe));
		this.setCurrentItemOrArmor(1, new ItemStack(Items.iron_boots));
		this.setCurrentItemOrArmor(2, new ItemStack(Items.iron_leggings));
		this.setCurrentItemOrArmor(4, new ItemStack(Items.iron_helmet));
		this.setCurrentItemOrArmor(3, new ItemStack(Items.iron_chestplate));
		this.setSize(0.9F, 2.0F);
		this.setCanPickUpLoot(true);
		team =2;
	}
	
	/**
	 * Alters the attributes of the entity e.g. attackDamage, maxHealth, moveSpeed.
	 * Called before the creation of the object.
	 */
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

	public void onUpdate() {
		super.onUpdate();
		brain.setInputs();
		brain.nextStep();
	}

}
