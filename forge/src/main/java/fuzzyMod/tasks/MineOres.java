package fuzzyMod.tasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class MineOres extends SearchTaskGeneric {
	private boolean isMiningOre; 
	private int numberOfTicksToDestroy;
	public MineOres(EntityMobWithInventory mob, int range) {
		super(mob, range);
		isMiningOre = false;
		setCurrentItem(Items.iron_pickaxe);
	}
	@Override
	public void nextStep() {
		if (!isMiningOre){
			nextBlock = getNextBlock(0);
			if (nextBlock != null) {
				storeItemDroppedDetails();
				isMiningOre = true;
				setCurrentItem(Items.diamond_pickaxe);
			}
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
			numberOfTicksToDestroy = (int)(currBlock.getBlockHardness(world, pos) * 6);
			return true;
		} 
		return false;
	}
	
	private void destroyBlockOre () {
		// probably set the number of ticks equal to the block hardness
		mob.swingItem(); 
		numberOfTicksToDestroy --;
		EffectRenderer effectRender = new EffectRenderer(world, new TextureManager(null));
		Minecraft.getMinecraft().effectRenderer.addBlockHitEffects(nextBlock, EnumFacing.UP);
		if (numberOfTicksToDestroy < 1) {
			world.destroyBlock(nextBlock, false);
			setCurrentItem(Items.wooden_pickaxe);
			isMiningOre= false;
			obtainItems();
		}
	}
	
}
