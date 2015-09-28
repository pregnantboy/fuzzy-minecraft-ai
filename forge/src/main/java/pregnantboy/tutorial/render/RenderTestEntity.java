package pregnantboy.tutorial.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pregnantboy.tutorial.Reference;
import pregnantboy.tutorial.entity.EntityTutMob;

@SideOnly(Side.CLIENT)
public class RenderTestEntity extends RenderBiped
{
    public RenderTestEntity(RenderManager p_i46169_1_, ModelBiped p_i46169_2_, float p_i46169_3_, float p_i46169_4_) {
    	super(p_i46169_1_, new ModelBiped(0,0,64,64) , p_i46169_3_, p_i46169_4_);
        this.addLayer(new LayerCustomHead(this.modelBipedMain.bipedHead));
        this.addLayer(new LayerHeldItem(this));
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this);
        this.addLayer(layerbipedarmor);
	}

	private static final ResourceLocation mobTextures = new ResourceLocation(Reference.MOD_ID + ":textures/entity/tutmod2.png");
    private static final String __OBFID = "CL_00000984";

   

    protected ResourceLocation func_180572_a(EntityTutMob p_180572_1_)
    {
    	
        return mobTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.func_180572_a((EntityTutMob)entity);
    }
}