package fuzzyMod;

import fuzzyMod.entity.TestEntity;
import fuzzyMod.init.TutorialItems;
import fuzzyMod.proxy.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class MainRegistry {
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static ServerProxy proxy;
	
	@Instance(Reference.MOD_ID)
	public static MainRegistry modInstance;
	
	@EventHandler
	/**
	 * Methods to call during pre-initialization
	 */
	public void preInit(FMLPreInitializationEvent event) {
		TutorialItems.init();
		TutorialItems.register();
		TestEntity.mainRegistry();
	}
	
	@EventHandler
	/**
	 * Methods to call during initialization
	 */
	public void init(FMLInitializationEvent event) {
		proxy.registerRenders();
	}
	
	@EventHandler
	/**
	 * Methods to call during post-initialization
	 */
	public void postInit (FMLPostInitializationEvent event) {
		if ( FMLCommonHandler.instance().getSide().isServer())
	    {
	    }

	    if ( FMLCommonHandler.instance().getSide().isClient())
	    {
	    	FMLCommonHandler.instance().bus().register(new PlayerTickEvents());
	        MinecraftForge.EVENT_BUS.register(new PlayerTickEvents());
	    }

	}
}
