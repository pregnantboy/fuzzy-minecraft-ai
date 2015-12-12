package pregnantboy.tutorial.tasks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class HarvestCrops extends SearchTaskGeneric {

	private boolean harvestingCrop;
	IBlockState wheatBlock = Blocks.wheat.getDefaultState();
	IBlockState farmlandBlock = Blocks.farmland.getDefaultState();

	public HarvestCrops(EntityMob mob, int range) {
		super(mob, range);
		harvestingCrop = false;
	}

	@Override
	public void nextStep() {
		if (!harvestingCrop) {
			nextBlock = getNextBlock(0);
		} else {
			if (reachedBlock()) {
				harvestCrop();
			} else {
				moveToBlock();
			}
		}
	}

	@Override
	protected boolean isCorrectBlock(int i, int j, int k, int mode) {
		// mode does not matter here
		return hasHarvestableCrop(i, j, k); 
	}
	
	private void harvestCrop () {
		mob.swingItem();
		world.destroyBlock(nextBlock, true);
		harvestingCrop = false;
	}
	
	private boolean hasHarvestableCrop (int i , int j, int k ) {
		BlockPos pos = new BlockPos(i, j, k);
		IBlockState currBlockState = world.getBlockState(pos);
		Block currBlock = currBlockState.getBlock();
		if (hasCrops(pos)) {
			BlockPos cropPos = pos.up();
			IBlockState cropBlockState = world.getBlockState(cropPos);
			if (isHarvestReady(cropBlockState)) {
				harvestingCrop = true;
				setCurrentItem(Items.wooden_hoe);
				return true;
			}
		}
		return false;
	}

	private boolean hasCrops (BlockPos pos) {
		Block block = world.getBlockState(pos.up()).getBlock();
//        return (block instanceof IPlantable && block.canSustainPlant(world, pos, EnumFacing.UP, (IPlantable)block));
		return (block instanceof IPlantable);
	}
	
	private boolean isHarvestReady (IBlockState cropBlockState) {
		return (cropBlockState.getBlock() instanceof BlockCrops && ((Integer)cropBlockState.getValue(BlockCrops.AGE)).intValue() == 7);
	}
}
