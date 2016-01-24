package fuzzyMod.tasks;

import java.util.Random;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class SearchTaskGeneric {
	protected EntityMobWithInventory mob;
	protected int range;
	protected World world;
	protected BlockPos nextBlock; // next block to move to
	protected Item itemDropped;
	protected int itemQuantity;
	public SearchTaskGeneric (EntityMobWithInventory mob, int range) {
		this.mob = mob;
		this.range = range;
		this.world = mob.getEntityWorld();
	}
	protected boolean reachedBlock() {
		Vec3 destination = new Vec3(nextBlock.getX(), mob.posY, nextBlock.getZ());
//		System.out.println("destination" + mob.getPositionVector().distanceTo(destination));
		return mob.getPositionVector().distanceTo(destination) < 2.5;
	}

	protected void moveToBlock() {
		mob.getNavigator().tryMoveToXYZ(nextBlock.getX(), mob.posY, nextBlock.getZ(), 1.0D);
	}	
	
	// most important step, determines the action in each tick
	
	public  abstract void nextStep();
	
	// mode number is for multiple isCorrectBlock checks
	
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
	
	// j is the vertical axis and is -1 of altitude of character.
	protected abstract boolean isCorrectBlock(int i, int j, int k, int mode);
	
	protected void setCurrentItem (Item item) {
		this.mob.setCurrentItemOrArmor(0, new ItemStack(item));
	}
	
	protected void storeItemDroppedDetails() {
		if (nextBlock != null && !world.isAirBlock(nextBlock)) {
			Block blockToDestroy = world.getBlockState(nextBlock).getBlock();
			itemDropped = blockToDestroy.getItemDropped(world.getBlockState(nextBlock), new Random(), 2);
			itemQuantity = blockToDestroy.quantityDropped(world.getBlockState(nextBlock),2, new Random());
			System.out.println(itemDropped.getUnlocalizedName() + " x"+ itemQuantity) ;
		}
	}
	
	protected void obtainItems() {
		mob.getMobIventory().addItem(itemDropped, itemQuantity);
		mob.getMobIventory().printInventory();
	}
}
