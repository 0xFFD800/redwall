package com.bob.redwall.tileentity.tesr;

import java.util.Random;

import com.bob.redwall.tileentity.TileEntityPlate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TESRPlate extends TileEntitySpecialRenderer<TileEntityPlate> {
    private final Random random = new Random();
    
	@Override
    public void render(TileEntityPlate te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();
        
        renderItem(te);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderItem(TileEntityPlate te) {
        ItemStack stack = te.getStack();
        int j = stack.isEmpty() ? 187 : Item.getIdFromItem(stack.getItem()) + stack.getMetadata();
        this.random.setSeed((long)j);

        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(0.5F, -0.5F, 0.0F);
        for (int i = 0; i < stack.getCount(); i++) {
	        GlStateManager.pushMatrix();
	        GlStateManager.translate(0.0F, 0.0F, 0.0625F * (i + 2));
	        if (i > 0)
	        	GlStateManager.rotate(this.random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
	        if (!stack.isEmpty())
	            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
	        GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
    }
}
