package com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBaseBiomeDecorations;
import com.bob.redwall.dimensions.shared.rtg.api.world.surface.SurfaceBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.TerrainBase;
import com.bob.redwall.init.BiomeHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class RealisticBiomeRedwallGravelBeach extends RealisticBiomeRedwallBase {

    public static Biome biome = BiomeHandler.redwall_gravel_beach;
    public static Biome river = BiomeHandler.redwall_river;

    public RealisticBiomeRedwallGravelBeach() {
        super(biome, river);
    }

    @Override
    public void initConfig() {
        this.getConfig().SURFACE_BLEED_IN.set(true);
        this.getConfig().SURFACE_BLEED_OUT.set(true);
        this.getConfig().ALLOW_VILLAGES.set(false);

        this.getConfig().addProperty(this.getConfig().SURFACE_MIX_FILLER_BLOCK).set("");
        this.getConfig().addProperty(this.getConfig().SURFACE_MIX_FILLER_BLOCK_META).set(0);
    }

    @Override
    public TerrainBase initTerrain() {
        return new TerrainVanillaStoneBeach();
    }

    public class TerrainVanillaStoneBeach extends TerrainBase {
        public TerrainVanillaStoneBeach() {

        }

        @Override
        public float generateNoise(IRTGWorld rtgWorld, int x, int y, float border, float river) {
            return terrainBeach(x, y, rtgWorld.simplex(), river, 180f, 35f, 63f);
        }
    }

    @Override
    public SurfaceBase initSurface() {
        return new SurfaceVanillaBeach(config, Blocks.GRAVEL.getDefaultState(), Blocks.GRAVEL.getDefaultState());
    }

    public class SurfaceVanillaBeach extends SurfaceBase {
        private IBlockState mixBlockFill;

        public SurfaceVanillaBeach(BiomeConfig config, IBlockState top, IBlockState filler) {
            super(config, top, filler);
            mixBlockFill = this.getConfigBlock(config.SURFACE_MIX_FILLER_BLOCK.get(), config.SURFACE_MIX_FILLER_BLOCK_META.get(), Blocks.GRAVEL.getDefaultState());
        }

        @Override
        public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, IRTGWorld rtgWorld, float[] noise, float river, Biome[] base) {
            for (int k = 255; k > -1; k--) {
                Block b = primer.getBlockState(x, k, z).getBlock();

                if (b == Blocks.AIR) {
                    depth = -1;
                } else if (b == Blocks.STONE) {
                    depth++;

                    if (depth == 0 && k > 61) {
                        primer.setBlockState(x, k, z, topBlock);
                    } else if (k > 63 && depth > 3 && depth < 6) {
                        primer.setBlockState(x, k, z, mixBlockFill);
                    } else if (depth < 4) {
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
    }
}
