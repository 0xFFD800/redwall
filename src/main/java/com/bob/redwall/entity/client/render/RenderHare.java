package com.bob.redwall.entity.client.render;

import javax.annotation.Nonnull;

import com.bob.redwall.entity.client.layer.LayerNPCArmor;
import com.bob.redwall.entity.client.layer.LayerNPCHeldItem;
import com.bob.redwall.entity.client.model.ModelHare;
import com.bob.redwall.entity.npc.EntityAbstractNPC;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderHare<T extends EntityAbstractNPC> extends RenderLiving<T> {
	public static final Factory FACTORY = new Factory();

	public RenderHare(RenderManager renderManager) {
		super(renderManager, new ModelHare(0.0F, 0.0F), 0.5F);
		this.addLayer(new LayerNPCHeldItem(this));
		this.addLayer(new LayerNPCArmor(this) {
			@Override
			protected void initArmor() {
				this.modelLeggings = new ModelHare(0.5F, 0.0F);
				this.modelArmor = new ModelHare(1.0F, 0.0F);
			}
		});
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		boolean flag = entity.isSneaking();
		float f = this.renderManager.playerViewY;
		float f1 = this.renderManager.playerViewX;
		boolean flag1 = this.renderManager.options.thirdPersonView == 2;
		float f2 = entity.height + 0.5F - (flag ? 0.25F : 0.0F);
		float f3 = 0.0F;
		if (entity.getTalkingActive()) {
			f3 = 0.5F;
			EntityRenderer.drawNameplate(this.getFontRendererFromRenderManager(), entity.getTalking().getFormattedText(), (float) x, (float) y + f2, (float) z, 0, f, f1, flag1, flag);
		}
		if (entity.getFavor() != null)
			EntityRenderer.drawNameplate(this.getFontRendererFromRenderManager(), "§b§3!", (float) x, (float) y + f2 + f3, (float) z, 0, f, f1, flag1, flag);
	}

	@Override
	protected void renderLivingLabel(T entityIn, String str, double x, double y, double z, int maxDistance) {
		double d0 = entityIn.getDistanceSq(this.renderManager.renderViewEntity);

		if (d0 <= (double) (maxDistance * maxDistance)) {
			boolean flag = entityIn.isSneaking();
			float f = this.renderManager.playerViewY;
			float f1 = this.renderManager.playerViewX;
			boolean flag1 = this.renderManager.options.thirdPersonView == 2;
			float f2 = entityIn.height + 0.7F - (flag ? 0.25F : 0.0F);
			int i = "deadmau5".equals(str) ? -10 : 0;
			EntityRenderer.drawNameplate(this.getFontRendererFromRenderManager(), str, (float) x, (float) y + f2, (float) z, i, f, f1, flag1, flag);
		}
	}

	@Nonnull
	@Override
	protected ResourceLocation getEntityTexture(@Nonnull T entity) {
		return new ResourceLocation(entity.getSkin());
	}

	public static class Factory implements IRenderFactory<EntityAbstractNPC> {
		@Override
		public Render<? super EntityAbstractNPC> createRenderFor(RenderManager manager) {
			return new RenderHare<EntityAbstractNPC>(manager);
		}
	}
}
