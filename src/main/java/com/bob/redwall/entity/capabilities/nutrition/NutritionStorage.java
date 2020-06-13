package com.bob.redwall.entity.capabilities.nutrition;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class NutritionStorage implements IStorage<INutrition> {
	 @Override
	 public NBTBase writeNBT(Capability<INutrition> capability, INutrition instance, EnumFacing side) {
		 NBTTagCompound tag = new NBTTagCompound();
		 tag.setFloat("protein", instance.getProtein());
		 tag.setFloat("carbs", instance.getCarbs());
		 tag.setFloat("veggies", instance.getVeggies());
		 tag.setFloat("fruits", instance.getFruits());
		 tag.setFloat("lastFood", instance.getLastFood());
		 return tag;
	 }
	 
	 @Override
	 public void readNBT(Capability<INutrition> capability, INutrition instance, EnumFacing side, NBTBase nbt) {
		 NBTTagCompound tag = null;
		 if(nbt instanceof NBTTagCompound) tag = ((NBTTagCompound)nbt);
		 if(tag != null) {
			 instance.setProtein(tag.getFloat("protein"));
			 instance.setCarbs(tag.getFloat("carbs"));
			 instance.setVeggies(tag.getFloat("veggies"));
			 instance.setFruits(tag.getFloat("fruits"));
			 instance.setLastFood(tag.getFloat("lastFood"));
		 }
	 }
}
