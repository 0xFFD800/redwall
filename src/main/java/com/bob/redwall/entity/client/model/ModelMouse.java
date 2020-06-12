package com.bob.redwall.entity.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelMouse extends ModelBiped {
    public ModelRenderer bipedBodywear;
    public ModelRenderer bipedSnout1;
    public ModelRenderer bipedSnout2;
    public ModelRenderer bipedTail1;
    public ModelRenderer bipedTail2;
    
	public ModelMouse(float modelSize, float extraHeight) {
        super(modelSize, extraHeight, 64, 64);
        
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -7.0F, -2.5F, 8, 7, 6, modelSize);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + extraHeight, 0.0F);
        
        this.bipedHeadwear = new ModelRenderer(this, 20, 32);
        this.bipedHeadwear.addBox(-4.0F, -7.0F, -2.5F, 8, 7, 6, modelSize + 0.49F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + extraHeight, 0.0F);

        this.bipedBodywear = new ModelRenderer(this, 0, 42);
        this.bipedBodywear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize + 0.25F);
        this.bipedBodywear.setRotationPoint(0.0F, 0.0F + extraHeight, 0.0F);
        
        this.bipedSnout1 = new ModelRenderer(this, 44, 0);
        this.bipedSnout1.addBox(-3.0F, -6.0F, -5.5F, 6, 6, 3, modelSize);
        this.bipedSnout1.setRotationPoint(0.0F, 0.0F + extraHeight, 0.0F);
        this.bipedSnout2 = new ModelRenderer(this, 22, 0);
        this.bipedSnout2.addBox(-1.0F, -3.0F, -7.5F, 2, 2, 2, modelSize);
        this.bipedSnout2.setRotationPoint(0.0F, 0.0F + extraHeight, 0.0F);
        
        this.bipedTail1 = new ModelRenderer(this, 0, 32);
        this.bipedTail1.addBox(-1.0F, -1.0F, 2.0F, 2, 2, 8, modelSize);
        this.bipedTail1.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.bipedTail1.rotateAngleX = -0.47F;
        this.bipedTail2 = new ModelRenderer(this, 28, 0);
        this.bipedTail2.addBox(-1.1F, -1.1F, -1.0F, 2, 2, 10, modelSize);
        this.bipedTail2.setRotationPoint(0.0F, 0.5F, 10.0F);
        this.bipedTail2.rotateAngleX = 0.37F;
        this.bipedTail1.addChild(this.bipedTail2);
    }

	@Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    	
        GlStateManager.pushMatrix();
        if (this.isChild) {
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
            this.bipedSnout1.render(scale);
            this.bipedSnout2.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            this.bipedBodywear.render(scale);
            this.bipedTail1.render(scale);
        } else {
            if (entityIn.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            this.bipedSnout1.render(scale);
            this.bipedSnout2.render(scale);
            this.bipedBodywear.render(scale);
            this.bipedTail1.render(scale);
        }
        GlStateManager.popMatrix();
    }
	
	@Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

        copyModelAngles(this.bipedHead, this.bipedSnout1);
        copyModelAngles(this.bipedHead, this.bipedSnout2);
        copyModelAngles(this.bipedBody, this.bipedBodywear);

        this.bipedTail1.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
        this.bipedTail2.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
    }
}
