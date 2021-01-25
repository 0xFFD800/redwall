package com.bob.redwall.entity.capabilities.vitality;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class VitalityProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(IVitality.class)
	public static final Capability<IVitality> VITALITY_CAP = null;
	private IVitality instance = VITALITY_CAP.getDefaultInstance();
	 
	 @Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == VITALITY_CAP;
	}
	 
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == VITALITY_CAP ? VITALITY_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return VITALITY_CAP.getStorage().writeNBT(VITALITY_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		VITALITY_CAP.getStorage().readNBT(VITALITY_CAP, this.instance, null, nbt);
	}
}
