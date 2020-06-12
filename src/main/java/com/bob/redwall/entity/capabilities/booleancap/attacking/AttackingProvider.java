package com.bob.redwall.entity.capabilities.booleancap.attacking;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class AttackingProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(IAttacking.class)
	public static final Capability<IAttacking> ATTACKING_CAP = null;
	private IAttacking instance = ATTACKING_CAP.getDefaultInstance();
	 
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ATTACKING_CAP;
	}
	 
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == ATTACKING_CAP ? ATTACKING_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return ATTACKING_CAP.getStorage().writeNBT(ATTACKING_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		ATTACKING_CAP.getStorage().readNBT(ATTACKING_CAP, this.instance, null, nbt);
	}
}
