package fuzzyMod.entity;

import java.util.Iterator;

import com.google.common.collect.Multimap;

import fuzzyMod.inventory.MobInventory;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public abstract class EntityMobWithInventory extends EntityMob {
	protected double mana;
	protected int arrows;
	protected MobInventory inventory;
	protected int team;
	public EntityMobWithInventory(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
		inventory = new MobInventory(10);
		mana = 20;
		arrows = 15;
		team = 1;
	}
	
	public MobInventory getMobIventory() {
		return inventory;
	}
	
	public double getMana() {
		return mana;
	}
	
	public int getArrows () {
		return arrows;
	}
	
	public void setTeam(int team) {
		this.team = team;
	}
	
	public int getTeamNo() {
		return this.team;
	}
	
	public double getStrength() {
		double attackDamage = 0;
		if (this.getHeldItem() != null) {
			Multimap mm = this.getHeldItem().getAttributeModifiers();
			if (mm != null && mm.containsKey("generic.attackDamage")) {
				Iterator <AttributeModifier> it = mm.get("generic.attackDamage").iterator();
				if (it.hasNext()) {
					AttributeModifier am = it.next();
					if (am != null && am instanceof AttributeModifier) {
						attackDamage = am.getAmount();
					}
				}
			}	
		}
		double armor = this.getTotalArmorValue();

		return armor + attackDamage;
	}
	
}
