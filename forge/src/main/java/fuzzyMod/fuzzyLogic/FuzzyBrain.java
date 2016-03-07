package fuzzyMod.fuzzyLogic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import fuzzyMod.entity.EntityMobWithInventory;
import fuzzyMod.targetTasks.NearestTarget;
import fuzzyMod.targetTasks.PlayerLastAttackedTarget;
import fuzzyMod.targetTasks.PlayerLastAttackerTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class FuzzyBrain {
	private EntityMobWithInventory mob;
	private FuzzyInterpreter fin;
	private FuzzyTasker futa;
	EntityPlayer player;
	World world;
	int ticker;
		
	public FuzzyBrain (EntityMobWithInventory mob, int slotNo) {
		this.mob = mob;
		fin = new FuzzyInterpreter(slotNo);
		player = Minecraft.getMinecraft().thePlayer;
		world = Minecraft.getMinecraft().theWorld;
		ticker = 100;
		futa = new FuzzyTasker(mob);
	}

	// should be executed every 10 ticks probably (0.5 seconds)
	public void setInputs () {
		if (player == null || world == null) {
			return;
		}
		if (ticker > 0) {
			ticker --;
			return;
		} 
		ticker = 100;
		fin.setInput("Health", this.mob.getHealth());
		fin.setInput("Mana", this.mob.getMana());
		fin.setInput("ArrowCount", this.mob.getArrows());
		fin.setInput("NumEnemies", NearestTarget.numEnemies(this.mob));
		fin.setInput("NumAllies", NearestTarget.numAllies(this.mob));
		fin.setInput("PlayerHealth", player.getHealth());
		fin.setInput("Sky", world.getSunBrightness(0));
		fin.setInput("PlayerHunger", player.getFoodStats().getFoodLevel());
		fin.setInput("OreCount", new Random().nextInt((40-0)+1));
		fin.setInput("BuildingBlockCount", new Random().nextInt((40-0)+1));
		fin.setInput("Weather", world.rainingStrength+ world.thunderingStrength*3);
		EntityMobWithInventory nearestEnemy = NearestTarget.nearestEnemy(this.mob);
		if (nearestEnemy != null) {
			fin.setInput("NearestEnemyStrength", nearestEnemy.getStrength());
			fin.setInput("DistFromNearestEnemy", this.mob.getDistanceToEntity(nearestEnemy));
		} else {
			fin.setInput("NearestEnemyStrength", 40);
			fin.setInput("DistFromNearestEnemy", 999);
		}
		EntityLivingBase lastTarget = (EntityLivingBase)PlayerLastAttackedTarget.getLastTarget(this.mob);
		if (lastTarget != null) {
			if (lastTarget instanceof EntityMobWithInventory) {
				fin.setInput("PlayerTargetStrength", ((EntityMobWithInventory)lastTarget).getStrength());
			} else {
				fin.setInput("PlayerTargetStrength", lastTarget.getMaxHealth()/1.5);
			}
		} else {
			fin.setInput("PlayerTargetStrength", 0);
		}
		
		EntityLiving playerAttacker =  PlayerLastAttackerTarget.getPlayerAttacker() ;
		if (playerAttacker != null) {
			if (playerAttacker instanceof EntityMobWithInventory) {
				fin.setInput("PlayerTargetStrength", ((EntityMobWithInventory)playerAttacker).getStrength());
			} else {
				fin.setInput("PlayerTargetStrength", playerAttacker.getMaxHealth()/1.5);
			}
		} else {
			fin.setInput("PlayerAttackerStrength" , 0);
		}
		EntityMobWithInventory attacker = this.mob.getAttacker();
		if (attacker != null && (lastTarget instanceof EntityMobWithInventory)) {
				fin.setInput("AttackerStrength", attacker.getStrength());
		} else {
			fin.setInput("AttackerStrength", 0);
		}
		ArrayList<String> action = fin.getActions();
		for (int i = 0 ; i <action.size(); i ++) {
			if (setAction(action.get(i))) {
				break;
			}
		}
	}
	
	public void printInputs() {
		fin.printInputs();
	}
	
	public boolean setAction(String action) {
		if (action.startsWith("AttackNearestEnemyWith")) {
			Entity target = futa.targeter(0);
			if (target != null) {
				mob.setAttackTarget((EntityLivingBase)target);
			} else {
				return false;
			}
		}
		if (action.startsWith("AttackPlayersTargetWith")) {
			Entity target = futa.targeter(1);
			System.out.println("the target" + target);
			if (target != null) {
				mob.setAttackTarget((EntityLivingBase)target);
			} else {
				return false;
			}
		}
		if (action.startsWith("AttackPlayersAttackerWith")) {
			Entity target = futa.targeter(2);
			if (target != null) {
				mob.setAttackTarget((EntityLivingBase)target);
			} else {
				return false;
			}
		}
		if (action.startsWith("AttackPlayerWith")) {
			if (mob.getTeamNo() != 1) {
				mob.setAttackTarget(player);
			} else {
				return false;
			}
		}
		if (action.endsWith("WithFireballs")) {
			futa.setTask(0);
			return true;
		}
		if (action.endsWith("WithMeleeWeapon")) {
			System.out.println("melee attack");
			futa.setTask(1);
			return true;
		}
		if (action.endsWith("WithArrows")) {
			futa.setTask(2);
			return true;
		}
		if (action.equals("BuildHouseSmall")) {
			futa.setTask(3);
			return true;
		}
		if (action.equals("BuildHouseLarge")) {
			futa.setTask(4);
			return true;
		}
		if (action.equals("BuildMineShort")) {
			futa.setTask(5);
			return true;
		}
		if (action.equals("BuildMineLong")) {
			futa.setTask(6);
			return true;
		}
		if (action.equals("BuildFarmSmall")) {
			futa.setTask(7);
			return true;
		}
		if (action.equals("BuildFarmLarge")) {
			futa.setTask(8);
			return true;
		}
		if (action.startsWith("Harvest")) {
			futa.setTask(9);
			return true;
		}
		if (action.startsWith("SowSeedsOf")) {
			futa.setTask(10);
			return true;
		}
		if (action.equals("MineOresAny")) {
			futa.setTask(11);
			return true;
		}
		return false;
	}
	
	public void nextStep () {
		futa.nextStep();
	}
}
