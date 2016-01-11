package fuzzyMod.tasks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;

public class MineOres extends SearchTaskGeneric {
	private boolean isMiningOre; 
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
			System.out.println("moving to ore");
			setCurrentItem(Items.diamond_pickaxe);
			return true;
		} 
		return false;
	}
	
	private void destroyBlockOre () {
		// probably set the number of ticks equal to the block hardness
		mob.swingItem();
		world.destroyBlock(nextBlock, true);
		setCurrentItem(Items.wooden_pickaxe);
		isMiningOre= false;
	}
	
}
