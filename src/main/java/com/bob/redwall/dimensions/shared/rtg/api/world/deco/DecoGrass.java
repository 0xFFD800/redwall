package com.bob.redwall.dimensions.shared.rtg.api.world.deco;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.biome.IRealisticBiome;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.WorldGenGrass;
import com.bob.redwall.init.BlockHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.terraingen.TerrainGen;

/*
 * @author WhichOnesPink
 */
public class DecoGrass extends DecoBase {
    private float strengthFactor;
    private int minY;
    private int maxY;
    private int loops;
    private int chance;
    private int notEqualsZeroChance;
    private IBlockState[] randomGrassBlocks;
    private byte[] randomGrassMetas;
    private int[] randomChances;

    private boolean useRandomGrass;
    private static final int MAX_LOOPS = 10;

    private IBlockState block;
    private int meta;
    private WorldGenGrass grassGenerator;

    public DecoGrass() {
        super();

        /*
         * Default values.
         * These can be overridden when configuring the Deco object in the realistic biome.
         */
        this.setMinY(1); // No height limit by default.
        this.setMaxY(255); // No height limit by default.
        this.setStrengthFactor(0f); // Not sure why it was done like this, but... the higher the value, the more there will be.
        this.setLoops(1);
        this.setChance(1);
        this.notEqualsZeroChance = 1;
        this.block = Blocks.TALLGRASS.getStateFromMeta(1);
        this.meta = 1;
        this.randomGrassBlocks = new IBlockState[]{Blocks.TALLGRASS.getStateFromMeta(1), Blocks.DOUBLE_PLANT.getStateFromMeta(2), BlockHandler.wheatgrass.getDefaultState(), BlockHandler.shortgrass.getDefaultState()};
        this.randomGrassMetas = new byte[]{1, 2, 0, 0};
        this.randomChances = new int[] {10, 5, 1, 20};
        this.useRandomGrass = (this.randomGrassBlocks.length > 0 && this.randomGrassBlocks.length == this.randomGrassMetas.length);

        this.addDecoTypes(DecoType.GRASS);
        this.grassGenerator = new WorldGenGrass.RandomType(this.randomGrassBlocks, this.randomGrassMetas, this.randomChances);
    }

    public DecoGrass(int meta) {
        this();
        this.meta = meta;
        this.grassGenerator = new WorldGenGrass.SingleType(this.block, meta);
    }

    public DecoGrass(IBlockState block) {
        this();
        this.block = block;
        this.grassGenerator = new WorldGenGrass.SingleType(block, this.meta);
    }

    public DecoGrass(IBlockState[] randomBlocks, byte[] randomMetas, int[] chances) {
        this();
        if (randomBlocks.length != randomMetas.length || randomBlocks.length != chances.length) {
            throw new RuntimeException("Mismatch in block/metadata/chances arrays for DecoGrass");
        }
        this.randomGrassBlocks = randomBlocks;
        this.randomGrassMetas = randomMetas;
        this.randomChances = chances;
        this.grassGenerator = new WorldGenGrass.RandomType(randomGrassBlocks, randomGrassMetas, chances);
    }

    @SuppressWarnings("deprecation")
	@Override
    public void generate(IRealisticBiome biome, IRTGWorld rtgWorld, Random rand, int worldX, int worldZ, float strength, float river, boolean hasPlacedVillageBlocks) {
        if (this.allowed) {
            if (TerrainGen.decorate(rtgWorld.world(), rand, new BlockPos(worldX, 0, worldZ), GRASS)) {
                this.setLoops((this.strengthFactor > 0f) ? (int) (this.strengthFactor * strength) : this.loops);
                this.setLoops((this.loops > DecoGrass.MAX_LOOPS) ? DecoGrass.MAX_LOOPS : this.loops);
                for (int i = 0; i < this.loops * 64; i++) {
                    int intX = worldX + rand.nextInt(16);// + 8;
                    int intY = this.minY + (rand.nextInt(this.maxY - this.minY) + 1);
                    int intZ = worldZ + rand.nextInt(16);// + 8;

                    //Do we want to choose a random grass?
                    if (this.useRandomGrass) {
                        //this.block = this.randomGrassBlocks[rand.nextInt(this.randomGrassBlocks.length)];
                        //this.meta = this.randomGrassMetas[rand.nextInt(this.randomGrassMetas.length)];
                    }

                    if (this.notEqualsZeroChance > 1) {
                        if (intY >= this.minY && intY <= this.maxY && rand.nextInt(this.notEqualsZeroChance) != 0) {
                            this.grassGenerator.generate(rtgWorld.world(), rand, new BlockPos(intX, intY, intZ));
                        }
                    } else {
                        if (intY >= this.minY && intY <= this.maxY && rand.nextInt(this.chance) == 0) {
                            this.grassGenerator.generate(rtgWorld.world(), rand, new BlockPos(intX, intY, intZ));
                        }
                    }
                }
            }
        }
    }

    public float getStrengthFactor() {
        return strengthFactor;
    }

    public DecoGrass setStrengthFactor(float strengthFactor) {
        this.strengthFactor = strengthFactor;
        return this;
    }

    public int getMinY() {
        return minY;
    }

    public DecoGrass setMinY(int minY) {
        this.minY = minY;
        return this;
    }

    public int getMaxY() {
        return maxY;
    }

    public DecoGrass setMaxY(int maxY) {
        this.maxY = maxY;
        return this;
    }

    public int getLoops() {
        return loops;
    }

    public DecoGrass setLoops(int loops) {
        this.loops = loops;
        return this;
    }

    public int getChance() {
        return chance;
    }

    public DecoGrass setChance(int chance) {
        this.chance = chance;
        return this;
    }

    public int getNotEqualsZerochance() {
        return notEqualsZeroChance;
    }

    public DecoGrass setNotEqualsZerochance(int notEqualsZeroChance) {
        this.notEqualsZeroChance = notEqualsZeroChance;
        return this;
    }

    public IBlockState[] getRandomGrassBlocks() {
        return randomGrassBlocks;
    }

    public DecoGrass setRandomGrassBlocks(IBlockState[] randomGrassBlocks) {
        this.randomGrassBlocks = randomGrassBlocks;
        return this;
    }

    public byte[] getRandomGrassMetas() {
        return randomGrassMetas;
    }

    public DecoGrass setRandomGrassMetas(byte[] randomGrassMetas) {
        this.randomGrassMetas = randomGrassMetas;
        return this;
    }
}
