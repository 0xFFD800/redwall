package com.bob.redwall.items.weapons.melee.swords;

import com.bob.redwall.items.crafting.ItemSwordHilt.EnumHandleType;
import com.bob.redwall.items.crafting.ItemSwordHilt.EnumPommelStoneType;
import com.bob.redwall.items.crafting.ItemSwordHilt.EnumPommelType;
import com.bob.redwall.items.weapons.ModCustomWeapon;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCustomSword extends ModCustomWeapon {
	Item.ToolMaterial material;
	float damage;
	
	public ItemCustomSword(String name, CreativeTabs tab, float spd, float dmg, float reach, ToolMaterial material) {
		super(name, tab, spd, dmg, reach, material);
        this.material = material;
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());
        this.damage = dmg;
	}

	@Override
	protected void attackEntity(EntityLivingBase target, EntityLivingBase attacker, ItemStack stack) {
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
	
	public String getToolMaterialName() {
        return this.material.toString();
    }

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        ItemStack mat = this.material.getRepairItemStack();
        if (!mat.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        return true;
    }

	@Override
	public float getDamageVsEntity() {
        return this.damage;
    }
	
	public EnumHandleType getHandleType(ItemStack stack) {
		return EnumHandleType.getById((int) (this.getTagCompoundSafe(stack).hasKey("handle_type") ? this.getTagCompoundSafe(stack).getFloat("handle_type") : EnumHandleType.WOOD.getId()));
	}
	
	public EnumPommelType getPommelType(ItemStack stack) {
		return EnumPommelType.getById((int) (this.getTagCompoundSafe(stack).hasKey("pommel_type") ? this.getTagCompoundSafe(stack).getFloat("pommel_type") : EnumPommelType.WOOD.getId()));
	}
	
	public EnumPommelStoneType getPommelStoneType(ItemStack stack) {
		return EnumPommelStoneType.getById((int) (this.getTagCompoundSafe(stack).hasKey("pommel_stone_type") ? this.getTagCompoundSafe(stack).getFloat("pommel_stone_type") : EnumPommelStoneType.NONE.getId()));
	}
	
	public void setHandleType(ItemStack stack, EnumHandleType handleType) {
		this.getTagCompoundSafe(stack).setFloat("handle_type", handleType.getId());
	}
	
	public void setPommelType(ItemStack stack, EnumPommelType pommelType) {
		this.getTagCompoundSafe(stack).setFloat("pommel_type", pommelType.getId());
	}
	
	public void setPommelStoneType(ItemStack stack, EnumPommelStoneType pommelStoneType) {
		this.getTagCompoundSafe(stack).setFloat("pommel_stone_type", pommelStoneType.getId());
	}

    private NBTTagCompound getTagCompoundSafe(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
        }
        return tagCompound;
    }

	@Override
	public boolean isTwoHanded(ItemStack stack, EntityLivingBase attacker) {
		return false;
	}

	@Override
	public boolean isBludgeon(ItemStack stack, EntityLivingBase attacker) {
		return false;
	}

	@Override
	public boolean isSweep(ItemStack weapon, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean isStab(ItemStack weapon, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canBlock(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean hasRightClickAbility(ItemStack itemstack, EntityLivingBase player) {
		return false;
	}
}
