package fuzzyMod.tasks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.BlockPos;

public class MineOres extends SearchTaskGeneric {
	private boolean isMiningOre; 
	public MineOres(EntityMob mob, int range) {
		super(mob, range);
		isMiningOre = false;
	}
	@Override
	public void nextStep() {
		if (!isMiningOre){
			nextBlock = getNextBlock(0);
		}
		
	}
	@Override
	protected boolean isCorrectBlock(int i, int j, int k, int mode) {
		BlockPos pos = new BlockPos(i, j, k);
		IBlockState currBlockState = world.getBlockState(pos);
		Block currBlock = currBlockState.getBlock();
		
	}
	
}
