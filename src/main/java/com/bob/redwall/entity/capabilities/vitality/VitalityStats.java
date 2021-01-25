package com.bob.redwall.entity.capabilities.vitality;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class VitalityStats implements IStorage<IVitality> {
	 @Override
	 public NBTBase writeNBT(Capability<IVitality> capability, IVitality instance, EnumFacing side) {
		 NBTTagCompound tag = new NBTTagCompound();
		 tag.setInteger("amount", instance.get());
		 return tag;
	 }
	 
	 @Override
	 public void readNBT(Capability<IVitality> capability, IVitality instance, EnumFacing side, NBTBase nbt) {
		 NBTTagCompound tag = null;
		 if(nbt instanceof NBTTagCompound) tag = ((NBTTagCompound)nbt);
		 if(tag != null) {
			 instance.set(tag.getInteger("amount"));
		 }
	 }
}
