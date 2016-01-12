package fuzzyMod.inventory;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;

public class MobInventory {
	private int limit, count;
	private Map <Item, Integer> invMap;
	public MobInventory (int limit) {
		this.limit = limit;
		count = 0;
		invMap = new HashMap ();
	}
	
	public boolean isFull () {
		return count >= limit;
	}
	
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
	
	public void dumpAll () {
		count = 0;
		invMap.clear();
	}
	
	public void printInventory () {
		for (Item i: invMap.keySet()) {
			System.out.println(i.getUnlocalizedName() + " x" + invMap.get(i));
		}
	}
	
}
