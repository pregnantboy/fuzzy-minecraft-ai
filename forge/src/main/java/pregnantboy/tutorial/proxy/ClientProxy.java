package pregnantboy.tutorial.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import pregnantboy.tutorial.entity.EntityTutMob;
import pregnantboy.tutorial.entity.EntityTutMob2;
import pregnantboy.tutorial.entity.EntityTutMob3;
import pregnantboy.tutorial.entity.TestEntity;
import pregnantboy.tutorial.init.TutorialItems;
import pregnantboy.tutorial.render.RenderTestEntity;
import pregnantboy.tutorial.render.RenderTestEntity2;
import pregnantboy.tutorial.render.RenderTestEntity3;

public class ClientProxy extends ServerProxy {
	@Override
	public void registerRenders() {
		TutorialItems.registerRenders();
		RenderingRegistry.registerEntityRenderingHandler(EntityTutMob.class, new RenderTestEntity(Minecraft.getMinecraft().getRenderManager(), new ModelBiped(), 0, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityTutMob2.class, new RenderTestEntity2(Minecraft.getMinecraft().getRenderManager(), new ModelBiped(), 0, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityTutMob3.class, new RenderTestEntity3(Minecraft.getMinecraft().getRenderManager(), new ModelBiped(), 0, 0));
	}
}
