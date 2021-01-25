package com.bob.redwall.entity.capabilities.speed;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class SpeedStats implements IStorage<ISpeed> {
	 @Override
	 public NBTBase writeNBT(Capability<ISpeed> capability, ISpeed instance, EnumFacing side) {
		 NBTTagCompound tag = new NBTTagCompound();
		 tag.setInteger("amount", instance.get());
		 return tag;
	 }
	 
	 @Override
	 public void readNBT(Capability<ISpeed> capability, ISpeed instance, EnumFacing side, NBTBase nbt) {
		 NBTTagCompound tag = null;
		 if(nbt instanceof NBTTagCompound) tag = ((NBTTagCompound)nbt);
		 if(tag != null) {
			 instance.set(tag.getInteger("amount"));
		 }
	 }
}
