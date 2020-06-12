package com.bob.redwall.items.food;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.Lists;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemModFood extends ItemFood {
	private final Map<String, PotionEffect> effects = new HashMap<String, PotionEffect>();
	private final Map<String, Float> probabilities = new HashMap<String, Float>();
	private final List<String> ids = Lists.newArrayList();
    public ItemModFood(String name, CreativeTabs tab, int amount, float saturation, boolean isWolfFood) {
    	super(amount, saturation, isWolfFood);
    	this.setRegistryName(name);
    	this.setCreativeTab(tab);
    	this.setUnlocalizedName(name);
    }
    
    public Item addPotionEffect(PotionEffect effect, float probability) {
    	String string = String.valueOf(new UUID(new Random().nextLong(), new Random().nextLong()).hashCode());
    	this.effects.put(string, effect);
    	this.probabilities.put(string, probability);
    	this.ids.add(string);
    	return this;
    }
    
    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
    	if(!worldIn.isRemote) {
    		for(String id : this.ids) {
    			PotionEffect potionId = this.effects.get(id);
    			float potionEffectProbability = this.probabilities.get(id);
    	        if (potionId != null && worldIn.rand.nextFloat() < potionEffectProbability) {
    	            player.addPotionEffect(new PotionEffect(potionId));
    	        }
    		}
    	}
    	super.onFoodEaten(stack, worldIn, player);
    }
}
