package com.bob.redwall.dimensions.shared.rtg.api.util;

import static net.minecraft.world.biome.Biome.REGISTRY;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeUtils {

    //TODO: Read this from somewhere
    private static Biome[] registeredBiomes = new Biome[256];

    static {
        for (Biome b : Biome.REGISTRY) {
            registeredBiomes[getId(b)] = b;
        }
    }

    public static Biome[] getRegisteredBiomes() {
        return registeredBiomes;
    }

    public static int biomeIds() {
        return 256;
    }

    public static ResourceLocation getLocForBiome(Biome biome) {
        return REGISTRY.getNameForObject(biome);
    }

    public static Biome getBiomeForLoc(ResourceLocation location) {
        return REGISTRY.getObject(location);
    }

    public static int getId(Biome biome) {
        return Biome.getIdForBiome(biome);
    }

    public static Biome getPreferredBeachForBiome(Biome biome) {
        return biome;
    }

    @SuppressWarnings("unused")
	private static boolean isTaigaBiome(Biome biome) {
        return BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD)
            && BiomeDictionary.hasType(biome, BiomeDictionary.Type.CONIFEROUS)
            && BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)
            && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.SNOWY);
    }
}
