package com.bob.redwall.entity.capabilities.booleancap.defending;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class DefendingProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(IDefending.class)
	public static final Capability<IDefending> DEFENDING_CAP = null;
	private IDefending instance = DEFENDING_CAP.getDefaultInstance();
	 
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == DEFENDING_CAP;
	}
	 
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == DEFENDING_CAP ? DEFENDING_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return DEFENDING_CAP.getStorage().writeNBT(DEFENDING_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		DEFENDING_CAP.getStorage().readNBT(DEFENDING_CAP, this.instance, null, nbt);
	}
}
