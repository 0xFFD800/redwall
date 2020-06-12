package com.bob.redwall.tileentity.tesr;

import com.bob.redwall.blocks.BlockWeaponRackVertical;
import com.bob.redwall.init.ItemHandler;
import com.bob.redwall.items.weapons.melee.ItemBattleAxe;
import com.bob.redwall.items.weapons.melee.ItemHalberd;
import com.bob.redwall.items.weapons.melee.ItemLance;
import com.bob.redwall.items.weapons.melee.ItemMace;
import com.bob.redwall.items.weapons.melee.ItemPike;
import com.bob.redwall.items.weapons.melee.swords.ItemCustomSword;
import com.bob.redwall.items.weapons.ranged.ItemModBow;
import com.bob.redwall.items.weapons.throwable.ItemDagger;
import com.bob.redwall.items.weapons.throwable.ItemSpear;
import com.bob.redwall.items.weapons.throwable.ItemThrowingAxe;
import com.bob.redwall.tileentity.TileEntityWeaponRack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TESRWeaponRack extends TileEntitySpecialRenderer<TileEntityWeaponRack> {
	@Override
    public void render(TileEntityWeaponRack te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();
        
        renderItem(te, te.getWorld().getBlockState(te.getPos()).getBlock() instanceof BlockWeaponRackVertical, te.getWorld().getBlockState(te.getPos()).getValue(BlockWeaponRackVertical.FACING));

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderItem(TileEntityWeaponRack te, boolean isVertical, EnumFacing facing) {
    	boolean eastwest = facing.getAxis() == EnumFacing.Axis.X;
        ItemStack stack = te.getStack();
        
        if (!stack.isEmpty()) {
        	if(isVertical) {
        		if(stack.getItem() instanceof ItemPike) {
        			this.renderItemVertical(stack, eastwest, 4.0F, false, false);
        		} else if(stack.getItem() instanceof ItemSpear || stack.getItem() instanceof ItemLance || stack.getItem() instanceof ItemHalberd || stack.getItem() instanceof ItemMace || stack.getItem() instanceof ItemBattleAxe) {
        			this.renderItemVertical(stack, eastwest, 2.0F, false, false);
        		} else if(stack.getItem() instanceof ItemDagger || stack.getItem() instanceof ItemThrowingAxe) {
        			this.renderItemVertical(stack, eastwest, 1.0F, false, false);
        		} else if(stack.getItem() instanceof ItemCustomSword) {
        			this.renderItemVertical(stack, eastwest, 2.0F, true, false);
        		} else if(stack.getItem() instanceof ItemBow || stack.getItem() instanceof ItemModBow) {
        			this.renderItemVertical(stack, eastwest, 2.0F, false, true);
        		}
        	} else {
        		if(stack.getItem() instanceof ItemPike) {
        			this.renderItemHorizontal(stack, facing, 4.0F, false, false);
        		} else if((stack.getItem() instanceof ItemMace && stack.getItem() != ItemHandler.guosim_paddle) || stack.getItem() instanceof ItemBattleAxe || stack.getItem() instanceof ItemCustomSword) {
        			this.renderItemHorizontal(stack, facing, 2.0F, false, false);
        		} else if(stack.getItem() instanceof ItemSpear || stack.getItem() instanceof ItemLance || stack.getItem() instanceof ItemHalberd || stack.getItem() == ItemHandler.guosim_paddle) {
        			this.renderItemHorizontal(stack, facing, 2.0F, true, false);
        		} else if(stack.getItem() instanceof ItemDagger || stack.getItem() instanceof ItemThrowingAxe) {
        			this.renderItemHorizontal(stack, facing, 1.0F, false, false);
        		} else if(stack.getItem() instanceof ItemBow || stack.getItem() instanceof ItemModBow) {
        			this.renderItemHorizontal(stack, facing, 2.0F, false, true);
        		}
        	}
        }
    }
    
    private void renderItemVertical(ItemStack stack, boolean eastwest, float scale, boolean sword, boolean bow) {
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();
        float f = bow ? -0.375F : 0.125F;
        GlStateManager.translate(0.5F, f + (sword ? 0 : ((scale == 1 ? scale : scale == 2 ? scale - 0.5 : scale - 1) / 2)), 0.5F);
        GlStateManager.scale(eastwest ? 1.0f : scale, scale, eastwest ? scale : 1.0f);

    	if(eastwest) GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(bow ? 45.0F : sword ? 135.0F : 315.0F, 0.0F, 0.0F, 1.0F);

        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);

        GlStateManager.popMatrix();
    }
    
    private void renderItemHorizontal(ItemStack stack, EnumFacing facing, float scale, boolean spear, boolean bow) {
    	boolean eastwest = facing.getAxis() == EnumFacing.Axis.X;
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();
        float nx = scale == 4F || (scale == 2F && spear) ? 0.5F : (scale == 2 && bow) ? 0.5F : scale == 2 ? -0.25F : 0.25F;
        float nz = 0.5F;
        float sx = 1.0F - (scale == 4F || (scale == 2F && spear) ? 0.5F : (scale == 2 && bow) ? 0.5F : scale == 2 ? -0.25F : 0.25F);
        float sz = 0.5F;
        float ex = 0.5F;
        float ez = 1.0F - (scale == 4F || (scale == 2F && spear) ? 0.5F : (scale == 2 && bow) ? 0.5F : scale == 2 ? -0.25F : 0.25F);
        float wx = 0.5F;
        float wz = scale == 4F || (scale == 2F && spear) ? 0.5F : (scale == 2 && bow) ? 0.5F : scale == 2 ? -0.25F : 0.25F;
        float y = bow ? 0.15F : 0.625F;
        switch(facing) {
        case NORTH:
        	GlStateManager.translate(nx, y, nz);
        	break;
        case SOUTH:
        	GlStateManager.translate(sx, y, sz);
        	break;
        case EAST:
        	GlStateManager.translate(ex, y, ez);
        	break;
    	default: 
        	GlStateManager.translate(wx, y, wz);
        	break;
        }
        GlStateManager.scale(eastwest ? 1.0f : scale, scale, eastwest ? scale : 1.0f);

    	GlStateManager.rotate(facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
    	GlStateManager.translate(0.0F, 0.0F, eastwest ? 0.35F : -0.35F);
        GlStateManager.rotate(bow ? 45.0F : 225.0F, 0.0F, 0.0F, 1.0F);

        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);

        GlStateManager.popMatrix();
    }
}
