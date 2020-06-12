
package com.bob.redwall.dimensions.shared.rtg.world.gen;

import com.bob.redwall.init.BiomeHandler;

import net.minecraft.world.biome.Biome;

/**
 *
 * @author Zeno410 & Emperor_Luke_II
 */
public class QuarryBiomeCombiner {
    public final int quarry = Biome.getIdForBiome(BiomeHandler.redwall_quarry);
    public final int plains = Biome.getIdForBiome(BiomeHandler.redwall_plains);
    public final int forest = Biome.getIdForBiome(BiomeHandler.redwall_forest);
    
    public void adjust(float[] result) {
        float quarryBorder = result[quarry];
        float plainsBorder = result[plains];
        float forestBorder = result[forest];
        if (plainsBorder > quarryBorder) {
            result[quarry] = 0;
            result[plains] = quarryBorder + plainsBorder;
        } else if(forestBorder > quarryBorder) {
            result[quarry] = 0;
            result[forest] = quarryBorder + forestBorder;
        } else {
            result[quarry] = quarryBorder + plainsBorder + forestBorder;
            result[plains] = 0;
        }
    }
}
