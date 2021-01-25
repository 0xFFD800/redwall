package com.bob.redwall.entity.capabilities.speed;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class SpeedProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(ISpeed.class)
	public static final Capability<ISpeed> SPEED_CAP = null;
	private ISpeed instance = SPEED_CAP.getDefaultInstance();
	 
	 @Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == SPEED_CAP;
	}
	 
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == SPEED_CAP ? SPEED_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return SPEED_CAP.getStorage().writeNBT(SPEED_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		SPEED_CAP.getStorage().readNBT(SPEED_CAP, this.instance, null, nbt);
	}
}
