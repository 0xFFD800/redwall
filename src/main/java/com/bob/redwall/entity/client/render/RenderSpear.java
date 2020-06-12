package com.bob.redwall.entity.client.render;

import com.bob.redwall.Ref;
import com.bob.redwall.entity.projectile.spear.EntitySpear;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSpear<T extends EntitySpear> extends Render<T> {
	public RenderSpear(RenderManager renderManager) {
		super(renderManager);
	}

	private static final ResourceLocation texture = new ResourceLocation(Ref.MODID, "textures/items/iron_spear.png");
	public static final Factory FACTORY = new Factory();
	

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return texture;
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.bindEntityTexture(entity);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer();
        GlStateManager.enableRescaleNormal();

        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0D, 0.0D, -1.0D);
        GlStateManager.rotate(70.0F, 1.0F, 0.0F, 0.0F);

        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }
        
        Minecraft.getMinecraft().getRenderItem().renderItem(entity.getPickup(), TransformType.FIRST_PERSON_RIGHT_HAND);

        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
	
	public static class Factory implements IRenderFactory<EntitySpear> {
        @Override
        public Render<? super EntitySpear> createRenderFor(RenderManager manager) {
            return new RenderSpear<EntitySpear>(manager);
        }
    }
}