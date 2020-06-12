package com.bob.redwall.dimensions.shared.rtg.api.world.deco;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.REED;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.biome.IRealisticBiome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

/**
 * @author WhichOnesPink
 */
public class DecoReed extends DecoBase {
    private float strengthFactor;
    private int maxY;
    private int loops;

    public DecoReed() {
        super();

        /*
         * Default values.
         * These can be overridden when configuring the Deco object in the realistic biome.
         */
        this.setMaxY(255); // No height limit by default.
        this.setStrengthFactor(0f); // Not sure why it was done like this, but... the higher the value, the more there will be.
        this.setLoops(1);

        this.addDecoTypes(DecoType.REED);
    }

    @Override
    public void generate(IRealisticBiome biome, IRTGWorld rtgWorld, Random rand, int worldX, int worldZ, float strength, float river, boolean hasPlacedVillageBlocks) {
        if (this.allowed) {
            if (TerrainGen.decorate(rtgWorld.world(), rand, new ChunkPos(new BlockPos(worldX, 0, worldZ)), REED)) {
                WorldGenerator worldGenerator = new WorldGenReed();

                this.setLoops((this.strengthFactor > 0f) ? (int) (this.strengthFactor * strength) : this.loops);
                for (int i = 0; i < this.loops; i++) {
                    int intX = worldX + rand.nextInt(16) + 8;
                    int intY = rand.nextInt(this.maxY);
                    int intZ = worldZ + rand.nextInt(16) + 8;

                    if (intY <= this.maxY) {
                        worldGenerator.generate(rtgWorld.world(), rand, new BlockPos(intX, intY, intZ));
                    }
                }
            }
        }
    }

    public float getStrengthFactor() {
        return strengthFactor;
    }

    public DecoReed setStrengthFactor(float strengthFactor) {
        this.strengthFactor = strengthFactor;
        return this;
    }

    public int getMaxY() {
        return maxY;
    }

    public DecoReed setMaxY(int maxY) {
        this.maxY = maxY;
        return this;
    }

    public int getLoops() {
        return loops;
    }

    public DecoReed setLoops(int loops) {
        this.loops = loops;
        return this;
    }
}
