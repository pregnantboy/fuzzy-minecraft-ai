package minecraft.FirstPlugin.Inventory;

import minecraft.FirstPlugin.entities.FarmingBot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler
{
	@Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) 
	{       
        if(id==5)
        {
   
            Entity ent = world.getEntityByID(x);
            if(ent!=null && ent instanceof FarmingBot)
            {
                return new FBContainer(player.inventory, ((FarmingBot)ent).gear);
            }
        }
        return null;
    }


    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) 
    {
        if(id==5)
        {
            Entity ent = world.getEntityByID(x);
            if(ent!=null && ent instanceof FarmingBot)
            {
                return new FBEntityGUI(player.inventory, new FBInventory("MyInventory", ((FarmingBot) ent).howManySlots()));
            }
        }
        return null;
    }
}
