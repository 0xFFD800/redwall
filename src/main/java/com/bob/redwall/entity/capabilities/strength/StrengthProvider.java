package com.bob.redwall.entity.capabilities.strength;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class StrengthProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(IStrength.class)
	public static final Capability<IStrength> STRENGTH_CAP = null;
	private IStrength instance = STRENGTH_CAP.getDefaultInstance();
	 
	 @Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == STRENGTH_CAP;
	}
	 
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == STRENGTH_CAP ? STRENGTH_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return STRENGTH_CAP.getStorage().writeNBT(STRENGTH_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		STRENGTH_CAP.getStorage().readNBT(STRENGTH_CAP, this.instance, null, nbt);
	}
}
