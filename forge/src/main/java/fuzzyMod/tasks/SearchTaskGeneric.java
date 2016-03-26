package fuzzyMod.tasks;

import java.util.Random;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
/**
 * Abstract class for search based tasks.
 */
public abstract class SearchTaskGeneric {
	protected EntityMobWithInventory mob;
	protected int range;
	protected World world;
	protected BlockPos nextBlock; // next block to move to
	protected Item itemDropped;
	protected int itemQuantity;
	/**
	 * Constructor class
	 * @param mob The AI executing this task.
	 * @param range The range to search for.
	 */
	public SearchTaskGeneric (EntityMobWithInventory mob, int range) {
		this.mob = mob;
		this.range = range;
		this.world = mob.getEntityWorld();
	}
	/**
	 * Returns true if the AI has reached the destination.
	 */
	protected boolean reachedBlock() {
		Vec3 destination = new Vec3(nextBlock.getX(), mob.posY, nextBlock.getZ());
//		System.out.println("destination" + mob.getPositionVector().distanceTo(destination));
		return mob.getPositionVector().distanceTo(destination) < 2.5;
	}
	/**
	 * Attempts to move the AI to the block.
	 */
	protected void moveToBlock() {
		mob.getNavigator().tryMoveToXYZ(nextBlock.getX(), mob.posY, nextBlock.getZ(), 1.0D);
	}	
	
	// most important step, determines the action in each tick
	/**
	 * Abstract class that sets the next action for the task.
	 */
	public  abstract void nextStep();
	
	// mode number is for multiple isCorrectBlock checks
	/**
	 * Using breadth first search, return the closest block that matches the criteria.
	 * @param mode The selector integer that sets the criteria for correct block.
	 */
	protected BlockPos getNextBlock(int mode) {

		int x = (int) mob.posX;
		int y = (int) mob.posY;
		int z = (int) mob.posZ;
		int j = y - 1;
		for (int r = 0; r < range; r++) {
			for (int i = x - r; i <= x + r; i++) {
				if (i == x - r || i == x + r) {
					for (int k = z - r; k <= z + r; k++) {
						if (isCorrectBlock(i, j, k, mode)) {
							return new BlockPos(i, j + 1, k);
						}
					}
				} else {
					int k = z - r;
					if (isCorrectBlock(i, j, k, mode)) {
						return new BlockPos(i, j + 1, k);
					}
					k = z + r;
					if (isCorrectBlock(i, j, k, mode)) {
						return new BlockPos(i, j + 1, k);
					}
				}
			}
		}
		return null;
	}
	/**
	 * Returns distance to closest block. If none is found, returns 9999.
	 */
	public double distToNearestBlock () {
		BlockPos foundBlock = getNextBlock(1);
		if (foundBlock == null) {
			return 9999;
		}
		else {
			return Math.sqrt(mob.getDistanceSqToCenter(foundBlock));
		}
	}
	
	/**
	 * Abstract class that checks if the block is the one that the AI is looking for.
	 */
	protected abstract boolean isCorrectBlock(int i, int j, int k, int mode);
	
	/**
	 * Sets the equipped item of the AI
	 * @param item The item to equip.
	 */
	protected void setCurrentItem (Item item) {
		this.mob.setCurrentItemOrArmor(0, new ItemStack(item));
	}
	/**
	 * Store the item that has been collected in a variable.
	 */
	protected void storeItemDroppedDetails() {
		if (nextBlock != null && !world.isAirBlock(nextBlock)) {
			Block blockToDestroy = world.getBlockState(nextBlock).getBlock();
			itemDropped = blockToDestroy.getItemDropped(world.getBlockState(nextBlock), new Random(), 2);
			itemQuantity = blockToDestroy.quantityDropped(world.getBlockState(nextBlock),2, new Random());
			System.out.println(itemDropped.getUnlocalizedName() + " x"+ itemQuantity) ;
		}
	}
	/**
	 * Store the current item in the inventory.
	 */
	protected void obtainItems() {
		mob.getMobIventory().addItem(itemDropped, itemQuantity);
		mob.getMobIventory().printInventory();
	}
}
