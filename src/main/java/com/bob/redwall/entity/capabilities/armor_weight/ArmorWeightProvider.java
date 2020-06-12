package com.bob.redwall.entity.capabilities.armor_weight;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ArmorWeightProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(IArmorWeight.class)
	public static final Capability<IArmorWeight> ARMOR_WEIGHT_CAP = null;
	private IArmorWeight instance = ARMOR_WEIGHT_CAP.getDefaultInstance();
	 
	 @Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ARMOR_WEIGHT_CAP;
	}
	 
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == ARMOR_WEIGHT_CAP ? ARMOR_WEIGHT_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return ARMOR_WEIGHT_CAP.getStorage().writeNBT(ARMOR_WEIGHT_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		ARMOR_WEIGHT_CAP.getStorage().readNBT(ARMOR_WEIGHT_CAP, this.instance, null, nbt);
	}
}
