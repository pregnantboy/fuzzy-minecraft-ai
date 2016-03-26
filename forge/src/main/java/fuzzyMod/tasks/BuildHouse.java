package fuzzyMod.tasks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * Non-combat task the allows the AI to build a house automatically.
 */
public class BuildHouse extends BuildGeneric{

	private int length, height, width;
	
	IBlockState stoneBlockState = Blocks.stone.getDefaultState();
	IBlockState glassBlockState = Blocks.glass.getDefaultState();
	IBlockState redSandstoneBlockState = Blocks.red_sandstone.getDefaultState();
	IBlockState plankBlockState = Blocks.planks.getDefaultState();
	IBlockState darkOakPlankBlockState = Blocks.planks.getStateFromMeta(5);
	IBlockState acaciaPlankBlockState = Blocks.planks.getStateFromMeta(4);
	IBlockState blockState = stoneBlockState;
	IBlockState grassBlockState = Blocks.grass.getDefaultState();
	IBlockState sandstoneBlockState = Blocks.sandstone.getDefaultState();
	IBlockState brickBlockState = Blocks.brick_block.getDefaultState();
	Block birchDoorBlock = Blocks.birch_door;

	/**
	 * Constructor class for build house.
	 * @param mob The referenced mob.
	 * @param length The length of the house.
	 * @param height The height of the house.
	 * @param width The width of the house.
	 */
	public BuildHouse(EntityMob mob, int length, int height, int width) {
		super(mob);
		this.length = length;
		this.height = height;
		this.width = width;
	}
	/**
	 * Initializes the build house task based on the AI's current location. It will then enqueue the blocks needed to build the house in a Queue. 
	 * Sets the equipped item as a iron shovel.
	 */
	@Override
	public void init() {
		
		if (hasBuildingInit) {
			return;
		}
		
		buildingSpot = mob.getPositionVector();
		this.x = mob.posX + 3;
		this.y = mob.posY;
		this.z = mob.posZ + 3;

		// build floor
		for (int l = -2; l < length + 2; l++) {
			for (int w = -2; w < width + 2; w++) {
				BlockPos pos = new BlockPos(x + w, y - 1, z + l);
				blockState = grassBlockState;
				if (w == width /2 || w == width/2 -1) {
					blockState = sandstoneBlockState;
				}
				if (l >= 0 && l < length && w >= 0 && w < width) {
					blockState = acaciaPlankBlockState;
				}
				enqueue(pos, blockState);
			}
		}

		// build walls

		for (int h = 0; h < height * 0.6; h++) {
			for (int l = 0; l < length; l++) {
				for (int w = 0; w < width; w++) {
					BlockPos pos = new BlockPos(x + w, y + h, z + l);
					boolean shouldEnqueue = false;
					// build walls
					if (l == 0 || l == length - 1 || w == 0 || w == width - 1) {
						blockState = brickBlockState;
						if (h == 2 || h == 3) {
							if (((w - 3) % 4) == 0 || ((w - 2) % 4) == 0 || ((l - 3) % 4) == 0 || ((l - 2) % 4) == 0) {
								blockState = glassBlockState;
							}
						}
						shouldEnqueue = true;
					}

					// build pillars
					if (l == 0 || l == 1 || l == length - 2 || l == length - 1) {
						if (w == 0 || w == 1 || w == length - 2 || w == length - 1) {
							blockState = darkOakPlankBlockState;
							shouldEnqueue = true;
						}
					}

					if (shouldEnqueue) {
						enqueue(pos, blockState);
					}
				}
			}
		}

		// build roof
		int diff = 1;
		for (int h = (int) (height * 0.6); h < height; h++) {
			for (int l = -diff; l < length + diff; l++) {
				for (int w = -diff; w < width + diff; w++) {
					BlockPos pos = new BlockPos(x + w, y + h, z + l);
					enqueue(pos, plankBlockState);
				}
			}
			diff--;
		}
		
		hasBuildingInit = true;
		mob.setCurrentItemOrArmor(0, new ItemStack(Items.iron_shovel));
	}
	/**
	 * Adds a doors to the house.
	 */
	@Override
	protected void finishingTouches() {
		// build add door
		BlockPos doorPos11 = new BlockPos(x + width / 2, y, z + length - 1);
		BlockPos doorPos12 = new BlockPos(x + width / 2 - 1, y, z + length - 1);
		BlockPos doorPos21 = new BlockPos(x + width / 2, y, z);
		BlockPos doorPos22 = new BlockPos(x + width / 2 - 1, y, z);
		BlockPos doorPos13 = new BlockPos(x + width / 2, y + 1, z + length - 1);
		BlockPos doorPos14 = new BlockPos(x + width / 2 - 1, y + 1, z + length - 1);
		BlockPos doorPos23 = new BlockPos(x + width / 2, y + 1, z);
		BlockPos doorPos24 = new BlockPos(x + width / 2 - 1, y + 1, z);
		// world.setBlockToAir(doorPos11);
		// world.setBlockToAir(doorPos12);
		// world.setBlockToAir(doorPos21);
		// world.setBlockToAir(doorPos22);
		// world.setBlockToAir(doorPos13);
		// world.setBlockToAir(doorPos14);
		// world.setBlockToAir(doorPos23);
		// world.setBlockToAir(doorPos24);
		ItemDoor.placeDoor(world, doorPos11, EnumFacing.NORTH, birchDoorBlock);
		ItemDoor.placeDoor(world, doorPos12, EnumFacing.NORTH, birchDoorBlock);
		ItemDoor.placeDoor(world, doorPos21, EnumFacing.SOUTH, birchDoorBlock);
		ItemDoor.placeDoor(world, doorPos22, EnumFacing.SOUTH, birchDoorBlock);
		hasBuiltOnce = true;
	}
	
}
