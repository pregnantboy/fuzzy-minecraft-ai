package pregnantboy.tutorial;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import pregnantboy.tutorial.entity.TestEntity;
import pregnantboy.tutorial.init.TutorialItems;
import pregnantboy.tutorial.proxy.ServerProxy;

@Mod(modid=Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class MainRegistry {
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static ServerProxy proxy;
	
	@Instance(Reference.MOD_ID)
	public static MainRegistry modInstance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		TutorialItems.init();
		TutorialItems.register();
		TestEntity.mainRegistry();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenders();
	}
	
	@EventHandler
	public void postInit (FMLPostInitializationEvent event) {
		
	}
}
