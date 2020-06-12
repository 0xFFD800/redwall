package com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.OpenSimplexNoise;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection.DecoCollectionDesert;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection.DecoCollectionDesertRiver;
import com.bob.redwall.dimensions.shared.rtg.api.world.surface.SurfaceBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.TerrainBase;
import com.bob.redwall.init.BiomeHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class RealisticBiomeValourDesert extends RealisticBiomeRedwallBase {

    public static Biome biome = BiomeHandler.redwall_desert;
    public static Biome river = BiomeHandler.redwall_river;

    public RealisticBiomeValourDesert() {

        super(biome, river);
    }

    @Override
    public void initConfig() {

        this.getConfig().ALLOW_SCENIC_LAKES.set(false);
        this.getConfig().SURFACE_FILLER_BLOCK.set("minecraft:sandstone");
        this.getConfig().SURFACE_FILLER_BLOCK_META.set(0);

        this.getConfig().addProperty(this.getConfig().ALLOW_CACTUS).set(true);
    }

    @Override
    public TerrainBase initTerrain() {

        return new TerrainVanillaDesert();
    }

    public class TerrainVanillaDesert extends TerrainBase {

        public TerrainVanillaDesert() {

            super(64);
        }

        @Override
        public float generateNoise(IRTGWorld rtgWorld, int x, int y, float border, float river) {
            //return terrainPolar(x, y, simplex, river);
            float duneHeight = (minDuneHeight + (float) rtgConfig.DUNE_HEIGHT.get());

            duneHeight *= (1f + rtgWorld.simplex().octave(2).noise2((float) x / 330f, (float) y / 330f)) / 2f;

            float stPitch = 200f;    // The higher this is, the more smoothly dunes blend with the terrain
            float stFactor = duneHeight;
            float hPitch = 70;    // Dune scale
            float hDivisor = 40;

            return terrainPolar(x, y, rtgWorld.simplex(), river, stPitch, stFactor, hPitch, hDivisor, base) +
                groundNoise(x, y, 1f, rtgWorld.simplex());
        }
    }

    @Override
    public SurfaceBase initSurface() {

        return new SurfaceVanillaDesert(config, biome.topBlock, biome.fillerBlock);
    }

    @Override
    public void rReplace(ChunkPrimer primer, int i, int j, int x, int y, int depth, IRTGWorld rtgWorld, float[] noise, float river, Biome[] base) {

        this.rReplaceWithRiver(primer, i, j, x, y, depth, rtgWorld, noise, river, base);
    }

    public class SurfaceVanillaDesert extends SurfaceBase {

        public SurfaceVanillaDesert(BiomeConfig config, IBlockState top, IBlockState fill) {

            super(config, top, fill);
        }

        @Override
        public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, IRTGWorld rtgWorld, float[] noise, float river, Biome[] base) {

            Random rand = rtgWorld.rand();
            OpenSimplexNoise simplex = rtgWorld.simplex();
            boolean water = false;
            boolean riverPaint = false;
            boolean grass = false;

            if (river > 0.05f && river + (simplex.noise2(i / 10f, j / 10f) * 0.1f) > 0.86f) {
                riverPaint = true;

                if (simplex.noise2(i / 12f, j / 12f) > 0.25f) {
                    grass = true;
                }
            }

            Block b;
            for (int k = 255; k > -1; k--) {
                b = primer.getBlockState(x, k, z).getBlock();
                if (b == Blocks.AIR) {
                    depth = -1;
                }
                else if (b == Blocks.STONE) {
                    depth++;

                    if (riverPaint) {
                        if (grass && depth < 4) {
                            //primer.setBlockState(x, k, z, Blocks.GRASS.getDefaultState());
                            primer.setBlockState(x, k, z, fillerBlock);
                        }
                        else if (depth == 0) {
                            primer.setBlockState(x, k, z, rand.nextInt(2) == 0 ? topBlock : Blocks.SANDSTONE.getDefaultState());
                        }
                    }
                    else if (depth > -1 && depth < 5) {
                        primer.setBlockState(x, k, z, topBlock);
                    }
                    else if (depth < 8) {
                        primer.setBlockState(x, k, z, fillerBlock);
                    }
                }
            }
        }
    }

    @Override
    public void initDecos() {

        this.addDecoCollection(new DecoCollectionDesertRiver(this.getConfig()));
        this.addDecoCollection(new DecoCollectionDesert(this.getConfig()));
    }

    @Override
    public int waterSurfaceLakeChance() {
        return 0;
    }
    
    @Override
    public boolean noWaterBelowSeaLevel() {
    	return true;
    }
}
