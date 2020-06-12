package com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.util.BlockUtil;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.OpenSimplexNoise;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection.DecoCollectionOcean;
import com.bob.redwall.dimensions.shared.rtg.api.world.surface.SurfaceBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.TerrainBase;
import com.bob.redwall.init.BiomeHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class RealisticBiomeRedwallOcean extends RealisticBiomeRedwallBase {
    public static Biome biome = BiomeHandler.redwall_ocean;
    public static Biome river = BiomeHandler.redwall_ocean;

    public RealisticBiomeRedwallOcean() {
        super(biome, river);
    }

    @Override
    public void initConfig() {
        this.getConfig().ALLOW_RIVERS.set(false);
        this.getConfig().ALLOW_SCENIC_LAKES.set(false);
        this.getConfig().ALLOW_VILLAGES.set(false);

        this.getConfig().addProperty(this.getConfig().ALLOW_SPONGE).set(false);
        this.getConfig().addProperty(this.getConfig().ALLOW_OCEAN_WAVES);

        this.getConfig().addProperty(this.getConfig().SURFACE_MIX_BLOCK).set("");
        this.getConfig().addProperty(this.getConfig().SURFACE_MIX_BLOCK_META).set(0);
    }

    @Override
    public TerrainBase initTerrain() {
        return new TerrainVanillaOcean();
    }

    public class TerrainVanillaOcean extends TerrainBase {
        public TerrainVanillaOcean() {

        }

        @Override
        public float generateNoise(IRTGWorld rtgWorld, int x, int y, float border, float river) {
            return terrainOcean(x, y, rtgWorld.simplex(), river, 50f);
        }
    }

    @Override
    public SurfaceBase initSurface() {
        return new SurfaceVanillaOcean(config, Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRAVEL.getDefaultState(), 20f, 0.2f);
    }

    public class SurfaceVanillaOcean extends SurfaceBase {
        private final int sandMetadata = 0;
        private IBlockState mixBlock;
        private float width;
        private float height;
        private float mixCheck;

        public SurfaceVanillaOcean(BiomeConfig config, IBlockState top, IBlockState filler, IBlockState mix, float mixWidth, float mixHeight) {
            super(config, top, filler);

            mixBlock = this.getConfigBlock(config.SURFACE_MIX_BLOCK.get(), config.SURFACE_MIX_BLOCK_META.get(), mix);

            width = mixWidth;
            height = mixHeight;
        }

        @Override
        public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, IRTGWorld rtgWorld, float[] noise, float river, Biome[] base) {
        	OpenSimplexNoise simplex = rtgWorld.simplex();

            for (int k = 255; k > -1; k--) {
                Block b = primer.getBlockState(x, k, z).getBlock();
                if (b == Blocks.AIR) {
                    depth = -1;
                } else if (b == Blocks.STONE) {
                    depth++;

                    if (depth == 0 && k > 0 && k < 63) {
                        mixCheck = simplex.noise2(i / width, j / width);

                        if (mixCheck > height) { // > 0.27f, i / 12f {
                            primer.setBlockState(x, k, z, mixBlock);
                        } else {
                            primer.setBlockState(x, k, z, topBlock);
                        }
                    } else if (depth < 4 && k < 63) {
                        primer.setBlockState(x, k, z, fillerBlock);
                    } else if (depth == 0 && k < 69) {
                        primer.setBlockState(x, k, z, BlockUtil.getStateSand(sandMetadata));
                    }
                }
            }
        }
    }

    @Override
    public void initDecos() {
        this.addDecoCollection(new DecoCollectionOcean(this.getConfig()));
    }

    @Override
    public int waterSurfaceLakeChance() {
        return 0;
    }

    @Override
    public int lavaSurfaceLakeChance() {
        return 0;
    }
    
    @Override
    public boolean isOcean() {
    	return true;
    }
    
    @Override
    public boolean noWaterBelowSeaLevel() {
    	return false;
    }
}
