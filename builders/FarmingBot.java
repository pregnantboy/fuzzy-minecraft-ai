package minecraft.FirstPlugin.entities;

import minecraft.FirstPlugin.FirstMod;
import minecraft.FirstPlugin.Ref;
import minecraft.FirstPlugin.Inventory.FBInventory;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class FarmingBot extends EntityMob
{
	public FBInventory gear;
	
	private InventoryBasic fBotInv;
	
	double xPos, yPos, zPos;
	BlockPos pos1;
	TileEntityChest chest;
	//boolean runOnce = true;
	
	public FarmingBot(World worldIn) 
	{
		super(worldIn);
		
		this.fBotInv = new InventoryBasic("Items", false, 8);
		this.setupGear();
		
		this.setSize(0.6F, 1.8F);
		
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityHarvestFarmBot(this, 0.6D));
		
		this.setCanPickUpLoot(true);
	}

	public boolean isAIEnable()
	{
		return true;
	}
	
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.2D);
	}
	
	@Override
    public boolean interact(EntityPlayer player) 
	{
        super.interact(player);
        
        //if(!player.worldObj.isRemote)
        player.openGui(Ref.MOD_ID, 5, this.worldObj, this.getEntityId(), 0, 0); //we will use x coord for sending entityId
        
        return true;
    }
	
	public int howManySlots() 
	{
        return 8;
    }

	public void onInventoryChanged(InventoryBasic inv) {}
	
    private void setupGear() 
    {
        InventoryBasic gear1 = this.fBotInv;
        this.gear = new FBInventory("FBInventory", howManySlots());

        if (gear1 != null) 
        {
            int i = Math.min(gear1.getSizeInventory(), this.gear.getSizeInventory());

            for (int j = 0; j < i; ++j)
            {
                ItemStack itemstack = gear1.getStackInSlot(j);

                if (itemstack != null) 
                {
                    this.gear.setInventorySlotContents(j, itemstack.copy());
                }
            }

            gear1 = null;
        }
    }

    public boolean func_175556_cs()
    {
        for (int i = 0; i < this.gear.getSizeInventory(); ++i)
        {
            ItemStack itemstack = this.gear.getStackInSlot(i);

            if (itemstack != null && (itemstack.getItem() == Items.wheat_seeds || itemstack.getItem() == Items.potato || itemstack.getItem() == Items.carrot))
            {
                return true;
            }
        }

        return false;
    }
    
    protected void func_175445_a(EntityItem p_175445_1_)
    {
        ItemStack itemstack = p_175445_1_.getEntityItem();
        Item item = itemstack.getItem();

        if (this.func_175558_a(item))
        {
            ItemStack itemstack1 = this.gear.func_174894_a(itemstack);

            if (itemstack1 == null)
            {
                p_175445_1_.setDead();
            }
            else
            {
                itemstack.stackSize = itemstack1.stackSize;
            }
        }
    }

    private boolean func_175558_a(Item p_175558_1_)
    {
        return p_175558_1_ == Items.bread || p_175558_1_ == Items.potato || p_175558_1_ == Items.carrot || p_175558_1_ == Items.wheat || p_175558_1_ == Items.wheat_seeds;
    }
    
    public InventoryBasic func_175551_co()
    {
        return this.gear;
    }
    
    public void onDeath(DamageSource ds)
    {
        super.onDeath(ds);
        if (gear != null && !this.worldObj.isRemote) 
        {
            for (int i = 0; i < gear.getSizeInventory(); ++i) 
            {
                ItemStack itemstack = gear.getStackInSlot(i);

                if (itemstack != null) 
                {
                    this.entityDropItem(itemstack, 0.0F);
                }
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound com) 
    {
        NBTTagList nbttaglist = new NBTTagList();
        
        for (int i = 0; i < this.gear.getSizeInventory(); ++i) 
        {
            ItemStack itemstack = this.gear.getStackInSlot(i);
            
            if (itemstack != null) 
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                itemstack.writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
            com.setTag("Items", nbttaglist);
        }
    }

    public void readEntityFromNBT(NBTTagCompound com) 
    {
        NBTTagList nbttaglist = com.getTagList("Items", 10);
        this.setupGear();
        
        for (int i = 0; i < nbttaglist.tagCount(); ++i) 
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;
            
            if (j >= 0 && j < this.gear.getSizeInventory()) 
            {
                this.gear.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }
    }
    
    public void storeHarvestedCrops()
    {	
		InventoryBasic inventorybasic = this.gear;
		
    	for (int i = 0; i < inventorybasic.getSizeInventory(); ++i)
		{
			ItemStack itemstack = inventorybasic.getStackInSlot(i);
			
			if (itemstack != null)
    		{
				if ((itemstack.getItem() == Items.carrot || itemstack.getItem() == Items.potato) && itemstack.stackSize > 9)
				{
					pos1 = findNearestChest();
						
					if(pos1 != null)
					{
						chest = (TileEntityChest) this.worldObj.getTileEntity(pos1);
						
						for (int j = 0; j < 27; j++)
						{
							ItemStack cItemStack = chest.getStackInSlot(j);
							
							if(cItemStack != null)
							{
								
								if(itemstack.getItem() == cItemStack.getItem() && cItemStack.stackSize != 64)
								{
									int chestS = cItemStack.stackSize;
									int gearS = itemstack.stackSize - 5;
									
									chest.setInventorySlotContents(j, cItemStack.splitStack((itemstack.stackSize - 5) + cItemStack.stackSize));
									gear.setInventorySlotContents(i, itemstack.splitStack(5));
									
									if((chestS + gearS) > 64)
									{
										for (int k = 0; k < 27; k++)
										{
											ItemStack c2ItemStack = chest.getStackInSlot(k);
											
											if(c2ItemStack == null)
											{
												chest.setInventorySlotContents(k, cItemStack.splitStack((chestS + gearS) - 64));
												break;
											}
										}
									}
									break;
								}
							}
							else
							{
								chest.setInventorySlotContents(j, itemstack.splitStack(itemstack.stackSize - 5));
								gear.setInventorySlotContents(i, itemstack);
								break;
							}
						}
					}
				}
				
				else if(itemstack.getItem() == Items.wheat && itemstack.stackSize > 4)
				{
					pos1 = findNearestChest();
					
					if(pos1 != null)
					{
						TileEntityChest chest = (TileEntityChest) this.worldObj.getTileEntity(pos1);
						
						chest.setInventorySlotContents(i, itemstack);
						gear.setInventorySlotContents(i, (ItemStack) null);

					}	
				}
    		}
		}   	
    }
    
    public BlockPos findNearestChest()
    {
    	for (double x = this.posX-10; x < this.posX+10; x++) 
		{	
		for (double y = this.posY-10; y < this.posY+10; y++) 
		{
		for (double z = this.posZ-10; z < this.posZ+10; z++) 
		{
			BlockPos pos = new BlockPos(x, y, z);
			
			if (this.worldObj.getBlockState(pos).getBlock() == Blocks.chest)
			{
				PathEntity path = this.getNavigator().getPathToXYZ(x, y, z);
				this.getNavigator().setPath(path, 0.8);
				return pos;
			}
		}
		}
		}
    	return null;
    }
    
    public void onUpdate()
    {
    	super.onUpdate();
    	
    	storeHarvestedCrops();
    }
    
}
