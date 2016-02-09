package fuzzyMod.fuzzyLogic;

import java.util.Random;

import fuzzyMod.entity.EntityMobWithInventory;
import fuzzyMod.targetTasks.NearestTarget;
import fuzzyMod.targetTasks.PlayerLastAttackedTarget;
import fuzzyMod.targetTasks.PlayerLastAttackerTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class FuzzyBrain {
	private EntityMobWithInventory mob;
	private FuzzyInterpreter fin;
	EntityPlayer player;
	World world;
	public FuzzyBrain (EntityMobWithInventory mob, int slotNo) {
		this.mob = mob;
		fin = new FuzzyInterpreter();
		player = Minecraft.getMinecraft().thePlayer;
		world = Minecraft.getMinecraft().theWorld;
	}

	// should be executed every 10 ticks probably (0.5 seconds)
	private void setInputs () {
		fin.setInput("Health", this.mob.getHealth());
		fin.setInput("Mana", this.mob.getMana());
		fin.setInput("ArrowCount", this.mob.getArrows());
		fin.setInput("NumEnemies", NearestTarget.numEnemies(this.mob));
		fin.setInput("NumAllies", NearestTarget.numAllies(this.mob));
		fin.setInput("PlayerHealth", player.getHealth());
		fin.setInput("Sky", world.getSunBrightness(0));
		fin.setInput("Playerhunger", player.getFoodStats().getFoodLevel());
		fin.setInput("OreCount", new Random().nextInt((40-0)+1));
		fin.setInput("BuildingBlockCount", new Random().nextInt((40-0)+1));
		fin.setInput("Weather", world.rainingStrength+ world.thunderingStrength*3);
		if (NearestTarget.nearestEnemy(this.mob) != null) {
			fin.setInput("NearestEnemyStrength", NearestTarget.nearestEnemy(this.mob).getStrength());
		}
		PlayerLastAttackedTarget.updateLastTarget(mob);
		if (PlayerLastAttackedTarget.getLastTarget() != null) {
			fin.setInput("PlayerTargetStrength", PlayerLastAttackedTarget.getLastTarget().getStrength());
		} else {
			fin.setInput("PlayerTargetStrength", 0);
		}
		PlayerLastAttackerTarget.updatePlayerAttacker();
		if (PlayerLastAttackerTarget.getPlayerAttacker() != null) {
			fin.setInput("PlayerAttackerStrength", PlayerLastAttackedTarget.getLastTarget().getStrength());
		} else {
			fin.setInput("PlayerAttackerStrength" , 0);
		}
		if (this.mob.getLastAttacker() != null && this.mob.getLastAttackerTime() < 100) {
			if (this.mob.getLastAttacker() instanceof EntityMobWithInventory) {
				EntityMobWithInventory attacker = (EntityMobWithInventory)this.mob.getLastAttacker();
				fin.setInput("AttackerStrength", attacker.getStrength());
			} else {
				fin.setInput("AttackerStrength", 0);
			}
		}
		
	}
}
