package com.bob.redwall.init;

import java.awt.Color;

import com.bob.redwall.Ref;
import com.bob.redwall.biomes.BiomeRedwallOcean;
import com.bob.redwall.biomes.BiomeRedwallRiver;
import com.bob.redwall.biomes.beach.BiomeRedwallBeach;
import com.bob.redwall.biomes.beach.BiomeRedwallStoneBeach;
import com.bob.redwall.biomes.cool.BiomeRedwallForest;
import com.bob.redwall.biomes.cool.BiomeRedwallMountains;
import com.bob.redwall.biomes.cool.BiomeRedwallNorthlands;
import com.bob.redwall.biomes.desert.BiomeRedwallBareDesert;
import com.bob.redwall.biomes.desert.BiomeRedwallDesert;
import com.bob.redwall.biomes.desert.BiomeRedwallMesa;
import com.bob.redwall.biomes.icy.BiomeRedwallArctic;
import com.bob.redwall.biomes.warm.BiomeRedwallGrassland;
import com.bob.redwall.biomes.warm.BiomeRedwallMarsh;
import com.bob.redwall.biomes.warm.BiomeRedwallPlains;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;

public class BiomeHandler extends Biome {
	public static final Color DEFAULT_WATER_COLOR = new Color(46, 64, 244);
	public static final int DWC_BIOME = BiomeHandler.getColorForBiomeFromRGB(DEFAULT_WATER_COLOR);
	public static final Color SWAMP_WATER_COLOR = new Color(25, 43, 33);
	public static final int SWC_BIOME = BiomeHandler.getColorForBiomeFromRGB(SWAMP_WATER_COLOR);
	public static final Color DEEPLOUGH_WATER_COLOR = new Color(0, 11, 73);
	public static final int DLWC_BIOME = BiomeHandler.getColorForBiomeFromRGB(DEEPLOUGH_WATER_COLOR);
	public static final Color COLD_WATER_COLOR = new Color(10, 10, 109);
	public static final int CWC_BIOME = BiomeHandler.getColorForBiomeFromRGB(COLD_WATER_COLOR);
	public static final Color WARM_WATER_COLOR = new Color(79, 94, 226);
	public static final int WWC_BIOME = BiomeHandler.getColorForBiomeFromRGB(WARM_WATER_COLOR);
	public static final Color OCEAN_WATER_COLOR = new Color(47, 79, 114);
	public static final int OWC_BIOME = BiomeHandler.getColorForBiomeFromRGB(OCEAN_WATER_COLOR);
	
    public static Biome redwall_arctic;
    public static Biome redwall_mountains;
    public static Biome redwall_plains;
    public static Biome redwall_plains_sunflower;
    public static Biome redwall_beach;
    public static Biome redwall_desert;
    public static Biome redwall_bare_desert;
    public static Biome redwall_forest;
    public static Biome redwall_forest_flowers;
    public static Biome redwall_forest_deciduous;
    public static Biome redwall_forest_coniferous;
    public static Biome redwall_forest_oak;
    public static Biome redwall_forest_birch;
    public static Biome redwall_forest_maple;
    public static Biome redwall_forest_elm;
    public static Biome redwall_forest_ash;
    public static Biome redwall_forest_spruce;
    public static Biome redwall_forest_pine;
    public static Biome redwall_forest_fir;
    public static Biome redwall_forest_larch;
    public static Biome redwall_scrubland;
    public static Biome redwall_hills;
    public static Biome redwall_red_desert;
    public static Biome redwall_bare_red_desert;
    public static Biome redwall_mesa;
    public static Biome redwall_ocean;
    public static Biome redwall_deep_ocean;
    public static Biome redwall_river;
    public static Biome redwall_river_frozen;
    public static Biome redwall_grassland;
    public static Biome redwall_arctic_hills;
    public static Biome redwall_gravel_beach;
    public static Biome redwall_rocky_beach;
    public static Biome redwall_marsh;
    public static Biome redwall_scrubland_plateau;
    public static Biome redwall_pines_plateau;
    public static Biome redwall_desert_plateau;
    public static Biome redwall_river_plateau;
	public static Biome redwall_abyss;
	public static Biome redwall_heathland;
	public static Biome redwall_quarry;
	public static Biome redwall_forest_southsward;
	public static Biome redwall_hills_southsward;
	public static Biome redwall_deeplough;
    public static Biome redwall_northlands;
    public static Biome redwall_northlands_hills;
    public static Biome redwall_noonvale;
    public static Biome redwall_noonvale_forest;
    public static Biome redwall_noonvale_hills;
    
	public BiomeHandler(BiomeProperties properties) {
		super(properties);
        this.decorator = new BiomeDecorator();
	}
	
	public static Biome getRegisteredBiome(String id) {
        Biome biome = (Biome)Biome.REGISTRY.getObject(new ResourceLocation(Ref.MODID, id));

        if (biome == null) {
            throw new IllegalStateException("Invalid Biome requested: " + id);
        } else {
            return biome;
        }
    }
	
	public static void init() {
		redwall_mountains = new BiomeRedwallMountains(BiomeRedwallMountains.Type.MUTATED, (new Biome.BiomeProperties("Mountains")).setBaseBiome("extreme_hills_with_trees").setBaseHeight(1.5F).setHeightVariation(1.5F).setTemperature(0.2F).setRainfall(0.3F).setWaterColor(CWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_mountains"));
		redwall_plains = new BiomeRedwallPlains(false, false, (new Biome.BiomeProperties("Plains")).setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F).setRainfall(0.4F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_plains"));
		redwall_plains_sunflower = new BiomeRedwallPlains(true, false, (new Biome.BiomeProperties("Sunflower Plains")).setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F).setRainfall(0.4F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_plains_sunflower"));
		redwall_beach = new BiomeRedwallBeach((new Biome.BiomeProperties("Seashore")).setBaseHeight(0.0F).setHeightVariation(0.025F).setTemperature(0.8F).setRainfall(0.4F).setWaterColor(OWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_beach"));
		redwall_gravel_beach = new BiomeRedwallStoneBeach((new Biome.BiomeProperties("Gravel Beach")).setBaseHeight(0.1F).setHeightVariation(0.8F).setTemperature(0.2F).setRainfall(0.3F).setWaterColor(OWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_gravel_beach"));
		redwall_rocky_beach = new BiomeRedwallStoneBeach((new Biome.BiomeProperties("Rocky Beach")).setBaseHeight(0.1F).setHeightVariation(0.8F).setTemperature(0.2F).setRainfall(0.3F).setWaterColor(OWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_rocky_beach"));
		redwall_desert = new BiomeRedwallDesert((new Biome.BiomeProperties("Desert")).setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled().setWaterColor(WWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_desert"));
		redwall_bare_desert = new BiomeRedwallBareDesert((new Biome.BiomeProperties("Bare Desert")).setBaseHeight(0.125F).setHeightVariation(0.35F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled().setWaterColor(WWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_bare_desert"));
		redwall_forest = new BiomeRedwallForest(BiomeRedwallForest.Type.MIXED, (new Biome.BiomeProperties("Forest")).setTemperature(0.7F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_forest"));
		redwall_forest_deciduous = new BiomeRedwallForest(BiomeRedwallForest.Type.DECIDUOUS, (new Biome.BiomeProperties("Deciduous Forest")).setTemperature(0.7F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_forest_deciduous"));
		redwall_forest_coniferous = new BiomeRedwallForest(BiomeRedwallForest.Type.CONIFEROUS, (new Biome.BiomeProperties("Coniferous Forest")).setTemperature(0.4F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_forest_coniferous"));
		redwall_forest_flowers = new BiomeRedwallForest(BiomeRedwallForest.Type.FLOWER, (new Biome.BiomeProperties("Flower Forest")).setTemperature(0.7F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_forest_flowers"));
		redwall_forest_oak = new BiomeRedwallForest(BiomeRedwallForest.Type.OAK, (new Biome.BiomeProperties("Oak Forest")).setTemperature(0.7F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_forest_oak"));
		redwall_forest_birch = new BiomeRedwallForest(BiomeRedwallForest.Type.BIRCH, (new Biome.BiomeProperties("Birch Forest")).setTemperature(0.7F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_forest_birch"));
		redwall_forest_maple = new BiomeRedwallForest(BiomeRedwallForest.Type.MAPLE, (new Biome.BiomeProperties("Maple Forest")).setTemperature(0.7F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_forest_maple"));
		redwall_forest_elm = new BiomeRedwallForest(BiomeRedwallForest.Type.ELM, (new Biome.BiomeProperties("Elm Forest")).setTemperature(0.7F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_forest_elm"));
		redwall_forest_ash = new BiomeRedwallForest(BiomeRedwallForest.Type.ASH, (new Biome.BiomeProperties("Ash Forest")).setTemperature(0.7F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_forest_ash"));
		redwall_forest_spruce = new BiomeRedwallForest(BiomeRedwallForest.Type.SPRUCE, (new Biome.BiomeProperties("Spruce Forest")).setTemperature(0.4F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_forest_spruce"));
		redwall_forest_pine = new BiomeRedwallForest(BiomeRedwallForest.Type.PINE, (new Biome.BiomeProperties("Pine Forest")).setTemperature(0.4F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_forest_pine"));
		redwall_forest_fir = new BiomeRedwallForest(BiomeRedwallForest.Type.FIR, (new Biome.BiomeProperties("Fir Forest")).setTemperature(0.4F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_forest_fir"));
		redwall_forest_larch = new BiomeRedwallForest(BiomeRedwallForest.Type.LARCH, (new Biome.BiomeProperties("Larch Forest")).setTemperature(0.7F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_forest_larch"));
		redwall_scrubland = new BiomeRedwallForest(BiomeRedwallForest.Type.SCRUB, (new Biome.BiomeProperties("Scrubland")).setTemperature(0.9F).setRainfall(0.7F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_scrubland"));
		redwall_heathland = new BiomeRedwallForest(BiomeRedwallForest.Type.HEATHLAND, (new Biome.BiomeProperties("Heathland")).setTemperature(0.8F).setRainfall(0.6F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_heathland"));
		redwall_hills = new BiomeRedwallPlains(false, true, (new Biome.BiomeProperties("Hills")).setBaseHeight(0.3F).setHeightVariation(0.3F).setTemperature(0.8F).setRainfall(0.4F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_hills"));
		redwall_red_desert = new BiomeRedwallMesa(BiomeRedwallMesa.Type.RED_DESERT, (new Biome.BiomeProperties("Red Desert")).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled().setWaterColor(WWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_red_desert"));
		redwall_bare_red_desert = new BiomeRedwallMesa(BiomeRedwallMesa.Type.BARE_RED_DESERT, (new Biome.BiomeProperties("Bare Red Desert")).setBaseHeight(0.125F).setHeightVariation(0.35F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled().setWaterColor(WWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_bare_red_desert"));
		redwall_mesa = new BiomeRedwallMesa(BiomeRedwallMesa.Type.PLATEAU, (new Biome.BiomeProperties("Mesa Plateau")).setBaseHeight(1.5F).setHeightVariation(0.025F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled().setWaterColor(WWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_mesa"));
		redwall_ocean = new BiomeRedwallOcean((new Biome.BiomeProperties("Seas")).setBaseHeight(-1.0F).setHeightVariation(0.1F).setWaterColor(OWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_ocean"));
		redwall_deep_ocean = new BiomeRedwallOcean((new Biome.BiomeProperties("Deep Seas")).setBaseHeight(-1.8F).setHeightVariation(0.1F).setWaterColor(OWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_deep_ocean"));
		redwall_river = new BiomeRedwallRiver((new Biome.BiomeProperties("River")).setBaseHeight(-0.5F).setHeightVariation(0.0F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_river"));
		redwall_river_frozen = new BiomeRedwallRiver((new Biome.BiomeProperties("Frozen River")).setBaseHeight(-0.5F).setHeightVariation(0.0F).setTemperature(0.0F).setRainfall(0.5F).setSnowEnabled().setWaterColor(CWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_river_frozen"));
		redwall_grassland = new BiomeRedwallGrassland((new Biome.BiomeProperties("Grassland")).setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(1.2F).setRainfall(0.0F).setRainDisabled().setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_grassland"));
		redwall_arctic = new BiomeRedwallArctic(false, (new Biome.BiomeProperties("Arctic")).setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.0F).setRainfall(0.5F).setSnowEnabled().setWaterColor(CWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_arctic"));
		redwall_arctic_hills = new BiomeRedwallArctic(false, (new Biome.BiomeProperties("Arctic Hills")).setBaseHeight(0.3F).setHeightVariation(0.3F).setTemperature(0.0F).setRainfall(0.5F).setSnowEnabled().setWaterColor(CWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_arctic_hills"));
		redwall_marsh = new BiomeRedwallMarsh((new Biome.BiomeProperties("Marsh")).setBaseHeight(-0.2F).setHeightVariation(0.1F).setTemperature(0.8F).setRainfall(0.9F).setWaterColor(SWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_marsh"));
		redwall_scrubland_plateau = new BiomeRedwallForest(BiomeRedwallForest.Type.SCRUB_PLATEAU, (new Biome.BiomeProperties("Plateau Scrubland")).setBaseHeight(1.5F).setHeightVariation(0.025F).setTemperature(0.9F).setRainfall(0.7F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_plateau_scrubland"));
		redwall_pines_plateau = new BiomeRedwallForest(BiomeRedwallForest.Type.PINE_PLATEAU, (new Biome.BiomeProperties("Plateau Pine Forest")).setBaseHeight(1.5F).setHeightVariation(0.025F).setTemperature(0.9F).setRainfall(0.7F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_plateau_pine_forest"));
		redwall_desert_plateau = new BiomeRedwallDesert((new Biome.BiomeProperties("Plateau Desert")).setBaseHeight(1.5F).setHeightVariation(0.05F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled().setWaterColor(WWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_plateau_desert"));
		redwall_river_plateau = new BiomeRedwallRiver(true, (new Biome.BiomeProperties("Plateau River")).setBaseHeight(1.0F).setHeightVariation(0.0F).setWaterColor(WWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_plateau_river"));
		redwall_abyss = new BiomeRedwallDesert((new Biome.BiomeProperties("Abyss")).setBaseHeight(-100.0F).setHeightVariation(0.0F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled().setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_abyss"));
		redwall_quarry = new BiomeRedwallMesa(BiomeRedwallMesa.Type.QUARRY, (new Biome.BiomeProperties("Quarry")).setTemperature(0.8F).setRainfall(0.7F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_quarry"));
		redwall_forest_southsward = new BiomeRedwallForest(BiomeRedwallForest.Type.SOUTHSWARD, (new Biome.BiomeProperties("Southsward Forest")).setTemperature(1.2F).setRainfall(0.2F).setWaterColor(WWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_southsward_forest"));
		redwall_hills_southsward = new BiomeRedwallPlains(false, true, (new Biome.BiomeProperties("Southsward Hills")).setBaseHeight(0.3F).setHeightVariation(0.3F).setTemperature(1.2F).setRainfall(0.2F).setWaterColor(WWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_southsward_hills"));
		redwall_deeplough = new BiomeRedwallOcean((new Biome.BiomeProperties("Deeplough")).setBaseHeight(-1.8F).setHeightVariation(0.1F).setWaterColor(DLWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_deeplough"));
		redwall_northlands = new BiomeRedwallNorthlands(false, false, (new Biome.BiomeProperties("Northlands")).setBaseHeight(0.125F).setHeightVariation(0.015F).setTemperature(0.4F).setRainfall(0.8F).setWaterColor(CWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_northlands"));
		redwall_northlands_hills = new BiomeRedwallNorthlands(false, true, (new Biome.BiomeProperties("Northlands Hills")).setBaseHeight(0.3F).setHeightVariation(0.3F).setTemperature(0.4F).setRainfall(0.8F).setWaterColor(CWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_northlands_hills"));
		redwall_noonvale = new BiomeRedwallNorthlands(true, false, (new Biome.BiomeProperties("Noonvale")).setBaseHeight(0.125F).setHeightVariation(0.015F).setTemperature(0.4F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_noonvale"));
		redwall_noonvale_forest = new BiomeRedwallForest(BiomeRedwallForest.Type.NOONVALE, (new Biome.BiomeProperties("Noonvale Forest")).setTemperature(0.35F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_noonvale_forest"));
		redwall_noonvale_hills = new BiomeRedwallNorthlands(true, true, (new Biome.BiomeProperties("Noonvale Hills")).setBaseHeight(0.3F).setHeightVariation(0.3F).setTemperature(0.4F).setRainfall(0.8F).setWaterColor(DWC_BIOME)).setRegistryName(new ResourceLocation(Ref.MODID, "redwall_noonvale_hills"));
	}
	
	public static void register(RegistryEvent.Register<Biome> event) {
        event.getRegistry().register(redwall_mountains);
        event.getRegistry().register(redwall_plains);
        event.getRegistry().register(redwall_plains_sunflower);
        event.getRegistry().register(redwall_beach);
        event.getRegistry().register(redwall_gravel_beach);
        event.getRegistry().register(redwall_rocky_beach);
        event.getRegistry().register(redwall_desert);
        event.getRegistry().register(redwall_bare_desert);
        event.getRegistry().register(redwall_forest);
        event.getRegistry().register(redwall_forest_deciduous);
        event.getRegistry().register(redwall_forest_coniferous);
        event.getRegistry().register(redwall_forest_flowers);
        event.getRegistry().register(redwall_forest_oak);
        event.getRegistry().register(redwall_forest_birch);
        event.getRegistry().register(redwall_forest_maple);
        event.getRegistry().register(redwall_forest_elm);
        event.getRegistry().register(redwall_forest_ash);
        event.getRegistry().register(redwall_forest_spruce);
        event.getRegistry().register(redwall_forest_pine);
        event.getRegistry().register(redwall_forest_fir);
        event.getRegistry().register(redwall_forest_larch);
        event.getRegistry().register(redwall_scrubland);
        event.getRegistry().register(redwall_heathland);
        event.getRegistry().register(redwall_hills);
        event.getRegistry().register(redwall_red_desert);
        event.getRegistry().register(redwall_bare_red_desert);
        event.getRegistry().register(redwall_mesa);
        event.getRegistry().register(redwall_ocean);
        event.getRegistry().register(redwall_deep_ocean);
        event.getRegistry().register(redwall_river);
        event.getRegistry().register(redwall_river_frozen);
        event.getRegistry().register(redwall_grassland);
        event.getRegistry().register(redwall_arctic);
        event.getRegistry().register(redwall_arctic_hills);
        event.getRegistry().register(redwall_marsh);
        event.getRegistry().register(redwall_scrubland_plateau);
        event.getRegistry().register(redwall_pines_plateau);
        event.getRegistry().register(redwall_desert_plateau);
        event.getRegistry().register(redwall_river_plateau);
        event.getRegistry().register(redwall_abyss);
        event.getRegistry().register(redwall_quarry);
        event.getRegistry().register(redwall_forest_southsward);
        event.getRegistry().register(redwall_hills_southsward);
        event.getRegistry().register(redwall_deeplough);
        event.getRegistry().register(redwall_northlands);
        event.getRegistry().register(redwall_northlands_hills);
        event.getRegistry().register(redwall_noonvale);
        event.getRegistry().register(redwall_noonvale_forest);
        event.getRegistry().register(redwall_noonvale_hills);
	
		BiomeDictionary.addTypes(redwall_mountains, BiomeDictionary.Type.COLD, BiomeDictionary.Type.MOUNTAIN);
		BiomeDictionary.addTypes(redwall_plains, BiomeDictionary.Type.PLAINS);
		BiomeDictionary.addTypes(redwall_plains_sunflower, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.RARE);
		BiomeDictionary.addTypes(redwall_beach, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.SANDY);
		BiomeDictionary.addTypes(redwall_gravel_beach, BiomeDictionary.Type.BEACH);
		BiomeDictionary.addTypes(redwall_rocky_beach, BiomeDictionary.Type.BEACH);
		BiomeDictionary.addTypes(redwall_desert, BiomeDictionary.Type.HOT, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.DRY);
		BiomeDictionary.addTypes(redwall_bare_desert, BiomeDictionary.Type.HOT, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.DRY, BiomeDictionary.Type.WASTELAND);
		BiomeDictionary.addTypes(redwall_forest, BiomeDictionary.Type.FOREST);
		BiomeDictionary.addTypes(redwall_forest_deciduous, BiomeDictionary.Type.FOREST);
		BiomeDictionary.addTypes(redwall_forest_coniferous, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS);
		BiomeDictionary.addTypes(redwall_forest_flowers, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.RARE);
		BiomeDictionary.addTypes(redwall_forest_oak, BiomeDictionary.Type.FOREST);
		BiomeDictionary.addTypes(redwall_forest_birch, BiomeDictionary.Type.FOREST);
		BiomeDictionary.addTypes(redwall_forest_maple, BiomeDictionary.Type.FOREST);
		BiomeDictionary.addTypes(redwall_forest_elm, BiomeDictionary.Type.FOREST);
		BiomeDictionary.addTypes(redwall_forest_ash, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE);
		BiomeDictionary.addTypes(redwall_forest_spruce, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS);
		BiomeDictionary.addTypes(redwall_forest_pine, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS);
		BiomeDictionary.addTypes(redwall_forest_fir, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS);
		BiomeDictionary.addTypes(redwall_forest_larch, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS);
		BiomeDictionary.addTypes(redwall_scrubland, BiomeDictionary.Type.WASTELAND);
		BiomeDictionary.addTypes(redwall_heathland, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.SPARSE);
		BiomeDictionary.addTypes(redwall_hills, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.HILLS);
		BiomeDictionary.addTypes(redwall_red_desert, BiomeDictionary.Type.HOT, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.DRY);
		BiomeDictionary.addTypes(redwall_bare_red_desert, BiomeDictionary.Type.HOT, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.DRY, BiomeDictionary.Type.WASTELAND);
		BiomeDictionary.addTypes(redwall_mesa, BiomeDictionary.Type.HOT, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.DRY, BiomeDictionary.Type.MESA);
		BiomeDictionary.addTypes(redwall_ocean, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.WATER);
		BiomeDictionary.addTypes(redwall_deep_ocean, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.WATER);
		BiomeDictionary.addTypes(redwall_river, BiomeDictionary.Type.RIVER, BiomeDictionary.Type.WATER);
		BiomeDictionary.addTypes(redwall_river_frozen, BiomeDictionary.Type.RIVER, BiomeDictionary.Type.WATER, BiomeDictionary.Type.COLD);
		BiomeDictionary.addTypes(redwall_grassland, BiomeDictionary.Type.HOT, BiomeDictionary.Type.SAVANNA);
		BiomeDictionary.addTypes(redwall_arctic, BiomeDictionary.Type.COLD, BiomeDictionary.Type.PLAINS);
		BiomeDictionary.addTypes(redwall_arctic_hills, BiomeDictionary.Type.COLD, BiomeDictionary.Type.HILLS);
		BiomeDictionary.addTypes(redwall_marsh, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.WET);
		BiomeDictionary.addTypes(redwall_scrubland_plateau, BiomeDictionary.Type.WASTELAND);
		BiomeDictionary.addTypes(redwall_pines_plateau, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS);
		BiomeDictionary.addTypes(redwall_desert_plateau, BiomeDictionary.Type.HOT, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.DRY);
		BiomeDictionary.addTypes(redwall_river_plateau, BiomeDictionary.Type.RIVER, BiomeDictionary.Type.WATER);
		BiomeDictionary.addTypes(redwall_abyss, BiomeDictionary.Type.HOT, BiomeDictionary.Type.SPOOKY);
		BiomeDictionary.addTypes(redwall_quarry, BiomeDictionary.Type.HOT, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.DRY, BiomeDictionary.Type.SPOOKY);
		BiomeDictionary.addTypes(redwall_forest_southsward, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE);
		BiomeDictionary.addTypes(redwall_hills_southsward, BiomeDictionary.Type.HILLS);
		BiomeDictionary.addTypes(redwall_deeplough, BiomeDictionary.Type.WATER, BiomeDictionary.Type.SPOOKY);
		BiomeDictionary.addTypes(redwall_northlands, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.COLD, BiomeDictionary.Type.WASTELAND);
		BiomeDictionary.addTypes(redwall_northlands_hills, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.COLD, BiomeDictionary.Type.WASTELAND);
		BiomeDictionary.addTypes(redwall_noonvale, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.LUSH);
		BiomeDictionary.addTypes(redwall_noonvale_forest, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.LUSH);
		BiomeDictionary.addTypes(redwall_noonvale_hills, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.LUSH);
	}
	
	public static int getColorForBiomeFromRGB(Color color) {
        int finalColor = ((int)color.getRed() << 8) + (int)color.getGreen();
        finalColor = (finalColor << 8) + (int)color.getBlue();
        return finalColor;
	}
}
