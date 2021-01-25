package com.bob.redwall.dimensions.shared.rtg.api.world.deco;

import java.util.Random;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.biome.IRealisticBiome;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.WorldGenSandstoneSlab;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * @author WhichOnesPink, Emperor_Luke_II
 */
public class DecoSandstoneSlab extends DecoBase {
    private float strengthFactor;
    private int maxY;
    private int loops;
    private int chance;

    public DecoSandstoneSlab() {
        super();

        /**
         * Default values.
         * These can be overridden when configuring the Deco object in the realistic biome.
         */
        this.setMaxY(255); // No height limit by default.
        this.setStrengthFactor(0f); // Not sure why it was done like this, but... the higher the value, the more there will be.
        this.setLoops(1);
        this.setChance(1);

        this.addDecoTypes(DecoType.DESERT_WELL);
    }

    @Override
    public void generate(IRealisticBiome biome, IRTGWorld rtgWorld, Random rand, int worldX, int worldZ, float strength, float river, boolean hasPlacedVillageBlocks) {
        if (this.allowed) {
            WorldGenerator worldGenerator = new WorldGenSandstoneSlab();

            this.setLoops((this.strengthFactor > 0f) ? (int) (this.strengthFactor * strength) : this.loops);
            for (int i = 0; i < this.loops; i++) {
                if (rand.nextInt(this.chance) == 0) {
                    int intX = worldX + rand.nextInt(16) + 8;
                    int intY = rand.nextInt(this.maxY);
                    int intZ = worldZ + rand.nextInt(16) + 8;
                    ChunkPos cp = new ChunkPos(new BlockPos(intX, intY, intZ));

                    if (intY <= this.maxY && RedwallUtils.isInMossflower(biome.baseBiome(), cp.x, cp.z)) {
                        worldGenerator.generate(rtgWorld.world(), rand, new BlockPos(intX, intY, intZ));
                    }
                }
            }
        }
    }

    public float getStrengthFactor() {
        return strengthFactor;
    }

    public DecoSandstoneSlab setStrengthFactor(float strengthFactor) {
        this.strengthFactor = strengthFactor;
        return this;
    }

    public int getMaxY() {
        return maxY;
    }

    public DecoSandstoneSlab setMaxY(int maxY) {
        this.maxY = maxY;
        return this;
    }

    public int getLoops() {
        return loops;
    }

    public DecoSandstoneSlab setLoops(int loops) {
        this.loops = loops;
        return this;
    }

    public int getChance() {
        return chance;
    }

    public DecoSandstoneSlab setChance(int chance) {
        this.chance = chance;
        return this;
    }
}
