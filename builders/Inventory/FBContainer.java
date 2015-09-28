package minecraft.FirstPlugin.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FBContainer extends Container
{
	protected IInventory gear;

    private int player_inventory_x = 70;
    private int player_inventory_y = 8;
    private int loot_x = 8;
    private int loot_y = 10;

    public FBContainer (InventoryPlayer inventoryPlayer, IInventory c)
    {
        gear = c;
        {
            int isize =  c.getSizeInventory();
            
            for (int i = 0; i < 4; i++) 
            {
                for (int j = 0; j < 2; j++) 
                {
                    addSlotToContainer(new Slot(c, j + i * 2, (loot_x + j * 18), (loot_y + i * 18)));
                }
            }
        }
        bindPlayerInventory(inventoryPlayer);
    }

    public boolean canInteractWith(EntityPlayer player) 
    {
        return gear.isUseableByPlayer(player);
    }


    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) 
    {
        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 9; j++) 
            {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                player_inventory_x + j * 18, player_inventory_y + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) 
        {
            addSlotToContainer(new Slot(inventoryPlayer, i, player_inventory_x + i * 18, player_inventory_y + 54 + 4));
        }
    }

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int s = gear.getSizeInventory();

            if (par2 < s)
            {
                if (!this.mergeItemStack(itemstack1, s, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, s, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);
        this.gear.closeInventory(par1EntityPlayer);
    }
}
