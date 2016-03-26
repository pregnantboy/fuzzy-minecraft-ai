package fuzzyMod.tasks;

import java.util.LinkedList;
import java.util.Queue;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * An abstract class for building structures.
 */
public abstract class BuildGeneric {
	
	protected double x, y, z;
	protected World world;
	protected Queue<BlockPos> blockPosQueue;
	protected Queue<IBlockState> blocksQueue;
	protected EntityMob mob;
	protected boolean hasBuildingInit, isBuildingDone;
	protected Vec3 buildingSpot;
	protected boolean hasBuiltOnce;
	/**
	 * Constructor class for build generic.
	 * @param mob The referenced mob.
	 */
	public BuildGeneric (EntityMob mob) {
		this.mob = mob;
		hasBuildingInit = false;
		isBuildingDone = false;
		world = mob.getEntityWorld();
		this.blockPosQueue = new LinkedList<BlockPos>();
		this.blocksQueue = new LinkedList<IBlockState>();
		hasBuiltOnce = false;
	}
	
	/**
	 * Checks if the AI is on the building spot. If yes, it will execute the next action. Or else, it will move to the building spot.
	 * @param buildSpeed The speed of building the structure.
	 */
	public boolean attemptBuildBlock(int buildSpeed) {
		if (isOnBuildingSpot()) {
			buildBlock(buildSpeed);
			mob.swingItem();
		} else {
			moveToBuildingSpot();
		}
		return isBuildingDone;
	}
	/**
	 * Dequeues a block from the queue and renders it in the game.
	 * @param buildSpeed The speed of building the structure.
	 */
	protected void buildBlock(int buildSpeed) {
		if (blockPosQueue.size() < 1 || blocksQueue.size() < 1) {
			finishingTouches();
			isBuildingDone = true;
			hasBuildingInit = false;
		} else {
			isBuildingDone = false;
			for (int i = 0; i < buildSpeed; i ++) {
				if (blockPosQueue.size() > 0 || blocksQueue.size() > 0) {
					world.setBlockState(blockPosQueue.remove(), blocksQueue.remove());
				}
			}
		}
	}
	
	/**
	 * Enqueues a block in the queue.
	 * @param pos Position of block to be placed.
	 * @param block Type of block to be placed.
	 */
	protected void enqueue(BlockPos pos, IBlockState block) {
		blockPosQueue.add(pos);
		blocksQueue.add(block);
	}	
	
	/**
	 * Abstract class to add finishing classes to the structure.
	 */
	protected abstract void finishingTouches();
	
	/**
	 * Abstract class to initialize the task.
	 */
	protected abstract void init();
	
	/**
	 * Checks if the AI is on the building spot.
	 */
	protected boolean isOnBuildingSpot () {
		return (mob.getPositionVector().distanceTo(buildingSpot) < 1);
	}
	
	/**
	 * Causes the AI to attempt to move to the building spot.
	 */
	protected void moveToBuildingSpot() {
		mob.getNavigator().tryMoveToXYZ(buildingSpot.xCoord, buildingSpot.yCoord, buildingSpot.zCoord, 1.0D);
	}
	/**
	 * Returns true if the structure has been built once by the AI.
	 */
	public boolean hasBuiltOnce () {
		return hasBuiltOnce;
	}
	
	/**
	 * Returns true if the structure is incomplete.
	 */
	public boolean isBuilding () {
		return hasBuildingInit;
	}
}
