package minecraft.FirstPlugin.entities;

import java.util.Random;

import minecraft.FirstPlugin.Init.CrudeOil;
import minecraft.FirstPlugin.Init.Rice;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import CGameAI.GAgents.GAgent;
import CGameAI.GAgents.StateProfile;
import CGameAI.Util.ActionState;
import CGameAI.Util.BrainType;
import CGameAI.Util.DataElementSet;
import CGameAI.Util.Func;

public class EntityTutMod1 extends EntityMob
{
	
	World world=this.getEntityWorld();
	public GAgent agent;
	DataElementSet dataSet;
	float x;
	int counter, i,u, a, checkReturnFalse = 1, j,k,l;
	int farmcounter;
	int length, height, breadth;
	Random ran = new Random();
	boolean ReturnFalse = true, startBuilding = false, buildFarm = false;
	EntityCow cow;
	double xPosition, yPosition, zPosition;
	public EntityTutMod1(World world)
	{
		super(world);
		this.setSize(0.9F, 2.0F);
		this.getDeathSound();

		//this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityTutMod1.class, 1.0D, false));
		//this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityTutMod1.class, true));

		this.tasks.addTask(2, new EntityAIWander(this, 0.5D));
		this.tasks.addTask(3, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAITempt(this, 0.7D, Items.gunpowder, false));
		
		//this.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
		this.canPickUpLoot();
		this.setCanPickUpLoot(true);
		
		initRules();
	}
	
	public boolean isAIEnable()
	{
		return true;
	}
	
	public boolean interact(EntityPlayer player)
	{

		ItemStack itemstack = player.inventory.getCurrentItem();

		if (itemstack != null)
		{
			if(itemstack.getItem() == CrudeOil.crudeOil)
			{
				startBuilding = true;
			}
			else if(itemstack.getItem() == Rice.rice){
				startBuilding = false;
				buildFarm = false;
			}
			else if(itemstack.getItem() == Items.wheat)
			{
				buildFarm = true;
			}
		}
		return super.interact(player);
	}
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.24000000417232513D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.2D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
	}

	private void loadActions()
    {

		 Func[] m_actions = new Func[]{
			        new Func(){
			        @Override
			             public ActionState Invoke(String s){
			                 stateBuild();
			                 return ActionState.Success;
			            }
			        },
			        new Func(){
			        @Override
			             public ActionState Invoke(String s){
			                 stateBuildFarm();
			                 return ActionState.Success;
			            }
			        }
			            
		 };
     
        agent.getmBrain().InsertAction("Clear", m_actions[0]);
        agent.getmBrain().InsertAction("Farms", m_actions[1]);
    }
	
	public void initRules()
    {
		StateProfile profile = new StateProfile();
		profile.load("C:/Users/Syah Hakim/Desktop/AIEditor/AIEditor Application/Sample/stateprofile2.xml");
		agent = new GAgent(profile, BrainType.SimpleRules);
		loadActions();
		agent.insertConfig("C:/Users/Syah Hakim/Desktop/AIEditor/AIEditor Application/Sample/BuilderBot.xml");
        loadActions();
        dataSet = new DataElementSet();
        dataSet.addElement("Build", Boolean.toString(this.inWater));
        dataSet.addElement("BuildFarm", Boolean.toString(this.inWater));
        //dataSet.addElement("isMoving", "false"); //trying out to find the space infront, if space is air, move
        
    }
	 
	 public void stateBuild()
	 {
		 //this.tasks.addTask(1, new EntityAIFront(this, 0.5D)); //Die gerak satu kali abe tk buat ape2.
		 
		 Vec3 vec3 = null;
         vec3 = new Vec3(this.posX, this.posY, this.posZ);
         
         this.xPosition = vec3.xCoord;
         this.yPosition = vec3.yCoord;
         this.zPosition = vec3.zCoord;
         
         //this.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, 1); //kalau pakai ni die gerak all the way.    
         System.out.println(xPosition);
		 	//this.getNavigator().getPathToEntityLiving(new EntityTutMod(this.worldObj));
		 
		 	//this.getNavigator().tryMoveToEntityLiving(new EntityTutMod(this.worldObj), 2);
		 	
         //tasks.addTask(3, new EntityAIAvoidEntity(this, EntityTutMod1.class, 8.0F, 0.6D, 0.6D));

 
 		double x = xPosition+1;
 		double y = yPosition;
 		double z = zPosition;
 		
 		for(int p=0;p<breadth;p++)
 		{
 			for(int o=0;o<height;o++)
 			{
 				for(int i=0;i<length;i++)
 				{
 					if(p==((breadth-1)/2)&&i==(length-1)&&o==0)
 					{
 	 					BlockPos door1 = new BlockPos(x,y,z-2);
 	 					BlockPos door2 = new BlockPos(x,y,z-1);
 	 					Block iblockstate = Blocks.birch_door;
 	 					
 	 					//world.setBlockState(block, iblockstate ,10);
 	 					ItemDoor.placeDoor(world, door1, EnumFacing.WEST, iblockstate);
 	 					ItemDoor.placeDoor(world, door2, EnumFacing.WEST, iblockstate);
 	 					//BlockPos block2 = new BlockPos(x,y+1,z);
// 	 					world.setBlockState(block2, iblockstate);
 					}
 					if(p==0||p==(breadth-1)||o==(height-1))
 					{
 						BlockPos block = new BlockPos(x,y,z);
 						IBlockState iblockstate = Blocks.stone.getDefaultState();
 						world.setBlockState(block, iblockstate);
 					}//o is height , p is length, i is breath
 					if(p==0&&o==(height-1)||p==0&&o==(height-3)||p==(breadth-1)&&o==(height-1)||p==(breadth-1)&&o==(height-3)||p==(breadth-1)&&o==(height-5)||p==0&&o==(height-5))
 					{
 						BlockPos block = new BlockPos(x,y,z);
 						IBlockState iblockstate = Blocks.planks.getDefaultState();
 						world.setBlockState(block, iblockstate);
 					}
 					if(o==1&&p==0&&(i!=0&&i!=(length-1))||o==1&&p==(breadth-1)&&(i!=0&&i!=(length-1)))
 					{
 						BlockPos block = new BlockPos(x,y,z);
 						IBlockState iblockstate = Blocks.glass.getDefaultState();
 						world.setBlockState(block, iblockstate);
 					}
 					
 					for(int t=1;t<(breadth-1);t++)
 					{
 						if(p==t&&i==0||p==t&&i==(length-1)&&(o!=1||o!=2))
 						{
 							BlockPos block = new BlockPos(x,y,z);
 	 						IBlockState iblockstate = Blocks.stone.getDefaultState();
 	 						world.setBlockState(block, iblockstate);
 						}
 					}
 					x= x+1;
 					System.out.println(x);
 				}
 				x=xPosition+1;
 				y=y+1;
 			}
 			x=xPosition+1;
 			y=yPosition;
 			z=z+1;
 		}
 		BlockPos block = new BlockPos(xPosition,yPosition-1,zPosition);
		IBlockState iblockstate = Blocks.dirt.getDefaultState();
		world.setBlockState(block, iblockstate);
		
	 }
	 
	 public void stateBuildFarm()
	 {
		 Vec3 vec3 = null;
         vec3 = new Vec3(this.posX, this.posY, this.posZ);
         
         this.xPosition = vec3.xCoord;
         this.yPosition = vec3.yCoord;
         this.zPosition = vec3.zCoord;
         
         //this.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, 1); //kalau pakai ni die gerak all the way.    
         System.out.println(xPosition);
		 	//this.getNavigator().getPathToEntityLiving(new EntityTutMod(this.worldObj));
		 
		 	//this.getNavigator().tryMoveToEntityLiving(new EntityTutMod(this.worldObj), 2);
		 	
         //tasks.addTask(3, new EntityAIAvoidEntity(this, EntityTutMod1.class, 8.0F, 0.6D, 0.6D));

 
 		double x = xPosition+1;
 		double y = yPosition;
 		double z = zPosition;
 		
 		for(int p=0;p<7;p++)
 		{

 				for(int i=0;i<12;i++)
 				{
 					if(p!=3&&p!=0&&p!=6&&i!=0&&i!=11)
 					{	
 						BlockPos block = new BlockPos(x,y-1,z);
 						IBlockState iblockstate = Blocks.farmland.getDefaultState();
 						world.setBlockState(block, iblockstate);
 						x= x+1;
 						System.out.println(x);
 					}
 					else if(p==3&&i!=0&&i!=11)
 					{
 						BlockPos block = new BlockPos(x,y-1,z);
 						IBlockState iblockstate = Blocks.water.getDefaultState();
 						world.setBlockState(block, iblockstate);
 						x= x+1;
 						System.out.println(x);	
 					}
 					else if(p==3&&i==0||p==3&&i==11)
 					{
 						BlockPos block = new BlockPos(x,y,z);
 						IBlockState iblockstate = Blocks.acacia_fence_gate.getStateFromMeta(3);
 						world.setBlockState(block, iblockstate);
 						x= x+1;
 						System.out.println(x);		
 					}
 					else
 					{
 						BlockPos block = new BlockPos(x,y,z);
 						IBlockState iblockstate = Blocks.acacia_fence.getDefaultState();
 						world.setBlockState(block, iblockstate);
 						x= x+1;
 						System.out.println(x);		
 					}
 				}
 			x=xPosition+1;
 			z=z+1;
 		}

		
	 }
	 public void onLivingUpdate()
	 {
		 super.onLivingUpdate();
		 System.out.println(world.getBlockState(this.getPosition().down().east(0)));
		 System.out.println(Blocks.grass.getDefaultState());
		 //if(this.getPosition().down().equals(Blocks.grass.getBlockState().getBlock()))
		 if(startBuilding == true)
		 { 
			 tasks.taskEntries.clear();
			 counter=0;
			 height = ran.nextInt(3)+3;
			 length = ran.nextInt(10)+5;
			 breadth = ran.nextInt(5)+5;
			 for(i =0; i<breadth;i++)
			 {
				for( u=0;u<length; u++)
				{
					for( a=0;a<height;a++)
					{
						if(a==0)
						{
							if(world.getBlockState(this.getPosition().down().east(u).south(i).up(a)).equals(Blocks.grass.getDefaultState()))
					 			{
									counter++;
									System.out.println(counter);
					 			}
						}
						else
						{
							if(world.getBlockState(this.getPosition().down().east(u).south(i).up(a)).equals(Blocks.air.getDefaultState()))
				 			{
								counter++;
								System.out.println(counter);
				 			}
						}
					}
				}
			 System.out.println("i"+i);
			 }
			 if(i*u*a == counter)
			 {
				 dataSet.modifyElement("Build", "true");
				 agent.Think(dataSet); 
				 System.out.println("true");
			 }
			 else
			 {
				 System.out.println("false");
				 dataSet.modifyElement("Build", "false");
				 agent.Think(dataSet); 
				 Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this, 5, 4);

		       	if (vec3 == null)
		        {
		        }
		        else
		        {
		        	if(this.getNavigator().noPath())
		        	{
			         this.xPosition = vec3.xCoord;
			         this.yPosition = vec3.yCoord;
			         this.zPosition = vec3.zCoord;
			         this.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, 1);  
		        	}
	            }

		         
			 }
			 buildFarm = false;
		 }
		 else if(buildFarm == true)
		 {
			 farmcounter = 0;
			 System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			 for(j =0; j<7;j++)
			 {
				for( k=0;k<12; k++)
				{
					if(world.getBlockState(this.getPosition().down().east(k).south(j-1)).equals(Blocks.grass.getDefaultState()))
					{
						
						farmcounter++;
						System.out.println("farm"+ farmcounter);
					}		
				}
			 }
			 System.out.println(j*k);
			 if(j*k == farmcounter)
			 {
				 dataSet.modifyElement("BuildFarm", "true");
				 agent.Think(dataSet); 
				 System.out.println("true");
			 }
			 else
			 {
				 System.out.println("false");
				 dataSet.modifyElement("BuildFarm", "false");
				 agent.Think(dataSet); 
				 Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this, 5, 4);

		       	if (vec3 == null)
		        {
		        }
		        else
		        {
		        	if(this.getNavigator().noPath())
		        	{
			         this.xPosition = vec3.xCoord;
			         this.yPosition = vec3.yCoord;
			         this.zPosition = vec3.zCoord;
			         this.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, 1);  
		        	}
	            }

		         
			 }
			 startBuilding = false;
		 }
		 else
		 {
				this.tasks.addTask(2, new EntityAIWander(this, 0.5D));
				this.tasks.addTask(3, new EntityAILookIdle(this));
				this.tasks.addTask(4, new EntityAITempt(this, 0.7D, Items.gunpowder, false));
				
		 }
	 
	 }
}
