package com.bob.redwall.init;

import com.bob.redwall.items.armor.ItemRedwallArmor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ArmorHandler {
	public static Item bronze_helmet;
	public static Item bronze_chest;
	public static Item bronze_legs;
	public static Item bronze_boots;

	public static Item iron_mixed_helmet;
	public static Item iron_mixed_chest;
	public static Item iron_mixed_legs;
	public static Item iron_mixed_boots;

	public static Item golden_mail_helmet;
	public static Item golden_mail_chest;
	public static Item golden_mail_legs;
	public static Item golden_mail_boots;

	public static Item golden_mixed_helmet;
	public static Item golden_mixed_chest;
	public static Item golden_mixed_legs;
	public static Item golden_mixed_boots;

	public static Item salamandastron_helmet;
	public static Item salamandastron_chest;
	public static Item salamandastron_legs;
	public static Item salamandastron_boots;
	
	public static Item southsward_helmet;
	public static Item southsward_chest;
	public static Item southsward_legs;
	public static Item southsward_boots;
	
	public static Item riftgard_helmet;
	public static Item riftgard_chest;
	public static Item riftgard_legs;
	public static Item riftgard_boots;
	
	public static Item kotir_helmet;
	public static Item kotir_chest;
	public static Item kotir_legs;
	public static Item kotir_boots;
	
	public static void init() {
		bronze_helmet = new ItemRedwallArmor("bronze_helmet", CreativeTabHandler.COMBAT, MaterialHandler.BRONZE_ARMOR, 0, EntityEquipmentSlot.HEAD, 0.2F);
		bronze_chest = new ItemRedwallArmor("bronze_chestplate", CreativeTabHandler.COMBAT, MaterialHandler.BRONZE_ARMOR, 0, EntityEquipmentSlot.CHEST, 0.4F);
		bronze_legs = new ItemRedwallArmor("bronze_leggings", CreativeTabHandler.COMBAT, MaterialHandler.BRONZE_ARMOR, 1, EntityEquipmentSlot.LEGS, 0.3F);
		bronze_boots = new ItemRedwallArmor("bronze_boots", CreativeTabHandler.COMBAT, MaterialHandler.BRONZE_ARMOR, 0, EntityEquipmentSlot.FEET, 0.2F);
		
		iron_mixed_helmet = new ItemRedwallArmor("iron_mixed_helmet", CreativeTabHandler.COMBAT, MaterialHandler.IRON_MIXED_ARMOR, 0, EntityEquipmentSlot.HEAD, 0.15F);
		iron_mixed_chest = new ItemRedwallArmor("iron_mixed_chestplate", CreativeTabHandler.COMBAT, MaterialHandler.IRON_MIXED_ARMOR, 0, EntityEquipmentSlot.CHEST, 0.25F);
		iron_mixed_legs = new ItemRedwallArmor("iron_mixed_leggings", CreativeTabHandler.COMBAT, MaterialHandler.IRON_MIXED_ARMOR, 1, EntityEquipmentSlot.LEGS, 0.2F);
		iron_mixed_boots = new ItemRedwallArmor("iron_mixed_boots", CreativeTabHandler.COMBAT, MaterialHandler.IRON_MIXED_ARMOR, 0, EntityEquipmentSlot.FEET, 0.1F);
		
		salamandastron_helmet = new ItemRedwallArmor("salamandastron_helmet", CreativeTabHandler.COMBAT, MaterialHandler.SALAMANDASTRON_ARMOR, 0, EntityEquipmentSlot.HEAD, 0.125F);
		salamandastron_chest = new ItemRedwallArmor("salamandastron_chestplate", CreativeTabHandler.COMBAT, MaterialHandler.SALAMANDASTRON_ARMOR, 0, EntityEquipmentSlot.CHEST, 0.2F);
		salamandastron_legs = new ItemRedwallArmor("salamandastron_leggings", CreativeTabHandler.COMBAT, MaterialHandler.SALAMANDASTRON_ARMOR, 1, EntityEquipmentSlot.LEGS, 0.19F);
		salamandastron_boots = new ItemRedwallArmor("salamandastron_boots", CreativeTabHandler.COMBAT, MaterialHandler.SALAMANDASTRON_ARMOR, 0, EntityEquipmentSlot.FEET, 0.8F);
		
		southsward_helmet = new ItemRedwallArmor("southsward_helmet", CreativeTabHandler.COMBAT, MaterialHandler.SOUTHSWARD_ARMOR, 0, EntityEquipmentSlot.HEAD, 0.2F);
		southsward_chest = new ItemRedwallArmor("southsward_chestplate", CreativeTabHandler.COMBAT, MaterialHandler.SOUTHSWARD_ARMOR, 0, EntityEquipmentSlot.CHEST, 0.35F);
		southsward_legs = new ItemRedwallArmor("southsward_leggings", CreativeTabHandler.COMBAT, MaterialHandler.SOUTHSWARD_ARMOR, 1, EntityEquipmentSlot.LEGS, 0.25F);
		southsward_boots = new ItemRedwallArmor("southsward_boots", CreativeTabHandler.COMBAT, MaterialHandler.SOUTHSWARD_ARMOR, 0, EntityEquipmentSlot.FEET, 0.15F);
		
		riftgard_helmet = new ItemRedwallArmor("riftgard_helmet", CreativeTabHandler.COMBAT, MaterialHandler.RIFTGARD_ARMOR, 0, EntityEquipmentSlot.HEAD, 0.15F);
		riftgard_chest = new ItemRedwallArmor("riftgard_chestplate", CreativeTabHandler.COMBAT, MaterialHandler.RIFTGARD_ARMOR, 0, EntityEquipmentSlot.CHEST, 0.25F);
		riftgard_legs = new ItemRedwallArmor("riftgard_leggings", CreativeTabHandler.COMBAT, MaterialHandler.RIFTGARD_ARMOR, 1, EntityEquipmentSlot.LEGS, 0.2F);
		riftgard_boots = new ItemRedwallArmor("riftgard_boots", CreativeTabHandler.COMBAT, MaterialHandler.RIFTGARD_ARMOR, 0, EntityEquipmentSlot.FEET, 0.1F);
		
		kotir_helmet = new ItemRedwallArmor("kotir_helmet", CreativeTabHandler.COMBAT, MaterialHandler.KOTIR_ARMOR, 0, EntityEquipmentSlot.HEAD, 0.2F);
		kotir_chest = new ItemRedwallArmor("kotir_chestplate", CreativeTabHandler.COMBAT, MaterialHandler.KOTIR_ARMOR, 0, EntityEquipmentSlot.CHEST, 0.35F);
		kotir_legs = new ItemRedwallArmor("kotir_leggings", CreativeTabHandler.COMBAT, MaterialHandler.KOTIR_ARMOR, 1, EntityEquipmentSlot.LEGS, 0.25F);
		kotir_boots = new ItemRedwallArmor("kotir_boots", CreativeTabHandler.COMBAT, MaterialHandler.KOTIR_ARMOR, 0, EntityEquipmentSlot.FEET, 0.15F);
	}
	
	public static void register(RegistryEvent.Register<Item> event) {
		registerItem(event, bronze_helmet);
		registerItem(event, bronze_chest);
		registerItem(event, bronze_legs);
		registerItem(event, bronze_boots);
		
		registerItem(event, iron_mixed_helmet);
		registerItem(event, iron_mixed_chest);
		registerItem(event, iron_mixed_legs);
		registerItem(event, iron_mixed_boots);
		
		registerItem(event, salamandastron_helmet);
		registerItem(event, salamandastron_chest);
		registerItem(event, salamandastron_legs);
		registerItem(event, salamandastron_boots);
		
		registerItem(event, southsward_helmet);
		registerItem(event, southsward_chest);
		registerItem(event, southsward_legs);
		registerItem(event, southsward_boots);
		
		registerItem(event, riftgard_helmet);
		registerItem(event, riftgard_chest);
		registerItem(event, riftgard_legs);
		registerItem(event, riftgard_boots);
		
		registerItem(event, kotir_helmet);
		registerItem(event, kotir_chest);
		registerItem(event, kotir_legs);
		registerItem(event, kotir_boots);
	}
	
	public static void registerRenders() {
		registerRender(bronze_helmet);
		registerRender(bronze_chest);
		registerRender(bronze_legs);
		registerRender(bronze_boots);
		
		registerRender(iron_mixed_helmet);
		registerRender(iron_mixed_chest);
		registerRender(iron_mixed_legs);
		registerRender(iron_mixed_boots);
		
		registerRender(salamandastron_helmet);
		registerRender(salamandastron_chest);
		registerRender(salamandastron_legs);
		registerRender(salamandastron_boots);
		
		registerRender(southsward_helmet);
		registerRender(southsward_chest);
		registerRender(southsward_legs);
		registerRender(southsward_boots);
		
		registerRender(riftgard_helmet);
		registerRender(riftgard_chest);
		registerRender(riftgard_legs);
		registerRender(riftgard_boots);
		
		registerRender(kotir_helmet);
		registerRender(kotir_chest);
		registerRender(kotir_legs);
		registerRender(kotir_boots);
	}
	
	public static void registerItem(RegistryEvent.Register<Item> event, Item item) {
		event.getRegistry().register(item);
	}
	
	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
