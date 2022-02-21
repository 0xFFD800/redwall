package com.bob.redwall.init;

import java.util.Random;

import com.bob.redwall.Ref;
import com.bob.redwall.blocks.multiuse.BlockHiddenDoor;
import com.bob.redwall.blocks.multiuse.BlockModDoor;
import com.bob.redwall.entity.statuseffect.StatusEffect;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.items.ModItem;
import com.bob.redwall.items.blocks.ItemBlockSpecial;
import com.bob.redwall.items.blocks.ItemHiddenDoor;
import com.bob.redwall.items.blocks.ItemModDoor;
import com.bob.redwall.items.blocks.ItemWeaponRack;
import com.bob.redwall.items.brewing.ItemDrinkVessel;
import com.bob.redwall.items.brewing.ItemEmptyDrinkVessel;
import com.bob.redwall.items.food.ItemCustomFish;
import com.bob.redwall.items.food.ItemModFood;
import com.bob.redwall.items.food.ItemModSeedFood;
import com.bob.redwall.items.food.ItemModSeeds;
import com.bob.redwall.items.food.ItemModSoup;
import com.bob.redwall.items.tools.ItemModAxe;
import com.bob.redwall.items.tools.ItemModHoe;
import com.bob.redwall.items.tools.ItemModPickaxe;
import com.bob.redwall.items.tools.ItemModSpade;
import com.bob.redwall.items.tools.ItemScythe;
import com.bob.redwall.items.weapons.melee.ItemBattleAxe;
import com.bob.redwall.items.weapons.melee.ItemHalberd;
import com.bob.redwall.items.weapons.melee.ItemLance;
import com.bob.redwall.items.weapons.melee.ItemMace;
import com.bob.redwall.items.weapons.melee.ItemPike;
import com.bob.redwall.items.weapons.melee.swords.ItemCustomSword;
import com.bob.redwall.items.weapons.melee.swords.ItemSwordMTW;
import com.bob.redwall.items.weapons.ranged.ItemModBow;
import com.bob.redwall.items.weapons.throwable.ItemDagger;
import com.bob.redwall.items.weapons.throwable.ItemSpear;
import com.bob.redwall.items.weapons.throwable.ItemThrowingAxe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;

public class ItemHandler {
	public static final EnumAction SPEAR_ACTION = EnumHelper.addAction("spear");

	public static Item maple_door;
	public static Item elm_door;
	public static Item ash_door;
	public static Item pine_door;
	public static Item fir_door;
	public static Item larch_door;
	public static Item hornbeam_door;
	public static Item beech_door;
	public static Item yew_door;
	public static Item alder_door;
	public static Item aspen_door;
	public static Item willow_door;
	public static Item apple_door;
	public static Item plum_door;
	public static Item southsward_hidden_door;

	public static Item weapon_rack;

	public static Item strawberry;
	public static Item blueberry;
	public static Item blackberry;
	public static Item raspberry;
	public static Item elderberry;
	public static Item wildberry;

	public static Item plum;
	public static Item damson;
	public static Item pear;
	public static Item quince;
	public static Item chestnut;
	public static Item roast_chestnut;

	public static Item strawberry_seeds;
	public static Item blueberry_seeds;
	public static Item blackberry_seeds;
	public static Item raspberry_seeds;
	public static Item elderberry_seeds;
	public static Item wildberry_seeds;

	public static Item trout;
	public static Item trout_cooked;
	public static Item bass;
	public static Item bass_cooked;
	public static Item perch;
	public static Item perch_cooked;
	public static Item grayling;
	public static Item grayling_cooked;

	public static Item corn;
	public static Item turnip;
	public static Item onion;
	public static Item peas;
	public static Item rice;
	public static Item yam;
	public static Item beans;

	public static Item grapes;

	public static Item rice_bowl;

	public static Item drink_bottle;

	public static Item mug_empty;
	public static Item mug_drink;

	public static Item bowl_drink;

	public static Item cornstalk;

	public static Item water_reeds;
	public static Item water_reeds_dried;

	public static Item tin_ingot;
	public static Item copper_ingot;
	public static Item bronze_ingot;

	public static Item tin_nugget;
	public static Item copper_nugget;
	public static Item bronze_nugget;

	public static Item sword_mtw;

	public static Item golden_sword;
	public static Item golden_broadsword;
	public static Item golden_rapier;
	public static Item golden_scimitar;
	public static Item golden_halberd;
	public static Item golden_spear;
	public static Item golden_pike;
	public static Item golden_mace;
	public static Item golden_dagger;
	public static Item golden_battleaxe;
	public static Item golden_throwing_axe;
	public static Item golden_scythe;

	public static Item stone_spear;
	public static Item stone_mace;
	public static Item stone_throwing_axe;

	public static Item bronze_halberd;
	public static Item bronze_spear;
	public static Item bronze_pike;
	public static Item bronze_mace;
	public static Item bronze_dagger;
	public static Item bronze_battleaxe;
	public static Item bronze_throwing_axe;
	public static Item bronze_axe;
	public static Item bronze_pickaxe;
	public static Item bronze_spade;
	public static Item bronze_hoe;
	public static Item bronze_scythe;

	public static Item iron_sword;
	public static Item iron_broadsword;
	public static Item iron_rapier;
	public static Item iron_scimitar;
	public static Item iron_halberd;
	public static Item iron_spear;
	public static Item iron_pike;
	public static Item iron_mace;
	public static Item iron_dagger;
	public static Item iron_battleaxe;
	public static Item iron_throwing_axe;
	public static Item iron_scythe;

	public static Item salamandastron_sword;
	public static Item salamandastron_claymore;
	public static Item salamandastron_broadsword;
	public static Item salamandastron_rapier;
	public static Item salamandastron_sabre;
	public static Item salamandastron_spear;
	public static Item salamandastron_lance;
	public static Item salamandastron_pike;
	public static Item salamandastron_dagger;
	public static Item salamandastron_dirk;
	public static Item salamandastron_battleaxe;
	public static Item salamandastron_longbow;

	public static Item southsward_sword;
	public static Item southsward_hammer;
	public static Item southsward_spear;
	public static Item southsward_pike;
	public static Item southsward_mace;
	public static Item southsward_dagger;
	public static Item southsward_longbow;

	public static Item riftgard_sword;
	public static Item riftgard_broadsword;
	public static Item riftgard_rapier;
	public static Item riftgard_sabre;
	public static Item riftgard_spear;
	public static Item riftgard_dagger;

	public static Item northlands_claymore;
	public static Item northlands_dirk;
	public static Item northlands_sgian_dhu;

	public static Item guosim_rapier;
	public static Item guosim_paddle;
	public static Item guosim_bow;

	public static Item rogue_crew_sword;
	public static Item rogue_crew_battleaxe;
	public static Item rogue_crew_throwing_axe;
	public static Item rogue_crew_longbow;

	public static Item kotir_sword;
	public static Item kotir_battleaxe;
	public static Item kotir_dagger;
	public static Item kotir_halberd;
	public static Item kotir_spear;
	public static Item kotir_pike;

	public static Item redwall_axe;
	public static Item redwall_pickaxe;
	public static Item redwall_spade;
	public static Item redwall_hoe;
	public static Item redwall_scythe;

	public static void init() {
		maple_door = new ItemModDoor("maple_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.maple_door);
		elm_door = new ItemModDoor("elm_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.elm_door);
		ash_door = new ItemModDoor("ash_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.ash_door);
		pine_door = new ItemModDoor("pine_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.pine_door);
		fir_door = new ItemModDoor("fir_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.fir_door);
		larch_door = new ItemModDoor("larch_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.larch_door);
		hornbeam_door = new ItemModDoor("hornbeam_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.hornbeam_door);
		beech_door = new ItemModDoor("beech_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.beech_door);
		yew_door = new ItemModDoor("yew_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.yew_door);
		alder_door = new ItemModDoor("alder_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.alder_door);
		aspen_door = new ItemModDoor("aspen_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.aspen_door);
		willow_door = new ItemModDoor("willow_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.willow_door);
		apple_door = new ItemModDoor("apple_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.apple_door);
		southsward_hidden_door = new ItemHiddenDoor("southsward_hidden_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.southsward_hidden_door);
		plum_door = new ItemModDoor("plum_door", CreativeTabHandler.BLOCKS, 1, BlockHandler.plum_door);

		((BlockModDoor) BlockHandler.maple_door).setItem(ItemHandler.maple_door);
		((BlockModDoor) BlockHandler.elm_door).setItem(ItemHandler.elm_door);
		((BlockModDoor) BlockHandler.ash_door).setItem(ItemHandler.ash_door);
		((BlockModDoor) BlockHandler.pine_door).setItem(ItemHandler.pine_door);
		((BlockModDoor) BlockHandler.fir_door).setItem(ItemHandler.fir_door);
		((BlockModDoor) BlockHandler.larch_door).setItem(ItemHandler.larch_door);
		((BlockModDoor) BlockHandler.hornbeam_door).setItem(ItemHandler.hornbeam_door);
		((BlockModDoor) BlockHandler.beech_door).setItem(ItemHandler.beech_door);
		((BlockModDoor) BlockHandler.yew_door).setItem(ItemHandler.yew_door);
		((BlockModDoor) BlockHandler.alder_door).setItem(ItemHandler.alder_door);
		((BlockModDoor) BlockHandler.aspen_door).setItem(ItemHandler.aspen_door);
		((BlockModDoor) BlockHandler.willow_door).setItem(ItemHandler.willow_door);
		((BlockModDoor) BlockHandler.apple_door).setItem(ItemHandler.apple_door);
		((BlockModDoor) BlockHandler.plum_door).setItem(ItemHandler.plum_door);
		((BlockHiddenDoor) BlockHandler.southsward_hidden_door).setItem(ItemHandler.southsward_hidden_door);

		weapon_rack = new ItemWeaponRack("weapon_rack", CreativeTabHandler.BLOCKS, 1);

		strawberry = new ItemModFood("strawberry", CreativeTabHandler.FOOD, 3, 0.3F, 1, 3, 2, 6);
		blueberry = new ItemModFood("blueberry", CreativeTabHandler.FOOD, 2, 0.3F, 1, 2, 1, 4);
		blackberry = new ItemModFood("blackberry", CreativeTabHandler.FOOD, 2, 0.3F, 1, 2, 1, 4);
		raspberry = new ItemModFood("raspberry", CreativeTabHandler.FOOD, 2, 0.3F, 1, 2, 1, 4);
		elderberry = new ItemModFood("elderberry", CreativeTabHandler.FOOD, 1, 0.3F, 1, 2, 1, 3);
		wildberry = ((ItemModFood) new ItemModFood("wildberry", CreativeTabHandler.FOOD, 1, 0.1F, 1, 2, 1, 2).addPotionEffect(new PotionEffect(StatusEffect.POISON, new Random().nextInt(200) + 200, 0, false, false), 0.2F)).addPotionEffect(new PotionEffect(MobEffects.HUNGER, new Random().nextInt(600) + 600, 0, false, false), 0.6F);

		plum = new ItemModFood("plum", CreativeTabHandler.FOOD, 5, 0.3F, 2, 5, 3, 10);
		damson = new ItemModFood("damson", CreativeTabHandler.FOOD, 4, 0.3F, 2, 4, 2, 8);

		strawberry_seeds = new ItemModSeeds("strawberry_seeds", CreativeTabHandler.FOOD, BlockHandler.strawberry_bush, Blocks.FARMLAND);
		blueberry_seeds = new ItemModSeeds("blueberry_seeds", CreativeTabHandler.FOOD, BlockHandler.blueberry_bush, Blocks.FARMLAND);
		blackberry_seeds = new ItemModSeeds("blackberry_seeds", CreativeTabHandler.FOOD, BlockHandler.blackberry_bush, Blocks.FARMLAND);
		raspberry_seeds = new ItemModSeeds("raspberry_seeds", CreativeTabHandler.FOOD, BlockHandler.raspberry_bush, Blocks.FARMLAND);
		elderberry_seeds = new ItemModSeeds("elderberry_seeds", CreativeTabHandler.FOOD, BlockHandler.elderberry_bush, Blocks.FARMLAND);
		wildberry_seeds = new ItemModSeeds("wildberry_seeds", CreativeTabHandler.FOOD, BlockHandler.wildberry_bush, Blocks.FARMLAND);

		trout = new ItemCustomFish(2, 0.1F, "trout", CreativeTabHandler.FOOD, 4, 1, 1, 1);
		trout_cooked = new ItemCustomFish(8, 0.6F, "trout_cooked", CreativeTabHandler.FOOD, 16, 5, 4, 5);
		bass = new ItemCustomFish(3, 0.1F, "bass", CreativeTabHandler.FOOD, 6, 2, 1, 1);
		bass_cooked = new ItemCustomFish(9, 0.6F, "bass_cooked", CreativeTabHandler.FOOD, 18, 6, 5, 5);
		perch = new ItemCustomFish(1, 0.1F, "perch", CreativeTabHandler.FOOD, 2, 1, 1, 1);
		perch_cooked = new ItemCustomFish(5, 0.6F, "perch_cooked", CreativeTabHandler.FOOD, 10, 3, 4, 2);
		grayling = new ItemCustomFish(4, 0.3F, "grayling", CreativeTabHandler.FOOD, 8, 2, 1, 2);
		grayling_cooked = new ItemCustomFish(9, 0.8F, "grayling_cooked", CreativeTabHandler.FOOD, 18, 6, 5, 5);

		corn = new ItemModSeedFood("corn", CreativeTabHandler.FOOD, 3, 0.3F, BlockHandler.cornstalk, 2, 4, 6, 1);
		turnip = new ItemModSeedFood("turnip", CreativeTabHandler.FOOD, 2, 0.3F, BlockHandler.turnips, 1, 2, 4, 1);

		onion = new ItemModSeedFood("onion", CreativeTabHandler.FOOD, 2, 0.3F, BlockHandler.onions, 1, 2, 4, 1);
		peas = new ItemModSeedFood("peas", CreativeTabHandler.FOOD, 1, 0.3F, BlockHandler.peas, 1, 1, 3, 1);
		yam = new ItemModSeedFood("yam", CreativeTabHandler.FOOD, 2, 0.6F, BlockHandler.yams, 3, 4, 5, 1);
		rice = new ItemModSeeds("rice", CreativeTabHandler.FOOD, BlockHandler.rice, Blocks.FARMLAND);
		beans = new ItemModSeedFood("beans", CreativeTabHandler.FOOD, 2, 0.3F, BlockHandler.beans, 3, 4, 5, 1);

		grapes = new ItemModFood("grapes", CreativeTabHandler.FOOD, 3, 0.3F, 2, 4, 3, 8);

		rice_bowl = new ItemModSoup("rice_bowl", CreativeTabHandler.FOOD, 2, 0.4F, 3, 4, 2, 1);

		drink_bottle = new ItemDrinkVessel("drink_bottle", CreativeTabHandler.FOOD, new ResourceLocation(Ref.MODID, "bottle")).setContainerItem(Items.GLASS_BOTTLE);

		mug_empty = new ItemEmptyDrinkVessel("mug_empty", CreativeTabHandler.FOOD, new ResourceLocation(Ref.MODID, "mug_drink"), new ResourceLocation(Ref.MODID, "mug"));
		mug_drink = new ItemDrinkVessel("mug_drink", CreativeTabHandler.FOOD, new ResourceLocation(Ref.MODID, "mug")).setContainerItem(ItemHandler.mug_empty);

		bowl_drink = new ItemDrinkVessel("bowl_drink", CreativeTabHandler.FOOD, new ResourceLocation(Ref.MODID, "bowl")).setContainerItem(Items.BOWL);

		cornstalk = new ModItem("cornstalk", CreativeTabHandler.MISC);

		water_reeds = new ItemBlockSpecial("water_reeds", CreativeTabHandler.MISC, BlockHandler.water_reeds);
		water_reeds_dried = new ModItem("water_reeds_dried", CreativeTabHandler.MISC);

		tin_ingot = new ModItem("tin_ingot", CreativeTabHandler.MISC);
		copper_ingot = new ModItem("copper_ingot", CreativeTabHandler.MISC);
		bronze_ingot = new ModItem("bronze_ingot", CreativeTabHandler.MISC);

		tin_nugget = new ModItem("tin_nugget", CreativeTabHandler.MISC);
		copper_nugget = new ModItem("copper_nugget", CreativeTabHandler.MISC);
		bronze_nugget = new ModItem("bronze_nugget", CreativeTabHandler.MISC);

		sword_mtw = new ItemSwordMTW("sword_mtw", CreativeTabHandler.COMBAT, -3.2F, 9.0F, 0.0F, null);

		golden_sword = new ItemCustomSword("golden_sword", CreativeTabHandler.COMBAT, -2.5F, 4.0F, 0.0F, ToolMaterial.GOLD);
		golden_broadsword = new ItemCustomSword("golden_broadsword", CreativeTabHandler.COMBAT, -2.6F, 4.5F, 0.0F, ToolMaterial.GOLD);
		golden_rapier = new ItemCustomSword("golden_rapier", CreativeTabHandler.COMBAT, -2.4F, 3.5F, 0.0F, ToolMaterial.GOLD);
		golden_scimitar = new ItemCustomSword("golden_scimitar", CreativeTabHandler.COMBAT, -2.4F, 4.0F, -0.5F, ToolMaterial.GOLD);
		golden_halberd = new ItemHalberd("golden_halberd", CreativeTabHandler.COMBAT, -2.9F, 7.0F, 2.0F, ToolMaterial.GOLD);
		golden_spear = new ItemSpear("golden_spear", CreativeTabHandler.COMBAT, -2.7F, 3.5F, 2.0F, ToolMaterial.GOLD);
		golden_pike = new ItemPike("golden_pike", CreativeTabHandler.COMBAT, -2.9F, 3.5F, 4.0F, ToolMaterial.GOLD);
		golden_mace = new ItemMace("golden_mace", CreativeTabHandler.COMBAT, -2.9F, 9.0F, 0.0F, ToolMaterial.GOLD);
		golden_dagger = new ItemDagger("golden_dagger", CreativeTabHandler.COMBAT, -2.0F, 1.5F, -2.0F, ToolMaterial.GOLD);
		golden_battleaxe = new ItemBattleAxe("golden_battleaxe", CreativeTabHandler.COMBAT, -2.8F, 7.0F, 0.0F, ToolMaterial.GOLD);
		golden_throwing_axe = new ItemThrowingAxe("golden_throwing_axe", CreativeTabHandler.COMBAT, -2.6F, 4.0F, 0.0F, ToolMaterial.GOLD);
		golden_scythe = new ItemScythe("golden_scythe", CreativeTabHandler.COMBAT, -2.7F, 4.0F, 1.0F, ToolMaterial.GOLD);

		stone_spear = new ItemSpear("stone_spear", CreativeTabHandler.COMBAT, -2.6F, 3.5F, 2.0F, ToolMaterial.STONE);
		stone_mace = new ItemMace("stone_mace", CreativeTabHandler.COMBAT, -2.8F, 9.0F, 0.0F, ToolMaterial.STONE);
		stone_throwing_axe = new ItemThrowingAxe("stone_throwing_axe", CreativeTabHandler.COMBAT, -2.5F, 4.0F, 0.0F, ToolMaterial.STONE);

		bronze_halberd = new ItemHalberd("bronze_halberd", CreativeTabHandler.COMBAT, -2.8F, 8.0F, 2.0F, MaterialHandler.BRONZE);
		bronze_spear = new ItemSpear("bronze_spear", CreativeTabHandler.COMBAT, -2.6F, 4.5F, 2.0F, MaterialHandler.BRONZE);
		bronze_pike = new ItemPike("bronze_pike", CreativeTabHandler.COMBAT, -2.8F, 4.5F, 4.0F, MaterialHandler.BRONZE);
		bronze_mace = new ItemMace("bronze_mace", CreativeTabHandler.COMBAT, -2.8F, 10.0F, 0.0F, MaterialHandler.BRONZE);
		bronze_dagger = new ItemDagger("bronze_dagger", CreativeTabHandler.COMBAT, -2.0F, 2.5F, -2.0F, MaterialHandler.BRONZE);
		bronze_battleaxe = new ItemBattleAxe("bronze_battleaxe", CreativeTabHandler.COMBAT, -2.7F, 8.0F, 0.0F, MaterialHandler.BRONZE);
		bronze_throwing_axe = new ItemThrowingAxe("bronze_throwing_axe", CreativeTabHandler.COMBAT, -2.5F, 5.0F, 0.0F, MaterialHandler.BRONZE);
		bronze_axe = new ItemModAxe("bronze_axe", CreativeTabHandler.COMBAT, -2.5F, 5.0F, MaterialHandler.BRONZE);
		bronze_pickaxe = new ItemModPickaxe("bronze_pickaxe", CreativeTabHandler.COMBAT, MaterialHandler.BRONZE);
		bronze_spade = new ItemModSpade("bronze_spade", CreativeTabHandler.COMBAT, MaterialHandler.BRONZE);
		bronze_hoe = new ItemModHoe("bronze_hoe", CreativeTabHandler.COMBAT, MaterialHandler.BRONZE);
		bronze_scythe = new ItemScythe("bronze_scythe", CreativeTabHandler.COMBAT, -2.7F, 5.5F, 1.0F, MaterialHandler.BRONZE);

		iron_sword = new ItemCustomSword("iron_sword", CreativeTabHandler.COMBAT, -2.4F, 6.0F, 0.0F, ToolMaterial.IRON);
		iron_broadsword = new ItemCustomSword("iron_broadsword", CreativeTabHandler.COMBAT, -2.5F, 6.5F, 0.0F, ToolMaterial.IRON);
		iron_rapier = new ItemCustomSword("iron_rapier", CreativeTabHandler.COMBAT, -2.3F, 5.5F, 0.0F, ToolMaterial.IRON);
		iron_scimitar = new ItemCustomSword("iron_scimitar", CreativeTabHandler.COMBAT, -2.3F, 6.0F, -0.5F, ToolMaterial.IRON);
		iron_halberd = new ItemHalberd("iron_halberd", CreativeTabHandler.COMBAT, -2.8F, 9.0F, 2.0F, ToolMaterial.IRON);
		iron_spear = new ItemSpear("iron_spear", CreativeTabHandler.COMBAT, -2.6F, 5.5F, 2.0F, ToolMaterial.IRON);
		iron_pike = new ItemPike("iron_pike", CreativeTabHandler.COMBAT, -2.8F, 5.5F, 4.0F, ToolMaterial.IRON);
		iron_mace = new ItemMace("iron_mace", CreativeTabHandler.COMBAT, -2.8F, 11.0F, 0.0F, ToolMaterial.IRON);
		iron_dagger = new ItemDagger("iron_dagger", CreativeTabHandler.COMBAT, -2.0F, 3.5F, -2.0F, ToolMaterial.IRON);
		iron_battleaxe = new ItemBattleAxe("iron_battleaxe", CreativeTabHandler.COMBAT, -2.7F, 9.0F, 0.0F, ToolMaterial.IRON);
		iron_throwing_axe = new ItemThrowingAxe("iron_throwing_axe", CreativeTabHandler.COMBAT, -2.5F, 6.0F, 0.0F, ToolMaterial.IRON);
		iron_scythe = new ItemScythe("iron_scythe", CreativeTabHandler.COMBAT, -2.7F, 6.0F, 1.0F, ToolMaterial.IRON);

		salamandastron_sword = new ItemCustomSword("salamandastron_sword", CreativeTabHandler.COMBAT, -2.3F, 7.0F, 0.5F, MaterialHandler.SALAMANDASTRON, Faction.FacList.SALAMANDASTRON);
		salamandastron_claymore = new ItemCustomSword("salamandastron_claymore", CreativeTabHandler.COMBAT, -2.4F, 7.0F, 1.0F, MaterialHandler.SALAMANDASTRON, Faction.FacList.SALAMANDASTRON);
		salamandastron_broadsword = new ItemCustomSword("salamandastron_broadsword", CreativeTabHandler.COMBAT, -2.4F, 7.5F, 0.5F, MaterialHandler.SALAMANDASTRON, Faction.FacList.SALAMANDASTRON);
		salamandastron_rapier = new ItemCustomSword("salamandastron_rapier", CreativeTabHandler.COMBAT, -2.2F, 6.5F, 0.5F, MaterialHandler.SALAMANDASTRON, Faction.FacList.SALAMANDASTRON);
		salamandastron_sabre = new ItemCustomSword("salamandastron_sabre", CreativeTabHandler.COMBAT, -2.15F, 6.5F, 0.0F, MaterialHandler.SALAMANDASTRON, Faction.FacList.SALAMANDASTRON);
		salamandastron_spear = new ItemSpear("salamandastron_spear", CreativeTabHandler.COMBAT, -2.5F, 6.5F, 2.5F, MaterialHandler.SALAMANDASTRON, Faction.FacList.SALAMANDASTRON);
		salamandastron_lance = new ItemLance("salamandastron_lance", CreativeTabHandler.COMBAT, -2.5F, 7.0F, 2.5F, MaterialHandler.SALAMANDASTRON, Faction.FacList.SALAMANDASTRON);
		salamandastron_pike = new ItemPike("salamandastron_pike", CreativeTabHandler.COMBAT, -2.8F, 6.5F, 4.5F, MaterialHandler.SALAMANDASTRON, Faction.FacList.SALAMANDASTRON);
		salamandastron_dagger = new ItemDagger("salamandastron_dagger", CreativeTabHandler.COMBAT, -1.8F, 4.5F, -1.5F, MaterialHandler.SALAMANDASTRON, Faction.FacList.SALAMANDASTRON);
		salamandastron_dirk = new ItemCustomSword("salamandastron_dirk", CreativeTabHandler.COMBAT, -2.0F, 6.5F, -1.0F, MaterialHandler.SALAMANDASTRON, Faction.FacList.SALAMANDASTRON);
		salamandastron_battleaxe = new ItemBattleAxe("salamandastron_battleaxe", CreativeTabHandler.COMBAT, -2.6F, 10.0F, 0.5F, MaterialHandler.SALAMANDASTRON, Faction.FacList.SALAMANDASTRON);
		salamandastron_longbow = new ItemModBow("salamandastron_longbow", CreativeTabHandler.COMBAT, 1, 3072, Items.ARROW.getClass(), 40.0F, 7.0F, Faction.FacList.SALAMANDASTRON);

		southsward_sword = new ItemCustomSword("southsward_sword", CreativeTabHandler.COMBAT, -2.5F, 7.5F, 0.0F, MaterialHandler.SOUTHSWARD, Faction.FacList.SOUTHSWARD);
		southsward_spear = new ItemSpear("southsward_spear", CreativeTabHandler.COMBAT, -2.7F, 7.0F, 2.0F, MaterialHandler.SOUTHSWARD, Faction.FacList.SOUTHSWARD);
		southsward_pike = new ItemPike("southsward_pike", CreativeTabHandler.COMBAT, -2.9F, 7.0F, 4.0F, MaterialHandler.SOUTHSWARD, Faction.FacList.SOUTHSWARD);
		southsward_mace = new ItemMace("southsward_mace", CreativeTabHandler.COMBAT, -2.9F, 12.5F, 0.0F, MaterialHandler.SOUTHSWARD, Faction.FacList.SOUTHSWARD);
		southsward_hammer = new ItemMace("southsward_hammer", CreativeTabHandler.COMBAT, -2.8F, 10.5F, 0.0F, MaterialHandler.SOUTHSWARD, Faction.FacList.SOUTHSWARD);
		southsward_dagger = new ItemDagger("southsward_dagger", CreativeTabHandler.COMBAT, -2.1F, 5.0F, -2.0F, MaterialHandler.SOUTHSWARD, Faction.FacList.SOUTHSWARD);
		southsward_longbow = new ItemModBow("southsward_longbow", CreativeTabHandler.COMBAT, 1, 1536, Items.ARROW.getClass(), 30.0F, 5.0F, Faction.FacList.SOUTHSWARD);

		riftgard_sword = new ItemCustomSword("riftgard_sword", CreativeTabHandler.COMBAT, -2.4F, 7.0F, 0.0F, MaterialHandler.RIFTGARD, Faction.FacList.RIFTGARD);
		riftgard_broadsword = new ItemCustomSword("riftgard_broadsword", CreativeTabHandler.COMBAT, -2.5F, 7.5F, 0.0F, MaterialHandler.RIFTGARD, Faction.FacList.RIFTGARD);
		riftgard_rapier = new ItemCustomSword("riftgard_rapier", CreativeTabHandler.COMBAT, -2.3F, 6.5F, 0.0F, MaterialHandler.RIFTGARD, Faction.FacList.RIFTGARD);
		riftgard_sabre = new ItemCustomSword("riftgard_sabre", CreativeTabHandler.COMBAT, -2.2F, 6.5F, -0.5F, MaterialHandler.RIFTGARD, Faction.FacList.RIFTGARD);
		riftgard_spear = new ItemSpear("riftgard_spear", CreativeTabHandler.COMBAT, -2.6F, 6.5F, 2.0F, MaterialHandler.RIFTGARD, Faction.FacList.RIFTGARD);
		riftgard_dagger = new ItemDagger("riftgard_dagger", CreativeTabHandler.COMBAT, -2.0F, 4.5F, -2.0F, MaterialHandler.RIFTGARD, Faction.FacList.RIFTGARD);

		northlands_claymore = new ItemCustomSword("northlands_claymore", CreativeTabHandler.COMBAT, -2.5F, 7.0F, 0.5F, MaterialHandler.NORTHLANDS, Faction.FacList.NORTHLANDS);
		northlands_dirk = new ItemCustomSword("northlands_dirk", CreativeTabHandler.COMBAT, -2.1F, 6.0F, -1.5F, MaterialHandler.NORTHLANDS, Faction.FacList.NORTHLANDS);
		northlands_sgian_dhu = new ItemDagger("northlands_sgian_dhu", CreativeTabHandler.COMBAT, -1.8F, 5.0F, -2.5F, MaterialHandler.NORTHLANDS, Faction.FacList.NORTHLANDS);

		guosim_rapier = new ItemCustomSword("guosim_rapier", CreativeTabHandler.COMBAT, -2.2F, 6.0F, -0.5F, MaterialHandler.GUOSIM, Faction.FacList.GUOSIM);
		guosim_paddle = new ItemMace("guosim_paddle", CreativeTabHandler.COMBAT, -2.6F, 7.5F, 2.0F, MaterialHandler.GUOSIM, Faction.FacList.GUOSIM);
		guosim_bow = new ItemModBow("guosim_bow", CreativeTabHandler.COMBAT, 1, 768, Items.ARROW.getClass(), 15.0F, 2.5F, Faction.FacList.GUOSIM);

		rogue_crew_sword = new ItemCustomSword("rogue_crew_sword", CreativeTabHandler.COMBAT, -2.4F, 7.0F, 0.0F, MaterialHandler.ROGUE_CREW, Faction.FacList.ROGUE_CREW);
		rogue_crew_battleaxe = new ItemBattleAxe("rogue_crew_battleaxe", CreativeTabHandler.COMBAT, -2.7F, 10.5F, 0.0F, MaterialHandler.ROGUE_CREW, Faction.FacList.ROGUE_CREW);
		rogue_crew_throwing_axe = new ItemThrowingAxe("rogue_crew_throwing_axe", CreativeTabHandler.COMBAT, -2.5F, 8.0F, 0.0F, MaterialHandler.ROGUE_CREW, Faction.FacList.ROGUE_CREW);
		rogue_crew_longbow = new ItemModBow("rogue_crew_longbow", CreativeTabHandler.COMBAT, 1, 2304, Items.ARROW.getClass(), 35.0F, 6.0F, Faction.FacList.ROGUE_CREW);

		kotir_sword = new ItemCustomSword("kotir_sword", CreativeTabHandler.COMBAT, -2.5F, 7.5F, 0.0F, MaterialHandler.KOTIR, Faction.FacList.VERMIN_MOSSFLOWER);
		kotir_battleaxe = new ItemBattleAxe("kotir_battleaxe", CreativeTabHandler.COMBAT, -2.8F, 10.5F, 0.0F, MaterialHandler.KOTIR, Faction.FacList.VERMIN_MOSSFLOWER);
		kotir_dagger = new ItemDagger("kotir_dagger", CreativeTabHandler.COMBAT, -2.1F, 5.0F, -2.0F, MaterialHandler.KOTIR, Faction.FacList.VERMIN_MOSSFLOWER);
		kotir_halberd = new ItemHalberd("kotir_halberd", CreativeTabHandler.COMBAT, -2.9F, 10.5F, 2.0F, MaterialHandler.KOTIR, Faction.FacList.VERMIN_MOSSFLOWER);
		kotir_spear = new ItemSpear("kotir_spear", CreativeTabHandler.COMBAT, -2.7F, 7.5F, 2.0F, MaterialHandler.KOTIR, Faction.FacList.VERMIN_MOSSFLOWER);
		kotir_pike = new ItemPike("kotir_pike", CreativeTabHandler.COMBAT, -2.9F, 7.5F, 4.0F, MaterialHandler.KOTIR, Faction.FacList.VERMIN_MOSSFLOWER);

		redwall_axe = new ItemModAxe("redwall_axe", CreativeTabHandler.COMBAT, -2.5F, 5.0F, MaterialHandler.REDWALL);
		redwall_pickaxe = new ItemModPickaxe("redwall_pickaxe", CreativeTabHandler.COMBAT, MaterialHandler.REDWALL);
		redwall_spade = new ItemModSpade("redwall_spade", CreativeTabHandler.COMBAT, MaterialHandler.REDWALL);
		redwall_hoe = new ItemModHoe("redwall_hoe", CreativeTabHandler.COMBAT, MaterialHandler.REDWALL);
		redwall_scythe = new ItemScythe("redwall_scythe", CreativeTabHandler.COMBAT, -2.7F, 6.5F, 1.0F, MaterialHandler.REDWALL, Faction.FacList.REDWALL);
	}

	public static void register(RegistryEvent.Register<Item> event) {
		registerItem(event, maple_door);
		registerItem(event, elm_door);
		registerItem(event, ash_door);
		registerItem(event, pine_door);
		registerItem(event, fir_door);
		registerItem(event, larch_door);
		registerItem(event, hornbeam_door);
		registerItem(event, beech_door);
		registerItem(event, yew_door);
		registerItem(event, alder_door);
		registerItem(event, aspen_door);
		registerItem(event, willow_door);
		registerItem(event, apple_door);
		registerItem(event, plum_door);
		registerItem(event, southsward_hidden_door);

		registerItem(event, weapon_rack);

		registerItem(event, strawberry);
		registerItem(event, blueberry);
		registerItem(event, blackberry);
		registerItem(event, raspberry);
		registerItem(event, elderberry);
		registerItem(event, wildberry);

		registerItem(event, plum);
		registerItem(event, damson);

		registerItem(event, strawberry_seeds);
		registerItem(event, blueberry_seeds);
		registerItem(event, blackberry_seeds);
		registerItem(event, raspberry_seeds);
		registerItem(event, elderberry_seeds);
		registerItem(event, wildberry_seeds);

		registerItem(event, trout);
		registerItem(event, trout_cooked);
		registerItem(event, bass);
		registerItem(event, bass_cooked);
		registerItem(event, perch);
		registerItem(event, perch_cooked);
		registerItem(event, grayling);
		registerItem(event, grayling_cooked);

		registerItem(event, corn);
		registerItem(event, turnip);

		registerItem(event, onion);
		registerItem(event, rice);
		registerItem(event, peas);
		registerItem(event, yam);
		registerItem(event, beans);

		registerItem(event, grapes);

		registerItem(event, rice_bowl);

		registerItem(event, drink_bottle);

		registerItem(event, mug_empty);
		registerItem(event, mug_drink);

		registerItem(event, bowl_drink);

		registerItem(event, cornstalk);

		registerItem(event, water_reeds);
		registerItem(event, water_reeds_dried);

		registerItem(event, tin_ingot);
		registerItem(event, copper_ingot);
		registerItem(event, bronze_ingot);

		registerItem(event, tin_nugget);
		registerItem(event, copper_nugget);
		registerItem(event, bronze_nugget);

		registerItem(event, sword_mtw);

		registerItem(event, golden_sword);
		registerItem(event, golden_broadsword);
		registerItem(event, golden_rapier);
		registerItem(event, golden_scimitar);
		registerItem(event, golden_halberd);
		registerItem(event, golden_spear);
		registerItem(event, golden_pike);
		registerItem(event, golden_mace);
		registerItem(event, golden_dagger);
		registerItem(event, golden_battleaxe);
		registerItem(event, golden_throwing_axe);
		registerItem(event, golden_scythe);

		registerItem(event, stone_spear);
		registerItem(event, stone_mace);
		registerItem(event, stone_throwing_axe);

		registerItem(event, bronze_halberd);
		registerItem(event, bronze_spear);
		registerItem(event, bronze_pike);
		registerItem(event, bronze_mace);
		registerItem(event, bronze_dagger);
		registerItem(event, bronze_battleaxe);
		registerItem(event, bronze_throwing_axe);
		registerItem(event, bronze_axe);
		registerItem(event, bronze_pickaxe);
		registerItem(event, bronze_spade);
		registerItem(event, bronze_hoe);
		registerItem(event, bronze_scythe);

		registerItem(event, iron_sword);
		registerItem(event, iron_broadsword);
		registerItem(event, iron_rapier);
		registerItem(event, iron_scimitar);
		registerItem(event, iron_halberd);
		registerItem(event, iron_spear);
		registerItem(event, iron_pike);
		registerItem(event, iron_mace);
		registerItem(event, iron_dagger);
		registerItem(event, iron_battleaxe);
		registerItem(event, iron_throwing_axe);
		registerItem(event, iron_scythe);

		registerItem(event, salamandastron_sword);
		registerItem(event, salamandastron_claymore);
		registerItem(event, salamandastron_broadsword);
		registerItem(event, salamandastron_rapier);
		registerItem(event, salamandastron_sabre);
		registerItem(event, salamandastron_spear);
		registerItem(event, salamandastron_lance);
		registerItem(event, salamandastron_pike);
		registerItem(event, salamandastron_dagger);
		registerItem(event, salamandastron_dirk);
		registerItem(event, salamandastron_battleaxe);
		registerItem(event, salamandastron_longbow);

		registerItem(event, southsward_sword);
		registerItem(event, southsward_spear);
		registerItem(event, southsward_pike);
		registerItem(event, southsward_mace);
		registerItem(event, southsward_hammer);
		registerItem(event, southsward_dagger);
		registerItem(event, southsward_longbow);

		registerItem(event, riftgard_sword);
		registerItem(event, riftgard_broadsword);
		registerItem(event, riftgard_rapier);
		registerItem(event, riftgard_sabre);
		registerItem(event, riftgard_spear);
		registerItem(event, riftgard_dagger);

		registerItem(event, northlands_claymore);
		registerItem(event, northlands_dirk);
		registerItem(event, northlands_sgian_dhu);

		registerItem(event, guosim_rapier);
		registerItem(event, guosim_paddle);
		registerItem(event, guosim_bow);

		registerItem(event, rogue_crew_sword);
		registerItem(event, rogue_crew_battleaxe);
		registerItem(event, rogue_crew_throwing_axe);
		registerItem(event, rogue_crew_longbow);

		registerItem(event, kotir_sword);
		registerItem(event, kotir_battleaxe);
		registerItem(event, kotir_dagger);
		registerItem(event, kotir_halberd);
		registerItem(event, kotir_spear);
		registerItem(event, kotir_pike);

		registerItem(event, redwall_axe);
		registerItem(event, redwall_pickaxe);
		registerItem(event, redwall_spade);
		registerItem(event, redwall_hoe);
		registerItem(event, redwall_scythe);
	}

	public static void registerRenders() {
		registerRender(maple_door);
		registerRender(ash_door);
		registerRender(elm_door);
		registerRender(pine_door);
		registerRender(fir_door);
		registerRender(larch_door);
		registerRender(hornbeam_door);
		registerRender(beech_door);
		registerRender(yew_door);
		registerRender(alder_door);
		registerRender(aspen_door);
		registerRender(willow_door);
		registerRender(apple_door);
		registerRender(plum_door);
		registerRender(southsward_hidden_door);

		registerRender(weapon_rack);

		registerRender(strawberry);
		registerRender(blueberry);
		registerRender(blackberry);
		registerRender(raspberry);
		registerRender(elderberry);
		registerRender(wildberry);

		registerRender(plum);
		registerRender(damson);

		registerRender(strawberry_seeds);
		registerRender(blueberry_seeds);
		registerRender(blackberry_seeds);
		registerRender(raspberry_seeds);
		registerRender(elderberry_seeds);
		registerRender(wildberry_seeds);

		registerRender(trout);
		registerRender(trout_cooked);
		registerRender(bass);
		registerRender(bass_cooked);
		registerRender(perch);
		registerRender(perch_cooked);
		registerRender(grayling);
		registerRender(grayling_cooked);

		registerRender(corn);
		registerRender(turnip);

		registerRender(onion);
		registerRender(rice);
		registerRender(peas);
		registerRender(beans);
		registerRender(yam);

		registerRender(grapes);

		registerRender(rice_bowl);

		registerRender(drink_bottle);

		registerRender(mug_empty);
		registerRender(mug_drink);

		registerRender(bowl_drink);

		registerRender(cornstalk);

		registerRender(water_reeds);
		registerRender(water_reeds_dried);

		registerRender(tin_ingot);
		registerRender(copper_ingot);
		registerRender(bronze_ingot);

		registerRender(tin_nugget);
		registerRender(copper_nugget);
		registerRender(bronze_nugget);

		registerRender(sword_mtw);

		registerRender(golden_sword);
		registerRender(golden_broadsword);
		registerRender(golden_rapier);
		registerRender(golden_scimitar);
		registerRender(golden_halberd);
		registerRender(golden_spear);
		registerRender(golden_pike);
		registerRender(golden_mace);
		registerRender(golden_dagger);
		registerRender(golden_battleaxe);
		registerRender(golden_throwing_axe);
		registerRender(golden_scythe);

		registerRender(stone_spear);
		registerRender(stone_mace);
		registerRender(stone_throwing_axe);

		registerRender(bronze_halberd);
		registerRender(bronze_spear);
		registerRender(bronze_pike);
		registerRender(bronze_mace);
		registerRender(bronze_dagger);
		registerRender(bronze_battleaxe);
		registerRender(bronze_throwing_axe);
		registerRender(bronze_axe);
		registerRender(bronze_pickaxe);
		registerRender(bronze_spade);
		registerRender(bronze_hoe);
		registerRender(bronze_scythe);

		registerRender(iron_sword);
		registerRender(iron_broadsword);
		registerRender(iron_rapier);
		registerRender(iron_scimitar);
		registerRender(iron_halberd);
		registerRender(iron_spear);
		registerRender(iron_pike);
		registerRender(iron_mace);
		registerRender(iron_dagger);
		registerRender(iron_battleaxe);
		registerRender(iron_throwing_axe);
		registerRender(iron_scythe);

		registerRender(salamandastron_sword);
		registerRender(salamandastron_claymore);
		registerRender(salamandastron_broadsword);
		registerRender(salamandastron_rapier);
		registerRender(salamandastron_sabre);
		registerRender(salamandastron_spear);
		registerRender(salamandastron_lance);
		registerRender(salamandastron_pike);
		registerRender(salamandastron_dagger);
		registerRender(salamandastron_dirk);
		registerRender(salamandastron_battleaxe);
		registerRender(salamandastron_longbow);

		registerRender(southsward_sword);
		registerRender(southsward_spear);
		registerRender(southsward_pike);
		registerRender(southsward_mace);
		registerRender(southsward_hammer);
		registerRender(southsward_dagger);
		registerRender(southsward_longbow);

		registerRender(riftgard_sword);
		registerRender(riftgard_broadsword);
		registerRender(riftgard_rapier);
		registerRender(riftgard_sabre);
		registerRender(riftgard_spear);
		registerRender(riftgard_dagger);

		registerRender(northlands_claymore);
		registerRender(northlands_dirk);
		registerRender(northlands_sgian_dhu);

		registerRender(guosim_rapier);
		registerRender(guosim_paddle);
		registerRender(guosim_bow);

		registerRender(rogue_crew_sword);
		registerRender(rogue_crew_battleaxe);
		registerRender(rogue_crew_throwing_axe);
		registerRender(rogue_crew_longbow);

		registerRender(kotir_sword);
		registerRender(kotir_battleaxe);
		registerRender(kotir_dagger);
		registerRender(kotir_halberd);
		registerRender(kotir_spear);
		registerRender(kotir_pike);

		registerRender(redwall_axe);
		registerRender(redwall_pickaxe);
		registerRender(redwall_spade);
		registerRender(redwall_hoe);
		registerRender(redwall_scythe);
	}

	public static void registerItem(RegistryEvent.Register<Item> event, Item item) {
		event.getRegistry().register(item);
	}

	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
