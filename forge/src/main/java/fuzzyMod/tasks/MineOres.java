package fuzzyMod.tasks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;

public class MineOres extends SearchTaskGeneric {
	private boolean isMiningOre; 
	private int numberOfTicksToDestroy;
	public MineOres(EntityMob mob, int range) {
		super(mob, range);
		isMiningOre = false;
		setCurrentItem(Items.iron_pickaxe);
	}
	@Override
	public void nextStep() {
		if (!isMiningOre){
			nextBlock = getNextBlock(0);
		} else {
			if (reachedBlock()) {
				destroyBlockOre();
			} else {
				moveToBlock();
			}
		}
		
	}
	@Override
	protected boolean isCorrectBlock(int i, int j, int k, int mode) {
		BlockPos pos = new BlockPos(i, j + 1, k);
		IBlockState currBlockState = world.getBlockState(pos);
		Block currBlock = currBlockState.getBlock();
		if (currBlock instanceof BlockOre) {
			isMiningOre = true;
			setCurrentItem(Items.diamond_pickaxe);
			numberOfTicksToDestroy = (int)(currBlock.getBlockHardness(world, pos) * 6);
			return true;
		} 
		return false;
	}
	
	private void destroyBlockOre () {
		// probably set the number of ticks equal to the block hardness
		mob.swingItem(); 
		numberOfTicksToDestroy --;
		if (numberOfTicksToDestroy < 1) {
			world.destroyBlock(nextBlock, true);
			setCurrentItem(Items.wooden_pickaxe);
			isMiningOre= false;
		}
	}
	
}
