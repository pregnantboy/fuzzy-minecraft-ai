package fuzzyMod.tasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
/**
 * Non-combat task that runs away from its target.
 */
public class RunAway {
	protected EntityMobWithInventory mob;
	protected Entity source;
	protected World world;
	protected double runSpeed;
	protected boolean destinationSet;
	protected PathEntity path;
	protected Vec3 destination;
	protected int ticker;
	/**
	 * Constructor class
	 * @param mob The AI executing this task.
	 * @param speed The movement speed when running away.
	 */
	public RunAway(EntityMobWithInventory mob, double speed) {
		this.mob = mob;
		this.runSpeed = speed;
		this.destinationSet = false;
		ticker = 0;
	}
	
	/**
	 * Set the target to run away from.
	 */
	public void setSource(Entity source) {
		this.source = source;
	}
	/**
	 * Looks for a random position in the opposite direction of the target.
	 */
	private void setDestination() {
		if (this.source != null) {
			if (this.source.getUniqueID() != mob.getUniqueID()) {
				Vec3 destination = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.mob, 16, 7,
						new Vec3(this.source.posX, this.source.posY, this.source.posZ));
//				System.out.println("destination set:" +destination);
				if (destination != null) {
					if (this.source.getDistanceSq(destination.xCoord, destination.yCoord,
							destination.zCoord) > this.source.getDistanceSqToEntity(this.mob)) {
						PathEntity newpath = this.mob.getNavigator().getPathToXYZ(destination.xCoord, destination.yCoord,
								destination.zCoord);
						if (newpath != null) {
							if (this.path != newpath) {
								this.path = newpath;
								this.destination = destination;
								this.destinationSet = true;
								return;
							}
						}
					}
				}
			}
		}
		this.destinationSet = false;
	}
	/**
	 * Sets the next action of this task.
	 */
	public void nextStep() {
		if (!this.mob.isEntityAlive() || this.source == null || !this.source.isEntityAlive()) {
			return;
		}
		if (this.mob.getPositionVector().distanceTo(source.getPositionVector()) < 30) {
			setDestination();
		}
		if (ticker < 0) {
			ticker = 30;
			if (this.destinationSet) {
				moveToBlock();
			} else {
				setDestination();
			}
		} 
		ticker --;
	}
	/**
	 * Attempts to move the AI to the set destination.
	 */
	protected void moveToBlock() {
		this.mob.getNavigator().setPath(this.path, this.runSpeed);
	}
}
