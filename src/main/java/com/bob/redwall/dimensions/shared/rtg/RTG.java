package com.bob.redwall.dimensions.shared.rtg;

import static com.bob.redwall.dimensions.shared.rtg.api.RTGAPI.configPath;
import static com.bob.redwall.dimensions.shared.rtg.api.world.biome.IRealisticBiome.arrRealisticBiomes;
import static com.bob.redwall.dimensions.shared.rtg.api.world.biome.OrganicBiomeGenerator.organicBiomes;

import java.io.File;
import java.util.ArrayList;

import com.bob.redwall.dimensions.ModDimensions;
import com.bob.redwall.dimensions.redwall.WorldTypeRedwall;
import com.bob.redwall.dimensions.redwall.structures.MapGenScatteredFeatureRedwall;
import com.bob.redwall.dimensions.shared.rtg.api.RTGAPI;
import com.bob.redwall.dimensions.shared.rtg.api.config.RTGConfig;
import com.bob.redwall.dimensions.shared.rtg.api.dimension.DimensionManagerRTG;
import com.bob.redwall.dimensions.shared.rtg.api.util.BiomeUtils;
import com.bob.redwall.dimensions.shared.rtg.api.world.biome.IRealisticBiome;
import com.bob.redwall.dimensions.shared.rtg.event.EventManagerRTG;
import com.bob.redwall.dimensions.shared.rtg.reference.ModInfo;
import com.bob.redwall.dimensions.shared.rtg.util.RealisticBiomePresenceTester;
import com.bob.redwall.dimensions.shared.rtg.world.biome.organic.OrganicBiome;
import com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.RealisticBiomeBase;
import com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall.RealisticBiomeRedwallBase;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;

public class RTG {
    public static WorldTypeRedwall worldtype;
    public static EventManagerRTG eventMgr;
    private static ArrayList<Runnable> oneShotServerCloseActions = new ArrayList<>();
    private static ArrayList<Runnable> serverCloseActions = new ArrayList<>();

    public static RTG instance;

    public static void initPre(FMLPreInitializationEvent event) {
        worldtype = new WorldTypeRedwall(ModInfo.WORLD_TYPE);

        DimensionManagerRTG.addRTGDimension(ModDimensions.DIM_REDWALL_ID);

        RTGAPI.configPath = event.getModConfigurationDirectory() + File.separator + ModInfo.CONFIG_DIRECTORY + File.separator;
        RTGAPI.rtgConfig = new RTGConfig();
        RTGAPI.rtgConfig.load(configPath + "rtg.cfg");

        registerStructures();
    }

    public static void init(FMLInitializationEvent event) {
        eventMgr = new EventManagerRTG();
        eventMgr.registerEventHandlers();
    }

    public static void initPost(FMLPostInitializationEvent event) {
    	RealisticBiomeRedwallBase.addBiomes();
    	
        RealisticBiomeBase.addModBiomes();

        initOrganicBiomes();
        
        RealisticBiomePresenceTester.doBiomeCheck();
    }

    public static void onServerStopped(FMLServerStoppedEvent event) {
    	try {
	        serverCloseActions.forEach(Runnable::run);
	        oneShotServerCloseActions.forEach(Runnable::run);
	        oneShotServerCloseActions.clear();
    	} catch(NullPointerException ex) {
    		
    	}
    }

    public static void registerStructures() {
        if (RTGAPI.config().ENABLE_SCATTERED_FEATURE_MODIFICATIONS.get()) {
            MapGenStructureIO.registerStructure(MapGenScatteredFeatureRedwall.Start.class, "rtg_MapGenScatteredFeatureRTG");
        }
    }

    public static void runOnServerClose(Runnable action) {
        serverCloseActions.add(action);
    }

    public static void runOnNextServerCloseOnly(Runnable action) {
        serverCloseActions.add(action);
    }

    public static void initOrganicBiomes() {
        Biome[] b = BiomeUtils.getRegisteredBiomes();
        for (Biome biome : b) {
            if (biome != null) {
                try {
                    arrRealisticBiomes[Biome.getIdForBiome(biome)].baseBiome().getBiomeName();
                } catch (Exception e) {
                    @SuppressWarnings("unused")
					IRealisticBiome organicBiome = new OrganicBiome(biome);
                    organicBiomes[Biome.getIdForBiome(biome)] = true;
                }
            }
        }
    }
}
