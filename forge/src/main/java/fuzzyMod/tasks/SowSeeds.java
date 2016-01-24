package fuzzyMod.tasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;

public class SowSeeds extends SearchTaskGeneric{
	private boolean sowingSeeds, tillingSoil; // actually means moving to block to sow/till
	IBlockState wheatBlock = Blocks.wheat.getDefaultState();
	IBlockState farmlandBlock = Blocks.farmland.getDefaultState();

	public SowSeeds(EntityMobWithInventory mob, int range) {
		super (mob, range);
		sowingSeeds = false;
		tillingSoil = false;
	}
	
	@Override
	public void nextStep() {
		if (!sowingSeeds && !tillingSoil) {
			nextBlock = getNextBlock(1);
			if (!sowingSeeds) {
				nextBlock = getNextBlock(2);
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

	private boolean isFertile(int i, int j, int k) {
		BlockPos pos = new BlockPos(i, j, k);
		IBlockState currBlockState = world.getBlockState(pos);
		Block currBlock = currBlockState.getBlock();
		if (currBlock.isFertile(world, pos) && notPlanted(pos)) {
			setCurrentItem(Items.wheat_seeds);
			System.out.println("found fertile block");
			sowingSeeds = true;
			return true;
		}
		return false;
	}
	
	
	private boolean isDirt(int i, int j, int k) {
		BlockPos pos = new BlockPos(i, j, k);
		IBlockState currBlockState = world.getBlockState(pos);
		Block currBlock = currBlockState.getBlock();
		if (currBlock == Blocks.dirt) {
			setCurrentItem(Items.iron_hoe);
			tillingSoil = true;
			return true;
		}
		return false;
	}

	private boolean notPlanted(BlockPos pos) {
		pos = pos.add(0, 1, 0);
		IBlockState currBlockState = world.getBlockState(pos);
		return currBlockState.getBlock().isAir(world, pos);
	}


	private void sowSeed() {
		mob.swingItem();
		world.setBlockState(nextBlock, wheatBlock, 3);
		nextBlock = null;
		sowingSeeds = false;
	}

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