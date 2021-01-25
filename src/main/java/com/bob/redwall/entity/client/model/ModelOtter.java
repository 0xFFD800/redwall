package com.bob.redwall.entity.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelOtter extends ModelBiped {
    public ModelRenderer bipedBodywear;
    public ModelRenderer bipedSnout1;
    public ModelRenderer bipedTail1;
    
	public ModelOtter(float modelSize, float extraHeight) {
        super(modelSize, extraHeight, 64, 64);
        
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -9.0F, -2.5F, 8, 7, 6, modelSize);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + extraHeight, 0.0F);
        
        this.bipedHeadwear = new ModelRenderer(this, 32, 33);
        this.bipedHeadwear.addBox(-4.0F, -9.0F, -2.5F, 8, 7, 6, modelSize + 0.49F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + extraHeight, 0.0F);

        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0F, -2.0F, -2.0F, 8, 13, 4, modelSize);
        this.bipedBody.setRotationPoint(0.0F, 0.0F + extraHeight, 0.0F);
        
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 13, 4, modelSize);
        this.bipedRightArm.setRotationPoint(-5.0F, 0.0F + extraHeight, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 13, 4, modelSize);
        this.bipedLeftArm.setRotationPoint(5.0F, 0.0F + extraHeight, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-2.0F, -1.0F, -2.0F, 4, 13, 4, modelSize);
        this.bipedRightLeg.setRotationPoint(-1.9F, 10.0F + extraHeight, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.addBox(-2.0F, -1.0F, -2.0F, 4, 13, 4, modelSize);
        this.bipedLeftLeg.setRotationPoint(1.9F, 10.0F + extraHeight, 0.0F);

        this.bipedBodywear = new ModelRenderer(this, 0, 47);
        this.bipedBodywear.addBox(-4.0F, -2.0F, -2.0F, 8, 13, 4, modelSize + 0.25F);
        this.bipedBodywear.setRotationPoint(0.0F, 0.0F + extraHeight, 0.0F);
        
        this.bipedSnout1 = new ModelRenderer(this, 44, 0);
        this.bipedSnout1.addBox(-3.0F, -8.0F, -4.5F, 6, 6, 2, modelSize);
        this.bipedSnout1.setRotationPoint(0.0F, 0.0F + extraHeight, 0.0F);
        
        this.bipedTail1 = new ModelRenderer(this, 0, 33);
        this.bipedTail1.addBox(-2.0F, -0.5F, 2.0F, 4, 1, 12, modelSize);
        this.bipedTail1.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.bipedTail1.rotateAngleX = -1.05F;
    }

	@Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    	
        GlStateManager.pushMatrix();
        if (this.isChild) {
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
            this.bipedSnout1.render(scale);
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
            this.bipedBodywear.render(scale);
            this.bipedTail1.render(scale);
        }
        GlStateManager.popMatrix();
    }
	
	@Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

        copyModelAngles(this.bipedHead, this.bipedSnout1);
        copyModelAngles(this.bipedBody, this.bipedBodywear);

        this.bipedTail1.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
    }
}
