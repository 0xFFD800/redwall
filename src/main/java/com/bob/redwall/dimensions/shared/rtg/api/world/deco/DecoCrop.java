package com.bob.redwall.dimensions.shared.rtg.api.world.deco;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.util.WorldUtil;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.biome.IRealisticBiome;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.WorldGenCrops;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * @author lightningo7
 */
public class DecoCrop extends DecoBase {

    private int type; // This can the number 0,1,2,3.
    private int size; //Higher = larger fields.
    private int density; //Higher = Crops in fields closer together.
    private int height; //Higher = Crops on more y levels - When higher tends to be less dense.
    private float strengthFactor; // Higher = More frequent spawns.
    private int minY; // Lower height restriction.
    private int maxY; // Upper height restriction.
    private int chance; // Higher = more rare.
    private boolean water;
    private Block block;
    private boolean farmland;

    public DecoCrop() {

        super();

        /*
         * Default values.
         * These can be overridden when configuring the Deco object in the realistic biome.
         */
        this.type = 3;
        this.size = 3;//DO NOT PUT HIGHER THAN 30
        this.density = 5;
        this.height = 2;
        this.setStrengthFactor(2f);
        this.setMinY(63); // Sensible lower height limit by default.
        this.setMaxY(255); // No upper height limit by default.
        this.setChance(25); //The higher the number the less common it will be
        this.water = true; //whether or not to spawn water with the crops
        this.block = null;
        this.farmland = false;

        this.addDecoTypes(DecoType.WHEAT);
    }

    @Override
    public void generate(IRealisticBiome biome, IRTGWorld rtgWorld, Random rand, int worldX, int worldZ, float strength, float river, boolean hasPlacedVillageBlocks) {

        if (this.allowed) {

            WorldUtil worldUtil = new WorldUtil(rtgWorld.world());
            WorldGenerator worldGenerator = new WorldGenCrops(type, size, density, height, water, this.block, this.farmland);

            if (this.chance < 1) {
                return;
            }

            for (int l1 = 0; l1 < this.strengthFactor * strength; ++l1) {
                int i1 = worldX + rand.nextInt(16);// + 8;
                int j1 = worldZ + rand.nextInt(16);// + 8;
                int k1 = rtgWorld.world().getHeight(new BlockPos(i1, 0, j1)).getY();

                if (k1 >= this.minY && k1 <= this.maxY && rand.nextInt(this.chance) == 0) {

                    // If we're in a village, check to make sure the boulder has extra room to grow to avoid corrupting the village.
                    if (hasPlacedVillageBlocks) {
                        if (!worldUtil.isSurroundedByBlock(Blocks.AIR.getDefaultState(), 2, WorldUtil.SurroundCheckType.CARDINAL, rand, i1, k1, j1)) {
                            return;
                        }
                    }

                    worldGenerator.generate(rtgWorld.world(), rand, new BlockPos(i1, k1, j1));
                }
            }
        }
    }

    public int getType() {

        return type;
    }

    public DecoCrop setType(int type) {

        this.type = type;
        return this;
    }

    public int getSize() {

        return size;
    }

    public DecoCrop setSize(int size) {

        this.size = size;
        return this;
    }

    public int getDensity() {

        return density;
    }

    public DecoCrop setDensity(int density) {

        this.density = density;
        return this;
    }

    public int getHeight() {

        return height;
    }

    public DecoCrop setHeight(int height) {

        this.height = height;
        return this;
    }

    public float getStrengthFactor() {

        return strengthFactor;
    }

    public DecoCrop setStrengthFactor(float strengthFactor) {

        this.strengthFactor = strengthFactor;
        return this;
    }

    public int getMinY() {

        return minY;
    }

    public DecoCrop setMinY(int minY) {

        this.minY = minY;
        return this;
    }

    public int getMaxY() {

        return maxY;
    }

    public DecoCrop setMaxY(int maxY) {

        this.maxY = maxY;
        return this;
    }

    public int getChance() {

        return chance;
    }

    public DecoCrop setChance(int chance) {

        this.chance = chance;
        return this;
    }

    public boolean isWater() {

        return water;
    }

    public DecoCrop setWater(boolean water) {

        this.water = water;
        return this;
    }

	public DecoCrop setBlock(Block blackberry_bush) {
		this.block = blackberry_bush;
		return this;
	}
	
	public DecoCrop setFarmland(boolean farmland) {
		this.farmland = farmland;
		return this;
	}
}
