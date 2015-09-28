package minecraft.FirstPlugin.entities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityHarvestFarmBot extends EntityAIMoveToBlock
{

	 /** Villager that is harvesting */
    private final FarmingBot fBot;
    private boolean field_179502_d;
    private int field_179501_f;
    private static final String __OBFID = "CL_00002253";

    public EntityHarvestFarmBot(FarmingBot p_i45889_1_, double p_i45889_2_)
    {
        super(p_i45889_1_, p_i45889_2_, 16);
        this.fBot = p_i45889_1_;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.field_179496_a <= 0)
        {
            if (!this.fBot.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
            {
                return false;
            }

            this.field_179501_f = -1;
            this.field_179502_d = this.fBot.func_175556_cs();
        }

        return super.shouldExecute();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.field_179501_f >= 0 && super.continueExecuting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        super.resetTask();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        super.updateTask();
        this.fBot.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.fBot.getVerticalFaceSpeed());

        if (this.func_179487_f())
        {
            World world = this.fBot.worldObj;
            BlockPos blockpos = this.destinationBlock.up();
            IBlockState iblockstate = world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (this.field_179501_f == 0 && block instanceof BlockCrops && ((Integer)iblockstate.getValue(BlockCrops.AGE)).intValue() == 7)
            {
                world.destroyBlock(blockpos, true);
            }
            else if (this.field_179501_f == 1 && block == Blocks.air)
            {
                InventoryBasic inventorybasic = this.fBot.func_175551_co();

                for (int i = 0; i < inventorybasic.getSizeInventory(); ++i)
                {
                    ItemStack itemstack = inventorybasic.getStackInSlot(i);
                    boolean flag = false;

                    if (itemstack != null)
                    {
                        if (itemstack.getItem() == Items.wheat_seeds)
                        {
                            world.setBlockState(blockpos, Blocks.wheat.getDefaultState(), 3);
                            flag = true;
                        }
                        else if (itemstack.getItem() == Items.potato)
                        {
                            world.setBlockState(blockpos, Blocks.potatoes.getDefaultState(), 3);
                            flag = true;
                        }
                        else if (itemstack.getItem() == Items.carrot)
                        {
                            world.setBlockState(blockpos, Blocks.carrots.getDefaultState(), 3);
                            flag = true;
                        }
                    }

                    if (flag)
                    {
                        --itemstack.stackSize;

                        if (itemstack.stackSize <= 0)
                        {
                            inventorybasic.setInventorySlotContents(i, (ItemStack)null);
                        }

                        break;
                    }
                }
            }

            this.field_179501_f = -1;
            this.field_179496_a = 10;
        }
    }

    protected boolean func_179488_a(World worldIn, BlockPos p_179488_2_)
    {
        Block block = worldIn.getBlockState(p_179488_2_).getBlock();

        if (block == Blocks.farmland)
        {
            p_179488_2_ = p_179488_2_.up();
            IBlockState iblockstate = worldIn.getBlockState(p_179488_2_);
            block = iblockstate.getBlock();

            if (block instanceof BlockCrops && ((Integer)iblockstate.getValue(BlockCrops.AGE)).intValue() == 7 && (this.field_179501_f == 0 || this.field_179501_f < 0))
            {
                this.field_179501_f = 0;
                return true;
            }

            if (block == Blocks.air && this.field_179502_d && (this.field_179501_f == 1 || this.field_179501_f < 0))
            {
                this.field_179501_f = 1;
                return true;
            }
        }

        return false;
    }

}
