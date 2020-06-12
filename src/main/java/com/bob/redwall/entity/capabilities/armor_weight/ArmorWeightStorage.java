package com.bob.redwall.entity.capabilities.armor_weight;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ArmorWeightStorage implements IStorage<IArmorWeight> {
	 @Override
	 public NBTBase writeNBT(Capability<IArmorWeight> capability, IArmorWeight instance, EnumFacing side) {
		 NBTTagCompound tag = new NBTTagCompound();
		 tag.setFloat("amount", instance.get());
		 return tag;
	 }
	 
	 @Override
	 public void readNBT(Capability<IArmorWeight> capability, IArmorWeight instance, EnumFacing side, NBTBase nbt) {
		 NBTTagCompound tag = null;
		 if(nbt instanceof NBTTagCompound) tag = ((NBTTagCompound)nbt);
		 if(tag != null) {
			 instance.set(tag.getFloat("amount"));
		 }
	 }
}
