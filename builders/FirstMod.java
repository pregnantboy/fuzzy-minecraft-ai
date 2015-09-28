package minecraft.FirstPlugin;

import minecraft.FirstPlugin.Generator.OreGeneration;
import minecraft.FirstPlugin.Init.BackArrow;
import minecraft.FirstPlugin.Init.ChickenRice;
import minecraft.FirstPlugin.Init.CrudeOil;
import minecraft.FirstPlugin.Init.FrontArrow;
import minecraft.FirstPlugin.Init.GunItem;
import minecraft.FirstPlugin.Init.HakimBlocks;
import minecraft.FirstPlugin.Init.HakimItem;
import minecraft.FirstPlugin.Init.LeftArrow;
import minecraft.FirstPlugin.Init.MagicWand;
import minecraft.FirstPlugin.Init.Rice;
import minecraft.FirstPlugin.Init.RightArrow;
import minecraft.FirstPlugin.Init.Stop;
import minecraft.FirstPlugin.Init.TutorialBlocks;
import minecraft.FirstPlugin.Init.TutorialItems;
import minecraft.FirstPlugin.Init.gunAmmo;
import minecraft.FirstPlugin.Inventory.GUIHandler;
//import minecraft.FirstPlugin.Inventory.GUIHandler2;
import minecraft.FirstPlugin.Render.GUIDisplayScore;
import minecraft.FirstPlugin.blocks.BlockJumpPad;
import minecraft.FirstPlugin.blocks.BlockSpeedPad;
import minecraft.FirstPlugin.entities.EntityBot1;
import minecraft.FirstPlugin.entities.TMEntity;
import minecraft.FirstPlugin.entities.TutorialEntity;
import minecraft.FirstPlugin.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;


@Mod(modid = Ref.MOD_ID, name = Ref.MOD_NAME, version = Ref.VERSION)
public class FirstMod 
{
	@Instance(Ref.MOD_ID)
	public static FirstMod modInstance;
	
	@SidedProxy(clientSide = Ref.CLIENT_PROXY_CLASS, serverSide = Ref.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static final TeleportTab tabTeleport = new TeleportTab("tabTeleport");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		
		BlockJumpPad.init();
		BlockJumpPad.register();
		
		BlockSpeedPad.init();
		BlockSpeedPad.register();
		
		GunItem.init();
		GunItem.register();
		
		gunAmmo.init();
		gunAmmo.register();
		
		MagicWand.init();
		MagicWand.register();
		
		HakimBlocks.init();
		HakimBlocks.register();
		
		HakimItem.init();
		HakimItem.register();
		
		CrudeOil.init();
		CrudeOil.register();
		
		ChickenRice.init();
		ChickenRice.register();
		
		Rice.init();
		Rice.register();
		
		FrontArrow.init();
		FrontArrow.register();
		
		BackArrow.init();
		BackArrow.register();
		
		LeftArrow.init();
		LeftArrow.register();
		
		RightArrow.init();
		RightArrow.register();
		
		Stop.init();
		Stop.register();
		
		TMEntity.mainRegistry();
		OreGeneration test = new OreGeneration();
		GameRegistry.registerWorldGenerator(test, 1);
		TutorialEntity.mainRegistry();
		TutorialItems.init();
		TutorialItems.register();
		
		TutorialBlocks.init();
		TutorialBlocks.register();


	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		proxy.registerRenders();
		GunItem.itemReceipt();
		gunAmmo.itemReceipt();
		MagicWand.itemReceipt();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler());
		//NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler2());
		EntityRegistry.addSpawn(EntityBot1.class, 10, 3, 10, EnumCreatureType.CREATURE, BiomeGenBase.taigaHills, BiomeGenBase.jungle, 
				BiomeGenBase.jungleHills, BiomeGenBase.plains, BiomeGenBase.taiga, BiomeGenBase.forest, 
				BiomeGenBase.forestHills, BiomeGenBase.swampland, BiomeGenBase.river, BiomeGenBase.beach, 
				BiomeGenBase.desert, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge);
		GameRegistry.addRecipe(new ItemStack(HakimBlocks.hakimtest_block), new Object[] {"SWS", "WCW", "SWS", 'W', Blocks.planks, 'S', Blocks.cobblestone, 'C', Items.coal});
		GameRegistry.addRecipe(new ItemStack(Rice.rice), new Object[] {"   ", "WWW", "BBB", 'W', Items.wheat, 'B', Items.water_bucket});
		GameRegistry.addRecipe(new ItemStack(ChickenRice.chickenRice), new Object[] {"   ", "CRC", "   ", 'R', Rice.rice, 'C', Items.cooked_chicken});
		GameRegistry.addRecipe(new ItemStack(CrudeOil.crudeOil,6), new Object[] {"   ", " C ", "   ", 'C', HakimBlocks.crude_oil});
		
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new GUIDisplayScore());
	}
}
