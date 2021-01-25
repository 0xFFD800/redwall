package com.bob.redwall.biomes.icy;

import java.util.Random;

import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenIcePath;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

public class BiomeRedwallArctic extends Biome {
	 private final boolean superIcy;
	    private final WorldGenIcePath icePatch = new WorldGenIcePath(4);

	    public BiomeRedwallArctic(boolean superIcyIn, Biome.BiomeProperties properties) {
	        super(properties);
	        this.superIcy = superIcyIn;

	        if (superIcyIn) {
	            this.topBlock = Blocks.SNOW.getDefaultState();
	        }

	        this.spawnableCreatureList.clear();
	        this.spawnableMonsterList.clear();
	        this.spawnableCaveCreatureList.clear();
	        this.spawnableWaterCreatureList.clear();
	    }

	    @Override
	    public float getSpawningChance() {
	        return RedwallWorldProvider.NPC_SPAWN_CHANCE_WORLDGEN / 1.5F;
	    }

	    @Override
	    @SuppressWarnings("deprecation")
		public void decorate(World worldIn, Random rand, BlockPos pos) {
	        if (this.superIcy && net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, pos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.ICE)) {
	            for (int l = 0; l < 2; ++l) {
	                int i1 = rand.nextInt(16) + 8;
	                int j1 = rand.nextInt(16) + 8;
	                this.icePatch.generate(worldIn, rand, worldIn.getHeight(pos.add(i1, 0, j1)));
	            }
	        }

	        super.decorate(worldIn, rand, pos);
	    }

	    public WorldGenAbstractTree genBigTreeChance(Random rand) {
	        return new WorldGenTaiga2(false);
	    }
}
