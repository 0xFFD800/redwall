package com.bob.redwall.entity.capabilities.agility;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class AgilityProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(IAgility.class)
	public static final Capability<IAgility> AGILITY_CAP = null;
	private IAgility instance = AGILITY_CAP.getDefaultInstance();
	 
	 @Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == AGILITY_CAP;
	}
	 
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == AGILITY_CAP ? AGILITY_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return AGILITY_CAP.getStorage().writeNBT(AGILITY_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		AGILITY_CAP.getStorage().readNBT(AGILITY_CAP, this.instance, null, nbt);
	}
}
