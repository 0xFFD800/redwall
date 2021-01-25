package com.bob.redwall.items.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStatType;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.capabilities.factions.IFactionCap;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.items.weapons.ModCustomWeapon;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemScythe extends ModCustomWeapon {
	private static final ArrayList<Material> properMaterials = Lists.newArrayList(Material.PLANTS, Material.VINE, Material.CORAL, Material.LEAVES, Material.GOURD);
	
	public ItemScythe(String name, CreativeTabs tab, float spd, float dmg, float reach, Item.ToolMaterial material) {
		super(name, tab, spd, dmg, reach, material);
	}
	
	public ItemScythe(String name, CreativeTabs tab, float spd, float dmg, float reach, Item.ToolMaterial material, Faction faction) {
		super(name, tab, spd, dmg, reach, material, faction);
	}

	@Override
	protected void attackEntity(EntityLivingBase target, EntityLivingBase attacker, ItemStack stack) { }

	@Override
	public boolean isTwoHanded(ItemStack stack, EntityLivingBase attacker) {
		return true;
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
	
	@Override
	@Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		if(!EnchantmentHelper.getEnchantments(stack).containsKey(Enchantments.FORTUNE)) stack.addEnchantment(Enchantments.FORTUNE, 1);
		return null;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		Iterator<Entry<Enchantment, Integer>> i = EnchantmentHelper.getEnchantments(stack).entrySet().iterator();
		Entry<Enchantment, Integer> e;
		while(i.hasNext()) {
			e = i.next();
			if(e.getKey() != Enchantments.FORTUNE) {
				return true;
			}
		}
		
		return false;
	}

	@Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        Block block = state.getBlock();

        if (block == Blocks.WEB) {
            return 5.0F * this.getToolMaterial().getEfficiency();
        } else {
            Material material = state.getMaterial();
            return properMaterials.contains(material) ? this.getToolMaterial().getEfficiency() : 1.0F;
        }
    }
	
	@Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity) {
        if (!world.isRemote) {
            stack.damageItem(1, entity);
            
            if(this.getFaction() != null && entity instanceof EntityPlayer && properMaterials.contains(state.getMaterial())) {
            	IFactionCap fac = ((EntityPlayer)entity).getCapability(FactionCapProvider.FACTION_CAP, null);
            	fac.set(this.getFaction(), FacStatType.FARM, fac.get(this.getFaction(), FacStatType.FARM) + 0.5F, true);
            	if(EnchantmentHelper.getEnchantments(stack).containsKey(Enchantments.FORTUNE)) {
            		for(int i = 0; i < stack.getEnchantmentTagList().tagCount(); i++) {
            			NBTBase u1 = stack.getEnchantmentTagList().get(i);
	            		if(u1 instanceof NBTTagCompound) {
	            			NBTTagCompound nbt = (NBTTagCompound)u1;
	            			if(Enchantment.getEnchantmentByID(nbt.getShort("id")) == Enchantments.FORTUNE) stack.getEnchantmentTagList().removeTag(i);
	            		}
	            	};
            	}
        		stack.addEnchantment(Enchantments.FORTUNE, RedwallUtils.getFacStatLevel((EntityPlayer)entity, this.getFaction(), FacStatType.FARM) + 1);
            }
        }

        return true;
    }
}
