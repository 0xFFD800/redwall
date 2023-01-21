package com.bob.redwall.entity.capabilities.species;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class SpeciesCapStorage implements IStorage<ISpeciesCap> {
	@Override
	public NBTBase writeNBT(Capability<ISpeciesCap> capability, ISpeciesCap instance, EnumFacing side) {
		return instance.writeToNBT();
	}

	@Override
	public void readNBT(Capability<ISpeciesCap> capability, ISpeciesCap instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag = null;
		if (nbt instanceof NBTTagCompound)
			tag = ((NBTTagCompound) nbt);
		if (tag != null)
			instance.readFromNBT(tag);
	}
}
