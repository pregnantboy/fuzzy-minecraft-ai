package fuzzyMod.tasks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase.Rail;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager.ItemAndEmeraldToItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemMinecart;
import net.minecraft.util.BlockPos;

public class BuildMine extends BuildGeneric{

	private int length, width, height;
	
	IBlockState cobblestoneBlockState = Blocks.cobblestone.getDefaultState();
	IBlockState coarseDirtBlockState = Blocks.dirt.getStateFromMeta(1);
	IBlockState railBlock = Blocks.rail.getDefaultState();
	IBlockState torchBlockFacingEast = Blocks.torch.getStateFromMeta(1);
	IBlockState torchBlockFacingWest = Blocks.torch.getStateFromMeta(2);
	IBlockState [] ores = {Blocks.coal_ore.getDefaultState(), Blocks.diamond_ore.getDefaultState(),
			Blocks.emerald_ore.getDefaultState(), Blocks.diamond_ore.getDefaultState(), Blocks.gold_ore.getDefaultState(),
			Blocks.iron_ore.getDefaultState(), Blocks.lapis_ore.getDefaultState(), Blocks.lit_redstone_ore.getDefaultState(),
			Blocks.quartz_ore.getDefaultState(), Blocks.redstone_ore.getDefaultState()};
	
	public BuildMine(EntityMob mob, int length) {
		super(mob);
		this.length = length;
		this.width = 7;
		this.height = 4;
	}

	@Override
	public void init() {
		if (hasBuildingInit) {
			System.out.println("already has init");
			return;
		}
		
		buildingSpot = mob.getPositionVector();
		this.x = mob.posX + 3;
		this.y = mob.posY;
		this.z = mob.posZ + 3;
		
		// build floor
		for (int l = 0; l < length ; l++) {
			for (int w = -1; w < width+1; w++) {
				BlockPos pos = new BlockPos(x + w, y - 1, z + l);
				enqueue(pos, cobblestoneBlockState);
				if (w == width/2) {
					pos = new BlockPos(x + w, y+0, z + l);
					enqueue(pos, railBlock);
				}
				if (w ==0 || w==-1 || w == width - 1 || w == width) {
					for (int k = 0; k < height ; k ++) {
						pos = new BlockPos(x + w, y + k, z + l);
						enqueue(pos, coarseDirtBlockState);
					}
				} else {
					//roof
					pos = new BlockPos(x + w, y + height - 1, z + l);
					enqueue(pos, coarseDirtBlockState);
				}
			}
			if (l > 0 && l < length -1) {
				BlockPos pos = new BlockPos (x + 1, y + 1, z + l);
				enqueue (pos, torchBlockFacingEast);
				pos = new BlockPos (x + width -2 , y + 1, z+ l);
				enqueue (pos, torchBlockFacingWest);
				pos = new BlockPos(x + 1, y, z + l);
				enqueue(pos, getRandomOre());
				pos = new BlockPos(x + width - 2, y, z + l);				
				enqueue(pos, getRandomOre());
			}
			
			if (l == length - 1) {
				for (int w = 0; w < width; w++) {
					for (int k = 0;k < height ; k ++) {
						BlockPos pos = new BlockPos(x + w, y + k, z + l);
						enqueue(pos, coarseDirtBlockState);
					}
				}
			}
		}
		
		hasBuildingInit = true;
	}
	
	@Override
	protected void finishingTouches() {
		for (int l = 0; l < length -1 ; l++) {
			for (int w = 2; w < width -2; w++) {
				for (int k = 0 ; k < height -1; k++ ) {
					if (!(k == 0 && w == width/2)) {
						BlockPos pos = new BlockPos(x + w, y + k, z + l);
						world.setBlockToAir(pos);
					}
				}
			}			
		}
		
	}
	
	private IBlockState getRandomOre () {
		int randomNum = (int)(Math.random() * (ores.length));
		return ores[randomNum];
	}
	
}
