package fuzzyMod.tasks;

import fuzzyMod.entity.EntityMobWithInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
/**
 * Non-combat task that allows the AI to store its current inventory in a chest.
 */
public class StoreLoot extends SearchTaskGeneric{
	private boolean storingLoot;
	private ItemStack[] itemStacks;
	private TileEntityChest tileEntity;
	private int currIndex;
	/**
	 * Constructor class for store loot task.
	 * @param mob The AI executing the task.
	 * @param range The range to search in.
	 */
	public StoreLoot(EntityMobWithInventory mob, int range) {
		super(mob, range);
		storingLoot = false;
	}
	
	/**
	 * Sets the next action for the task. Could be looking for, moving to or dumping loot in a chest.
	 */
	@Override
	public void nextStep() {
		if (!storingLoot) {
			nextBlock = getNextBlock(0);
			if (storingLoot) {
				itemStacks = mob.getMobIventory().getItemStacks();
				currIndex = 0;
				tileEntity = (TileEntityChest)world.getTileEntity(nextBlock);
			}
		} else {
			if (reachedBlock()) {
				dumpLoot(); 
			} else {
				moveToBlock();
			}
		}
	}
	/**
	 * Checks if block is chest.
	 * @param i,j,k The position of the block to check.
	 * @param mode not necessary.
	 */
	@Override
	protected boolean isCorrectBlock(int i, int j, int k, int mode) {
		BlockPos pos = new BlockPos(i, j + 1, k);
		IBlockState currBlockState = world.getBlockState(pos);
		Block currBlock = currBlockState.getBlock();
		if (currBlock instanceof BlockChest) {
			storingLoot = true;
			return true;
		} 
		return false;
	}
	/**
	 * Stores all item in the chest. The chest will show the items stored if the player interacts with 
	 */
	protected void dumpLoot() {
		// if finished storing
		 if (currIndex >= itemStacks.length) {
			System.out.println("finished dumping");
			storingLoot = false;
			mob.getMobIventory().dumpAll();
		 } else {
			 ItemStack currItemStack = itemStacks[currIndex];
			 for (int i = 0 ; i < tileEntity.getSizeInventory(); i ++) {
				 ItemStack slotStack = tileEntity.getStackInSlot(i);
				 if (slotStack == null) {
					 // stores in empty slot
					 tileEntity.setInventorySlotContents(i, currItemStack);
					 break;
				 } else {
					 if (slotStack.getItem().getUnlocalizedName().equalsIgnoreCase(currItemStack.getItem().getUnlocalizedName())) {
						 // found same item in slot - will increase stack
						 currItemStack.stackSize += slotStack.stackSize;
						 tileEntity.setInventorySlotContents(i, currItemStack);
						 break;
					 }
				 }
			 }
			 currIndex ++;
			 System.out.println("current index increased to: " + currIndex);
		 }
	}
	
}
