package fuzzyMod.tasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
/**
 * Non-combat task that either sow seeds on fertile ground or till soil.
 */
public class SowSeeds extends SearchTaskGeneric{
	private boolean sowingSeeds, tillingSoil; // actually means moving to block to sow/till
	IBlockState wheatBlock = Blocks.wheat.getDefaultState();
	IBlockState farmlandBlock = Blocks.farmland.getDefaultState();
	/**
	 * Constructor class
	 * @param mob The AI executing this task.
	 * @param range The range to search in.
	 */
	public SowSeeds(EntityMobWithInventory mob, int range) {
		super (mob, range);
		sowingSeeds = false;
		tillingSoil = false;
	}
	
	/**
	 * Sets the next action of this task. Could be either tilling soil or sowing seeds.
	 */
	@Override
	public void nextStep() {
		if (!sowingSeeds && !tillingSoil) {
			nextBlock = getNextBlock(1);
			if (nextBlock != null) {
				sowingSeeds = true;
				setCurrentItem(Items.wheat_seeds);
			}
			if (!sowingSeeds) {
				nextBlock = getNextBlock(2);
				if (nextBlock != null) {
					tillingSoil = true;
					setCurrentItem(Items.iron_hoe);
				}
			}
		} else {
			if (reachedBlock()) {
				if (sowingSeeds) {
					sowSeed();
					System.out.println("seed planted");
				}
				if (tillingSoil) {
					tillSoil();
					System.out.println("soil tilled");
				}
			} else {
				moveToBlock();
			}
		}
	}
	/**
	 * Checks if the block is fertile soil or is dirt depending on the mode.
	 */
	@Override
	protected boolean isCorrectBlock(int i, int j, int k, int mode) {
		switch (mode) {
		case 1:
			return isFertile(i, j, k);
		case 2:
			return isDirt(i, j, k);
		default:
			return false;
		}
	}
	/**
	 * Checks if the ground is fertile.
	 * @param i,j,k The position of the block to check.
	 */
	private boolean isFertile(int i, int j, int k) {
		BlockPos pos = new BlockPos(i, j, k);
		IBlockState currBlockState = world.getBlockState(pos);
		Block currBlock = currBlockState.getBlock();
		if (currBlock.isFertile(world, pos) && notPlanted(pos)) {
			System.out.println("found fertile block");
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the ground is dirt to till.
	 * @param i,j,k The position of the block to check.
	 */
	private boolean isDirt(int i, int j, int k) {
		BlockPos pos = new BlockPos(i, j, k);
		IBlockState currBlockState = world.getBlockState(pos);
		Block currBlock = currBlockState.getBlock();
		if (currBlock == Blocks.dirt) {
			return true;
		}
		return false;
	}
	/**
	 * Returns true if the block has been planted on.
	 */
	private boolean notPlanted(BlockPos pos) {
		pos = pos.add(0, 1, 0);
		IBlockState currBlockState = world.getBlockState(pos);
		return currBlockState.getBlock().isAir(world, pos);
	}

	/**
	 * Plants a seed on the block.
	 */
	private void sowSeed() {
		mob.swingItem();
		world.setBlockState(nextBlock, wheatBlock, 3);
		nextBlock = null;
		sowingSeeds = false;
	}
	/**
	 * Renders the soil plantable.
	 */
	private void tillSoil() {
		mob.swingItem();
		nextBlock = nextBlock.add(0, -1, 0);
		world.setBlockState(nextBlock, farmlandBlock);
		nextBlock = null;
		tillingSoil = false;
	}
	// /give player minecraft:dye 1 15 for bone meal
	// checkout destroyBlock
	// setBlockState flag = 3?
}