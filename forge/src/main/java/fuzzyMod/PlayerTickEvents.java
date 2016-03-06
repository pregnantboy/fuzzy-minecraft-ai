package fuzzyMod;

import fuzzyMod.targetTasks.PlayerLastAttackedTarget;
import fuzzyMod.targetTasks.PlayerLastAttackerTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerTickEvents {
	int ticker;
	
	public PlayerTickEvents () {
		ticker = 10;
	}
	
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
