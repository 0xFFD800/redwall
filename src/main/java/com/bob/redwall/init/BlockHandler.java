package com.bob.redwall.init;

import javax.annotation.Nullable;

import com.bob.redwall.Ref;
import com.bob.redwall.blocks.BlockWeaponRackHorizontal;
import com.bob.redwall.blocks.BlockWeaponRackVertical;
import com.bob.redwall.blocks.ModBlock;
import com.bob.redwall.blocks.multiuse.BlockHiddenDoor;
import com.bob.redwall.blocks.multiuse.BlockModBrick;
import com.bob.redwall.blocks.multiuse.BlockModDoor;
import com.bob.redwall.blocks.multiuse.BlockModFence;
import com.bob.redwall.blocks.multiuse.BlockModFenceGate;
import com.bob.redwall.blocks.multiuse.BlockModPillar;
import com.bob.redwall.blocks.multiuse.BlockModSlab;
import com.bob.redwall.blocks.multiuse.BlockModStairs;
import com.bob.redwall.blocks.multiuse.BlockModTorch;
import com.bob.redwall.blocks.plants.BlockModGrass;
import com.bob.redwall.blocks.plants.BlockWaterReeds;
import com.bob.redwall.blocks.plants.BlockWheatgrass;
import com.bob.redwall.blocks.plants.berry_bushes.BlockBlackberryBush;
import com.bob.redwall.blocks.plants.berry_bushes.BlockBlueberryBush;
import com.bob.redwall.blocks.plants.berry_bushes.BlockElderberryBush;
import com.bob.redwall.blocks.plants.berry_bushes.BlockRaspberryBush;
import com.bob.redwall.blocks.plants.berry_bushes.BlockStrawberryBush;
import com.bob.redwall.blocks.plants.berry_bushes.BlockWildberryBush;
import com.bob.redwall.blocks.plants.crops.BlockBeans;
import com.bob.redwall.blocks.plants.crops.BlockCornStalk;
import com.bob.redwall.blocks.plants.crops.BlockOnions;
import com.bob.redwall.blocks.plants.crops.BlockPeas;
import com.bob.redwall.blocks.plants.crops.BlockRice;
import com.bob.redwall.blocks.plants.crops.BlockTurnips;
import com.bob.redwall.blocks.plants.crops.BlockYams;
import com.bob.redwall.blocks.plants.saplings.BlockAlderSapling;
import com.bob.redwall.blocks.plants.saplings.BlockAppleSapling;
import com.bob.redwall.blocks.plants.saplings.BlockAshSapling;
import com.bob.redwall.blocks.plants.saplings.BlockAspenSapling;
import com.bob.redwall.blocks.plants.saplings.BlockBeechSapling;
import com.bob.redwall.blocks.plants.saplings.BlockElmSapling;
import com.bob.redwall.blocks.plants.saplings.BlockFirSapling;
import com.bob.redwall.blocks.plants.saplings.BlockHornbeamSapling;
import com.bob.redwall.blocks.plants.saplings.BlockLarchSapling;
import com.bob.redwall.blocks.plants.saplings.BlockMapleSapling;
import com.bob.redwall.blocks.plants.saplings.BlockPineSapling;
import com.bob.redwall.blocks.plants.saplings.BlockPlumSapling;
import com.bob.redwall.blocks.plants.saplings.BlockWillowSapling;
import com.bob.redwall.blocks.plants.saplings.BlockYewSapling;
import com.bob.redwall.blocks.plants.treeleaves.BlockFruitTreeLeaves;
import com.bob.redwall.blocks.plants.treeleaves.BlockModLeaves;
import com.bob.redwall.blocks.plants.treeleaves.BlockPlumLeaves;
import com.bob.redwall.blocks.plants.treelogs.BlockModLog;
import com.bob.redwall.blocks.stations.BlockCookingGeneric;
import com.bob.redwall.blocks.stations.BlockSmeltery;
import com.bob.redwall.blocks.stations.BlockSmithingGeneric;
import com.bob.redwall.items.blocks.ItemModBlock;
import com.bob.redwall.items.blocks.ItemModSlab;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHandler {
	public static Block basalt;
	public static ItemBlock ib_basalt;

	public static Block strawberry_bush;
	public static Block blueberry_bush;
	public static Block blackberry_bush;
	public static Block raspberry_bush;
	public static Block elderberry_bush;
	public static Block wildberry_bush;

	public static Block cornstalk;

	public static Block water_reeds;

	public static Block thatch;
	public static ItemBlock ib_thatch;
	public static Block thatch_slab;
	public static Block double_thatch_slab;
	public static ItemBlock ib_thatch_slab;
	public static Block thatch_stairs;
	public static ItemBlock ib_thatch_stairs;

	public static Block turnips;
	public static Block onions;
	public static Block peas;
	public static Block rice;
	public static Block beans;
	public static Block yams;

	public static Block wheatgrass;
	public static ItemBlock ib_wheatgrass;
	public static Block shortgrass;
	public static ItemBlock ib_shortgrass;

	public static Block maple_log;
	public static ItemBlock ib_maple_log;
	public static Block elm_log;
	public static ItemBlock ib_elm_log;
	public static Block ash_log;
	public static ItemBlock ib_ash_log;
	public static Block pine_log;
	public static ItemBlock ib_pine_log;
	public static Block fir_log;
	public static ItemBlock ib_fir_log;
	public static Block larch_log;
	public static ItemBlock ib_larch_log;
	public static Block hornbeam_log;
	public static ItemBlock ib_hornbeam_log;
	public static Block beech_log;
	public static ItemBlock ib_beech_log;
	public static Block yew_log;
	public static ItemBlock ib_yew_log;
	public static Block alder_log;
	public static ItemBlock ib_alder_log;
	public static Block aspen_log;
	public static ItemBlock ib_aspen_log;
	public static Block willow_log;
	public static ItemBlock ib_willow_log;
	public static Block apple_log;
	public static ItemBlock ib_apple_log;
	public static Block plum_log;
	public static ItemBlock ib_plum_log;

	public static Block maple_leaves;
	public static ItemBlock ib_maple_leaves;
	public static Block elm_leaves;
	public static ItemBlock ib_elm_leaves;
	public static Block ash_leaves;
	public static ItemBlock ib_ash_leaves;
	public static Block pine_leaves;
	public static ItemBlock ib_pine_leaves;
	public static Block fir_leaves;
	public static ItemBlock ib_fir_leaves;
	public static Block larch_leaves;
	public static ItemBlock ib_larch_leaves;
	public static Block hornbeam_leaves;
	public static ItemBlock ib_hornbeam_leaves;
	public static Block beech_leaves;
	public static ItemBlock ib_beech_leaves;
	public static Block yew_leaves;
	public static ItemBlock ib_yew_leaves;
	public static Block alder_leaves;
	public static ItemBlock ib_alder_leaves;
	public static Block aspen_leaves;
	public static ItemBlock ib_aspen_leaves;
	public static Block willow_leaves;
	public static ItemBlock ib_willow_leaves;
	public static Block apple_leaves;
	public static ItemBlock ib_apple_leaves;
	public static Block plum_leaves;
	public static ItemBlock ib_plum_leaves;

	public static Block maple_planks;
	public static ItemBlock ib_maple_planks;
	public static Block elm_planks;
	public static ItemBlock ib_elm_planks;
	public static Block ash_planks;
	public static ItemBlock ib_ash_planks;
	public static Block pine_planks;
	public static ItemBlock ib_pine_planks;
	public static Block fir_planks;
	public static ItemBlock ib_fir_planks;
	public static Block larch_planks;
	public static ItemBlock ib_larch_planks;
	public static Block hornbeam_planks;
	public static ItemBlock ib_hornbeam_planks;
	public static Block beech_planks;
	public static ItemBlock ib_beech_planks;
	public static Block yew_planks;
	public static ItemBlock ib_yew_planks;
	public static Block alder_planks;
	public static ItemBlock ib_alder_planks;
	public static Block aspen_planks;
	public static ItemBlock ib_aspen_planks;
	public static Block willow_planks;
	public static ItemBlock ib_willow_planks;
	public static Block apple_planks;
	public static ItemBlock ib_apple_planks;
	public static Block plum_planks;
	public static ItemBlock ib_plum_planks;

	public static Block maple_sapling;
	public static ItemBlock ib_maple_sapling;
	public static Block elm_sapling;
	public static ItemBlock ib_elm_sapling;
	public static Block ash_sapling;
	public static ItemBlock ib_ash_sapling;
	public static Block pine_sapling;
	public static ItemBlock ib_pine_sapling;
	public static Block fir_sapling;
	public static ItemBlock ib_fir_sapling;
	public static Block larch_sapling;
	public static ItemBlock ib_larch_sapling;
	public static Block hornbeam_sapling;
	public static ItemBlock ib_hornbeam_sapling;
	public static Block beech_sapling;
	public static ItemBlock ib_beech_sapling;
	public static Block yew_sapling;
	public static ItemBlock ib_yew_sapling;
	public static Block alder_sapling;
	public static ItemBlock ib_alder_sapling;
	public static Block aspen_sapling;
	public static ItemBlock ib_aspen_sapling;
	public static Block willow_sapling;
	public static ItemBlock ib_willow_sapling;
	public static Block apple_sapling;
	public static ItemBlock ib_apple_sapling;
	public static Block plum_sapling;
	public static ItemBlock ib_plum_sapling;

	public static Block maple_door;
	public static Block elm_door;
	public static Block ash_door;
	public static Block pine_door;
	public static Block fir_door;
	public static Block larch_door;
	public static Block hornbeam_door;
	public static Block beech_door;
	public static Block yew_door;
	public static Block alder_door;
	public static Block aspen_door;
	public static Block willow_door;
	public static Block apple_door;
	public static Block plum_door;

	public static Block maple_stairs;
	public static ItemBlock ib_maple_stairs;
	public static Block elm_stairs;
	public static ItemBlock ib_elm_stairs;
	public static Block ash_stairs;
	public static ItemBlock ib_ash_stairs;
	public static Block pine_stairs;
	public static ItemBlock ib_pine_stairs;
	public static Block fir_stairs;
	public static ItemBlock ib_fir_stairs;
	public static Block larch_stairs;
	public static ItemBlock ib_larch_stairs;
	public static Block hornbeam_stairs;
	public static ItemBlock ib_hornbeam_stairs;
	public static Block beech_stairs;
	public static ItemBlock ib_beech_stairs;
	public static Block yew_stairs;
	public static ItemBlock ib_yew_stairs;
	public static Block alder_stairs;
	public static ItemBlock ib_alder_stairs;
	public static Block aspen_stairs;
	public static ItemBlock ib_aspen_stairs;
	public static Block willow_stairs;
	public static ItemBlock ib_willow_stairs;
	public static Block apple_stairs;
	public static ItemBlock ib_apple_stairs;
	public static Block plum_stairs;
	public static ItemBlock ib_plum_stairs;

	public static Block maple_slab;
	public static Block double_maple_slab;
	public static ItemBlock ib_maple_slab;
	public static Block elm_slab;
	public static Block double_elm_slab;
	public static ItemBlock ib_elm_slab;
	public static Block ash_slab;
	public static Block double_ash_slab;
	public static ItemBlock ib_ash_slab;
	public static Block pine_slab;
	public static Block double_pine_slab;
	public static ItemBlock ib_pine_slab;
	public static Block fir_slab;
	public static Block double_fir_slab;
	public static ItemBlock ib_fir_slab;
	public static Block larch_slab;
	public static Block double_larch_slab;
	public static ItemBlock ib_larch_slab;
	public static Block hornbeam_slab;
	public static Block double_hornbeam_slab;
	public static ItemBlock ib_hornbeam_slab;
	public static Block beech_slab;
	public static Block double_beech_slab;
	public static ItemBlock ib_beech_slab;
	public static Block yew_slab;
	public static Block double_yew_slab;
	public static ItemBlock ib_yew_slab;
	public static Block alder_slab;
	public static Block double_alder_slab;
	public static ItemBlock ib_alder_slab;
	public static Block aspen_slab;
	public static Block double_aspen_slab;
	public static ItemBlock ib_aspen_slab;
	public static Block willow_slab;
	public static Block double_willow_slab;
	public static ItemBlock ib_willow_slab;
	public static Block apple_slab;
	public static Block double_apple_slab;
	public static ItemBlock ib_apple_slab;
	public static Block plum_slab;
	public static Block double_plum_slab;
	public static ItemBlock ib_plum_slab;

	public static Block maple_fence;
	public static ItemBlock ib_maple_fence;
	public static Block elm_fence;
	public static ItemBlock ib_elm_fence;
	public static Block ash_fence;
	public static ItemBlock ib_ash_fence;
	public static Block pine_fence;
	public static ItemBlock ib_pine_fence;
	public static Block fir_fence;
	public static ItemBlock ib_fir_fence;
	public static Block larch_fence;
	public static ItemBlock ib_larch_fence;
	public static Block hornbeam_fence;
	public static ItemBlock ib_hornbeam_fence;
	public static Block beech_fence;
	public static ItemBlock ib_beech_fence;
	public static Block yew_fence;
	public static ItemBlock ib_yew_fence;
	public static Block alder_fence;
	public static ItemBlock ib_alder_fence;
	public static Block aspen_fence;
	public static ItemBlock ib_aspen_fence;
	public static Block willow_fence;
	public static ItemBlock ib_willow_fence;
	public static Block apple_fence;
	public static ItemBlock ib_apple_fence;
	public static Block plum_fence;
	public static ItemBlock ib_plum_fence;

	public static Block maple_fence_gate;
	public static ItemBlock ib_maple_fence_gate;
	public static Block elm_fence_gate;
	public static ItemBlock ib_elm_fence_gate;
	public static Block ash_fence_gate;
	public static ItemBlock ib_ash_fence_gate;
	public static Block pine_fence_gate;
	public static ItemBlock ib_pine_fence_gate;
	public static Block fir_fence_gate;
	public static ItemBlock ib_fir_fence_gate;
	public static Block larch_fence_gate;
	public static ItemBlock ib_larch_fence_gate;
	public static Block hornbeam_fence_gate;
	public static ItemBlock ib_hornbeam_fence_gate;
	public static Block beech_fence_gate;
	public static ItemBlock ib_beech_fence_gate;
	public static Block yew_fence_gate;
	public static ItemBlock ib_yew_fence_gate;
	public static Block alder_fence_gate;
	public static ItemBlock ib_alder_fence_gate;
	public static Block aspen_fence_gate;
	public static ItemBlock ib_aspen_fence_gate;
	public static Block willow_fence_gate;
	public static ItemBlock ib_willow_fence_gate;
	public static Block apple_fence_gate;
	public static ItemBlock ib_apple_fence_gate;
	public static Block plum_fence_gate;
	public static ItemBlock ib_plum_fence_gate;

	public static Block southsward_brick;
	public static ItemBlock ib_southsward_brick;
	public static Block southsward_pillar;
	public static ItemBlock ib_southsward_pillar;
	public static Block southsward_stairs;
	public static ItemBlock ib_southsward_stairs;
	public static Block southsward_slab;
	public static Block double_southsward_slab;
	public static ItemBlock ib_southsward_slab;
	public static Block southsward_hidden_door;

	public static Block redwall_brick;
	public static ItemBlock ib_redwall_brick;
	public static Block redwall_pillar;
	public static ItemBlock ib_redwall_pillar;
	public static Block redwall_stairs;
	public static ItemBlock ib_redwall_stairs;
	public static Block redwall_slab;
	public static Block double_redwall_slab;
	public static ItemBlock ib_redwall_slab;

	public static Block kotir_brick;
	public static ItemBlock ib_kotir_brick;
	public static Block kotir_pillar;
	public static ItemBlock ib_kotir_pillar;
	public static Block kotir_stairs;
	public static ItemBlock ib_kotir_stairs;
	public static Block kotir_slab;
	public static Block double_kotir_slab;
	public static ItemBlock ib_kotir_slab;

	public static Block bronze_block;
	public static ItemBlock ib_bronze_block;
	public static Block copper_block;
	public static ItemBlock ib_copper_block;
	public static Block tin_block;
	public static ItemBlock ib_tin_block;

	public static Block copper_ore;
	public static ItemBlock ib_copper_ore;
	public static Block tin_ore;
	public static ItemBlock ib_tin_ore;

	public static Block candle;
	public static ItemBlock ib_candle;

	public static Block weapon_rack_vertical;
	public static Block weapon_rack_horizontal;

	public static Block smithing_generic;
	public static ItemBlock ib_smithing_generic;
	public static Block smeltery;
	public static ItemBlock ib_smeltery;
	public static Block cooking_generic;
	public static ItemBlock ib_cooking_generic;

	public static void init() {
		basalt = new ModBlock(Material.ROCK, "basalt", CreativeTabHandler.BLOCKS, 2.0F, 15.0F);
		ib_basalt = (ItemBlock) new ItemModBlock(basalt, basalt.getRegistryName());

		strawberry_bush = new BlockStrawberryBush("strawberry_bush");
		blueberry_bush = new BlockBlueberryBush("blueberry_bush");
		blackberry_bush = new BlockBlackberryBush("blackberry_bush");
		raspberry_bush = new BlockRaspberryBush("raspberry_bush");
		elderberry_bush = new BlockElderberryBush("elderberry_bush");
		wildberry_bush = new BlockWildberryBush("wildberry_bush");

		turnips = new BlockTurnips("turnips");
		onions = new BlockOnions("onions");
		rice = new BlockRice("rice");
		peas = new BlockPeas("peas");
		beans = new BlockBeans("beans");
		yams = new BlockYams("yams");

		cornstalk = new BlockCornStalk("cornstalk", Material.PLANTS);

		water_reeds = new BlockWaterReeds("water_reeds");

		thatch = new ModBlock(Material.GRASS, "thatch", CreativeTabHandler.BLOCKS, 1.0F, 1.5F, 0, "axe");
		ib_thatch = (ItemBlock) new ItemModBlock(thatch, thatch.getRegistryName());
		thatch_stairs = new BlockModStairs(BlockHandler.thatch.getDefaultState(), "thatch_stairs", CreativeTabHandler.BLOCKS, 1.0F, 1.5F, 0, "axe");
		ib_thatch_stairs = (ItemBlock) new ItemModBlock(thatch_stairs, thatch_stairs.getRegistryName());
		thatch_slab = new BlockModSlab(Material.GRASS, "thatch_slab", CreativeTabHandler.BLOCKS, 1.0F, 1.5F, 0, "axe");
		double_thatch_slab = new BlockModSlab.BlockModDoubleSlab(Material.GRASS, "double_thatch_slab", CreativeTabHandler.BLOCKS, 1.0F, 1.5F, 0, "axe");
		ib_thatch_slab = (ItemBlock) new ItemModSlab(thatch_slab, (BlockModSlab) thatch_slab, (BlockModSlab) double_thatch_slab, thatch_slab.getRegistryName());

		wheatgrass = new BlockWheatgrass(Material.VINE, "modgrass", CreativeTabHandler.BLOCKS);
		ib_wheatgrass = (ItemBlock) new ItemModBlock(wheatgrass, wheatgrass.getRegistryName());
		shortgrass = new BlockModGrass(Material.VINE, "modgrass2", CreativeTabHandler.BLOCKS);
		ib_shortgrass = (ItemBlock) new ItemModBlock(shortgrass, shortgrass.getRegistryName());

		maple_log = new BlockModLog("maple_log", CreativeTabHandler.BLOCKS);
		ib_maple_log = (ItemBlock) new ItemModBlock(maple_log, maple_log.getRegistryName());
		elm_log = new BlockModLog("elm_log", CreativeTabHandler.BLOCKS);
		ib_elm_log = (ItemBlock) new ItemModBlock(elm_log, elm_log.getRegistryName());
		ash_log = new BlockModLog("ash_log", CreativeTabHandler.BLOCKS);
		ib_ash_log = (ItemBlock) new ItemModBlock(ash_log, ash_log.getRegistryName());
		pine_log = new BlockModLog("pine_log", CreativeTabHandler.BLOCKS);
		ib_pine_log = (ItemBlock) new ItemModBlock(pine_log, pine_log.getRegistryName());
		fir_log = new BlockModLog("fir_log", CreativeTabHandler.BLOCKS);
		ib_fir_log = (ItemBlock) new ItemModBlock(fir_log, fir_log.getRegistryName());
		larch_log = new BlockModLog("larch_log", CreativeTabHandler.BLOCKS);
		ib_larch_log = (ItemBlock) new ItemModBlock(larch_log, larch_log.getRegistryName());
		hornbeam_log = new BlockModLog("hornbeam_log", CreativeTabHandler.BLOCKS);
		ib_hornbeam_log = (ItemBlock) new ItemModBlock(hornbeam_log, hornbeam_log.getRegistryName());
		beech_log = new BlockModLog("beech_log", CreativeTabHandler.BLOCKS);
		ib_beech_log = (ItemBlock) new ItemModBlock(beech_log, beech_log.getRegistryName());
		yew_log = new BlockModLog("yew_log", CreativeTabHandler.BLOCKS);
		ib_yew_log = (ItemBlock) new ItemModBlock(yew_log, yew_log.getRegistryName());
		alder_log = new BlockModLog("alder_log", CreativeTabHandler.BLOCKS);
		ib_alder_log = (ItemBlock) new ItemModBlock(alder_log, alder_log.getRegistryName());
		aspen_log = new BlockModLog("aspen_log", CreativeTabHandler.BLOCKS);
		ib_aspen_log = (ItemBlock) new ItemModBlock(aspen_log, aspen_log.getRegistryName());
		willow_log = new BlockModLog("willow_log", CreativeTabHandler.BLOCKS);
		ib_willow_log = (ItemBlock) new ItemModBlock(willow_log, willow_log.getRegistryName());
		apple_log = new BlockModLog("apple_log", CreativeTabHandler.BLOCKS);
		ib_apple_log = (ItemBlock) new ItemModBlock(apple_log, apple_log.getRegistryName());
		plum_log = new BlockModLog("plum_log", CreativeTabHandler.BLOCKS);
		ib_plum_log = (ItemBlock) new ItemModBlock(plum_log, plum_log.getRegistryName());

		maple_leaves = new BlockModLeaves("maple_leaves", CreativeTabHandler.BLOCKS, new ResourceLocation(Ref.MODID, "maple_sapling"));
		ib_maple_leaves = (ItemBlock) new ItemModBlock(maple_leaves, maple_leaves.getRegistryName());
		elm_leaves = new BlockModLeaves("elm_leaves", CreativeTabHandler.BLOCKS, new ResourceLocation(Ref.MODID, "elm_sapling"));
		ib_elm_leaves = (ItemBlock) new ItemModBlock(elm_leaves, elm_leaves.getRegistryName());
		ash_leaves = new BlockModLeaves("ash_leaves", CreativeTabHandler.BLOCKS, new ResourceLocation(Ref.MODID, "ash_sapling"));
		ib_ash_leaves = (ItemBlock) new ItemModBlock(ash_leaves, ash_leaves.getRegistryName());
		pine_leaves = new BlockModLeaves("pine_leaves", CreativeTabHandler.BLOCKS, new ResourceLocation(Ref.MODID, "pine_sapling"));
		ib_pine_leaves = (ItemBlock) new ItemModBlock(pine_leaves, pine_leaves.getRegistryName());
		fir_leaves = new BlockModLeaves("fir_leaves", CreativeTabHandler.BLOCKS, new ResourceLocation(Ref.MODID, "fir_sapling"));
		ib_fir_leaves = (ItemBlock) new ItemModBlock(fir_leaves, fir_leaves.getRegistryName());
		larch_leaves = new BlockModLeaves("larch_leaves", CreativeTabHandler.BLOCKS, new ResourceLocation(Ref.MODID, "larch_sapling"));
		ib_larch_leaves = (ItemBlock) new ItemModBlock(larch_leaves, larch_leaves.getRegistryName());
		hornbeam_leaves = new BlockModLeaves("hornbeam_leaves", CreativeTabHandler.BLOCKS, new ResourceLocation(Ref.MODID, "hornbeam_sapling"));
		ib_hornbeam_leaves = (ItemBlock) new ItemModBlock(hornbeam_leaves, hornbeam_leaves.getRegistryName());
		beech_leaves = new BlockModLeaves("beech_leaves", CreativeTabHandler.BLOCKS, new ResourceLocation(Ref.MODID, "beech_sapling"));
		ib_beech_leaves = (ItemBlock) new ItemModBlock(beech_leaves, beech_leaves.getRegistryName());
		yew_leaves = new BlockModLeaves("yew_leaves", CreativeTabHandler.BLOCKS, new ResourceLocation(Ref.MODID, "yew_sapling"));
		ib_yew_leaves = (ItemBlock) new ItemModBlock(yew_leaves, yew_leaves.getRegistryName());
		alder_leaves = new BlockModLeaves("alder_leaves", CreativeTabHandler.BLOCKS, new ResourceLocation(Ref.MODID, "alder_sapling"));
		ib_alder_leaves = (ItemBlock) new ItemModBlock(alder_leaves, alder_leaves.getRegistryName());
		aspen_leaves = new BlockModLeaves("aspen_leaves", CreativeTabHandler.BLOCKS, new ResourceLocation(Ref.MODID, "aspen_sapling"));
		ib_aspen_leaves = (ItemBlock) new ItemModBlock(aspen_leaves, aspen_leaves.getRegistryName());
		willow_leaves = new BlockModLeaves("willow_leaves", CreativeTabHandler.BLOCKS, new ResourceLocation(Ref.MODID, "willow_sapling"));
		ib_willow_leaves = (ItemBlock) new ItemModBlock(willow_leaves, willow_leaves.getRegistryName());
		apple_leaves = new BlockFruitTreeLeaves("apple_leaves", CreativeTabHandler.BLOCKS, new ResourceLocation("minecraft:apple"), new ResourceLocation(Ref.MODID, "apple_sapling"));
		ib_apple_leaves = (ItemBlock) new ItemModBlock(apple_leaves, apple_leaves.getRegistryName());
		plum_leaves = new BlockPlumLeaves("plum_leaves", CreativeTabHandler.BLOCKS, new ResourceLocation(Ref.MODID, "plum"), new ResourceLocation(Ref.MODID, "damson"), new ResourceLocation(Ref.MODID, "plum_sapling"));
		ib_plum_leaves = (ItemBlock) new ItemModBlock(plum_leaves, plum_leaves.getRegistryName());

		maple_planks = new ModBlock(Material.WOOD, "maple_planks", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_maple_planks = (ItemBlock) new ItemModBlock(maple_planks, maple_planks.getRegistryName());
		elm_planks = new ModBlock(Material.WOOD, "elm_planks", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_elm_planks = (ItemBlock) new ItemModBlock(elm_planks, elm_planks.getRegistryName());
		ash_planks = new ModBlock(Material.WOOD, "ash_planks", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_ash_planks = (ItemBlock) new ItemModBlock(ash_planks, ash_planks.getRegistryName());
		pine_planks = new ModBlock(Material.WOOD, "pine_planks", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_pine_planks = (ItemBlock) new ItemModBlock(pine_planks, pine_planks.getRegistryName());
		fir_planks = new ModBlock(Material.WOOD, "fir_planks", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_fir_planks = (ItemBlock) new ItemModBlock(fir_planks, fir_planks.getRegistryName());
		larch_planks = new ModBlock(Material.WOOD, "larch_planks", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_larch_planks = (ItemBlock) new ItemModBlock(larch_planks, larch_planks.getRegistryName());
		hornbeam_planks = new ModBlock(Material.WOOD, "hornbeam_planks", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_hornbeam_planks = (ItemBlock) new ItemModBlock(hornbeam_planks, hornbeam_planks.getRegistryName());
		beech_planks = new ModBlock(Material.WOOD, "beech_planks", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_beech_planks = (ItemBlock) new ItemModBlock(beech_planks, beech_planks.getRegistryName());
		yew_planks = new ModBlock(Material.WOOD, "yew_planks", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_yew_planks = (ItemBlock) new ItemModBlock(yew_planks, yew_planks.getRegistryName());
		alder_planks = new ModBlock(Material.WOOD, "alder_planks", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_alder_planks = (ItemBlock) new ItemModBlock(alder_planks, alder_planks.getRegistryName());
		aspen_planks = new ModBlock(Material.WOOD, "aspen_planks", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_aspen_planks = (ItemBlock) new ItemModBlock(aspen_planks, aspen_planks.getRegistryName());
		willow_planks = new ModBlock(Material.WOOD, "willow_planks", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_willow_planks = (ItemBlock) new ItemModBlock(willow_planks, willow_planks.getRegistryName());
		apple_planks = new ModBlock(Material.WOOD, "apple_planks", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_apple_planks = (ItemBlock) new ItemModBlock(apple_planks, apple_planks.getRegistryName());
		plum_planks = new ModBlock(Material.WOOD, "plum_planks", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_plum_planks = (ItemBlock) new ItemModBlock(plum_planks, plum_planks.getRegistryName());

		maple_sapling = new BlockMapleSapling("maple_sapling", CreativeTabHandler.BLOCKS);
		ib_maple_sapling = (ItemBlock) new ItemModBlock(maple_sapling, maple_sapling.getRegistryName());
		elm_sapling = new BlockElmSapling("elm_sapling", CreativeTabHandler.BLOCKS);
		ib_elm_sapling = (ItemBlock) new ItemModBlock(elm_sapling, elm_sapling.getRegistryName());
		ash_sapling = new BlockAshSapling("ash_sapling", CreativeTabHandler.BLOCKS);
		ib_ash_sapling = (ItemBlock) new ItemModBlock(ash_sapling, ash_sapling.getRegistryName());
		pine_sapling = new BlockPineSapling("pine_sapling", CreativeTabHandler.BLOCKS);
		ib_pine_sapling = (ItemBlock) new ItemModBlock(pine_sapling, pine_sapling.getRegistryName());
		fir_sapling = new BlockFirSapling("fir_sapling", CreativeTabHandler.BLOCKS);
		ib_fir_sapling = (ItemBlock) new ItemModBlock(fir_sapling, fir_sapling.getRegistryName());
		larch_sapling = new BlockLarchSapling("larch_sapling", CreativeTabHandler.BLOCKS);
		ib_larch_sapling = (ItemBlock) new ItemModBlock(larch_sapling, larch_sapling.getRegistryName());
		hornbeam_sapling = new BlockHornbeamSapling("hornbeam_sapling", CreativeTabHandler.BLOCKS);
		ib_hornbeam_sapling = (ItemBlock) new ItemModBlock(hornbeam_sapling, hornbeam_sapling.getRegistryName());
		beech_sapling = new BlockBeechSapling("beech_sapling", CreativeTabHandler.BLOCKS);
		ib_beech_sapling = (ItemBlock) new ItemModBlock(beech_sapling, beech_sapling.getRegistryName());
		yew_sapling = new BlockYewSapling("yew_sapling", CreativeTabHandler.BLOCKS);
		ib_yew_sapling = (ItemBlock) new ItemModBlock(yew_sapling, yew_sapling.getRegistryName());
		alder_sapling = new BlockAlderSapling("alder_sapling", CreativeTabHandler.BLOCKS);
		ib_alder_sapling = (ItemBlock) new ItemModBlock(alder_sapling, alder_sapling.getRegistryName());
		aspen_sapling = new BlockAspenSapling("aspen_sapling", CreativeTabHandler.BLOCKS);
		ib_aspen_sapling = (ItemBlock) new ItemModBlock(aspen_sapling, aspen_sapling.getRegistryName());
		willow_sapling = new BlockWillowSapling("willow_sapling", CreativeTabHandler.BLOCKS);
		ib_willow_sapling = (ItemBlock) new ItemModBlock(willow_sapling, willow_sapling.getRegistryName());
		apple_sapling = new BlockAppleSapling("apple_sapling", CreativeTabHandler.BLOCKS);
		ib_apple_sapling = (ItemBlock) new ItemModBlock(apple_sapling, apple_sapling.getRegistryName());
		plum_sapling = new BlockPlumSapling("plum_sapling", CreativeTabHandler.BLOCKS);
		ib_plum_sapling = (ItemBlock) new ItemModBlock(plum_sapling, plum_sapling.getRegistryName());

		maple_door = new BlockModDoor(Material.WOOD, "maple_door", null, 2.0F, 5.0F, 0, "axe", ItemHandler.maple_door);
		elm_door = new BlockModDoor(Material.WOOD, "elm_door", null, 2.0F, 5.0F, 0, "axe", ItemHandler.elm_door);
		ash_door = new BlockModDoor(Material.WOOD, "ash_door", null, 2.0F, 5.0F, 0, "axe", ItemHandler.ash_door);
		pine_door = new BlockModDoor(Material.WOOD, "pine_door", null, 2.0F, 5.0F, 0, "axe", ItemHandler.pine_door);
		fir_door = new BlockModDoor(Material.WOOD, "fir_door", null, 2.0F, 5.0F, 0, "axe", ItemHandler.fir_door);
		larch_door = new BlockModDoor(Material.WOOD, "larch_door", null, 2.0F, 5.0F, 0, "axe", ItemHandler.larch_door);
		hornbeam_door = new BlockModDoor(Material.WOOD, "hornbeam_door", null, 2.0F, 5.0F, 0, "axe", ItemHandler.hornbeam_door);
		beech_door = new BlockModDoor(Material.WOOD, "beech_door", null, 2.0F, 5.0F, 0, "axe", ItemHandler.beech_door);
		yew_door = new BlockModDoor(Material.WOOD, "yew_door", null, 2.0F, 5.0F, 0, "axe", ItemHandler.yew_door);
		alder_door = new BlockModDoor(Material.WOOD, "alder_door", null, 2.0F, 5.0F, 0, "axe", ItemHandler.alder_door);
		aspen_door = new BlockModDoor(Material.WOOD, "aspen_door", null, 2.0F, 5.0F, 0, "axe", ItemHandler.aspen_door);
		willow_door = new BlockModDoor(Material.WOOD, "willow_door", null, 2.0F, 5.0F, 0, "axe", ItemHandler.willow_door);
		apple_door = new BlockModDoor(Material.WOOD, "apple_door", null, 2.0F, 5.0F, 0, "axe", ItemHandler.apple_door);
		plum_door = new BlockModDoor(Material.WOOD, "plum_door", null, 2.0F, 5.0F, 0, "axe", ItemHandler.plum_door);

		maple_stairs = new BlockModStairs(BlockHandler.maple_planks.getDefaultState(), "maple_stairs", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_maple_stairs = (ItemBlock) new ItemModBlock(maple_stairs, maple_stairs.getRegistryName());
		elm_stairs = new BlockModStairs(BlockHandler.elm_planks.getDefaultState(), "elm_stairs", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_elm_stairs = (ItemBlock) new ItemModBlock(elm_stairs, elm_stairs.getRegistryName());
		ash_stairs = new BlockModStairs(BlockHandler.ash_planks.getDefaultState(), "ash_stairs", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_ash_stairs = (ItemBlock) new ItemModBlock(ash_stairs, ash_stairs.getRegistryName());
		pine_stairs = new BlockModStairs(BlockHandler.pine_planks.getDefaultState(), "pine_stairs", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_pine_stairs = (ItemBlock) new ItemModBlock(pine_stairs, pine_stairs.getRegistryName());
		fir_stairs = new BlockModStairs(BlockHandler.fir_planks.getDefaultState(), "fir_stairs", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_fir_stairs = (ItemBlock) new ItemModBlock(fir_stairs, fir_stairs.getRegistryName());
		larch_stairs = new BlockModStairs(BlockHandler.larch_planks.getDefaultState(), "larch_stairs", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_larch_stairs = (ItemBlock) new ItemModBlock(larch_stairs, larch_stairs.getRegistryName());
		hornbeam_stairs = new BlockModStairs(BlockHandler.hornbeam_planks.getDefaultState(), "hornbeam_stairs", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_hornbeam_stairs = (ItemBlock) new ItemModBlock(hornbeam_stairs, hornbeam_stairs.getRegistryName());
		beech_stairs = new BlockModStairs(BlockHandler.beech_planks.getDefaultState(), "beech_stairs", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_beech_stairs = (ItemBlock) new ItemModBlock(beech_stairs, beech_stairs.getRegistryName());
		yew_stairs = new BlockModStairs(BlockHandler.yew_planks.getDefaultState(), "yew_stairs", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_yew_stairs = (ItemBlock) new ItemModBlock(yew_stairs, yew_stairs.getRegistryName());
		alder_stairs = new BlockModStairs(BlockHandler.alder_planks.getDefaultState(), "alder_stairs", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_alder_stairs = (ItemBlock) new ItemModBlock(alder_stairs, alder_stairs.getRegistryName());
		aspen_stairs = new BlockModStairs(BlockHandler.aspen_planks.getDefaultState(), "aspen_stairs", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_aspen_stairs = (ItemBlock) new ItemModBlock(aspen_stairs, aspen_stairs.getRegistryName());
		willow_stairs = new BlockModStairs(BlockHandler.willow_planks.getDefaultState(), "willow_stairs", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_willow_stairs = (ItemBlock) new ItemModBlock(willow_stairs, willow_stairs.getRegistryName());
		apple_stairs = new BlockModStairs(BlockHandler.apple_planks.getDefaultState(), "apple_stairs", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_apple_stairs = (ItemBlock) new ItemModBlock(apple_stairs, apple_stairs.getRegistryName());
		plum_stairs = new BlockModStairs(BlockHandler.plum_planks.getDefaultState(), "plum_stairs", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_plum_stairs = (ItemBlock) new ItemModBlock(plum_stairs, plum_stairs.getRegistryName());

		maple_slab = new BlockModSlab(Material.WOOD, "maple_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_maple_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_maple_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_maple_slab = (ItemBlock) new ItemModSlab(maple_slab, (BlockModSlab) maple_slab, (BlockModSlab) double_maple_slab, maple_slab.getRegistryName());
		elm_slab = new BlockModSlab(Material.WOOD, "elm_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_elm_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_elm_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_elm_slab = (ItemBlock) new ItemModSlab(elm_slab, (BlockModSlab) elm_slab, (BlockModSlab) double_elm_slab, elm_slab.getRegistryName());
		ash_slab = new BlockModSlab(Material.WOOD, "ash_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_ash_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_ash_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_ash_slab = (ItemBlock) new ItemModSlab(ash_slab, (BlockModSlab) ash_slab, (BlockModSlab) double_ash_slab, ash_slab.getRegistryName());
		pine_slab = new BlockModSlab(Material.WOOD, "pine_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_pine_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_pine_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_pine_slab = (ItemBlock) new ItemModSlab(pine_slab, (BlockModSlab) pine_slab, (BlockModSlab) double_pine_slab, pine_slab.getRegistryName());
		fir_slab = new BlockModSlab(Material.WOOD, "fir_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_fir_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_fir_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_fir_slab = (ItemBlock) new ItemModSlab(fir_slab, (BlockModSlab) fir_slab, (BlockModSlab) double_fir_slab, fir_slab.getRegistryName());
		larch_slab = new BlockModSlab(Material.WOOD, "larch_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_larch_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_larch_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_larch_slab = (ItemBlock) new ItemModSlab(larch_slab, (BlockModSlab) larch_slab, (BlockModSlab) double_larch_slab, larch_slab.getRegistryName());
		hornbeam_slab = new BlockModSlab(Material.WOOD, "hornbeam_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_hornbeam_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_hornbeam_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_hornbeam_slab = (ItemBlock) new ItemModSlab(hornbeam_slab, (BlockModSlab) hornbeam_slab, (BlockModSlab) double_hornbeam_slab, hornbeam_slab.getRegistryName());
		beech_slab = new BlockModSlab(Material.WOOD, "beech_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_beech_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_beech_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_beech_slab = (ItemBlock) new ItemModSlab(beech_slab, (BlockModSlab) beech_slab, (BlockModSlab) double_beech_slab, beech_slab.getRegistryName());
		yew_slab = new BlockModSlab(Material.WOOD, "yew_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_yew_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_yew_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_yew_slab = (ItemBlock) new ItemModSlab(yew_slab, (BlockModSlab) yew_slab, (BlockModSlab) double_yew_slab, yew_slab.getRegistryName());
		alder_slab = new BlockModSlab(Material.WOOD, "alder_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_alder_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_alder_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_alder_slab = (ItemBlock) new ItemModSlab(alder_slab, (BlockModSlab) alder_slab, (BlockModSlab) double_alder_slab, alder_slab.getRegistryName());
		aspen_slab = new BlockModSlab(Material.WOOD, "aspen_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_aspen_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_aspen_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_aspen_slab = (ItemBlock) new ItemModSlab(aspen_slab, (BlockModSlab) aspen_slab, (BlockModSlab) double_aspen_slab, aspen_slab.getRegistryName());
		willow_slab = new BlockModSlab(Material.WOOD, "willow_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_willow_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_willow_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_willow_slab = (ItemBlock) new ItemModSlab(willow_slab, (BlockModSlab) willow_slab, (BlockModSlab) double_willow_slab, willow_slab.getRegistryName());
		apple_slab = new BlockModSlab(Material.WOOD, "apple_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_apple_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_apple_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_apple_slab = (ItemBlock) new ItemModSlab(apple_slab, (BlockModSlab) apple_slab, (BlockModSlab) double_apple_slab, apple_slab.getRegistryName());
		plum_slab = new BlockModSlab(Material.WOOD, "plum_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_plum_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_plum_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_plum_slab = (ItemBlock) new ItemModSlab(plum_slab, (BlockModSlab) plum_slab, (BlockModSlab) double_plum_slab, plum_slab.getRegistryName());

		maple_fence = new BlockModFence(Material.WOOD, "maple_fence", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_maple_fence = (ItemBlock) new ItemModBlock(maple_fence, maple_fence.getRegistryName());
		elm_fence = new BlockModFence(Material.WOOD, "elm_fence", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_elm_fence = (ItemBlock) new ItemModBlock(elm_fence, elm_fence.getRegistryName());
		ash_fence = new BlockModFence(Material.WOOD, "ash_fence", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_ash_fence = (ItemBlock) new ItemModBlock(ash_fence, ash_fence.getRegistryName());
		pine_fence = new BlockModFence(Material.WOOD, "pine_fence", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_pine_fence = (ItemBlock) new ItemModBlock(pine_fence, pine_fence.getRegistryName());
		fir_fence = new BlockModFence(Material.WOOD, "fir_fence", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_fir_fence = (ItemBlock) new ItemModBlock(fir_fence, fir_fence.getRegistryName());
		larch_fence = new BlockModFence(Material.WOOD, "larch_fence", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_larch_fence = (ItemBlock) new ItemModBlock(larch_fence, larch_fence.getRegistryName());
		hornbeam_fence = new BlockModFence(Material.WOOD, "hornbeam_fence", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_hornbeam_fence = (ItemBlock) new ItemModBlock(hornbeam_fence, hornbeam_fence.getRegistryName());
		beech_fence = new BlockModFence(Material.WOOD, "beech_fence", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_beech_fence = (ItemBlock) new ItemModBlock(beech_fence, beech_fence.getRegistryName());
		yew_fence = new BlockModFence(Material.WOOD, "yew_fence", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_yew_fence = (ItemBlock) new ItemModBlock(yew_fence, yew_fence.getRegistryName());
		alder_fence = new BlockModFence(Material.WOOD, "alder_fence", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_alder_fence = (ItemBlock) new ItemModBlock(alder_fence, alder_fence.getRegistryName());
		aspen_fence = new BlockModFence(Material.WOOD, "aspen_fence", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_aspen_fence = (ItemBlock) new ItemModBlock(aspen_fence, aspen_fence.getRegistryName());
		willow_fence = new BlockModFence(Material.WOOD, "willow_fence", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_willow_fence = (ItemBlock) new ItemModBlock(willow_fence, willow_fence.getRegistryName());
		apple_fence = new BlockModFence(Material.WOOD, "apple_fence", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_apple_fence = (ItemBlock) new ItemModBlock(apple_fence, apple_fence.getRegistryName());
		plum_fence = new BlockModFence(Material.WOOD, "plum_fence", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_plum_fence = (ItemBlock) new ItemModBlock(plum_fence, plum_fence.getRegistryName());

		maple_fence_gate = new BlockModFenceGate(Material.WOOD, "maple_fence_gate", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_maple_fence_gate = (ItemBlock) new ItemModBlock(maple_fence_gate, maple_fence_gate.getRegistryName());
		elm_fence_gate = new BlockModFenceGate(Material.WOOD, "elm_fence_gate", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_elm_fence_gate = (ItemBlock) new ItemModBlock(elm_fence_gate, elm_fence_gate.getRegistryName());
		ash_fence_gate = new BlockModFenceGate(Material.WOOD, "ash_fence_gate", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_ash_fence_gate = (ItemBlock) new ItemModBlock(ash_fence_gate, ash_fence_gate.getRegistryName());
		pine_fence_gate = new BlockModFenceGate(Material.WOOD, "pine_fence_gate", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_pine_fence_gate = (ItemBlock) new ItemModBlock(pine_fence_gate, pine_fence_gate.getRegistryName());
		fir_fence_gate = new BlockModFenceGate(Material.WOOD, "fir_fence_gate", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_fir_fence_gate = (ItemBlock) new ItemModBlock(fir_fence_gate, fir_fence_gate.getRegistryName());
		larch_fence_gate = new BlockModFenceGate(Material.WOOD, "larch_fence_gate", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_larch_fence_gate = (ItemBlock) new ItemModBlock(larch_fence_gate, larch_fence_gate.getRegistryName());
		hornbeam_fence_gate = new BlockModFenceGate(Material.WOOD, "hornbeam_fence_gate", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_hornbeam_fence_gate = (ItemBlock) new ItemModBlock(hornbeam_fence_gate, hornbeam_fence_gate.getRegistryName());
		beech_fence_gate = new BlockModFenceGate(Material.WOOD, "beech_fence_gate", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_beech_fence_gate = (ItemBlock) new ItemModBlock(beech_fence_gate, beech_fence_gate.getRegistryName());
		yew_fence_gate = new BlockModFenceGate(Material.WOOD, "yew_fence_gate", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_yew_fence_gate = (ItemBlock) new ItemModBlock(yew_fence_gate, yew_fence_gate.getRegistryName());
		alder_fence_gate = new BlockModFenceGate(Material.WOOD, "alder_fence_gate", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_alder_fence_gate = (ItemBlock) new ItemModBlock(alder_fence_gate, alder_fence_gate.getRegistryName());
		aspen_fence_gate = new BlockModFenceGate(Material.WOOD, "aspen_fence_gate", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_aspen_fence_gate = (ItemBlock) new ItemModBlock(aspen_fence_gate, aspen_fence_gate.getRegistryName());
		willow_fence_gate = new BlockModFenceGate(Material.WOOD, "willow_fence_gate", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_willow_fence_gate = (ItemBlock) new ItemModBlock(willow_fence_gate, willow_fence_gate.getRegistryName());
		apple_fence_gate = new BlockModFenceGate(Material.WOOD, "apple_fence_gate", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_apple_fence_gate = (ItemBlock) new ItemModBlock(apple_fence_gate, apple_fence_gate.getRegistryName());
		plum_fence_gate = new BlockModFenceGate(Material.WOOD, "plum_fence_gate", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		ib_plum_fence_gate = (ItemBlock) new ItemModBlock(plum_fence_gate, plum_fence_gate.getRegistryName());

		southsward_brick = new BlockModBrick("southsward_brick", CreativeTabHandler.BLOCKS, 2.5F, 15.0F, 0, "pickaxe");
		ib_southsward_brick = (ItemBlock) (new ItemMultiTexture(BlockHandler.southsward_brick, BlockHandler.southsward_brick, new ItemMultiTexture.Mapper() {
			@Override
			public String apply(ItemStack p_apply_1_) {
				return BlockModBrick.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
			}
		})).setUnlocalizedName("southsward_brick").setRegistryName(southsward_brick.getRegistryName());
		ib_southsward_brick.addPropertyOverride(new ResourceLocation("damage"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return stack.getMetadata();
			}
		});
		southsward_pillar = new BlockModPillar("southsward_pillar", CreativeTabHandler.BLOCKS, 2.5F, 15.0F, 0, "pickaxe");
		ib_southsward_pillar = (ItemBlock) new ItemModBlock(southsward_pillar, southsward_pillar.getRegistryName());
		southsward_stairs = new BlockModStairs(BlockHandler.southsward_brick.getDefaultState(), "southsward_stairs", CreativeTabHandler.BLOCKS, 2.5F, 15.0F, 0, "pickaxe");
		ib_southsward_stairs = (ItemBlock) new ItemModBlock(southsward_stairs, southsward_stairs.getRegistryName());
		southsward_slab = new BlockModSlab(Material.WOOD, "southsward_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_southsward_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_southsward_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_southsward_slab = (ItemBlock) new ItemModSlab(southsward_slab, (BlockModSlab) southsward_slab, (BlockModSlab) double_southsward_slab, southsward_slab.getRegistryName());
		southsward_hidden_door = new BlockHiddenDoor(Material.ROCK, "southsward_hidden_door", null, 2.5F, 15.0F, 0, "pickaxe", ItemHandler.southsward_hidden_door);

		redwall_brick = new BlockModBrick("redwall_brick", CreativeTabHandler.BLOCKS, 2.5F, 15.0F, 0, "pickaxe");
		ib_redwall_brick = (ItemBlock) (new ItemMultiTexture(BlockHandler.redwall_brick, BlockHandler.redwall_brick, new ItemMultiTexture.Mapper() {
			@Override
			public String apply(ItemStack p_apply_1_) {
				return BlockModBrick.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
			}
		})).setUnlocalizedName("redwall_brick").setRegistryName(redwall_brick.getRegistryName());
		ib_redwall_brick.addPropertyOverride(new ResourceLocation("damage"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return stack.getMetadata();
			}
		});
		redwall_pillar = new BlockModPillar("redwall_pillar", CreativeTabHandler.BLOCKS, 2.5F, 15.0F, 0, "pickaxe");
		ib_redwall_pillar = (ItemBlock) new ItemModBlock(redwall_pillar, redwall_pillar.getRegistryName());
		redwall_stairs = new BlockModStairs(BlockHandler.redwall_brick.getDefaultState(), "redwall_stairs", CreativeTabHandler.BLOCKS, 2.5F, 15.0F, 0, "pickaxe");
		ib_redwall_stairs = (ItemBlock) new ItemModBlock(redwall_stairs, redwall_stairs.getRegistryName());
		redwall_slab = new BlockModSlab(Material.WOOD, "redwall_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_redwall_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_redwall_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_redwall_slab = (ItemBlock) new ItemModSlab(redwall_slab, (BlockModSlab) redwall_slab, (BlockModSlab) double_redwall_slab, redwall_slab.getRegistryName());
		
		kotir_brick = new BlockModBrick("kotir_brick", CreativeTabHandler.BLOCKS, 2.5F, 15.0F, 0, "pickaxe");
		ib_kotir_brick = (ItemBlock) (new ItemMultiTexture(BlockHandler.kotir_brick, BlockHandler.kotir_brick, new ItemMultiTexture.Mapper() {
			@Override
			public String apply(ItemStack p_apply_1_) {
				return BlockModBrick.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
			}
		})).setUnlocalizedName("kotir_brick").setRegistryName(kotir_brick.getRegistryName());
		ib_kotir_brick.addPropertyOverride(new ResourceLocation("damage"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return stack.getMetadata();
			}
		});
		kotir_pillar = new BlockModPillar("kotir_pillar", CreativeTabHandler.BLOCKS, 2.5F, 15.0F, 0, "pickaxe");
		ib_kotir_pillar = (ItemBlock) new ItemModBlock(kotir_pillar, kotir_pillar.getRegistryName());
		kotir_stairs = new BlockModStairs(BlockHandler.kotir_brick.getDefaultState(), "kotir_stairs", CreativeTabHandler.BLOCKS, 2.5F, 15.0F, 0, "pickaxe");
		ib_kotir_stairs = (ItemBlock) new ItemModBlock(kotir_stairs, kotir_stairs.getRegistryName());
		kotir_slab = new BlockModSlab(Material.WOOD, "kotir_slab", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "axe");
		double_kotir_slab = new BlockModSlab.BlockModDoubleSlab(Material.WOOD, "double_kotir_slab", null, 2.0F, 5.0F, 0, "axe");
		ib_kotir_slab = (ItemBlock) new ItemModSlab(kotir_slab, (BlockModSlab) kotir_slab, (BlockModSlab) double_kotir_slab, kotir_slab.getRegistryName());

		bronze_block = new ModBlock(Material.ROCK, "bronze_block", CreativeTabHandler.BLOCKS, 5.0F, 10.0F, 0, "pickaxe");
		ib_bronze_block = (ItemBlock) new ItemModBlock(bronze_block, bronze_block.getRegistryName());
		copper_block = new ModBlock(Material.ROCK, "copper_block", CreativeTabHandler.BLOCKS, 5.0F, 10.0F, 0, "pickaxe");
		ib_copper_block = (ItemBlock) new ItemModBlock(copper_block, copper_block.getRegistryName());
		tin_block = new ModBlock(Material.ROCK, "tin_block", CreativeTabHandler.BLOCKS, 5.0F, 10.0F, 0, "pickaxe");
		ib_tin_block = (ItemBlock) new ItemModBlock(tin_block, tin_block.getRegistryName());

		copper_ore = new ModBlock(Material.ROCK, "copper_ore", CreativeTabHandler.BLOCKS, 3.0F, 5.0F, 0, "pickaxe");
		ib_copper_ore = (ItemBlock) new ItemModBlock(copper_ore, copper_ore.getRegistryName());
		tin_ore = new ModBlock(Material.ROCK, "tin_ore", CreativeTabHandler.BLOCKS, 3.0F, 5.0F, 0, "pickaxe");
		ib_tin_ore = (ItemBlock) new ItemModBlock(tin_ore, tin_ore.getRegistryName());

		candle = new BlockModTorch(Material.CIRCUITS, "candle", CreativeTabHandler.BLOCKS, 1.0F);
		ib_candle = (ItemBlock) new ItemModBlock(candle, candle.getRegistryName());

		weapon_rack_vertical = new BlockWeaponRackVertical(Material.CIRCUITS, "weapon_rack_vertical");
		weapon_rack_horizontal = new BlockWeaponRackHorizontal(Material.CIRCUITS, "weapon_rack_horizontal");

		smithing_generic = new BlockSmithingGeneric(Material.ROCK, "smithing_generic", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "pickaxe");
		ib_smithing_generic = (ItemBlock) new ItemModBlock(smithing_generic, smithing_generic.getRegistryName());
		smeltery = new BlockSmeltery(Material.ROCK, "smeltery", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "pickaxe");
		ib_smeltery = (ItemBlock) new ItemModBlock(smeltery, smeltery.getRegistryName());
		cooking_generic = new BlockCookingGeneric(Material.ROCK, "cooking_generic", CreativeTabHandler.BLOCKS, 2.0F, 5.0F, 0, "pickaxe");
		ib_cooking_generic = (ItemBlock) new ItemModBlock(cooking_generic, cooking_generic.getRegistryName());
	}

	public static void register(RegistryEvent.Register<Block> event) {
		registerBlock(event, basalt);

		registerBlock(event, strawberry_bush);
		registerBlock(event, blueberry_bush);
		registerBlock(event, blackberry_bush);
		registerBlock(event, raspberry_bush);
		registerBlock(event, elderberry_bush);
		registerBlock(event, wildberry_bush);

		registerBlock(event, turnips);
		registerBlock(event, onions);
		registerBlock(event, peas);
		registerBlock(event, rice);
		registerBlock(event, beans);
		registerBlock(event, yams);

		registerBlock(event, cornstalk);

		registerBlock(event, water_reeds);

		registerBlock(event, thatch);
		registerBlock(event, thatch_slab);
		registerBlock(event, double_thatch_slab);
		registerBlock(event, thatch_stairs);

		registerBlock(event, wheatgrass);
		registerBlock(event, shortgrass);

		registerBlock(event, maple_log);
		registerBlock(event, elm_log);
		registerBlock(event, ash_log);
		registerBlock(event, pine_log);
		registerBlock(event, fir_log);
		registerBlock(event, larch_log);
		registerBlock(event, hornbeam_log);
		registerBlock(event, beech_log);
		registerBlock(event, yew_log);
		registerBlock(event, alder_log);
		registerBlock(event, aspen_log);
		registerBlock(event, willow_log);
		registerBlock(event, apple_log);
		registerBlock(event, plum_log);

		registerBlock(event, maple_leaves);
		registerBlock(event, elm_leaves);
		registerBlock(event, ash_leaves);
		registerBlock(event, pine_leaves);
		registerBlock(event, fir_leaves);
		registerBlock(event, larch_leaves);
		registerBlock(event, hornbeam_leaves);
		registerBlock(event, beech_leaves);
		registerBlock(event, yew_leaves);
		registerBlock(event, alder_leaves);
		registerBlock(event, aspen_leaves);
		registerBlock(event, willow_leaves);
		registerBlock(event, apple_leaves);
		ModelLoader.setCustomStateMapper(apple_leaves, (new StateMap.Builder()).withName(null).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
		registerBlock(event, plum_leaves);
		ModelLoader.setCustomStateMapper(plum_leaves, (new StateMap.Builder()).withName(null).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());

		registerBlock(event, maple_planks);
		registerBlock(event, elm_planks);
		registerBlock(event, ash_planks);
		registerBlock(event, pine_planks);
		registerBlock(event, fir_planks);
		registerBlock(event, larch_planks);
		registerBlock(event, hornbeam_planks);
		registerBlock(event, beech_planks);
		registerBlock(event, yew_planks);
		registerBlock(event, alder_planks);
		registerBlock(event, aspen_planks);
		registerBlock(event, willow_planks);
		registerBlock(event, apple_planks);
		registerBlock(event, plum_planks);

		registerBlock(event, maple_sapling);
		registerBlock(event, elm_sapling);
		registerBlock(event, ash_sapling);
		registerBlock(event, pine_sapling);
		registerBlock(event, fir_sapling);
		registerBlock(event, larch_sapling);
		registerBlock(event, hornbeam_sapling);
		registerBlock(event, beech_sapling);
		registerBlock(event, yew_sapling);
		registerBlock(event, alder_sapling);
		registerBlock(event, aspen_sapling);
		registerBlock(event, willow_sapling);
		registerBlock(event, apple_sapling);
		registerBlock(event, plum_sapling);

		registerBlock(event, maple_door);
		registerBlock(event, elm_door);
		registerBlock(event, ash_door);
		registerBlock(event, pine_door);
		registerBlock(event, fir_door);
		registerBlock(event, larch_door);
		registerBlock(event, hornbeam_door);
		registerBlock(event, beech_door);
		registerBlock(event, yew_door);
		registerBlock(event, alder_door);
		registerBlock(event, aspen_door);
		registerBlock(event, willow_door);
		registerBlock(event, apple_door);
		registerBlock(event, plum_door);

		registerBlock(event, maple_stairs);
		registerBlock(event, elm_stairs);
		registerBlock(event, ash_stairs);
		registerBlock(event, pine_stairs);
		registerBlock(event, fir_stairs);
		registerBlock(event, larch_stairs);
		registerBlock(event, hornbeam_stairs);
		registerBlock(event, beech_stairs);
		registerBlock(event, yew_stairs);
		registerBlock(event, alder_stairs);
		registerBlock(event, aspen_stairs);
		registerBlock(event, willow_stairs);
		registerBlock(event, apple_stairs);
		registerBlock(event, plum_stairs);

		registerBlock(event, maple_slab);
		registerBlock(event, double_maple_slab);
		registerBlock(event, elm_slab);
		registerBlock(event, double_elm_slab);
		registerBlock(event, ash_slab);
		registerBlock(event, double_ash_slab);
		registerBlock(event, pine_slab);
		registerBlock(event, double_pine_slab);
		registerBlock(event, fir_slab);
		registerBlock(event, double_fir_slab);
		registerBlock(event, larch_slab);
		registerBlock(event, double_larch_slab);
		registerBlock(event, hornbeam_slab);
		registerBlock(event, double_hornbeam_slab);
		registerBlock(event, beech_slab);
		registerBlock(event, double_beech_slab);
		registerBlock(event, yew_slab);
		registerBlock(event, double_yew_slab);
		registerBlock(event, alder_slab);
		registerBlock(event, double_alder_slab);
		registerBlock(event, aspen_slab);
		registerBlock(event, double_aspen_slab);
		registerBlock(event, willow_slab);
		registerBlock(event, double_willow_slab);
		registerBlock(event, apple_slab);
		registerBlock(event, double_apple_slab);
		registerBlock(event, plum_slab);
		registerBlock(event, double_plum_slab);

		registerBlock(event, maple_fence);
		registerBlock(event, elm_fence);
		registerBlock(event, ash_fence);
		registerBlock(event, pine_fence);
		registerBlock(event, fir_fence);
		registerBlock(event, larch_fence);
		registerBlock(event, hornbeam_fence);
		registerBlock(event, beech_fence);
		registerBlock(event, yew_fence);
		registerBlock(event, alder_fence);
		registerBlock(event, aspen_fence);
		registerBlock(event, willow_fence);
		registerBlock(event, apple_fence);
		registerBlock(event, plum_fence);

		registerBlock(event, maple_fence_gate);
		registerBlock(event, elm_fence_gate);
		registerBlock(event, ash_fence_gate);
		registerBlock(event, pine_fence_gate);
		registerBlock(event, fir_fence_gate);
		registerBlock(event, larch_fence_gate);
		registerBlock(event, hornbeam_fence_gate);
		registerBlock(event, beech_fence_gate);
		registerBlock(event, yew_fence_gate);
		registerBlock(event, alder_fence_gate);
		registerBlock(event, aspen_fence_gate);
		registerBlock(event, willow_fence_gate);
		registerBlock(event, apple_fence_gate);
		registerBlock(event, plum_fence_gate);

		registerBlock(event, southsward_brick);
		registerBlock(event, southsward_pillar);
		registerBlock(event, southsward_stairs);
		registerBlock(event, southsward_slab);
		registerBlock(event, double_southsward_slab);
		registerBlock(event, southsward_hidden_door);

		registerBlock(event, redwall_brick);
		registerBlock(event, redwall_pillar);
		registerBlock(event, redwall_stairs);
		registerBlock(event, redwall_slab);
		registerBlock(event, double_redwall_slab);

		registerBlock(event, kotir_brick);
		registerBlock(event, kotir_pillar);
		registerBlock(event, kotir_stairs);
		registerBlock(event, kotir_slab);
		registerBlock(event, double_kotir_slab);

		registerBlock(event, bronze_block);
		registerBlock(event, copper_block);
		registerBlock(event, tin_block);

		registerBlock(event, copper_ore);
		registerBlock(event, tin_ore);

		registerBlock(event, candle);

		registerBlock(event, weapon_rack_vertical);
		registerBlock(event, weapon_rack_horizontal);

		registerBlock(event, smithing_generic);
		registerBlock(event, smeltery);
		registerBlock(event, cooking_generic);
	}

	public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
		registerItemBlock(event, ib_basalt);

		registerItemBlock(event, ib_thatch);
		registerItemBlock(event, ib_thatch_slab);
		registerItemBlock(event, ib_thatch_stairs);

		registerItemBlock(event, ib_wheatgrass);
		registerItemBlock(event, ib_shortgrass);

		registerItemBlock(event, ib_maple_log);
		registerItemBlock(event, ib_elm_log);
		registerItemBlock(event, ib_ash_log);
		registerItemBlock(event, ib_pine_log);
		registerItemBlock(event, ib_fir_log);
		registerItemBlock(event, ib_larch_log);
		registerItemBlock(event, ib_hornbeam_log);
		registerItemBlock(event, ib_beech_log);
		registerItemBlock(event, ib_yew_log);
		registerItemBlock(event, ib_alder_log);
		registerItemBlock(event, ib_aspen_log);
		registerItemBlock(event, ib_willow_log);
		registerItemBlock(event, ib_apple_log);
		registerItemBlock(event, ib_plum_log);

		registerItemBlock(event, ib_maple_leaves);
		registerItemBlock(event, ib_elm_leaves);
		registerItemBlock(event, ib_ash_leaves);
		registerItemBlock(event, ib_pine_leaves);
		registerItemBlock(event, ib_fir_leaves);
		registerItemBlock(event, ib_larch_leaves);
		registerItemBlock(event, ib_hornbeam_leaves);
		registerItemBlock(event, ib_beech_leaves);
		registerItemBlock(event, ib_yew_leaves);
		registerItemBlock(event, ib_alder_leaves);
		registerItemBlock(event, ib_aspen_leaves);
		registerItemBlock(event, ib_willow_leaves);
		registerItemBlock(event, ib_apple_leaves);
		registerItemBlock(event, ib_plum_leaves);

		registerItemBlock(event, ib_maple_planks);
		registerItemBlock(event, ib_elm_planks);
		registerItemBlock(event, ib_ash_planks);
		registerItemBlock(event, ib_pine_planks);
		registerItemBlock(event, ib_fir_planks);
		registerItemBlock(event, ib_larch_planks);
		registerItemBlock(event, ib_hornbeam_planks);
		registerItemBlock(event, ib_beech_planks);
		registerItemBlock(event, ib_yew_planks);
		registerItemBlock(event, ib_alder_planks);
		registerItemBlock(event, ib_aspen_planks);
		registerItemBlock(event, ib_willow_planks);
		registerItemBlock(event, ib_apple_planks);
		registerItemBlock(event, ib_plum_planks);

		registerItemBlock(event, ib_maple_sapling);
		registerItemBlock(event, ib_elm_sapling);
		registerItemBlock(event, ib_ash_sapling);
		registerItemBlock(event, ib_pine_sapling);
		registerItemBlock(event, ib_fir_sapling);
		registerItemBlock(event, ib_larch_sapling);
		registerItemBlock(event, ib_hornbeam_sapling);
		registerItemBlock(event, ib_beech_sapling);
		registerItemBlock(event, ib_yew_sapling);
		registerItemBlock(event, ib_alder_sapling);
		registerItemBlock(event, ib_aspen_sapling);
		registerItemBlock(event, ib_willow_sapling);
		registerItemBlock(event, ib_apple_sapling);
		registerItemBlock(event, ib_plum_sapling);

		registerItemBlock(event, ib_maple_stairs);
		registerItemBlock(event, ib_elm_stairs);
		registerItemBlock(event, ib_ash_stairs);
		registerItemBlock(event, ib_pine_stairs);
		registerItemBlock(event, ib_fir_stairs);
		registerItemBlock(event, ib_larch_stairs);
		registerItemBlock(event, ib_hornbeam_stairs);
		registerItemBlock(event, ib_beech_stairs);
		registerItemBlock(event, ib_yew_stairs);
		registerItemBlock(event, ib_alder_stairs);
		registerItemBlock(event, ib_aspen_stairs);
		registerItemBlock(event, ib_willow_stairs);
		registerItemBlock(event, ib_apple_stairs);
		registerItemBlock(event, ib_plum_stairs);

		registerItemBlock(event, ib_maple_slab);
		registerItemBlock(event, ib_elm_slab);
		registerItemBlock(event, ib_ash_slab);
		registerItemBlock(event, ib_pine_slab);
		registerItemBlock(event, ib_fir_slab);
		registerItemBlock(event, ib_larch_slab);
		registerItemBlock(event, ib_hornbeam_slab);
		registerItemBlock(event, ib_beech_slab);
		registerItemBlock(event, ib_yew_slab);
		registerItemBlock(event, ib_alder_slab);
		registerItemBlock(event, ib_aspen_slab);
		registerItemBlock(event, ib_willow_slab);
		registerItemBlock(event, ib_apple_slab);
		registerItemBlock(event, ib_plum_slab);

		registerItemBlock(event, ib_maple_fence);
		registerItemBlock(event, ib_elm_fence);
		registerItemBlock(event, ib_ash_fence);
		registerItemBlock(event, ib_pine_fence);
		registerItemBlock(event, ib_fir_fence);
		registerItemBlock(event, ib_larch_fence);
		registerItemBlock(event, ib_hornbeam_fence);
		registerItemBlock(event, ib_beech_fence);
		registerItemBlock(event, ib_yew_fence);
		registerItemBlock(event, ib_alder_fence);
		registerItemBlock(event, ib_aspen_fence);
		registerItemBlock(event, ib_willow_fence);
		registerItemBlock(event, ib_apple_fence);
		registerItemBlock(event, ib_plum_fence);

		registerItemBlock(event, ib_maple_fence_gate);
		registerItemBlock(event, ib_elm_fence_gate);
		registerItemBlock(event, ib_ash_fence_gate);
		registerItemBlock(event, ib_pine_fence_gate);
		registerItemBlock(event, ib_fir_fence_gate);
		registerItemBlock(event, ib_larch_fence_gate);
		registerItemBlock(event, ib_hornbeam_fence_gate);
		registerItemBlock(event, ib_beech_fence_gate);
		registerItemBlock(event, ib_yew_fence_gate);
		registerItemBlock(event, ib_alder_fence_gate);
		registerItemBlock(event, ib_aspen_fence_gate);
		registerItemBlock(event, ib_willow_fence_gate);
		registerItemBlock(event, ib_apple_fence_gate);
		registerItemBlock(event, ib_plum_fence_gate);

		registerItemBlock(event, ib_southsward_brick);
		registerItemBlock(event, ib_southsward_pillar);
		registerItemBlock(event, ib_southsward_stairs);
		registerItemBlock(event, ib_southsward_slab);

		registerItemBlock(event, ib_redwall_brick);
		registerItemBlock(event, ib_redwall_pillar);
		registerItemBlock(event, ib_redwall_stairs);
		registerItemBlock(event, ib_redwall_slab);

		registerItemBlock(event, ib_kotir_brick);
		registerItemBlock(event, ib_kotir_pillar);
		registerItemBlock(event, ib_kotir_stairs);
		registerItemBlock(event, ib_kotir_slab);

		registerItemBlock(event, ib_bronze_block);
		registerItemBlock(event, ib_copper_block);
		registerItemBlock(event, ib_tin_block);

		registerItemBlock(event, ib_copper_ore);
		registerItemBlock(event, ib_tin_ore);

		registerItemBlock(event, ib_candle);

		registerItemBlock(event, ib_smithing_generic);
		registerItemBlock(event, ib_smeltery);
		registerItemBlock(event, ib_cooking_generic);
	}

	public static void registerRenders() {
		registerRender(basalt);

		registerRender(strawberry_bush);
		registerRender(blueberry_bush);
		registerRender(blackberry_bush);
		registerRender(raspberry_bush);
		registerRender(elderberry_bush);
		registerRender(wildberry_bush);

		registerRender(turnips);
		registerRender(onions);
		registerRender(peas);
		registerRender(rice);
		registerRender(yams);
		registerRender(beans);

		registerRender(cornstalk);

		registerRender(water_reeds);

		registerRender(thatch);
		registerRender(thatch_slab);
		registerRender(double_thatch_slab);
		registerRender(thatch_stairs);

		registerRender(wheatgrass);
		registerRender(shortgrass);

		registerRender(maple_log);
		registerRender(elm_log);
		registerRender(ash_log);
		registerRender(pine_log);
		registerRender(fir_log);
		registerRender(larch_log);
		registerRender(hornbeam_log);
		registerRender(beech_log);
		registerRender(yew_log);
		registerRender(alder_log);
		registerRender(aspen_log);
		registerRender(willow_log);
		registerRender(apple_log);
		registerRender(plum_log);

		registerRender(maple_leaves);
		registerRender(elm_leaves);
		registerRender(ash_leaves);
		registerRender(pine_leaves);
		registerRender(fir_leaves);
		registerRender(larch_leaves);
		registerRender(hornbeam_leaves);
		registerRender(beech_leaves);
		registerRender(yew_leaves);
		registerRender(alder_leaves);
		registerRender(aspen_leaves);
		registerRender(willow_leaves);
		registerRender(apple_leaves);
		registerRender(plum_leaves);

		registerRender(maple_planks);
		registerRender(elm_planks);
		registerRender(ash_planks);
		registerRender(pine_planks);
		registerRender(fir_planks);
		registerRender(larch_planks);
		registerRender(hornbeam_planks);
		registerRender(beech_planks);
		registerRender(yew_planks);
		registerRender(alder_planks);
		registerRender(aspen_planks);
		registerRender(willow_planks);
		registerRender(apple_planks);
		registerRender(plum_planks);

		registerRender(maple_sapling);
		registerRender(elm_sapling);
		registerRender(ash_sapling);
		registerRender(pine_sapling);
		registerRender(fir_sapling);
		registerRender(larch_sapling);
		registerRender(hornbeam_sapling);
		registerRender(beech_sapling);
		registerRender(yew_sapling);
		registerRender(alder_sapling);
		registerRender(aspen_sapling);
		registerRender(willow_sapling);
		registerRender(apple_sapling);
		registerRender(plum_sapling);

		registerRender(maple_stairs);
		registerRender(elm_stairs);
		registerRender(ash_stairs);
		registerRender(pine_stairs);
		registerRender(fir_stairs);
		registerRender(larch_stairs);
		registerRender(hornbeam_stairs);
		registerRender(beech_stairs);
		registerRender(yew_stairs);
		registerRender(alder_stairs);
		registerRender(aspen_stairs);
		registerRender(willow_stairs);
		registerRender(apple_stairs);
		registerRender(plum_stairs);

		registerRender(maple_slab);
		registerRender(elm_slab);
		registerRender(ash_slab);
		registerRender(pine_slab);
		registerRender(fir_slab);
		registerRender(larch_slab);
		registerRender(hornbeam_slab);
		registerRender(beech_slab);
		registerRender(yew_slab);
		registerRender(alder_slab);
		registerRender(aspen_slab);
		registerRender(willow_slab);
		registerRender(apple_slab);
		registerRender(plum_slab);

		registerRender(double_maple_slab);
		registerRender(double_elm_slab);
		registerRender(double_ash_slab);
		registerRender(double_pine_slab);
		registerRender(double_fir_slab);
		registerRender(double_larch_slab);
		registerRender(double_hornbeam_slab);
		registerRender(double_beech_slab);
		registerRender(double_yew_slab);
		registerRender(double_alder_slab);
		registerRender(double_aspen_slab);
		registerRender(double_willow_slab);
		registerRender(double_apple_slab);
		registerRender(double_plum_slab);

		registerRender(maple_fence);
		registerRender(elm_fence);
		registerRender(ash_fence);
		registerRender(pine_fence);
		registerRender(fir_fence);
		registerRender(larch_fence);
		registerRender(hornbeam_fence);
		registerRender(beech_fence);
		registerRender(yew_fence);
		registerRender(alder_fence);
		registerRender(aspen_fence);
		registerRender(willow_fence);
		registerRender(apple_fence);
		registerRender(plum_fence);

		registerRender(maple_fence_gate);
		registerRender(elm_fence_gate);
		registerRender(ash_fence_gate);
		registerRender(pine_fence_gate);
		registerRender(fir_fence_gate);
		registerRender(larch_fence_gate);
		registerRender(hornbeam_fence_gate);
		registerRender(beech_fence_gate);
		registerRender(yew_fence_gate);
		registerRender(alder_fence_gate);
		registerRender(aspen_fence_gate);
		registerRender(willow_fence_gate);
		registerRender(apple_fence_gate);
		registerRender(plum_fence_gate);

		registerRender(southsward_brick, 0, new ResourceLocation(Ref.MODID, "southsward_brick"));
		registerRender(southsward_brick, 1, new ResourceLocation(Ref.MODID, "item/southsward_brick_mossy"));
		registerRender(southsward_brick, 2, new ResourceLocation(Ref.MODID, "item/southsward_brick_cracked"));
		registerRender(southsward_brick, 3, new ResourceLocation(Ref.MODID, "item/southsward_brick_chiseled"));

		registerRender(southsward_pillar);
		registerRender(southsward_stairs);
		registerRender(southsward_slab);
		registerRender(double_southsward_slab);
		registerRender(southsward_hidden_door);

		registerRender(redwall_brick, 0, new ResourceLocation(Ref.MODID, "redwall_brick"));
		registerRender(redwall_brick, 1, new ResourceLocation(Ref.MODID, "item/redwall_brick_mossy"));
		registerRender(redwall_brick, 2, new ResourceLocation(Ref.MODID, "item/redwall_brick_cracked"));
		registerRender(redwall_brick, 3, new ResourceLocation(Ref.MODID, "item/redwall_brick_chiseled"));
		registerRender(redwall_pillar);
		registerRender(redwall_stairs);
		registerRender(redwall_slab);
		registerRender(double_redwall_slab);

		registerRender(kotir_brick, 0, new ResourceLocation(Ref.MODID, "kotir_brick"));
		registerRender(kotir_brick, 1, new ResourceLocation(Ref.MODID, "item/kotir_brick_mossy"));
		registerRender(kotir_brick, 2, new ResourceLocation(Ref.MODID, "item/kotir_brick_cracked"));
		registerRender(kotir_brick, 3, new ResourceLocation(Ref.MODID, "item/kotir_brick_chiseled"));
		registerRender(kotir_pillar);
		registerRender(kotir_stairs);
		registerRender(kotir_slab);
		registerRender(double_kotir_slab);

		registerRender(bronze_block);
		registerRender(copper_block);
		registerRender(tin_block);

		registerRender(copper_ore);
		registerRender(tin_ore);

		registerRender(candle);

		registerRender(weapon_rack_vertical);
		registerRender(weapon_rack_horizontal);

		registerRender(smithing_generic);
		registerRender(smeltery);
		registerRender(cooking_generic);
	}

	public static void registerBlock(RegistryEvent.Register<Block> event, Block block) {
		event.getRegistry().register(block);
	}

	public static void registerItemBlock(RegistryEvent.Register<Item> event, ItemBlock block) {
		event.getRegistry().register(block);
	}

	public static void registerRender(Block block) {
		registerRender(block, 0, Item.getItemFromBlock(block).getRegistryName());
	}

	public static void registerRender(Block block, int subtype, ResourceLocation name) {
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, subtype, new ModelResourceLocation(name, "inventory"));
	}
}
