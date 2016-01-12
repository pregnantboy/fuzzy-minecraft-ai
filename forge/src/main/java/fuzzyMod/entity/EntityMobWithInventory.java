package fuzzyMod.entity;

import fuzzyMod.inventory.MobInventory;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public abstract class EntityMobWithInventory extends EntityMob {

	private MobInventory inventory;
	public EntityMobWithInventory(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
		inventory = new MobInventory(10);
	}
	
	public MobInventory getMobIventory() {
		return inventory;
	}
	
}
