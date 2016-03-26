package fuzzyMod.inventory;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Inventory for custom Mobs.
 */
public class MobInventory {
	private int limit, count;
	private Map <Item, Integer> invMap;
	/**
	 * Constructor class for MobInventory.
	 * @param limit The maximum capacity of the inventory.
	 */
	public MobInventory (int limit) {
		this.limit = limit;
		count = 0;
		invMap = new HashMap ();
	}
	
	/**
	 * Returns true if inventory is full.
	 */
	public boolean isFull () {
		return count >= limit;
	}
	
	/**
	 * Adds an item stack into the inventory
	 * @param item Item of item stack.
	 * @param quantity Size of stack.
	 */
	public void addItem (Item item, int quantity) {
		if (!this.isFull()) {
			quantity = Math.min(quantity, limit-count);
			count += quantity;
			if (invMap.containsKey(item)) {
				int currentQty = invMap.get(item);
				quantity += currentQty;
			} 
			invMap.put(item, quantity);
		}
	}
	
	/**
	 * Removes everything in the current inventory.
	 */
	public void dumpAll () {
		count = 0;
		invMap.clear();
	}
	
	/**
	 * Returns array of ItemStack in the inventory.
	 */
	public ItemStack[] getItemStacks () {
		ItemStack [] itemStacks = new ItemStack [invMap.size()];
		int index = 0;
		for (Item i: invMap.keySet()) {
			itemStacks[index] = new ItemStack (i, invMap.get(i));
			index ++;
		}
		return itemStacks;
	}
	/**
	 * Prints the inventory list for debugging purposes.
	 */
	public void printInventory () {
		for (Item i: invMap.keySet()) {
			System.out.println(i.getUnlocalizedName() + " x" + invMap.get(i));
		}
	}
	
}
