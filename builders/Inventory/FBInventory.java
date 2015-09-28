package minecraft.FirstPlugin.Inventory;

import net.minecraft.inventory.InventoryBasic;

public class FBInventory extends InventoryBasic
{
	public FBInventory(String name, int slots)
    {
        super(name, false, slots);
    }
}
