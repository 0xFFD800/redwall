package com.bob.redwall.entity.capabilities.season;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class SeasonCapProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(ISeasonCap.class)
	public static final Capability<ISeasonCap> SEASON_CAP = null;
	private ISeasonCap instance = SEASON_CAP.getDefaultInstance();
	 
	 @Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == SEASON_CAP;
	}
	 
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == SEASON_CAP ? SEASON_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return SEASON_CAP.getStorage().writeNBT(SEASON_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		SEASON_CAP.getStorage().readNBT(SEASON_CAP, this.instance, null, nbt);
	}
}
