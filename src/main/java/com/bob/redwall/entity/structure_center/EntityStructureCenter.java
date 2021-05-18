package com.bob.redwall.entity.structure_center;

import java.util.ArrayList;
import java.util.List;

import com.bob.redwall.factions.Faction;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public abstract class EntityStructureCenter extends EntityLivingBase {
	public static final List<EntityStructureCenter> ACTIVE_STRUCTURES = new ArrayList<>();
	
	private AxisAlignedBB aoe;
	
	public EntityStructureCenter(World worldIn, AxisAlignedBB aoe) {
		super(worldIn);
		this.aoe = aoe;
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return new ArrayList<>();
	}

	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
		
	}

	@Override
	public EnumHandSide getPrimaryHand() {
		return EnumHandSide.RIGHT;
	}
	
	public AxisAlignedBB getAOE() {
		return this.aoe;
	}
	
	public abstract Faction getFaction();
}
