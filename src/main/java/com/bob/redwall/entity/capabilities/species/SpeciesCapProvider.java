package com.bob.redwall.entity.capabilities.species;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class SpeciesCapProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(ISpeciesCap.class)
	public static final Capability<ISpeciesCap> FACTION_CAP = null;
	private ISpeciesCap instance = FACTION_CAP.getDefaultInstance();
	 
	 @Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == FACTION_CAP;
	}
	 
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == FACTION_CAP ? FACTION_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return FACTION_CAP.getStorage().writeNBT(FACTION_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		FACTION_CAP.getStorage().readNBT(FACTION_CAP, this.instance, null, nbt);
	}
}
