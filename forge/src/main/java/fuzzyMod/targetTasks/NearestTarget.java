package fuzzyMod.targetTasks;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;

public class NearestTarget {
	
	public static EntityMobWithInventory nearestEnemy (EntityMobWithInventory mob) {
		List <EntityMobWithInventory> enemylist = NearestTarget.enemylist(mob);
		if (enemylist.size() == 0) {
			return null;
		}
		Collections.sort(enemylist, new NearestTarget.Sorter(mob));
		return enemylist.get(0);
	}
	
	public static EntityMobWithInventory nearestAttacker (EntityMobWithInventory mob) {
		List <EntityMobWithInventory> enemylist = NearestTarget.enemylist(mob);
		if (enemylist.size() == 0) {
			return null;
		}
		Collections.sort(enemylist, new NearestTarget.Sorter(mob));
		for (int i = 0 ; i < enemylist.size(); i++) {
			if (enemylist.get(i).getAttackTarget() == mob) {
				return enemylist.get(i);
			}
		}
		return null;
	}
	
	public static List<EntityMobWithInventory> enemylist (final EntityMobWithInventory mob) {
		Predicate targetEnemySelector = new Predicate()
        {
            public boolean checkIfDifferentTeam(EntityMobWithInventory target)
            {
                if (target.getTeamNo() == mob.getTeamNo())
                {
                    return false;
                }
                if (target == mob) {
                	return false;
                }

                return true;
            }
            public boolean apply(Object target)
            {	
            	if (!(target instanceof EntityMobWithInventory)) {
            		return false;
            	}
                return this.checkIfDifferentTeam((EntityMobWithInventory)target);
            }
        };
        List list = mob.worldObj.getEntitiesWithinAABB(EntityMobWithInventory.class, mob.getEntityBoundingBox().expand(30.0D, 4.0D, 30.0D), 
        		Predicates.and(targetEnemySelector, IEntitySelector.NOT_SPECTATING));
        return list;
	}
	
	
	public static int numEnemies(EntityMobWithInventory mob) {
		List list = NearestTarget.enemylist(mob);
        return list.size();
	}
	
	public static int numAllies(final EntityMobWithInventory mob) {
		Predicate targetAllySelector = new Predicate()
        {
            public boolean checkIfSameTeam(EntityMobWithInventory target)
            {
                if (target.getTeamNo() != mob.getTeamNo())
                {
                    return false;
                }
                if (target == mob) {
                	return false;
                }

                return true;
            }
            public boolean apply(Object target)
            {	
            	if (!(target instanceof EntityMobWithInventory)) {
            		return false;
            	}
                return this.checkIfSameTeam((EntityMobWithInventory)target);
            }
        };
        List list = mob.worldObj.getEntitiesWithinAABB(EntityMobWithInventory.class, mob.getEntityBoundingBox().expand(40.0D, 4.0D, 40.0D), 
        		Predicates.and(targetAllySelector, IEntitySelector.NOT_SPECTATING));
        return list.size();
	}
	
	public static class Sorter implements Comparator
    {
        private final Entity theEntity;

        public Sorter(Entity mob)
        {
            this.theEntity = mob;
        }

        public int compare(Entity p_compare_1_, Entity p_compare_2_)
        {
            double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
            double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
            return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
        }

        public int compare(Object p_compare_1_, Object p_compare_2_)
        {
            return this.compare((Entity)p_compare_1_, (Entity)p_compare_2_);
        }
    }
}
