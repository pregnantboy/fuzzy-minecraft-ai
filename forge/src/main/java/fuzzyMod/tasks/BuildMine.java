package fuzzyMod.tasks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
/**
 * Non-combat task the allows the AI to build a mine automatically.
 */
public class BuildMine extends BuildGeneric{

	private int length, width, height;
	private boolean buildCompletion;
	
	IBlockState cobblestoneBlockState = Blocks.cobblestone.getDefaultState();
	IBlockState coarseDirtBlockState = Blocks.dirt.getStateFromMeta(1);
	IBlockState railBlock = Blocks.rail.getDefaultState();
	IBlockState torchBlockFacingEast = Blocks.torch.getStateFromMeta(1);
	IBlockState torchBlockFacingWest = Blocks.torch.getStateFromMeta(2);
	IBlockState [] ores = {Blocks.coal_ore.getDefaultState(), Blocks.diamond_ore.getDefaultState(),
			Blocks.emerald_ore.getDefaultState(), Blocks.diamond_ore.getDefaultState(), Blocks.gold_ore.getDefaultState(),
			Blocks.iron_ore.getDefaultState(), Blocks.lapis_ore.getDefaultState()};
	/**
	 * Constructor class for build mine.
	 * @param mob The referenced mob.
	 * @param length The length of the mine.
	 */
	public BuildMine(EntityMob mob, int length) {
		super(mob);
		this.length = length;
		this.width = 7;
		this.height = 4;
	}
	
	/**
	 * Initializes the build mine task based on the AI's current location. It will then enqueue the blocks needed to build the mine in a Queue. 
	 * Sets the equipped item as a iron shovel.
	 */
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
	/**
	 * Clears any other blocks in the mine.
	 */
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
		buildCompletion = true;
	}
	
	/**
	 * Returns a random ore.
	 */
	private IBlockState getRandomOre () {
		int randomNum = (int)(Math.random() * (ores.length));
		return ores[randomNum];
	}
	
}
