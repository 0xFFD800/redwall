package com.bob.redwall.entity.capabilities.nutrition;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class NutritionProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(INutrition.class)
	public static final Capability<INutrition> NUTRITION_CAP = null;
	private INutrition instance = NUTRITION_CAP.getDefaultInstance();
	 
	 @Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == NUTRITION_CAP;
	}
	 
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == NUTRITION_CAP ? NUTRITION_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return NUTRITION_CAP.getStorage().writeNBT(NUTRITION_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		NUTRITION_CAP.getStorage().readNBT(NUTRITION_CAP, this.instance, null, nbt);
	}
}
