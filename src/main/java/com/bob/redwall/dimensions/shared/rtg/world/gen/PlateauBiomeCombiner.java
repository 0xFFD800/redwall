
package com.bob.redwall.dimensions.shared.rtg.world.gen;

import com.bob.redwall.init.BiomeHandler;

import net.minecraft.world.biome.Biome;

/**
 *
 * @author Zeno410 & Emperor_Luke_II
 */
public class PlateauBiomeCombiner {
    public final int mesa = Biome.getIdForBiome(BiomeHandler.redwall_abyss);
    public final int mesaPlateau = Biome.getIdForBiome(BiomeHandler.redwall_desert_plateau);
    public final int mesaPlateauF = Biome.getIdForBiome(BiomeHandler.redwall_pines_plateau);
    public final int mesaPlateauM = Biome.getIdForBiome(BiomeHandler.redwall_river_plateau);
    public final int mesaPlateauFM = Biome.getIdForBiome(BiomeHandler.redwall_scrubland_plateau);
    
    public void adjust(float[] result) {
        float mesaBorder = result[mesa];
        float plateauBorder = result[mesaPlateau] + result[mesaPlateauM];
        float plateauFBorder = result[mesaPlateauF] + result[mesaPlateauFM];
        result[mesa] = 0;
        result[mesaPlateauM] = 0;
        result[mesaPlateauFM] = 0;
        if (plateauBorder >plateauFBorder) {
            result[mesaPlateau] = mesaBorder + plateauBorder + plateauFBorder;
            result[mesaPlateauF] = 0;
        } else {
            result[mesaPlateau] = 0;
            result[mesaPlateauF] = mesaBorder + plateauBorder + plateauFBorder;
        }
    }
}
