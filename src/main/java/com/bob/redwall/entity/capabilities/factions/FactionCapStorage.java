package com.bob.redwall.entity.capabilities.factions;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class FactionCapStorage implements IStorage<IFactionCap> {
	 @Override
	 public NBTBase writeNBT(Capability<IFactionCap> capability, IFactionCap instance, EnumFacing side) {
		 return instance.writeToNBT();
	 }
	 
	 @Override
	 public void readNBT(Capability<IFactionCap> capability, IFactionCap instance, EnumFacing side, NBTBase nbt) {
		 NBTTagCompound tag = null;
		 if(nbt instanceof NBTTagCompound) tag = ((NBTTagCompound)nbt);
		 if(tag != null) {
			 instance.readFromNBT(tag);
		 }
	 }
}
