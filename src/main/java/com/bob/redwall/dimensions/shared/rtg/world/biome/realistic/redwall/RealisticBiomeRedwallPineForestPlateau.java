package com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.util.BlockUtil;
import com.bob.redwall.dimensions.shared.rtg.api.util.CliffCalculator;
import com.bob.redwall.dimensions.shared.rtg.api.util.PlateauStep;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.OpenSimplexNoise;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.SimplexOctave;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection.DecoCollectionForestPine;
import com.bob.redwall.dimensions.shared.rtg.api.world.surface.SurfaceBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.TerrainBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.heighteffect.VoronoiPlateauEffect;
import com.bob.redwall.init.BiomeHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class RealisticBiomeRedwallPineForestPlateau extends RealisticBiomeRedwallBase {

    public static Biome biome = BiomeHandler.redwall_pines_plateau;
    public static Biome river = BiomeHandler.redwall_river;

    public RealisticBiomeRedwallPineForestPlateau() {

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
        return new SurfaceVanillaForest(config, Blocks.GRASS.getDefaultState(), Blocks.DIRT.getDefaultState(), 0f, 1.5f, 60f, 65f, 1.5f, Blocks.SAND.getDefaultState(), 0.5f, Blocks.GRAVEL.getDefaultState(), 0.25f, BlockUtil.getStateDirt(1), 0.2f);
    }

    public class SurfaceVanillaForest extends SurfaceBase {
        private float min;

        private float sCliff = 1.5f;
        private float sHeight = 60f;
        private float sStrength = 65f;
        private float cCliff = 1.5f;

        private IBlockState mixBlock;
        private float mixHeight;
        private IBlockState mix2Block;
        private float mix2Height;
        private IBlockState mix3Block;
        private float mix3Height;

        public SurfaceVanillaForest(BiomeConfig config, IBlockState top, IBlockState fill, float minCliff, float stoneCliff,
                                    float stoneHeight, float stoneStrength, float clayCliff, IBlockState mix, float mixHeight, IBlockState mix2, float mix2Height, IBlockState mix3, float mix3Height) {

            super(config, top, fill);
            min = minCliff;

            sCliff = stoneCliff;
            sHeight = stoneHeight;
            sStrength = stoneStrength;
            cCliff = clayCliff;

            this.mixBlock = this.getConfigBlock(config.SURFACE_MIX_BLOCK.get(), config.SURFACE_MIX_BLOCK_META.get(), mix);
            this.mixHeight = mixHeight;
            this.mix2Block = this.getConfigBlock(config.SURFACE_MIX_2_BLOCK.get(), config.SURFACE_MIX_2_BLOCK_META.get(), mix2);
            this.mix2Height = mix2Height;
            this.mix3Block = mix3;
            this.mix3Height = mix3Height;
        }

        @Override
        public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, IRTGWorld rtgWorld, float[] noise, float river, Biome[] base) {
            Random rand = rtgWorld.rand();
            OpenSimplexNoise simplex = rtgWorld.simplex();
            float c = CliffCalculator.calc(x, z, noise);
            int cliff = 0;
            Block b;
            
            for (int k = 255; k > -1; k--) {
                b = primer.getBlockState(x, k, z).getBlock();
                if (b == Blocks.AIR) {
                    depth = -1;
                } else if (b == Blocks.STONE) {
                    depth++;

                    if (depth == 0) {

                        float p = simplex.noise3(i / 8f, j / 8f, k / 8f) * 0.5f;
                        if (c > min && c > sCliff - ((k - sHeight) / sStrength) + p) {
                            cliff = 1;
                        }
                        if (c > cCliff) {
                            cliff = 2;
                        }

                        if (cliff == 1) {
                            if (rand.nextInt(3) == 0) {
                                primer.setBlockState(x, k, z, hcCobble(rtgWorld, i, j, x, z, k));
                            } else {
                                primer.setBlockState(x, k, z, hcStone(rtgWorld, i, j, x, z, k));
                            }
                        } else if (cliff == 2) {
                            primer.setBlockState(x, k, z, getShadowStoneBlock(rtgWorld, i, j, x, z, k));
                        } else if (k < 63) {
                            if (k < 62) {
                                primer.setBlockState(x, k, z, fillerBlock);
                            } else {
                                primer.setBlockState(x, k, z, topBlock);
                            }
                        } else {
                            float mixRandom = rand.nextFloat();

                            if(mixRandom < mix3Height) {
                                primer.setBlockState(x, k, z, mix3Block);
                            } else if (mixRandom < mix2Height) {
                                primer.setBlockState(x, k, z, mix2Block);
                            } else if (mixRandom < mixHeight) {
                                primer.setBlockState(x, k, z, mixBlock);
                            } else {
                                primer.setBlockState(x, k, z, topBlock);
                            }
                        }
                    } else if (depth < 6) {
                        if (cliff == 1) {
                            primer.setBlockState(x, k, z, hcStone(rtgWorld, i, j, x, z, k));
                        } else if (cliff == 2) {
                            primer.setBlockState(x, k, z, Blocks.SANDSTONE.getDefaultState());
                        } else {
                            primer.setBlockState(x, k, z, fillerBlock);
                        }
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
        this.addDecoCollection(new DecoCollectionForestPine(this.getConfig()));
    }
}
