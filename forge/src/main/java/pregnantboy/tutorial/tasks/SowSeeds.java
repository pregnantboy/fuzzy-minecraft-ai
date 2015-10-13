package pregnantboy.tutorial.tasks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SowSeeds {
	protected EntityMob mob;
	int range;
	private World world;
	private BlockPos nextBlock;
	private boolean sowingSeeds, tillingSoil;
	IBlockState wheatBlock = Blocks.wheat.getDefaultState();
	IBlockState farmlandBlock = Blocks.farmland.getDefaultState();

	public SowSeeds(EntityMob mob, int range) {
		this.mob = mob;
		this.range = range;
		this.world = mob.getEntityWorld();
		sowingSeeds = false;
		tillingSoil = false;
	}

	private Vec3 getVec3FromBlockPos(BlockPos pos) {
		return new Vec3(pos.getX(), pos.getY(), pos.getZ());
	}

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
				System.out.println("moving to block");
				moveToBlock();
			}
		}
	}

	private BlockPos getNextBlock(int mode) {

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
		System.out.println("cant find");
		return null;
	}

	private boolean isCorrectBlock(int i, int j, int k, int mode) {
		switch (mode) {
		case 1:
			return isFertile(i, j, k);
		case 2:
			return isDirt(i, j, k);
		case 3:
			return harvestCrop(i, j, k);
		default:
			return false;
		}
	}

	private boolean isFertile(int i, int j, int k) {
		BlockPos pos = new BlockPos(i, j, k);
		IBlockState currBlockState = world.getBlockState(pos);
		Block currBlock = currBlockState.getBlock();
		if (currBlock.isFertile(world, pos) && notPlanted(pos)) {
			this.mob.setCurrentItemOrArmor(0, new ItemStack(Items.wheat_seeds));
			System.out.println("found fertile block");
			sowingSeeds = true;
			return true;
		}
		return false;
	}
	
	private boolean harvestCrop (int i , int j, int k ) {
		return false;
	}

	private boolean isDirt(int i, int j, int k) {
		BlockPos pos = new BlockPos(i, j, k);
		IBlockState currBlockState = world.getBlockState(pos);
		Block currBlock = currBlockState.getBlock();
		if (currBlock == Blocks.dirt) {
			this.mob.setCurrentItemOrArmor(0, new ItemStack(Items.iron_hoe));
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

	private boolean reachedBlock() {
		Vec3 destination = new Vec3(nextBlock.getX(), nextBlock.getY(), nextBlock.getZ());
		return mob.getPositionVector().distanceTo(destination) < 2.0;
	}

	private void moveToBlock() {
		mob.getNavigator().tryMoveToXYZ(nextBlock.getX(), nextBlock.getY(), nextBlock.getZ(), 1.0D);
	}

	private void sowSeed() {
		mob.swingItem();
		world.setBlockState(nextBlock, wheatBlock);
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
}