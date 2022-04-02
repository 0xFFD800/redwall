package com.bob.redwall.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class ModBlock extends Block {
	public ModBlock(Material mat, String name, CreativeTabs tab, float hardness, float resistance, int harvest, String tool) {
		super(mat);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(tab);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setHarvestLevel(tool, harvest);
	}

	public ModBlock(Material mat, String name, CreativeTabs tab, float hardness, float resistance) {
		super(mat);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(tab);
		this.setHardness(hardness);
		this.setResistance(resistance);
	}
}
