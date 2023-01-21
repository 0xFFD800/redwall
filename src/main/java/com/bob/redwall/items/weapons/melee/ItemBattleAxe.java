package com.bob.redwall.items.weapons.melee;

import java.util.Set;

import com.bob.redwall.entity.capabilities.factions.Faction;
import com.bob.redwall.items.weapons.ModCustomWeapon;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemBattleAxe extends ModCustomWeapon {
	private final Set<Block> effectiveBlocks = Sets.newHashSet(new Block[] {Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE});
    private final float efficiencyOnProperMaterial;
    
	public ItemBattleAxe(String name, CreativeTabs tab, float speed, float dmg, float reach, ToolMaterial material) {
		super(name, tab, speed, dmg, reach, material);
        this.efficiencyOnProperMaterial = material.getEfficiency()/2;
	}

	public ItemBattleAxe(String name, CreativeTabs tab, float spd, float dmg, float reach, ToolMaterial material, Faction faction) {
		super(name, tab, spd, dmg, reach, material, faction);
        this.efficiencyOnProperMaterial = material.getEfficiency()/2;
	}

	@Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        for (String type : getToolClasses(stack)) {
            if (state.getBlock().isToolEffective(type, state))
                return efficiencyOnProperMaterial;
        }
        return this.effectiveBlocks.contains(state.getBlock()) ? this.efficiencyOnProperMaterial : 1.0F;
    }

	@Override
	public boolean isTwoHanded(ItemStack stack, EntityLivingBase attacker) {
		return true;
	}

	@Override
	protected void attackEntity(EntityLivingBase target, EntityLivingBase attacker, ItemStack stack) { }

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
		return false;
	}

	@Override
	public boolean canBlock(ItemStack itemstack, EntityLivingBase player) {
		return false;
	}

	@Override
	public boolean hasRightClickAbility(ItemStack itemstack, EntityLivingBase player) {
		return false;
	}
}
