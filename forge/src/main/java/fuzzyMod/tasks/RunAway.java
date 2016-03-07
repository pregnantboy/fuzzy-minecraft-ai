package fuzzyMod.tasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class RunAway {
	protected EntityMobWithInventory mob;
	protected Entity source;
	protected World world;
	protected double runSpeed;
	protected boolean destinationSet;
	protected PathEntity path;
	protected Vec3 destination;

	public RunAway(EntityMobWithInventory mob, double speed) {
		this.mob = mob;
		this.runSpeed = speed;
		this.destinationSet = false;
	}

	public void setSource(Entity source) {
		this.source = source;
	}

	private void setDestination() {
		if (this.source != null) {
			if (this.source.getUniqueID() != mob.getUniqueID()) {
				Vec3 destination = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.mob, 16, 7,
						new Vec3(this.source.posX, this.source.posY, this.source.posZ));
				System.out.println("destination set:" +destination);
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

	public void nextStep() {
		if (!this.mob.isEntityAlive() || this.source == null || !this.source.isEntityAlive()) {
			return;
		}
		if (this.mob.getPositionVector().distanceTo(source.getPositionVector()) < 30) {
			setDestination();
		}
		if (this.destinationSet) {
			moveToBlock();
		} else {
			setDestination();
		}
	}

	protected void moveToBlock() {
		this.mob.getNavigator().setPath(this.path, this.runSpeed);
	}
}
