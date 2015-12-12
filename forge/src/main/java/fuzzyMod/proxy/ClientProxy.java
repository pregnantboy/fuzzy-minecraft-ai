package fuzzyMod.proxy;

import fuzzyMod.entity.EntityTutMob;
import fuzzyMod.entity.EntityTutMob2;
import fuzzyMod.entity.EntityTutMob3;
import fuzzyMod.entity.EntityTutMob4;
import fuzzyMod.entity.TestEntity;
import fuzzyMod.init.TutorialItems;
import fuzzyMod.render.RenderTestEntity;
import fuzzyMod.render.RenderTestEntity2;
import fuzzyMod.render.RenderTestEntity3;
import fuzzyMod.render.RenderTestEntity4;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy {
	@Override
	public void registerRenders() {
		TutorialItems.registerRenders();
		RenderingRegistry.registerEntityRenderingHandler(EntityTutMob.class, new RenderTestEntity(Minecraft.getMinecraft().getRenderManager(), new ModelBiped(), 0, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityTutMob2.class, new RenderTestEntity2(Minecraft.getMinecraft().getRenderManager(), new ModelBiped(), 0, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityTutMob3.class, new RenderTestEntity3(Minecraft.getMinecraft().getRenderManager(), new ModelBiped(), 0, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityTutMob4.class, new RenderTestEntity4(Minecraft.getMinecraft().getRenderManager(), new ModelBiped(), 0, 0));
	}
}
