package fuzzyMod.fuzzyLogic;

import fuzzyMod.entity.EntityMobWithInventory;
import fuzzyMod.targetTasks.NearestTarget;
import fuzzyMod.targetTasks.PlayerLastAttackedTarget;
import fuzzyMod.targetTasks.PlayerLastAttackerTarget;
import fuzzyMod.tasks.ArrowAttack;
import fuzzyMod.tasks.BuildFarm;
import fuzzyMod.tasks.BuildHouse;
import fuzzyMod.tasks.BuildMine;
import fuzzyMod.tasks.FireballAttack;
import fuzzyMod.tasks.HarvestCrops;
import fuzzyMod.tasks.MeleeAttack;
import fuzzyMod.tasks.MineOres;
import fuzzyMod.tasks.SowSeeds;
import fuzzyMod.tasks.StoreLoot;

public class FuzzyTasker {
	private EntityMobWithInventory mob;
	ArrowAttack arrow;
	FireballAttack fireball;
	MeleeAttack melee;
	BuildHouse smallhouse;
	BuildHouse largehouse;
	BuildMine shortmine;
	BuildMine longmine;
	BuildFarm smallfarm, largefarm;
	HarvestCrops harvest;
	SowSeeds sow;
	StoreLoot loot;
	MineOres mine;
	int taskMode;
	int buildspeed;
	public FuzzyTasker (EntityMobWithInventory mob) {
		this.mob = mob;
		fireball = new FireballAttack(this.mob, 30, 3);
		melee = new MeleeAttack(this.mob);
		arrow = new ArrowAttack (this.mob, 30);
		smallhouse = new BuildHouse(this.mob, 8, 8, 10);
		largehouse = new BuildHouse(this.mob, 15, 15, 10);
		shortmine = new BuildMine(this.mob, 10);
		longmine = new BuildMine(this.mob, 20);
		smallfarm = new BuildFarm (this.mob, 10, 10);
		largefarm = new BuildFarm (this.mob, 20, 20);
		harvest = new HarvestCrops(this.mob, 30);
		sow = new SowSeeds(this.mob, 30);
		loot = new StoreLoot(this.mob,30);
		mine = new MineOres(this.mob,30);
		taskMode = -1;
		buildspeed = 5;
	}
	
	public EntityMobWithInventory targeter (int mode) {
		EntityMobWithInventory target;
		switch (mode) {
			case 0: {
				// nearest enemy
				target = NearestTarget.nearestEnemy(mob);
				break;
			}
			case 1: {
				// player last attacked target
				target = PlayerLastAttackedTarget.getLastTarget(mob); 
			}
			case 2: {
				// player last attacker target
				target = PlayerLastAttackerTarget.getPlayerAttacker();
			}
			default:{
				target = null;
			}
		}
		return target;
	}
	
	public void setTask (int mode) {
		taskMode = mode;
	}
	
	public void nextStep () {
		switch (taskMode) {
		case 0: {
			fireball.nextStep();
			break;
		}
		case 1: {
			melee.nextStep();
			break;
		}
		case 2: {
			arrow.nextStep();
			break;
		}
		case 3: {
			smallhouse.init();
			smallhouse.attemptBuildBlock(buildspeed);
			break;
		}
		case 4: {
			largehouse.init();
			largehouse.attemptBuildBlock(buildspeed);
			break;
		}
		case 5: {
			shortmine.init();
			shortmine.attemptBuildBlock(buildspeed);
			break;
		}
		case 6: {
			longmine.init();
			longmine.attemptBuildBlock(buildspeed);
			break;
		} 
		case 7: {
			smallfarm.init();
			smallfarm.attemptBuildBlock(buildspeed);
			break;
		}
		case 8: {
			largefarm.init();
			largefarm.attemptBuildBlock(buildspeed);
			break;
		}
		case 9 : {
			harvest.nextStep();
			break;
		}
		case 10 : {
			if (this.mob.getMobIventory().isFull()) {
				loot.nextStep();
			} else {
				sow.nextStep();
			}
			break;
		}
		case 11: {
			if (this.mob.getMobIventory().isFull()) {
				loot.nextStep();
			} else {
				mine.nextStep();
			}
		}
		default: {
			return;
		}
		}
	}

}