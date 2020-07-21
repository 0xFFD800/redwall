package com.bob.redwall.init;

import com.bob.redwall.Ref;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MaterialHandler {
	public static final ToolMaterial BRONZE = EnumHelper.addToolMaterial("bronze", 2, 256, 5.0F, 1.5F, 10);
	public static final ToolMaterial SILVER = EnumHelper.addToolMaterial("silver", 1, 96, 3.0F, 1.0F, 10);
	public static final ToolMaterial REDWALL = EnumHelper.addToolMaterial("redwall", 3, 3072, 5.0F, 3.5F, 10);
	public static final ToolMaterial SALAMANDASTRON = EnumHelper.addToolMaterial("salamandastron", 3, 6144, 8.0F, 3.0F, 10);
	public static final ToolMaterial SOUTHSWARD = EnumHelper.addToolMaterial("southsward", 3, 3072, 6.0F, 3.5F, 10);
	public static final ToolMaterial RIFTGARD = EnumHelper.addToolMaterial("riftgard", 3, 2048, 6.0F, 3.0F, 10);
	public static final ToolMaterial NORTHLANDS = EnumHelper.addToolMaterial("northlands", 3, 2048, 6.0F, 3.0F, 10);
	public static final ToolMaterial GUOSIM = EnumHelper.addToolMaterial("guosim", 3, 1536, 6.0F, 3.0F, 10);
	public static final ToolMaterial ROGUE_CREW = EnumHelper.addToolMaterial("rogue_crew", 3, 4608, 7.0F, 2.5F, 10);
	public static final ToolMaterial KOTIR = EnumHelper.addToolMaterial("kotir", 3, 4608, 6.0F, 3.5F, 10);

	public static final ArmorMaterial SILVER_ARMOR = EnumHelper.addArmorMaterial("silver_armor", Ref.MODID + ":silver_armor", 256, new int[]{2, 4, 5, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0F);
	public static final ArmorMaterial BRONZE_ARMOR = EnumHelper.addArmorMaterial("bronze_armor", Ref.MODID + ":bronze_armor", 256, new int[]{2, 4, 5, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0F);
	public static final ArmorMaterial IRON_MIXED_ARMOR = EnumHelper.addArmorMaterial("iron_mixed_armor", Ref.MODID + ":iron_mixed_armor", 1024, new int[]{1, 5, 5, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0F);
	public static final ArmorMaterial SALAMANDASTRON_ARMOR = EnumHelper.addArmorMaterial("salamandastron_armor", Ref.MODID + ":salamandastron_armor", 3072, new int[]{1, 5, 5, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 2F);
	public static final ArmorMaterial SOUTHSWARD_ARMOR = EnumHelper.addArmorMaterial("southsward_armor", Ref.MODID + ":southsward_armor", 2048, new int[]{2, 5, 6, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 2F);
	public static final ArmorMaterial RIFTGARD_ARMOR = EnumHelper.addArmorMaterial("riftgard_armor", Ref.MODID + ":riftgard_armor", 2048, new int[]{2, 5, 6, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0F);
	public static final ArmorMaterial KOTIR_ARMOR = EnumHelper.addArmorMaterial("kotir_armor", Ref.MODID + ":kotir_armor", 4608, new int[]{2, 5, 6, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2F);
}
