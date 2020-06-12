package com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.util.PlateauStep;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.OpenSimplexNoise;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.SimplexOctave;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection.DecoCollectionDesert;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection.DecoCollectionDesertRiver;
import com.bob.redwall.dimensions.shared.rtg.api.world.surface.SurfaceBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.TerrainBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.heighteffect.VoronoiPlateauEffect;
import com.bob.redwall.init.BiomeHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class RealisticBiomeRedwallDesertPlateau extends RealisticBiomeRedwallBase {
    public static Biome biome = BiomeHandler.redwall_desert_plateau;
    public static Biome river = BiomeHandler.redwall_river;

    public RealisticBiomeRedwallDesertPlateau() {

        super(biome, river);

        // Prevent ores from messing up the surface.
        this.rDecorator().graniteSize = 0;
        this.rDecorator().dioriteSize = 0;
        //this.rDecorator().andesiteSize = 0; // This looks good.
        //this.rDecorator().gravelSize = 0; // So does this.
        this.rDecorator().dirtSize = 0;
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
        return new TerrainRTGMesaPlateau(67);
        //return new TerrainVanillaMesaPlateau(true, 35f, 160f, 60f, 40f, 69f);
    }

    public static class TerrainRTGMesaPlateau extends TerrainBase {
        final PlateauStep step;
        final VoronoiPlateauEffect plateau;
        final int groundNoise;
        private SimplexOctave.Disk jitter = new SimplexOctave.Disk();
        private float jitterWavelength = 30;
        private float jitterAmplitude = 10;
        
        public TerrainRTGMesaPlateau(float base) {
            plateau = new VoronoiPlateauEffect();
            step = new PlateauStep();
            step.finish = 0.4f;
            step.start = 0.25f;
            plateau.pointWavelength = 200;
            this.base = base;
            groundNoise = 4;
        }
        

         
        @Override
        public float generateNoise(IRTGWorld rtgWorld, int passedX, int passedY, float border, float river) {
            rtgWorld.simplex().riverJitter().evaluateNoise((float) passedX / jitterWavelength, (float) passedY / jitterWavelength, jitter);
            float x = (float)(passedX + jitter.deltax() * jitterAmplitude);
            float y = (float)(passedY + jitter.deltay() * jitterAmplitude);
            //if (simplex > bordercap) simplex = bordercap;
            float added = 64F / (border + 0.001F);
            float next = riverized(base + TerrainBase.groundNoise(x, y, groundNoise, rtgWorld.simplex()), river);
            return next + added;
        }
    }

    @Override
    public SurfaceBase initSurface() {

        return new SurfaceVanillaDesert(config, biome.topBlock, biome.fillerBlock);
    }

    public class SurfaceVanillaDesert extends SurfaceBase {

        public SurfaceVanillaDesert(BiomeConfig config, IBlockState top, IBlockState fill) {

            super(config, top, fill);
        }

        @Override
        public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, IRTGWorld rtgWorld, float[] noise, float river, Biome[] base) {

            Random rand = rtgWorld.rand();
            OpenSimplexNoise simplex = rtgWorld.simplex();
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
    public int waterSurfaceLakeChance() {
        return 100;
    }
    
    @Override
    public boolean noWaterBelowSeaLevel() {
    	return true;
    }

    @Override
    public void initDecos() {

        this.addDecoCollection(new DecoCollectionDesertRiver(this.getConfig()));
        this.addDecoCollection(new DecoCollectionDesert(this.getConfig()));
    }
}
