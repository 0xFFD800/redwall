package com.bob.redwall.dimensions.redwall.structures;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.biomes.cool.BiomeRedwallForest;
import com.bob.redwall.dimensions.redwall.structures.guosim.WorldGenGuosimCamp;
import com.bob.redwall.dimensions.redwall.structures.guosim.WorldGenGuosimCookingTent;
import com.bob.redwall.dimensions.redwall.structures.mossflower.WorldGenGroundDwelling;
import com.bob.redwall.dimensions.redwall.structures.mossflower.WorldGenKotirFortAbandoned;
import com.bob.redwall.dimensions.redwall.structures.mossflower.WorldGenKotirTurretAbandoned;
import com.bob.redwall.dimensions.redwall.structures.mossflower.WorldGenKotirTurretInhabited;
import com.bob.redwall.dimensions.redwall.structures.mossflower.WorldGenSquirrelDrey;
import com.bob.redwall.dimensions.shared.rtg.api.util.Logger;
import com.google.common.collect.Sets;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

/**
 * Original Author: Choonster (https://github.com/Choonster) Source:
 * https://github.com/Choonster/TestMod2/blob/1575b85ad8949381215f3aeb6ca76ea2368074de/src/main/java/com/choonster/testmod2/world/gen/structure/MapGenScatteredFeatureModBiomes.java
 * Modified by: WhichOnesPink (https://github.com/whichonespink44) and
 * Emperor_Luke_II
 */
public class MapGenScatteredFeatureRedwall extends MapGenScatteredFeature {
	private int maxDistanceBetweenScatteredFeatures;
	private final int minDistanceBetweenScatteredFeatures;

	public MapGenScatteredFeatureRedwall() {
		this.range = 1;
		int minDistance = 4;
		int maxDistance = 12;

		this.maxDistanceBetweenScatteredFeatures = maxDistance;
		this.minDistanceBetweenScatteredFeatures = minDistance;
	}

	public MapGenScatteredFeatureRedwall(Map<String, String> p_i2061_1_) {
		this();

		for (Entry<String, String> entry : p_i2061_1_.entrySet())
			if (((String) entry.getKey()).equals("distance")) this.maxDistanceBetweenScatteredFeatures = MathHelper.getInt((String) entry.getValue(), this.maxDistanceBetweenScatteredFeatures, 9);
	}

	@Override
	public String getStructureName() {
		return "RedwallSmall";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		int i = chunkX;
		int j = chunkZ;

		if (chunkX < 0) {
			chunkX -= this.maxDistanceBetweenScatteredFeatures - 1;
		}

		if (chunkZ < 0) {
			chunkZ -= this.maxDistanceBetweenScatteredFeatures - 1;
		}

		int k = chunkX / this.maxDistanceBetweenScatteredFeatures;
		int l = chunkZ / this.maxDistanceBetweenScatteredFeatures;
		Random random = this.world.setRandomSeed(k, l, 14357617);
		k *= this.maxDistanceBetweenScatteredFeatures;
		l *= this.maxDistanceBetweenScatteredFeatures;
		k += random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
		l += random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);

		if (i == k && j == l) {
			BlockPos pos = new BlockPos(i * 16 + 8, 0, j * 16 + 8);
			Biome biome = this.world.getBiomeProvider().getBiome(pos);

			if (null == biome) {
				Logger.error("MapGenScatteredFeatureRTG#canSpawnStructureAtCoords received a null biome at %d %d.", pos.getX(), pos.getZ());
				return false;
			}

			// Mossflower structures.
			if (RedwallUtils.isInMossflower(biome, chunkX, chunkZ) || RedwallUtils.isInBulrushBower(biome, chunkX, chunkZ)) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		return new MapGenScatteredFeatureRedwall.Start(this.world, this.rand, chunkX, chunkZ);
	}

	public static class Start extends StructureStart {
		private final Set<ChunkPos> processed = Sets.<ChunkPos>newHashSet();

		public Start() {}

		public Start(World worldIn, Random random, int chunkX, int chunkZ) {
			this(worldIn, random, chunkX, chunkZ, worldIn.getBiome(new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8)));
		}

		public Start(World worldIn, Random random, int chunkX, int chunkZ, Biome biomeIn) {
			super(chunkX, chunkZ);

			if (null == biomeIn) {
				Logger.error("MapGenScatteredFeatureRTG.Start received a null biome at %d %d.", chunkX * 16 + 8, chunkZ * 16 + 8);
				return;
			}

			LinkedList<StructureComponent> arrComponents = new LinkedList<StructureComponent>();

			if (RedwallUtils.isInMossflower(biomeIn, chunkX, chunkZ) && biomeIn instanceof BiomeRedwallForest && ((BiomeRedwallForest) biomeIn).getType() != BiomeRedwallForest.Type.HEATHLAND) {
				arrComponents.add(new WorldGenGroundDwelling(new StructureBoundingBox(new int[] { chunkX * 16, worldIn.getHeight(chunkX * 16, chunkZ * 16), chunkZ * 16, chunkX * 16 + WorldGenGroundDwelling.getSize().getX(), worldIn.getHeight(chunkX * 16, chunkZ * 16) + WorldGenGroundDwelling.getSize().getY(), chunkZ * 16 + WorldGenGroundDwelling.getSize().getZ() })));
				arrComponents.add(new WorldGenSquirrelDrey(new StructureBoundingBox(new int[] { chunkX * 16, worldIn.getHeight(chunkX * 16, chunkZ * 16), chunkZ * 16, chunkX * 16 + WorldGenSquirrelDrey.getSize().getX(), worldIn.getHeight(chunkX * 16, chunkZ * 16) + WorldGenSquirrelDrey.getSize().getY(), chunkZ * 16 + WorldGenSquirrelDrey.getSize().getZ() })));
				arrComponents.add(new WorldGenKotirTurretAbandoned(new StructureBoundingBox(new int[] { chunkX * 16, worldIn.getHeight(chunkX * 16, chunkZ * 16), chunkZ * 16, chunkX * 16 + WorldGenKotirTurretAbandoned.getSize().getX(), worldIn.getHeight(chunkX * 16, chunkZ * 16) + WorldGenKotirTurretAbandoned.getSize().getY(), chunkZ * 16 + WorldGenKotirTurretAbandoned.getSize().getZ() })));
				arrComponents.add(new WorldGenKotirFortAbandoned(new StructureBoundingBox(new int[] { chunkX * 16, worldIn.getHeight(chunkX * 16, chunkZ * 16), chunkZ * 16, chunkX * 16 + WorldGenKotirFortAbandoned.getSize().getX(), worldIn.getHeight(chunkX * 16, chunkZ * 16) + WorldGenKotirFortAbandoned.getSize().getY(), chunkZ * 16 + WorldGenKotirFortAbandoned.getSize().getZ() })));
				arrComponents.add(new WorldGenKotirTurretInhabited(new StructureBoundingBox(new int[] { chunkX * 16, worldIn.getHeight(chunkX * 16, chunkZ * 16), chunkZ * 16, chunkX * 16 + WorldGenKotirTurretInhabited.getSize().getX(), worldIn.getHeight(chunkX * 16, chunkZ * 16) + WorldGenKotirTurretInhabited.getSize().getY(), chunkZ * 16 + WorldGenKotirTurretInhabited.getSize().getZ() })));
			} else if (RedwallUtils.isInBulrushBower(biomeIn, chunkX, chunkZ)) {
				arrComponents.add(new WorldGenGuosimCamp(new StructureBoundingBox(new int[] { chunkX * 16, worldIn.getHeight(chunkX * 16, chunkZ * 16), chunkZ * 16, chunkX * 16 + WorldGenGuosimCamp.getSize().getX(), worldIn.getHeight(chunkX * 16, chunkZ * 16) + WorldGenGuosimCamp.getSize().getY(), chunkZ * 16 + WorldGenGuosimCamp.getSize().getZ() })));
				arrComponents.add(new WorldGenGuosimCookingTent(new StructureBoundingBox(new int[] { chunkX * 16, worldIn.getHeight(chunkX * 16, chunkZ * 16), chunkZ * 16, chunkX * 16 + WorldGenGuosimCookingTent.getSize().getX(), worldIn.getHeight(chunkX * 16, chunkZ * 16) + WorldGenGuosimCookingTent.getSize().getY(), chunkZ * 16 + WorldGenGuosimCookingTent.getSize().getZ() })));
			}

			this.components.clear();

			if (arrComponents.size() > 0) this.components.add((StructureComponent) arrComponents.get(random.nextInt(arrComponents.size())));

			Logger.info("Scattered feature candidate at %d, %d: %s", chunkX * 16, chunkZ * 16, this.components.toString());

			this.updateBoundingBox();
		}

		@Override
		public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb) {
			Logger.info("Generating %s at %d, %d", this.components.toString(), structurebb.minX, structurebb.minZ);
			super.generateStructure(worldIn, rand, structurebb);
		}

		@Override
		public boolean isValidForPostProcess(ChunkPos pair) {
			return this.processed.contains(pair) ? false : super.isValidForPostProcess(pair);
		}

		@Override
		public void notifyPostProcessAt(ChunkPos pair) {
			super.notifyPostProcessAt(pair);
			this.processed.add(pair);
			// Add adjacent chunks to avoid duplicate structures.
			this.processed.add(new ChunkPos(pair.x + 1, pair.z));
			this.processed.add(new ChunkPos(pair.x - 1, pair.z));
			this.processed.add(new ChunkPos(pair.x + 1, pair.z + 1));
			this.processed.add(new ChunkPos(pair.x - 1, pair.z + 1));
			this.processed.add(new ChunkPos(pair.x, pair.z + 1));
			this.processed.add(new ChunkPos(pair.x, pair.z - 1));
			this.processed.add(new ChunkPos(pair.x + 1, pair.z - 1));
			this.processed.add(new ChunkPos(pair.x - 1, pair.z - 1));
		}
	}

	public static void registerScatteredFeaturePieces() {
		MapGenStructureIO.registerStructureComponent(WorldGenGroundDwelling.class, "MoGD");
		MapGenStructureIO.registerStructureComponent(WorldGenSquirrelDrey.class, "MoSD");
		MapGenStructureIO.registerStructureComponent(WorldGenKotirTurretAbandoned.class, "MoKA");
		MapGenStructureIO.registerStructureComponent(WorldGenKotirTurretInhabited.class, "MoKI");
	}
}