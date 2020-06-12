package com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.surface.SurfaceBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.TerrainBase;
import com.bob.redwall.init.BiomeHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class RealisticBiomeRedwallAbyss extends RealisticBiomeRedwallBase {
    public static Biome biome = BiomeHandler.redwall_abyss;
    public static Biome river = BiomeHandler.redwall_river;

    public RealisticBiomeRedwallAbyss() {
        super(biome, river);
    }

    @Override
    public void initConfig() {
        this.getConfig().addProperty(this.getConfig().ALLOW_LOGS).set(true);
        this.getConfig().addProperty(this.getConfig().FALLEN_LOG_DENSITY_MULTIPLIER);

        this.getConfig().addProperty(this.getConfig().SURFACE_MIX_BLOCK).set("");
        this.getConfig().addProperty(this.getConfig().SURFACE_MIX_BLOCK_META).set(0);
        this.getConfig().addProperty(this.getConfig().SURFACE_MIX_2_BLOCK).set("");
        this.getConfig().addProperty(this.getConfig().SURFACE_MIX_2_BLOCK_META).set(0);
    }
    


    @Override
    public TerrainBase initTerrain() {
        return new TerrainAbyss();
    }

    public class TerrainAbyss extends TerrainBase {
        public TerrainAbyss() {
            super(0);
        }

        @Override
        public float generateNoise(IRTGWorld rtgWorld, int x, int y, float border, float river) {
            return 0;
        }
    }

    @Override
    public SurfaceBase initSurface() {
        return new SurfaceAbyss(config, biome.topBlock, biome.fillerBlock);
    }

    public class SurfaceAbyss extends SurfaceBase {
        public SurfaceAbyss(BiomeConfig config, IBlockState top, IBlockState fill) {

            super(config, top, fill);
        }

        @Override
        public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, IRTGWorld rtgWorld, float[] noise, float river, Biome[] base) {
            Block b;
            for (int k = 255; k > -1; k--) {
                b = primer.getBlockState(x, k, z).getBlock();
                if(b != Blocks.BEDROCK) {
                	primer.setBlockState(x, k, z, Blocks.AIR.getDefaultState());
                }
            }
        }
    }

    @Override
    public int waterSurfaceLakeChance() {
        return 100;
    }
    
    @Override
    public boolean noWaterBelowSeaLevel() {
    	return true;
    }

    @Override
    public void initDecos() {}
}
