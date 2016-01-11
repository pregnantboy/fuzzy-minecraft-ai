package fuzzyMod.tasks;

import java.util.LinkedList;
import java.util.Queue;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class BuildGeneric {
	
	protected double x, y, z;
	protected World world;
	protected Queue<BlockPos> blockPosQueue;
	protected Queue<IBlockState> blocksQueue;
	protected EntityMob mob;
	protected boolean hasBuildingInit, isBuildingDone;
	protected Vec3 buildingSpot;
	
	public BuildGeneric (EntityMob mob) {
		this.mob = mob;
		hasBuildingInit = false;
		isBuildingDone = false;
		world = mob.getEntityWorld();
		this.blockPosQueue = new LinkedList<BlockPos>();
		this.blocksQueue = new LinkedList<IBlockState>();
	}
	
	public boolean attemptBuildBlock(int buildSpeed) {
		if (isOnBuildingSpot()) {
			buildBlock(buildSpeed);
			mob.swingItem();
		} else {
			moveToBuildingSpot();
		}
		return isBuildingDone;
	}

	protected void buildBlock(int buildSpeed) {
		if (blockPosQueue.size() < 1 || blocksQueue.size() < 1) {
			finishingTouches();
			isBuildingDone = true;
		} else {
			for (int i = 0; i < buildSpeed; i ++) {
				world.setBlockState(blockPosQueue.remove(), blocksQueue.remove());
			}
		}
	}
	
	protected void enqueue(BlockPos pos, IBlockState block) {
		blockPosQueue.add(pos);
		blocksQueue.add(block);
	}	
	
	
	protected abstract void finishingTouches();
	
	protected abstract void init();
	
	protected boolean isOnBuildingSpot () {
		return (mob.getPositionVector().distanceTo(buildingSpot) < 1);
	}
	
	protected void moveToBuildingSpot() {
		mob.getNavigator().tryMoveToXYZ(buildingSpot.xCoord, buildingSpot.yCoord, buildingSpot.zCoord, 1.0D);
	}
	
	
}