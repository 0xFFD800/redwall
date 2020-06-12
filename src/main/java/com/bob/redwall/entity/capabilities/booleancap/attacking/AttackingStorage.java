package com.bob.redwall.entity.capabilities.booleancap.attacking;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AttackingStorage implements IStorage<IAttacking> {
	 @Override
	 public NBTBase writeNBT(Capability<IAttacking> capability, IAttacking instance, EnumFacing side) {
		 return new NBTTagByte((byte) ((instance.get() ? 8 : 0) + instance.getMode()));
	 }

	 
	 @Override
	 public void readNBT(Capability<IAttacking> capability, IAttacking instance, EnumFacing side, NBTBase nbt) {
		 instance.set(((NBTPrimitive) nbt).getByte() >= 8 ? true : false);
		 instance.setMode(instance.get() ? ((NBTPrimitive) nbt).getByte() - 8 : ((NBTPrimitive) nbt).getByte()); 
	 }
}
