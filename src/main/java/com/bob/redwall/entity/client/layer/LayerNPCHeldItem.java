package com.bob.redwall.entity.client.layer;

import com.bob.redwall.entity.npc.EntityAbstractNPC;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;

public class LayerNPCHeldItem extends LayerHeldItem {
	public LayerNPCHeldItem(RenderLivingBase<?> livingEntityRendererIn) {
		super(livingEntityRendererIn);
	}
	
	@Override
	public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if(!(entity instanceof EntityAbstractNPC)) super.doRenderLayer(entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
		else if(((EntityAbstractNPC)entity).isTargetLocked()) {
	        boolean flag = entity.getPrimaryHand() == EnumHandSide.RIGHT;
	        ItemStack itemstack = flag ? entity.getHeldItemOffhand() : entity.getHeldItemMainhand();
	        ItemStack itemstack1 = flag ? entity.getHeldItemMainhand() : entity.getHeldItemOffhand();

	        if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
	            GlStateManager.pushMatrix();

	            if (this.livingEntityRenderer.getMainModel().isChild) {
	                GlStateManager.translate(0.0F, 0.75F, 0.0F);
	                GlStateManager.scale(0.5F, 0.5F, 0.5F);
	            }

	            this.renderHeldItem(entity, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
	            this.renderHeldItem(entity, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
	            GlStateManager.popMatrix();
	        }
		}
    }

    private void renderHeldItem(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide) {
        if (!p_188358_2_.isEmpty()) {
            GlStateManager.pushMatrix();

            if (p_188358_1_.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
            this.translateToHand(handSide);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            boolean flag = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
            GlStateManager.popMatrix();
        }
    }
}
