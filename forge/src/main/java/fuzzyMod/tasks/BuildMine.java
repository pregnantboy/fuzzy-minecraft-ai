package fuzzyMod.tasks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class BuildMine extends BuildGeneric{

	private int length, width, height;
	
	IBlockState cobblestoneBlockState = Blocks.cobblestone.getDefaultState();
	IBlockState coarseDirtBlockState = Blocks.dirt.getStateById(1);
	
	public BuildMine(EntityMob mob, int length) {
		super(mob);
		this.length = length;
		this.width = 5;
		this.height = 3;
	}

	@Override
	protected void init() {
		if (hasBuildingInit) {
			return;
		}
		
		buildingSpot = mob.getPositionVector();
		this.x = mob.posX + 3;
		this.y = mob.posY;
		this.z = mob.posZ + 3;
		
		// build floor
		for (int l = 0; l < length ; l++) {
			for (int w = 0; w < width; w++) {
				BlockPos pos = new BlockPos(x + w, y - 1, z + l);
				enqueue(pos, cobblestoneBlockState);
				if (w ==0 || w == width - 1) {
					for (int k = 0; k < 3 ; k ++) {
						pos = new BlockPos(x + w, y + k, z + l);
						enqueue(pos, coarseDirtBlockState);
					}
				} else {
					pos = new BlockPos(x + w, y + k, z + l);
				}
			}	
		}
		
		for (int l = 0; l < length; l ++) {
			for (int w = 0; w < width; w++) {
				BlockPos pos = new BlockPos(x + w, y - 1, z + l);
				enqueue(pos, cobblestoneBlockState);
			}
		}
				
	}
	
	@Override
	protected void finishingTouches() {
		// TODO Auto-generated method stub
		
	}
	
}
