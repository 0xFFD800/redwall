package com.bob.redwall.entity.capabilities.species;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class SpeciesCapProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(ISpeciesCap.class)
	public static final Capability<ISpeciesCap> SPECIES_CAP = null;
	private ISpeciesCap instance = SPECIES_CAP.getDefaultInstance();
	 
	 @Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == SPECIES_CAP;
	}
	 
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == SPECIES_CAP ? SPECIES_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return SPECIES_CAP.getStorage().writeNBT(SPECIES_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		SPECIES_CAP.getStorage().readNBT(SPECIES_CAP, this.instance, null, nbt);
	}
}
