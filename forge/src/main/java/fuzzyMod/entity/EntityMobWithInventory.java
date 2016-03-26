package fuzzyMod.entity;

import java.util.Iterator;

import com.google.common.collect.Multimap;

import fuzzyMod.inventory.MobInventory;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

/**
 * An abstract class that extends from the base class EntityMob. It features extended attributes like mana, team, arrows
 * and an inventory to keep track of stored items.
 * This class should be implemented for the fuzzy mod.
 */
public abstract class EntityMobWithInventory extends EntityMob {
	protected double mana;
	protected int arrows;
	protected MobInventory inventory;
	protected int team;
	protected EntityMobWithInventory attacker;
	int ticker;
	/**
	 * entityMobWithInventory constructor
	 */
	public EntityMobWithInventory(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
		attacker = null;
		inventory = new MobInventory(10);
		mana = 20;
		arrows = 15;
		team = 1;
		ticker = 500;
	}
	
	/**
	 * Returns mob inventory
	 */
	public MobInventory getMobIventory() {
		return inventory;
	}
	
	/**
	 * Returns mob Mana.
	 */
	public double getMana() {
		return mana;
	}
	
	/**
	 * Returns mob arrow count.
	 */
	public int getArrows () {
		return arrows;
	}
	
	/**
	 * Set the team number of the mob.
	 */
	public void setTeam(int team) {
		this.team = team;
	}
	
	/**
	 * Returns the team number of the mob.
	 */
	public int getTeamNo() {
		return this.team;
	}
	
	/**
	 * Called on every tick of the mob.
	 */
	public void onUpdate() {
		super.onUpdate();
		if (ticker> 0) {
			ticker --;
			return;
		} 
		ticker = 20;
		if (arrows < 15) {
			arrows ++;
		}
		if (mana < 20) {
			mana ++;
		}
	}
	
	/**
	 * Calculates and returns the strenght depending on the held item and armor attributes.
	 */
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
	
	/**
	 * Sets the attacker of the mob.
	 */
	public void setAttacker(EntityMobWithInventory attacker) {
		this.attacker = attacker;
	}
	
	/**
	 * Returns the attacker of the mob.
	 */
	public EntityMobWithInventory getAttacker() {
		if (attacker != null && attacker.isEntityAlive()) {
			return attacker;
		} else {
			return null;
		}
	}
	
	/**
	 * Decrements the arrow count of the mob.
	 * @param arrow number of arrows to decrement.
	 */
	public void useArrow(int arrow) {
		this.arrows -= arrow;
	}
	
	/**
	 * Decrements the mana points of the mob.
	 * @param mana number of mana points to decrement.
	 */
	public void useMana(int mana) {
		this.mana -=mana;
	}
}
