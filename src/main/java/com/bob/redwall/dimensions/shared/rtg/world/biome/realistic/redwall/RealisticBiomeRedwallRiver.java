package com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.OpenSimplexNoise;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBaseBiomeDecorations;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoWaterReed;
import com.bob.redwall.dimensions.shared.rtg.api.world.surface.SurfaceBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.TerrainBase;
import com.bob.redwall.init.BiomeHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class RealisticBiomeRedwallRiver extends RealisticBiomeRedwallBase {
    public static Biome biome = BiomeHandler.redwall_river;
    public static Biome river = BiomeHandler.redwall_river;

    public RealisticBiomeRedwallRiver() {
        super(biome, river);
    }

    @Override
    public void initConfig() {
        this.getConfig().ALLOW_VILLAGES.set(false);
    }

    @Override
    public TerrainBase initTerrain() {
        return new TerrainVanillaRiver();
    }

    public class TerrainVanillaRiver extends TerrainBase {
        public TerrainVanillaRiver() {

        }

        @Override
        public float generateNoise(IRTGWorld rtgWorld, int x, int y, float border, float river) {
            return terrainFlatLakes(x, y, rtgWorld.simplex(), river, 3f, 50f);
        }
    }

    @Override
    public SurfaceBase initSurface() {
    	return new SurfaceVanillaRiver(config, Blocks.GRAVEL.getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.CLAY.getDefaultState(), 20f, 0.1f);
    }

    public class SurfaceVanillaRiver extends SurfaceBase {
        private IBlockState mixBlock;
        private float width;
        private float height;
        private float mixCheck;

        public SurfaceVanillaRiver(BiomeConfig config, IBlockState top, IBlockState filler, IBlockState mix, float mixWidth, float mixHeight) {
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

                        if (mixCheck > height) {
                            primer.setBlockState(x, k, z, mixBlock);
                        } else {
                            primer.setBlockState(x, k, z, topBlock);
                        }
                    } else if (depth < 4 && k < 63) {
                        primer.setBlockState(x, k, z, fillerBlock);
                    }
                }
            }
        }
    }

    @Override
    public void initDecos() {
        DecoBaseBiomeDecorations decoBaseBiomeDecorations = new DecoBaseBiomeDecorations();
        this.addDeco(decoBaseBiomeDecorations);

        DecoWaterReed decoReed = new DecoWaterReed();
        decoReed.setMinRiver(0.7f);
        decoReed.setMaxY(68);
        decoReed.setLoops(3);
        this.addDeco(decoReed);
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
    public boolean noWaterBelowSeaLevel() {
    	return false;
    }
}
