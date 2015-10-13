package pregnantboy.tutorial.entity;

import net.minecraft.entity.EntityList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.ModClassLoader;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import pregnantboy.tutorial.MainRegistry;

public class TestEntity {
	public static void mainRegistry() {
		registerEntity();
	}
		
	public static void registerEntity() {
		createEntity(EntityTutMob.class, "TutMob", 0x90C3D4, 0x010203);
		createEntity(EntityTutMob2.class, "TutMob2", 0x010203, 0x90C3D4);
		createEntity(EntityTutMob3.class, "TutMob3", 0x010203, 0x90C3D4);
	}
	
	public static void createEntity(Class entityClass, String entityName, int solidColour, int spotColour) {
		int randomId = EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, randomId);
		EntityRegistry.registerModEntity(entityClass, entityName, randomId, MainRegistry.modInstance, 64, 1, true);
		createEgg(randomId, solidColour, spotColour);
	}
	
	public static void createEgg(int randomId, int solidColour, int spotColour) {
		EntityList.entityEggs.put(Integer.valueOf(randomId), new EntityList.EntityEggInfo(randomId, solidColour, spotColour));
	}
}
