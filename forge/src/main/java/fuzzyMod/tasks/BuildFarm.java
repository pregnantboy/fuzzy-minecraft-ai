package fuzzyMod.tasks;

import java.util.LinkedList;
import java.util.Queue;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BuildFarm extends BuildGeneric {

	private int length, width;

	IBlockState farmBlock = Blocks.farmland.getDefaultState();
//	IBlockState waterBlock = Blocks.wheat.getDefaultState();
	IBlockState waterBlock = Blocks.water.getDefaultState();
	IBlockState acaciaFenceGateBlock = Blocks.acacia_fence_gate.getDefaultState();
	IBlockState acaciaFenceBlock = Blocks.acacia_fence.getDefaultState();
	IBlockState grassBlock = Blocks.grass.getDefaultState();
//	IBlockState grassBlock = Blocks.wheat.getDefaultState();
	IBlockState currBlock;

	public BuildFarm(EntityMob mob, int length, int width) {
		super(mob);
		this.length = length;
		this.width = width;
	}

	public void init() {

		if (hasBuildingInit) {
			return;
		}

		buildingSpot = mob.getPositionVector();
		x = mob.posX + 3;
		y = mob.posY;
		z = mob.posZ + 3;
		for (int w = -3; w < width +2; w++) {
			for (int l = -3; l < length +2; l++) {
				for (int h = -2; h < 0 ; h ++ ) {
					BlockPos pos = new BlockPos(x + w, y + h, z + l);
					enqueue(pos, grassBlock);
				}
			}
		}
		for (int w = 0; w < width; w++) {
			for (int l = 0; l < length; l++) {
				BlockPos pos = new BlockPos(x + w, y - 1, z + l);
				currBlock = farmBlock;
				if (w == 0 || w == width - 1 || l == 0 || l == length - 1) {
					pos = new BlockPos(x + w, y - 1, z + l);
					currBlock = grassBlock;
					enqueue(pos, currBlock);
					pos = new BlockPos(x + w, y, z + l);
					currBlock = acaciaFenceBlock;
				} else if (w % 4 == 0) {
					currBlock = waterBlock;
					if (l == (int)length/2) {
						currBlock = farmBlock;
					}
				}
				enqueue(pos, currBlock);
			}
		}

		// build fence gate
		BlockPos gatePos11 = new BlockPos(x + width / 2, y, z);
		BlockPos gatePos12 = new BlockPos(x + width / 2 - 1, y, z);
		BlockPos gatePos21 = new BlockPos(x + width / 2, y, z + length - 1);
		BlockPos gatePos22 = new BlockPos(x + width / 2 - 1, y, z + length - 1);

		enqueue(gatePos11, acaciaFenceGateBlock);
		enqueue(gatePos12, acaciaFenceGateBlock);
		enqueue(gatePos21, acaciaFenceGateBlock);
		enqueue(gatePos22, acaciaFenceGateBlock);

		hasBuildingInit = true;
		mob.setCurrentItemOrArmor(0, new ItemStack(Items.iron_hoe));
	}

	@Override
	protected void finishingTouches() {
		// TODO Auto-generated method stub

	}

}
