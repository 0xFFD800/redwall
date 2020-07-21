package com.bob.redwall.init;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.entity.capabilities.factions.FactionCap;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.items.brewing.Drink;
import com.bob.redwall.items.brewing.ItemDrinkVessel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeRepairItem;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

public class CraftingHandler {
	public static void register(RegistryEvent.Register<IRecipe> event) {
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.WOODEN_SWORD.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.STONE_SWORD.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.IRON_SWORD.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.GOLDEN_SWORD.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.DIAMOND_SWORD.getRegistryName());

		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.IRON_PICKAXE.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.IRON_SHOVEL.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.IRON_HOE.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.IRON_AXE.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Blocks.IRON_BLOCK.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.IRON_NUGGET.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.IRON_INGOT.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Blocks.ANVIL.getRegistryName());

		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.GOLDEN_PICKAXE.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.GOLDEN_SHOVEL.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.GOLDEN_HOE.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.GOLDEN_AXE.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Blocks.GOLD_BLOCK.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.GOLD_NUGGET.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.GOLD_INGOT.getRegistryName());

		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.DIAMOND_PICKAXE.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.DIAMOND_SHOVEL.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.DIAMOND_HOE.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.DIAMOND_AXE.getRegistryName());

		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.IRON_HELMET.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.IRON_CHESTPLATE.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.IRON_LEGGINGS.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.IRON_BOOTS.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.GOLDEN_HELMET.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.GOLDEN_CHESTPLATE.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.GOLDEN_LEGGINGS.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.GOLDEN_BOOTS.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.DIAMOND_HELMET.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.DIAMOND_CHESTPLATE.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.DIAMOND_LEGGINGS.getRegistryName());
		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.DIAMOND_BOOTS.getRegistryName());

		((IForgeRegistryModifiable<IRecipe>) event.getRegistry()).remove(Items.BREAD.getRegistryName());

		FurnaceRecipes.instance().getSmeltingList().clear();
		GameRegistry.addSmelting(Blocks.SAND, new ItemStack(Blocks.GLASS), 0.1F);
		GameRegistry.addSmelting(Blocks.COBBLESTONE, new ItemStack(Blocks.STONE), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STONEBRICK, 1, BlockStoneBrick.DEFAULT_META), new ItemStack(Blocks.STONEBRICK, 1, BlockStoneBrick.CRACKED_META), 0.1F);
		GameRegistry.addSmelting(Items.CLAY_BALL, new ItemStack(Items.BRICK), 0.3F);
		GameRegistry.addSmelting(Blocks.CLAY, new ItemStack(Blocks.HARDENED_CLAY), 0.35F);
		GameRegistry.addSmelting(Blocks.CACTUS, new ItemStack(Items.DYE, 1, EnumDyeColor.GREEN.getDyeDamage()), 0.2F);
		GameRegistry.addSmelting(Blocks.LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
		GameRegistry.addSmelting(Blocks.LOG2, new ItemStack(Items.COAL, 1, 1), 0.15F);
		GameRegistry.addSmelting(new ItemStack(Blocks.SPONGE, 1, 1), new ItemStack(Blocks.SPONGE, 1, 0), 0.15F);
		GameRegistry.addSmelting(Items.CHORUS_FRUIT, new ItemStack(Items.CHORUS_FRUIT_POPPED), 0.1F);

		for (ItemFishFood.FishType itemfishfood$fishtype : ItemFishFood.FishType.values()) {
			if (itemfishfood$fishtype.canCook()) {
				GameRegistry.addSmelting(new ItemStack(Items.FISH, 1, itemfishfood$fishtype.getMetadata()), new ItemStack(Items.COOKED_FISH, 1, itemfishfood$fishtype.getMetadata()), 0.35F);
			}
		}

		GameRegistry.addSmelting(Items.CHAINMAIL_HELMET, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.CHAINMAIL_CHESTPLATE, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.CHAINMAIL_LEGGINGS, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.CHAINMAIL_BOOTS, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.IRON_PICKAXE, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.IRON_SHOVEL, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.IRON_AXE, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.IRON_HOE, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.IRON_SWORD, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.IRON_HELMET, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.IRON_CHESTPLATE, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.IRON_LEGGINGS, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.IRON_BOOTS, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.IRON_HORSE_ARMOR, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.GOLDEN_PICKAXE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.GOLDEN_SHOVEL, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.GOLDEN_AXE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.GOLDEN_HOE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.GOLDEN_SWORD, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.GOLDEN_HELMET, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.GOLDEN_CHESTPLATE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.GOLDEN_LEGGINGS, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.GOLDEN_BOOTS, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		GameRegistry.addSmelting(Items.GOLDEN_HORSE_ARMOR, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.WHITE.getMetadata()), new ItemStack(Blocks.WHITE_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.ORANGE.getMetadata()), new ItemStack(Blocks.ORANGE_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.MAGENTA.getMetadata()), new ItemStack(Blocks.MAGENTA_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.LIGHT_BLUE.getMetadata()), new ItemStack(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.YELLOW.getMetadata()), new ItemStack(Blocks.YELLOW_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.LIME.getMetadata()), new ItemStack(Blocks.LIME_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.PINK.getMetadata()), new ItemStack(Blocks.PINK_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.GRAY.getMetadata()), new ItemStack(Blocks.GRAY_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.SILVER.getMetadata()), new ItemStack(Blocks.SILVER_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.CYAN.getMetadata()), new ItemStack(Blocks.CYAN_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.PURPLE.getMetadata()), new ItemStack(Blocks.PURPLE_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BLUE.getMetadata()), new ItemStack(Blocks.BLUE_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BROWN.getMetadata()), new ItemStack(Blocks.BROWN_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.GREEN.getMetadata()), new ItemStack(Blocks.GREEN_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.RED.getMetadata()), new ItemStack(Blocks.RED_GLAZED_TERRACOTTA), 0.1F);
		GameRegistry.addSmelting(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BLACK.getMetadata()), new ItemStack(Blocks.BLACK_GLAZED_TERRACOTTA), 0.1F);
	}

	public static class SmithingGeneric {
		private static final SmithingGeneric INSTANCE = new SmithingGeneric();
		protected final List<LeveledRecipe> recipes = Lists.<LeveledRecipe>newArrayList();

		public static SmithingGeneric getInstance() {
			return INSTANCE;
		}

		private SmithingGeneric() {
			this.recipes.add(new LeveledRecipe(new RecipeRepairItem(), 0));

			this.addRecipe(0, new ItemStack(ItemHandler.bronze_dagger), new Object[] { "#", "X", '#', ItemHandler.bronze_ingot, 'X', Items.STICK });
			this.addRecipe(0, new ItemStack(ItemHandler.bronze_spear), new Object[] { "  #", " X ", "X  ", '#', ItemHandler.bronze_ingot, 'X', Items.STICK });
			this.addRecipe(0, new ItemStack(ItemHandler.bronze_pike), new Object[] { " # ", " # ", "XXX", '#', ItemHandler.bronze_ingot, 'X', Items.STICK });
			this.addRecipe(1, new ItemStack(ItemHandler.bronze_halberd), new Object[] { " ##", " X#", "X  ", '#', ItemHandler.bronze_ingot, 'X', Items.STICK });
			this.addRecipe(1, new ItemStack(ItemHandler.bronze_throwing_axe), new Object[] { "###", "#X ", " X ", '#', ItemHandler.bronze_ingot, 'X', Items.STICK });
			this.addRecipe(1, new ItemStack(ItemHandler.bronze_battleaxe), new Object[] { "###", "#X#", " X ", '#', ItemHandler.bronze_ingot, 'X', Items.STICK });
			this.addRecipe(0, new ItemStack(ItemHandler.bronze_mace), new Object[] { "##", "##", "X ", '#', ItemHandler.bronze_ingot, 'X', Items.STICK });

			this.addRecipe(1, new ItemStack(ItemHandler.bronze_axe), new Object[] { "##", "X#", "X ", '#', ItemHandler.bronze_ingot, 'X', Items.STICK });
			this.addRecipe(0, new ItemStack(ItemHandler.bronze_pickaxe), new Object[] { "###", " X ", " X ", '#', ItemHandler.bronze_ingot, 'X', Items.STICK });
			this.addRecipe(0, new ItemStack(ItemHandler.bronze_spade), new Object[] { "#", "X", "X", '#', ItemHandler.bronze_ingot, 'X', Items.STICK });
			this.addRecipe(0, new ItemStack(ItemHandler.bronze_hoe), new Object[] { "##", "X ", "X ", '#', ItemHandler.bronze_ingot, 'X', Items.STICK });
			this.addRecipe(1, new ItemStack(ItemHandler.bronze_scythe), new Object[] { "##", "X#", "X#", '#', ItemHandler.bronze_ingot, 'X', Items.STICK });

			this.addRecipe(3, new ItemStack(ArmorHandler.bronze_helmet), new Object[] { "###", "# #", '#', ItemHandler.bronze_ingot });
			this.addRecipe(3, new ItemStack(ArmorHandler.bronze_chest), new Object[] { "# #", "###", "###", '#', ItemHandler.bronze_ingot });
			this.addRecipe(3, new ItemStack(ArmorHandler.bronze_legs), new Object[] { "###", "# #", "# #", '#', ItemHandler.bronze_ingot });
			this.addRecipe(3, new ItemStack(ArmorHandler.bronze_boots), new Object[] { "# #", "# #", '#', ItemHandler.bronze_ingot });

			this.addRecipe(3, new ItemStack(Items.IRON_AXE), new Object[] { "##", "X#", "X ", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(2, new ItemStack(Items.IRON_PICKAXE), new Object[] { "###", " X ", " X ", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(2, new ItemStack(Items.IRON_SHOVEL), new Object[] { "#", "X", "X", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(2, new ItemStack(Items.IRON_HOE), new Object[] { "##", "X ", "X ", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(3, new ItemStack(ItemHandler.iron_scythe), new Object[] { "##", "X#", "X#", '#', Items.IRON_INGOT, 'X', Items.STICK });

			this.addRecipe(4, new ItemStack(ItemHandler.iron_sword), new Object[] { "#", "#", "X", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(4, new ItemStack(ItemHandler.iron_rapier), new Object[] { " #", " #", "X ", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(4, new ItemStack(ItemHandler.iron_broadsword), new Object[] { " #", "##", "X ", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(4, new ItemStack(ItemHandler.iron_scimitar), new Object[] { " #", "# ", "X ", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(2, new ItemStack(ItemHandler.iron_dagger), new Object[] { "#", "X", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(2, new ItemStack(ItemHandler.iron_spear), new Object[] { "  #", " X ", "X  ", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(2, new ItemStack(ItemHandler.iron_pike), new Object[] { " # ", " # ", "XXX", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(3, new ItemStack(ItemHandler.iron_halberd), new Object[] { " ##", " X#", "X  ", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(3, new ItemStack(ItemHandler.iron_throwing_axe), new Object[] { "###", "#X ", " X ", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(3, new ItemStack(ItemHandler.iron_battleaxe), new Object[] { "###", "#X#", " X ", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(2, new ItemStack(ItemHandler.iron_mace), new Object[] { "##", "##", "X ", '#', Items.IRON_INGOT, 'X', Items.STICK });

			this.addRecipe(5, new ItemStack(Items.IRON_HELMET), new Object[] { "###", "# #", '#', Items.IRON_INGOT });
			this.addRecipe(5, new ItemStack(Items.IRON_CHESTPLATE), new Object[] { "# #", "###", "###", '#', Items.IRON_INGOT });
			this.addRecipe(5, new ItemStack(Items.IRON_LEGGINGS), new Object[] { "###", "# #", "# #", '#', Items.IRON_INGOT });
			this.addRecipe(5, new ItemStack(Items.IRON_BOOTS), new Object[] { "# #", "# #", '#', Items.IRON_INGOT });

			this.addRecipe(2, new ItemStack(Items.GOLDEN_AXE), new Object[] { "##", "X#", "X ", '#', Items.GOLD_INGOT, 'X', Items.STICK });
			this.addRecipe(1, new ItemStack(Items.GOLDEN_PICKAXE), new Object[] { "###", " X ", " X ", '#', Items.GOLD_INGOT, 'X', Items.STICK });
			this.addRecipe(1, new ItemStack(Items.GOLDEN_SHOVEL), new Object[] { "#", "X", "X", '#', Items.GOLD_INGOT, 'X', Items.STICK });
			this.addRecipe(1, new ItemStack(Items.GOLDEN_HOE), new Object[] { "##", "X ", "X ", '#', Items.GOLD_INGOT, 'X', Items.STICK });
			this.addRecipe(2, new ItemStack(ItemHandler.golden_scythe), new Object[] { "##", "X#", "X#", '#', Items.GOLD_INGOT, 'X', Items.STICK });

			this.addRecipe(3, new ItemStack(ItemHandler.golden_sword), new Object[] { "#", "#", "X", '#', Items.GOLD_INGOT, 'X', Items.STICK });
			this.addRecipe(3, new ItemStack(ItemHandler.golden_rapier), new Object[] { " #", " #", "X ", '#', Items.GOLD_INGOT, 'X', Items.STICK });
			this.addRecipe(3, new ItemStack(ItemHandler.golden_broadsword), new Object[] { " #", "##", "X ", '#', Items.GOLD_INGOT, 'X', Items.STICK });
			this.addRecipe(3, new ItemStack(ItemHandler.golden_scimitar), new Object[] { " #", "# ", "X ", '#', Items.GOLD_INGOT, 'X', Items.STICK });
			this.addRecipe(1, new ItemStack(ItemHandler.golden_dagger), new Object[] { "#", "X", '#', Items.GOLD_INGOT, 'X', Items.STICK });
			this.addRecipe(1, new ItemStack(ItemHandler.golden_spear), new Object[] { "  #", " X ", "X  ", '#', Items.GOLD_INGOT, 'X', Items.STICK });
			this.addRecipe(1, new ItemStack(ItemHandler.golden_pike), new Object[] { " # ", " # ", "XXX", '#', Items.GOLD_INGOT, 'X', Items.STICK });
			this.addRecipe(2, new ItemStack(ItemHandler.golden_halberd), new Object[] { " ##", " X#", "X  ", '#', Items.GOLD_INGOT, 'X', Items.STICK });
			this.addRecipe(2, new ItemStack(ItemHandler.golden_throwing_axe), new Object[] { "###", "#X ", " X ", '#', Items.GOLD_INGOT, 'X', Items.STICK });
			this.addRecipe(2, new ItemStack(ItemHandler.golden_battleaxe), new Object[] { "###", "#X#", " X ", '#', Items.GOLD_INGOT, 'X', Items.STICK });
			this.addRecipe(1, new ItemStack(ItemHandler.golden_mace), new Object[] { "##", "##", "X ", '#', Items.GOLD_INGOT, 'X', Items.STICK });

			this.addRecipe(4, new ItemStack(Items.GOLDEN_HELMET), new Object[] { "###", "# #", '#', Items.GOLD_INGOT });
			this.addRecipe(4, new ItemStack(Items.GOLDEN_CHESTPLATE), new Object[] { "# #", "###", "###", '#', Items.GOLD_INGOT });
			this.addRecipe(4, new ItemStack(Items.GOLDEN_LEGGINGS), new Object[] { "###", "# #", "# #", '#', Items.GOLD_INGOT });
			this.addRecipe(4, new ItemStack(Items.GOLDEN_BOOTS), new Object[] { "# #", "# #", '#', Items.GOLD_INGOT });

			/*
			 * NBTTagCompound nbt = new NBTTagCompound(); NBTTagCompound display = new NBTTagCompound(); 
			 * //display.setString("Name", String.valueOf(new Random().nextDouble())); 
			 * NBTTagList lore = new NBTTagList();
			 * lore.appendTag(new NBTTagString(I18n.format("key.canLock", new Object[0])));
			 * display.setTag("Lore", lore); 
			 * nbt.setTag("display", display); 
			 * ItemStack itemstack = new ItemStack(ItemHandler.key, 1, 0);
			 * itemstack.setTagCompound(nbt); 
			 * this.addRecipe(itemstack.copy(), new Object[] {" # ", "# #", " # ", '#', ItemHandler.heated_iron_ingot});
			 */

			Collections.sort(this.recipes, new Comparator<LeveledRecipe>() {
				@Override
				public int compare(LeveledRecipe p_compare_1_, LeveledRecipe p_compare_2_) {
					return p_compare_1_.recipe instanceof ShapelessRecipes && p_compare_2_.recipe instanceof ShapedRecipes ? 1 : (p_compare_2_.recipe instanceof ShapelessRecipes && p_compare_1_.recipe instanceof ShapedRecipes ? -1 : p_compare_1_.reqLevel > p_compare_2_.reqLevel ? 1 : p_compare_1_.reqLevel < p_compare_2_.reqLevel ? -1 : 0);
				}
			});
		}

		public ShapedRecipes addRecipe(int reqLevel, ItemStack stack, Object... recipeComponents) {
			String s = "";
			int i = 0;
			int j = 0;
			int k = 0;

			if (recipeComponents[i] instanceof String[]) {
				String[] astring = (String[]) ((String[]) recipeComponents[i++]);

				for (String s2 : astring) {
					++k;
					j = s2.length();
					s = s + s2;
				}
			} else {
				while (recipeComponents[i] instanceof String) {
					String s1 = (String) recipeComponents[i++];
					++k;
					j = s1.length();
					s = s + s1;
				}
			}

			Map<Character, ItemStack> map;

			for (map = Maps.<Character, ItemStack>newHashMap(); i < recipeComponents.length; i += 2) {
				Character character = (Character) recipeComponents[i];
				ItemStack itemstack = ItemStack.EMPTY;

				if (recipeComponents[i + 1] instanceof Item) {
					itemstack = new ItemStack((Item) recipeComponents[i + 1]);
				} else if (recipeComponents[i + 1] instanceof Block) {
					itemstack = new ItemStack((Block) recipeComponents[i + 1], 1, 32767);
				} else if (recipeComponents[i + 1] instanceof ItemStack) {
					itemstack = (ItemStack) recipeComponents[i + 1];
				}

				map.put(character, itemstack);
			}

			NonNullList<Ingredient> aitemstack = NonNullList.create();

			for (int l = 0; l < j * k; ++l) {
				char c0 = s.charAt(l);

				if (map.containsKey(Character.valueOf(c0))) {
					aitemstack.add(l, Ingredient.fromStacks(((ItemStack) map.get(Character.valueOf(c0))).copy()));
				} else {
					aitemstack.add(l, Ingredient.EMPTY);
				}
			}

			ShapedRecipes shapedrecipes = new ShapedRecipes("", j, k, aitemstack, stack);
			this.recipes.add(new LeveledRecipe(shapedrecipes, reqLevel));
			return shapedrecipes;
		}

		public void addShapelessRecipe(int reqLevel, ItemStack stack, Object... recipeComponents) {
			NonNullList<Ingredient> list = NonNullList.create();

			for (Object object : recipeComponents) {
				if (object instanceof ItemStack) {
					list.add(Ingredient.fromStacks(((ItemStack) object).copy()));
				} else if (object instanceof Item) {
					list.add(Ingredient.fromStacks(new ItemStack((Item) object)));
				} else {
					if (!(object instanceof Block)) {
						throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
					}

					list.add(Ingredient.fromStacks(new ItemStack((Block) object)));
				}
			}

			this.recipes.add(new LeveledRecipe(new ShapelessRecipes("", stack, list), reqLevel));
		}

		public void addRecipe(LeveledRecipe recipe) {
			this.recipes.add(recipe);
		}

		public ItemStack findMatchingRecipe(EntityPlayer player, InventoryCrafting craftMatrix, World worldIn) {
			for (LeveledRecipe irecipe : this.recipes) {
				if (irecipe.recipe.matches(craftMatrix, worldIn) && RedwallUtils.getFacStatLevel(player, Faction.FacList.GENERIC, FactionCap.FacStatType.SMITH) >= irecipe.reqLevel) {
					return irecipe.recipe.getCraftingResult(craftMatrix);
				}
			}

			return ItemStack.EMPTY;
		}

		public NonNullList<ItemStack> getRemainingItems(InventoryCrafting craftMatrix, World worldIn) {
			for (LeveledRecipe irecipe : this.recipes) {
				if (irecipe.recipe.matches(craftMatrix, worldIn)) {
					return irecipe.recipe.getRemainingItems(craftMatrix);
				}
			}

			NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(craftMatrix.getSizeInventory(), ItemStack.EMPTY);

			for (int i = 0; i < nonnulllist.size(); ++i) {
				nonnulllist.set(i, craftMatrix.getStackInSlot(i));
			}

			return nonnulllist;
		}

		public List<LeveledRecipe> getRecipeList() {
			return this.recipes;
		}
	}

	public static class SmithingRedwall extends SmithingGeneric {
		private static final SmithingRedwall INSTANCE = new SmithingRedwall();

		public static SmithingRedwall getInstance() {
			return INSTANCE;
		}

		private SmithingRedwall() {
			this.recipes.add(new LeveledRecipe(new RecipeRepairItem(), 0));

			this.addRecipe(2, new ItemStack(ItemHandler.redwall_axe), new Object[] { "##", "X#", "X ", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(1, new ItemStack(ItemHandler.redwall_pickaxe), new Object[] { "###", " X ", " X ", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(0, new ItemStack(ItemHandler.redwall_spade), new Object[] { "#", "X", "X", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(1, new ItemStack(ItemHandler.redwall_hoe), new Object[] { "##", "X ", "X ", '#', Items.IRON_INGOT, 'X', Items.STICK });
			this.addRecipe(2, new ItemStack(ItemHandler.redwall_scythe), new Object[] { "##", "X#", "X#", '#', Items.IRON_INGOT, 'X', Items.STICK });

			this.addRecipe(0, new ItemStack(BlockHandler.brewing_redwall), new Object[] { "VVV", "###", "VVV", '#', Items.IRON_INGOT, 'V', Items.STICK });

			Collections.sort(this.recipes, new Comparator<LeveledRecipe>() {
				@Override
				public int compare(LeveledRecipe p_compare_1_, LeveledRecipe p_compare_2_) {
					return p_compare_1_.recipe instanceof ShapelessRecipes && p_compare_2_.recipe instanceof ShapedRecipes ? 1 : (p_compare_2_.recipe instanceof ShapelessRecipes && p_compare_1_.recipe instanceof ShapedRecipes ? -1 : p_compare_1_.reqLevel > p_compare_2_.reqLevel ? 1 : p_compare_1_.reqLevel < p_compare_2_.reqLevel ? -1 : 0);
				}
			});
		}

		public ItemStack findMatchingRecipe(EntityPlayer player, InventoryCrafting craftMatrix, World worldIn) {
			for (LeveledRecipe irecipe : this.recipes) {
				if (irecipe.recipe.matches(craftMatrix, worldIn) && RedwallUtils.getFacStatLevel(player, Faction.FacList.REDWALL, FactionCap.FacStatType.SMITH) >= irecipe.reqLevel) {
					return irecipe.recipe.getCraftingResult(craftMatrix);
				}
			}

			return ItemStack.EMPTY;
		}
	}

	public static class Smeltery {
		private static final Smeltery INSTANCE = new Smeltery();
		private final List<IRecipe> recipes = Lists.<IRecipe>newArrayList();

		public static Smeltery getInstance() {
			return INSTANCE;
		}

		private Smeltery() {
			this.addShapelessRecipe(new ItemStack(ItemHandler.copper_ingot), new Object[] { BlockHandler.copper_ore });
			this.addRecipe(new ItemStack(BlockHandler.copper_block), new Object[] { "###", "###", "###", '#', ItemHandler.copper_ingot });
			this.addShapelessRecipe(new ItemStack(ItemHandler.copper_nugget, 9), new Object[] { ItemHandler.copper_ingot });
			this.addRecipe(new ItemStack(ItemHandler.copper_ingot), new Object[] { "###", "###", "###", '#', ItemHandler.copper_nugget });

			this.addShapelessRecipe(new ItemStack(ItemHandler.tin_ingot), new Object[] { BlockHandler.tin_ore });
			this.addRecipe(new ItemStack(BlockHandler.tin_block), new Object[] { "###", "###", "###", '#', ItemHandler.tin_ingot });
			this.addShapelessRecipe(new ItemStack(ItemHandler.tin_nugget, 9), new Object[] { ItemHandler.tin_ingot });
			this.addRecipe(new ItemStack(ItemHandler.tin_ingot), new Object[] { "###", "###", "###", '#', ItemHandler.tin_nugget });

			this.addShapelessRecipe(new ItemStack(ItemHandler.bronze_ingot), new Object[] { ItemHandler.copper_ingot, ItemHandler.tin_nugget, new ItemStack(Items.COAL, 1, 1) });
			this.addRecipe(new ItemStack(BlockHandler.bronze_block), new Object[] { "###", "###", "###", '#', ItemHandler.bronze_ingot });
			this.addShapelessRecipe(new ItemStack(ItemHandler.bronze_nugget, 9), new Object[] { ItemHandler.bronze_ingot });
			this.addRecipe(new ItemStack(ItemHandler.bronze_ingot), new Object[] { "###", "###", "###", '#', ItemHandler.bronze_nugget });

			this.addShapelessRecipe(new ItemStack(Items.IRON_INGOT), new Object[] { Blocks.IRON_ORE, new ItemStack(Items.COAL, 1, 1) });
			this.addRecipe(new ItemStack(Blocks.IRON_BLOCK), new Object[] { "###", "###", "###", '#', Items.IRON_INGOT });
			this.addShapelessRecipe(new ItemStack(Items.IRON_NUGGET, 9), new Object[] { Items.IRON_INGOT });
			this.addRecipe(new ItemStack(Items.IRON_NUGGET), new Object[] { "###", "###", "###", '#', Items.IRON_NUGGET });
			this.addRecipe(new ItemStack(Blocks.ANVIL), new Object[] { "###", " # ", "###", '#', Items.IRON_INGOT });

			this.addShapelessRecipe(new ItemStack(Items.GOLD_INGOT), new Object[] { Blocks.GOLD_ORE });
			this.addRecipe(new ItemStack(Blocks.GOLD_BLOCK), new Object[] { "###", "###", "###", '#', Items.GOLD_INGOT });
			this.addShapelessRecipe(new ItemStack(Items.GOLD_NUGGET), new Object[] { Items.GOLD_INGOT });
			this.addRecipe(new ItemStack(Items.GOLD_INGOT), new Object[] { "###", "###", "###", '#', Items.GOLD_NUGGET });

			Collections.sort(this.recipes, new Comparator<IRecipe>() {
				@Override
				public int compare(IRecipe p_compare_1_, IRecipe p_compare_2_) {
					return p_compare_1_ instanceof ShapelessRecipes && p_compare_2_ instanceof ShapedRecipes ? 1 : (p_compare_2_ instanceof ShapelessRecipes && p_compare_1_ instanceof ShapedRecipes ? -1 : 0);
				}
			});
		}

		public ShapedRecipes addRecipe(ItemStack stack, Object... recipeComponents) {
			String s = "";
			int i = 0;
			int j = 0;
			int k = 0;

			if (recipeComponents[i] instanceof String[]) {
				String[] astring = (String[]) ((String[]) recipeComponents[i++]);

				for (String s2 : astring) {
					++k;
					j = s2.length();
					s = s + s2;
				}
			} else {
				while (recipeComponents[i] instanceof String) {
					String s1 = (String) recipeComponents[i++];
					++k;
					j = s1.length();
					s = s + s1;
				}
			}

			Map<Character, ItemStack> map;

			for (map = Maps.<Character, ItemStack>newHashMap(); i < recipeComponents.length; i += 2) {
				Character character = (Character) recipeComponents[i];
				ItemStack itemstack = ItemStack.EMPTY;

				if (recipeComponents[i + 1] instanceof Item) {
					itemstack = new ItemStack((Item) recipeComponents[i + 1]);
				} else if (recipeComponents[i + 1] instanceof Block) {
					itemstack = new ItemStack((Block) recipeComponents[i + 1], 1, 32767);
				} else if (recipeComponents[i + 1] instanceof ItemStack) {
					itemstack = (ItemStack) recipeComponents[i + 1];
				}

				map.put(character, itemstack);
			}

			NonNullList<Ingredient> aitemstack = NonNullList.create();

			for (int l = 0; l < j * k; ++l) {
				char c0 = s.charAt(l);

				if (map.containsKey(Character.valueOf(c0))) {
					aitemstack.add(l, Ingredient.fromStacks(((ItemStack) map.get(Character.valueOf(c0))).copy()));
				} else {
					aitemstack.add(l, Ingredient.EMPTY);
				}
			}

			ShapedRecipes shapedrecipes = new ShapedRecipes("", j, k, aitemstack, stack);
			this.recipes.add(shapedrecipes);
			return shapedrecipes;
		}

		public void addShapelessRecipe(ItemStack stack, Object... recipeComponents) {
			NonNullList<Ingredient> list = NonNullList.create();

			for (Object object : recipeComponents) {
				if (object instanceof ItemStack) {
					list.add(Ingredient.fromStacks(((ItemStack) object).copy()));
				} else if (object instanceof Item) {
					list.add(Ingredient.fromStacks(new ItemStack((Item) object)));
				} else {
					if (!(object instanceof Block)) {
						throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
					}

					list.add(Ingredient.fromStacks(new ItemStack((Block) object)));
				}
			}

			this.recipes.add(new ShapelessRecipes("", stack, list));
		}

		public void addRecipe(IRecipe recipe) {
			this.recipes.add(recipe);
		}

		public ItemStack findMatchingRecipe(EntityPlayer player, InventoryCrafting craftMatrix, World worldIn) {
			for (IRecipe irecipe : this.recipes) {
				if (irecipe.matches(craftMatrix, worldIn)) {
					return irecipe.getCraftingResult(craftMatrix);
				}
			}

			return ItemStack.EMPTY;
		}

		public NonNullList<ItemStack> getRemainingItems(InventoryCrafting craftMatrix, World worldIn) {
			for (IRecipe irecipe : this.recipes) {
				if (irecipe.matches(craftMatrix, worldIn)) {
					return irecipe.getRemainingItems(craftMatrix);
				}
			}

			NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(craftMatrix.getSizeInventory(), ItemStack.EMPTY);

			for (int i = 0; i < nonnulllist.size(); ++i) {
				nonnulllist.set(i, craftMatrix.getStackInSlot(i));
			}

			return nonnulllist;
		}

		public List<IRecipe> getRecipeList() {
			return this.recipes;
		}
	}

	public static class Cooking {
		private static final Cooking INSTANCE = new Cooking();
		private final List<LeveledRecipe> recipes = Lists.<LeveledRecipe>newArrayList();
		private final List<Boolean> shouldCook = Lists.<Boolean>newArrayList();

		public static Cooking getInstance() {
			return INSTANCE;
		}

		private Cooking() {
			this.addRecipe(0, true, new ItemStack(Items.BREAD), new Object[] { "###", " X ", '#', Items.WHEAT, 'X', PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER) });
			this.addShapelessRecipe(0, true, new ItemStack(Items.BAKED_POTATO), new Object[] { Items.POTATO });
			this.addShapelessRecipe(0, true, new ItemStack(Items.COOKED_FISH, 1, 0), new Object[] { new ItemStack(Items.FISH, 1, 0) });
			this.addShapelessRecipe(0, true, new ItemStack(Items.COOKED_FISH, 1, 1), new Object[] { new ItemStack(Items.FISH, 1, 1) });
			this.addShapelessRecipe(0, true, new ItemStack(ItemHandler.bass_cooked), new Object[] { ItemHandler.bass });
			this.addShapelessRecipe(0, true, new ItemStack(ItemHandler.grayling_cooked), new Object[] { ItemHandler.grayling });
			this.addShapelessRecipe(0, true, new ItemStack(ItemHandler.perch_cooked), new Object[] { ItemHandler.perch });
			this.addShapelessRecipe(0, true, new ItemStack(ItemHandler.trout_cooked), new Object[] { ItemHandler.trout });

			Collections.sort(this.recipes, new Comparator<LeveledRecipe>() {
				@Override
				public int compare(LeveledRecipe p_compare_1_, LeveledRecipe p_compare_2_) {
					return p_compare_1_.recipe instanceof ShapelessRecipes && p_compare_2_.recipe instanceof ShapedRecipes ? 1 : (p_compare_2_.recipe instanceof ShapelessRecipes && p_compare_1_.recipe instanceof ShapedRecipes ? -1 : p_compare_1_.reqLevel > p_compare_2_.reqLevel ? 1 : p_compare_1_.reqLevel < p_compare_2_.reqLevel ? -1 : 0);
				}
			});
		}

		public ShapedRecipes addRecipe(int reqLevel, boolean shouldCook, ItemStack stack, Object... recipeComponents) {
			String s = "";
			int i = 0;
			int j = 0;
			int k = 0;

			if (recipeComponents[i] instanceof String[]) {
				String[] astring = (String[]) ((String[]) recipeComponents[i++]);

				for (String s2 : astring) {
					++k;
					j = s2.length();
					s = s + s2;
				}
			} else {
				while (recipeComponents[i] instanceof String) {
					String s1 = (String) recipeComponents[i++];
					++k;
					j = s1.length();
					s = s + s1;
				}
			}

			Map<Character, ItemStack> map;

			for (map = Maps.<Character, ItemStack>newHashMap(); i < recipeComponents.length; i += 2) {
				Character character = (Character) recipeComponents[i];
				ItemStack itemstack = ItemStack.EMPTY;

				if (recipeComponents[i + 1] instanceof Item) {
					itemstack = new ItemStack((Item) recipeComponents[i + 1]);
				} else if (recipeComponents[i + 1] instanceof Block) {
					itemstack = new ItemStack((Block) recipeComponents[i + 1], 1, 32767);
				} else if (recipeComponents[i + 1] instanceof ItemStack) {
					itemstack = (ItemStack) recipeComponents[i + 1];
				}

				map.put(character, itemstack);
			}

			NonNullList<Ingredient> aitemstack = NonNullList.create();

			for (int l = 0; l < j * k; ++l) {
				char c0 = s.charAt(l);

				if (map.containsKey(Character.valueOf(c0))) {
					aitemstack.add(l, Ingredient.fromStacks(((ItemStack) map.get(Character.valueOf(c0))).copy()));
				} else {
					aitemstack.add(l, Ingredient.EMPTY);
				}
			}

			ShapedRecipes shapedrecipes = new ShapedRecipes("", j, k, aitemstack, stack);
			this.recipes.add(new LeveledRecipe(shapedrecipes, reqLevel));
			this.shouldCook.add(shouldCook);
			return shapedrecipes;
		}

		public void addShapelessRecipe(int reqLevel, boolean shouldCook, ItemStack stack, Object... recipeComponents) {
			NonNullList<Ingredient> list = NonNullList.create();

			for (Object object : recipeComponents) {
				if (object instanceof ItemStack) {
					list.add(Ingredient.fromStacks(((ItemStack) object).copy()));
				} else if (object instanceof Item) {
					list.add(Ingredient.fromStacks(new ItemStack((Item) object)));
				} else {
					if (!(object instanceof Block)) {
						throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
					}

					list.add(Ingredient.fromStacks(new ItemStack((Block) object)));
				}
			}

			this.recipes.add(new LeveledRecipe(new ShapelessRecipes("", stack, list), reqLevel));
			this.shouldCook.add(shouldCook);
		}

		public void addRecipe(LeveledRecipe recipe) {
			this.recipes.add(recipe);
		}

		public ItemStack findMatchingRecipe(EntityPlayer player, InventoryCrafting craftMatrix, World worldIn) {
			for (LeveledRecipe irecipe : this.recipes) {
				if (irecipe.recipe.matches(craftMatrix, worldIn) && RedwallUtils.getFacStatLevel(player, Faction.FacList.GENERIC, FactionCap.FacStatType.COOK) >= irecipe.reqLevel) {
					return irecipe.recipe.getCraftingResult(craftMatrix);
				}
			}

			return ItemStack.EMPTY;
		}

		public LeveledRecipe findRecipe(EntityPlayer player, InventoryCrafting craftMatrix, World worldIn) {
			for (LeveledRecipe irecipe : this.recipes) {
				if (irecipe.recipe.matches(craftMatrix, worldIn) && RedwallUtils.getFacStatLevel(player, Faction.FacList.GENERIC, FactionCap.FacStatType.COOK) >= irecipe.reqLevel) {
					return irecipe;
				}
			}

			return null;
		}

		public NonNullList<ItemStack> getRemainingItems(InventoryCrafting craftMatrix, World worldIn) {
			for (LeveledRecipe irecipe : this.recipes) {
				if (irecipe.recipe.matches(craftMatrix, worldIn)) {
					return irecipe.recipe.getRemainingItems(craftMatrix);
				}
			}

			NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(craftMatrix.getSizeInventory(), ItemStack.EMPTY);

			for (int i = 0; i < nonnulllist.size(); ++i) {
				nonnulllist.set(i, craftMatrix.getStackInSlot(i));
			}

			return nonnulllist;
		}

		public List<LeveledRecipe> getRecipeList() {
			return this.recipes;
		}

		public List<Boolean> getCookList() {
			return this.shouldCook;
		}
	}

	public static class BrewingRedwall extends SmithingRedwall {
		private static final BrewingRedwall INSTANCE = new BrewingRedwall();

		public static BrewingRedwall getInstance() {
			return INSTANCE;
		}

		private BrewingRedwall() {
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.APPLE_CIDER), new Object[] { " X ", " # ", "VCV", 'X', Items.APPLE, '#', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.ELDERBERRY_WINE), new Object[] { "###", "VCV", '#', ItemHandler.elderberry, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.DAMSON_WINE), new Object[] { "###", "VCV", '#', ItemHandler.damson, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });
			this.addRecipe(2, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.STRAWBERRY_FIZZ), new Object[] { "#X#", "XXX", "VCV", '#', ItemHandler.strawberry, 'X', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });
			this.addRecipe(1, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.BEETROOT_PORT), new Object[] { "###", "VCV", '#', Items.BEETROOT, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });

			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.APPLE_CIDER), new Object[] { " X ", " # ", "VCV", 'X', Items.APPLE, '#', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.ELDERBERRY_WINE), new Object[] { "###", "VCV", '#', ItemHandler.elderberry, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.DAMSON_WINE), new Object[] { "###", "VCV", '#', ItemHandler.damson, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });
			this.addRecipe(2, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.STRAWBERRY_FIZZ), new Object[] { "#X#", "XXX", "VCV", '#', ItemHandler.strawberry, 'X', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });
			this.addRecipe(1, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.BEETROOT_PORT), new Object[] { "###", "VCV", '#', Items.BEETROOT, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });

			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.APPLE_CIDER), new Object[] { " X ", " # ", "VCV", 'X', Items.APPLE, '#', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.ELDERBERRY_WINE), new Object[] { "###", "VCV", '#', ItemHandler.elderberry, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.DAMSON_WINE), new Object[] { "###", "VCV", '#', ItemHandler.damson, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });
			this.addRecipe(2, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.STRAWBERRY_FIZZ), new Object[] { "#X#", "XXX", "VCV", '#', ItemHandler.strawberry, 'X', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });
			this.addRecipe(1, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.BEETROOT_PORT), new Object[] { "###", "VCV", '#', Items.BEETROOT, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });

			Collections.sort(this.recipes, new Comparator<LeveledRecipe>() {
				@Override
				public int compare(LeveledRecipe p_compare_1_, LeveledRecipe p_compare_2_) {
					return p_compare_1_.recipe instanceof ShapelessRecipes && p_compare_2_.recipe instanceof ShapedRecipes ? 1 : (p_compare_2_.recipe instanceof ShapelessRecipes && p_compare_1_.recipe instanceof ShapedRecipes ? -1 : p_compare_1_.reqLevel > p_compare_2_.reqLevel ? 1 : p_compare_1_.reqLevel < p_compare_2_.reqLevel ? -1 : 0);
				}
			});
		}

		public ItemStack findMatchingRecipe(EntityPlayer player, InventoryCrafting craftMatrix, World worldIn) {
			for (LeveledRecipe irecipe : this.recipes) {
				if (irecipe.recipe.matches(craftMatrix, worldIn) && RedwallUtils.getFacStatLevel(player, Faction.FacList.REDWALL, FactionCap.FacStatType.BREW) >= irecipe.reqLevel) {
					return irecipe.recipe.getCraftingResult(craftMatrix);
				}
			}

			return ItemStack.EMPTY;
		}
	}

	public static class BrewingWoodlander extends BrewingRedwall {
		private static final BrewingWoodlander INSTANCE = new BrewingWoodlander();

		public static BrewingWoodlander getInstance() {
			return INSTANCE;
		}

		private BrewingWoodlander() {
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.APPLE_CIDER), new Object[] { " X ", " # ", "VCV", 'X', Items.APPLE, '#', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.DAMSON_WINE), new Object[] { "###", "VCV", '#', ItemHandler.damson, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });

			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.APPLE_CIDER), new Object[] { " X ", " # ", "VCV", 'X', Items.APPLE, '#', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.DAMSON_WINE), new Object[] { "###", "VCV", '#', ItemHandler.damson, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });

			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.APPLE_CIDER), new Object[] { " X ", " # ", "VCV", 'X', Items.APPLE, '#', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.DAMSON_WINE), new Object[] { "###", "VCV", '#', ItemHandler.damson, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });

			Collections.sort(this.recipes, new Comparator<LeveledRecipe>() {
				@Override
				public int compare(LeveledRecipe p_compare_1_, LeveledRecipe p_compare_2_) {
					return p_compare_1_.recipe instanceof ShapelessRecipes && p_compare_2_.recipe instanceof ShapedRecipes ? 1 : (p_compare_2_.recipe instanceof ShapelessRecipes && p_compare_1_.recipe instanceof ShapedRecipes ? -1 : p_compare_1_.reqLevel > p_compare_2_.reqLevel ? 1 : p_compare_1_.reqLevel < p_compare_2_.reqLevel ? -1 : 0);
				}
			});
		}

		public ItemStack findMatchingRecipe(EntityPlayer player, InventoryCrafting craftMatrix, World worldIn) {
			for (LeveledRecipe irecipe : this.recipes) {
				if (irecipe.recipe.matches(craftMatrix, worldIn) && (RedwallUtils.getFacStatLevel(player, Faction.FacList.WOODLANDERS, FactionCap.FacStatType.BREW) >= irecipe.reqLevel || RedwallUtils.getFacStatLevel(player, Faction.FacList.PEACE_ISLE, FactionCap.FacStatType.BREW) >= irecipe.reqLevel || RedwallUtils.getFacStatLevel(player, Faction.FacList.NOONVALE, FactionCap.FacStatType.BREW) >= irecipe.reqLevel)) {
					return irecipe.recipe.getCraftingResult(craftMatrix);
				}
			}

			return ItemStack.EMPTY;
		}
	}

	public static class BrewingGuosim extends BrewingRedwall {
		private static final BrewingGuosim INSTANCE = new BrewingGuosim();

		public static BrewingGuosim getInstance() {
			return INSTANCE;
		}

		private BrewingGuosim() {
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.ELDERBERRY_WINE), new Object[] { "###", "VCV", '#', ItemHandler.elderberry, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.DAMSON_WINE), new Object[] { "###", "VCV", '#', ItemHandler.damson, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });
			this.addRecipe(1, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.SHREWBEER), new Object[] { " X#", "VCV", 'X', Items.BREAD, '#', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });

			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.ELDERBERRY_WINE), new Object[] { "###", "VCV", '#', ItemHandler.elderberry, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.DAMSON_WINE), new Object[] { "###", "VCV", '#', ItemHandler.damson, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });
			this.addRecipe(1, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.SHREWBEER), new Object[] { " X#", "VCV", 'X', Items.BREAD, '#', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });

			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.ELDERBERRY_WINE), new Object[] { "###", "VCV", '#', ItemHandler.elderberry, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.DAMSON_WINE), new Object[] { "###", "VCV", '#', ItemHandler.damson, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });
			this.addRecipe(1, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.SHREWBEER), new Object[] { " X#", "VCV", 'X', Items.BREAD, '#', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });

			Collections.sort(this.recipes, new Comparator<LeveledRecipe>() {
				@Override
				public int compare(LeveledRecipe p_compare_1_, LeveledRecipe p_compare_2_) {
					return p_compare_1_.recipe instanceof ShapelessRecipes && p_compare_2_.recipe instanceof ShapedRecipes ? 1 : (p_compare_2_.recipe instanceof ShapelessRecipes && p_compare_1_.recipe instanceof ShapedRecipes ? -1 : p_compare_1_.reqLevel > p_compare_2_.reqLevel ? 1 : p_compare_1_.reqLevel < p_compare_2_.reqLevel ? -1 : 0);
				}
			});
		}

		public ItemStack findMatchingRecipe(EntityPlayer player, InventoryCrafting craftMatrix, World worldIn) {
			for (LeveledRecipe irecipe : this.recipes) {
				if (irecipe.recipe.matches(craftMatrix, worldIn) && RedwallUtils.getFacStatLevel(player, Faction.FacList.GUOSIM, FactionCap.FacStatType.BREW) >= irecipe.reqLevel) {
					return irecipe.recipe.getCraftingResult(craftMatrix);
				}
			}

			return ItemStack.EMPTY;
		}
	}

	public static class BrewingOtter extends BrewingRedwall {
		private static final BrewingOtter INSTANCE = new BrewingOtter();

		public static BrewingOtter getInstance() {
			return INSTANCE;
		}

		private BrewingOtter() {
			this.addRecipe(2, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.BURGOOLA), new Object[] { "#X#", "VCV", 'X', ItemHandler.raspberry, '#', Items.REEDS, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });

			this.addRecipe(2, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.BURGOOLA), new Object[] { "#X#", "VCV", 'X', ItemHandler.raspberry, '#', Items.REEDS, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });

			this.addRecipe(2, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.BURGOOLA), new Object[] { "#X#", "VCV", 'X', ItemHandler.raspberry, '#', Items.REEDS, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });

			Collections.sort(this.recipes, new Comparator<LeveledRecipe>() {
				@Override
				public int compare(LeveledRecipe p_compare_1_, LeveledRecipe p_compare_2_) {
					return p_compare_1_.recipe instanceof ShapelessRecipes && p_compare_2_.recipe instanceof ShapedRecipes ? 1 : (p_compare_2_.recipe instanceof ShapelessRecipes && p_compare_1_.recipe instanceof ShapedRecipes ? -1 : p_compare_1_.reqLevel > p_compare_2_.reqLevel ? 1 : p_compare_1_.reqLevel < p_compare_2_.reqLevel ? -1 : 0);
				}
			});
		}

		public ItemStack findMatchingRecipe(EntityPlayer player, InventoryCrafting craftMatrix, World worldIn) {
			for (LeveledRecipe irecipe : this.recipes) {
				if (irecipe.recipe.matches(craftMatrix, worldIn) && (RedwallUtils.getFacStatLevel(player, Faction.FacList.OTTERS_GREEN_ISLE, FactionCap.FacStatType.BREW) >= irecipe.reqLevel || RedwallUtils.getFacStatLevel(player, Faction.FacList.OTTERS_MOSSFLOWER, FactionCap.FacStatType.BREW) >= irecipe.reqLevel)) {
					return irecipe.recipe.getCraftingResult(craftMatrix);
				}
			}

			return ItemStack.EMPTY;
		}
	}

	public static class BrewingSalamandastron extends BrewingRedwall {
		private static final BrewingSalamandastron INSTANCE = new BrewingSalamandastron();

		public static BrewingSalamandastron getInstance() {
			return INSTANCE;
		}

		private BrewingSalamandastron() {
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.ELDERBERRY_WINE), new Object[] { "###", "VCV", '#', ItemHandler.elderberry, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.APPLE_CIDER), new Object[] { " X ", " # ", "VCV", 'X', Items.APPLE, '#', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });
			this.addRecipe(3, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.MOUNTAIN_ALE), new Object[] { "###", "VCV", '#', Items.WHEAT, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });

			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.ELDERBERRY_WINE), new Object[] { "###", "VCV", '#', ItemHandler.elderberry, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.APPLE_CIDER), new Object[] { " X ", " # ", "VCV", 'X', Items.APPLE, '#', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });
			this.addRecipe(3, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.MOUNTAIN_ALE), new Object[] { "###", "VCV", '#', Items.WHEAT, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });

			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.ELDERBERRY_WINE), new Object[] { "###", "VCV", '#', ItemHandler.elderberry, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.APPLE_CIDER), new Object[] { " X ", " # ", "VCV", 'X', Items.APPLE, '#', Items.SUGAR, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });
			this.addRecipe(3, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.MOUNTAIN_ALE), new Object[] { "###", "VCV", '#', Items.WHEAT, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });

			Collections.sort(this.recipes, new Comparator<LeveledRecipe>() {
				@Override
				public int compare(LeveledRecipe p_compare_1_, LeveledRecipe p_compare_2_) {
					return p_compare_1_.recipe instanceof ShapelessRecipes && p_compare_2_.recipe instanceof ShapedRecipes ? 1 : (p_compare_2_.recipe instanceof ShapelessRecipes && p_compare_1_.recipe instanceof ShapedRecipes ? -1 : p_compare_1_.reqLevel > p_compare_2_.reqLevel ? 1 : p_compare_1_.reqLevel < p_compare_2_.reqLevel ? -1 : 0);
				}
			});
		}

		public ItemStack findMatchingRecipe(EntityPlayer player, InventoryCrafting craftMatrix, World worldIn) {
			for (LeveledRecipe irecipe : this.recipes) {
				if (irecipe.recipe.matches(craftMatrix, worldIn) && (RedwallUtils.getFacStatLevel(player, Faction.FacList.SALAMANDASTRON, FactionCap.FacStatType.BREW) >= irecipe.reqLevel)) {
					return irecipe.recipe.getCraftingResult(craftMatrix);
				}
			}

			return ItemStack.EMPTY;
		}
	}

	public static class BrewingVerminMossflower extends BrewingRedwall {
		private static final BrewingVerminMossflower INSTANCE = new BrewingVerminMossflower();

		public static BrewingVerminMossflower getInstance() {
			return INSTANCE;
		}

		private BrewingVerminMossflower() {
			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.drink_bottle), Drink.DrinkList.BLACKBERRY_GROG), new Object[] { "###", "VCV", '#', ItemHandler.blackberry, 'V', Items.WATER_BUCKET, 'C', Items.GLASS_BOTTLE });

			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.mug_drink), Drink.DrinkList.BLACKBERRY_GROG), new Object[] { "###", "VCV", '#', ItemHandler.blackberry, 'V', Items.WATER_BUCKET, 'C', ItemHandler.mug_empty });

			this.addRecipe(0, ItemDrinkVessel.setDrink(new ItemStack(ItemHandler.bowl_drink), Drink.DrinkList.BLACKBERRY_GROG), new Object[] { "###", "VCV", '#', ItemHandler.blackberry, 'V', Items.WATER_BUCKET, 'C', Items.BOWL });

			Collections.sort(this.recipes, new Comparator<LeveledRecipe>() {
				@Override
				public int compare(LeveledRecipe p_compare_1_, LeveledRecipe p_compare_2_) {
					return p_compare_1_.recipe instanceof ShapelessRecipes && p_compare_2_.recipe instanceof ShapedRecipes ? 1 : (p_compare_2_.recipe instanceof ShapelessRecipes && p_compare_1_.recipe instanceof ShapedRecipes ? -1 : p_compare_1_.reqLevel > p_compare_2_.reqLevel ? 1 : p_compare_1_.reqLevel < p_compare_2_.reqLevel ? -1 : 0);
				}
			});
		}

		public ItemStack findMatchingRecipe(EntityPlayer player, InventoryCrafting craftMatrix, World worldIn) {
			for (LeveledRecipe irecipe : this.recipes) {
				if (irecipe.recipe.matches(craftMatrix, worldIn) && (RedwallUtils.getFacStatLevel(player, Faction.FacList.VERMIN_MOSSFLOWER, FactionCap.FacStatType.BREW) >= irecipe.reqLevel || RedwallUtils.getFacStatLevel(player, Faction.FacList.VERMIN_NORTHLANDS, FactionCap.FacStatType.BREW) >= irecipe.reqLevel)) {
					return irecipe.recipe.getCraftingResult(craftMatrix);
				}
			}

			return ItemStack.EMPTY;
		}
	}

	public static class LeveledRecipe {
		private IRecipe recipe;
		private int reqLevel;

		private LeveledRecipe(IRecipe recipe, int reqLevel) {
			this.recipe = recipe;
			this.reqLevel = reqLevel;
		}
	}
}
