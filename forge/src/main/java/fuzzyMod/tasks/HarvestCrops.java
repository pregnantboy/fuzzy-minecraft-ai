package fuzzyMod.tasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.IPlantable;
/**
 * Non-combat task that harvest crops automatically.
 */
public class HarvestCrops extends SearchTaskGeneric {

	private boolean harvestingCrop;
	IBlockState wheatBlock = Blocks.wheat.getDefaultState();
	IBlockState farmlandBlock = Blocks.farmland.getDefaultState();
	/**
	 * Constructor for harvest crops.
	 * @param mob The referenced mob.
	 * @param range The range to search for the harvestable crops.
	 */
	public HarvestCrops(EntityMobWithInventory mob, int range) {
		super(mob, range);
		harvestingCrop = false;
	}
	
	/**
	 * Returns the next action of the task. This depends on where it has found the next crop to harvest or whether it has reached the harvestable crop.
	 */
	@Override
	public void nextStep() {
		if (!harvestingCrop) {
			nextBlock = getNextBlock(0);
			if (nextBlock != null) {
				storeItemDroppedDetails();
				harvestingCrop = true;
				setCurrentItem(Items.wooden_hoe);
			}
		} else {
			if (reachedBlock()) {
				harvestCrop();
			} else {
				moveToBlock();
			}
		}
	}
	/**
	 * Checks if the crop is harvestable.
	 */
	@Override
	protected boolean isCorrectBlock(int i, int j, int k, int mode) {
		// mode does not matter here
		return hasHarvestableCrop(i, j, k); 
	}
	
	/**
	 * Harvest the crop. Destroy the crop and add into inventory.
	 */
	private void harvestCrop () {
		mob.swingItem();
		world.destroyBlock(nextBlock, false);
		obtainItems();
		harvestingCrop = false;
	}
	/**
	 * Returns true of the position contains at crop of age level 7.
	 * @param i,j,k The coordinates of the block.
	 */
	private boolean hasHarvestableCrop (int i , int j, int k ) {
		BlockPos pos = new BlockPos(i, j, k);
		IBlockState currBlockState = world.getBlockState(pos);
		Block currBlock = currBlockState.getBlock();
		if (hasCrops(pos)) {
			BlockPos cropPos = pos.up();
			IBlockState cropBlockState = world.getBlockState(cropPos);
			if (isHarvestReady(cropBlockState)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Checks of the block has a crop on it.
	 * @param pos Positon of the block.
	 */
	private boolean hasCrops (BlockPos pos) {
		Block block = world.getBlockState(pos.up()).getBlock();
//        return (block instanceof IPlantable && block.canSustainPlant(world, pos, EnumFacing.UP, (IPlantable)block));
		return (block instanceof IPlantable);
	}
	/**
	 * Check if the state of the crop is harvestable - age = 7.
	 */
	private boolean isHarvestReady (IBlockState cropBlockState) {
		return (cropBlockState.getBlock() instanceof BlockCrops && ((Integer)cropBlockState.getValue(BlockCrops.AGE)).intValue() == 7);
	}
}
