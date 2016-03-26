package fuzzyMod;

import fuzzyMod.targetTasks.PlayerLastAttackedTarget;
import fuzzyMod.targetTasks.PlayerLastAttackerTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This class is in charge of events that occur every tick when the player is rendered in the game
 */
public class PlayerTickEvents {
	int ticker;
	
	public PlayerTickEvents () {
		ticker = 10;
	}
	
	/**
	 * Methods to call on every tick when Player is alive, called per player.
	 */
	@SubscribeEvent
    public void onPlayerTick(LivingUpdateEvent e)

    {

        if (e.entity instanceof EntityPlayer)

        {
        	if (ticker > 0) {
        		ticker --;
        		return;
        	} else {
        		ticker = 10;
        		PlayerLastAttackedTarget.updateLastTarget();
        		PlayerLastAttackerTarget.updatePlayerAttacker();
        	}
        }

    }

}
