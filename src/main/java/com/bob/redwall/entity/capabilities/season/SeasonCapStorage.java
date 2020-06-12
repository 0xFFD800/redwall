package com.bob.redwall.entity.capabilities.season;

import com.bob.redwall.dimensions.redwall.EnumSeasons;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class SeasonCapStorage implements IStorage<ISeasonCap> {
	 @Override
	 public NBTBase writeNBT(Capability<ISeasonCap> capability, ISeasonCap instance, EnumFacing side) {
		 if(instance.getSeason() == null) return new NBTTagString(EnumSeasons.SPRING.name());
		 NBTTagString nbt = new NBTTagString(instance.getSeason().name());
		 return nbt;
	 }

	 
	 @Override
	 public void readNBT(Capability<ISeasonCap> capability, ISeasonCap instance, EnumFacing side, NBTBase nbt) {
		 NBTTagString tag = (NBTTagString)nbt;
		 instance.setSeason(EnumSeasons.valueOf(tag.getString()));
	 }
}
