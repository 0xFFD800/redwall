package com.bob.redwall.dimensions.shared;

import java.awt.image.BufferedImage;

import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerFromImage extends GenLayer {
	private int[] biomeMap;
	private int mapHeight;
	private int mapWidth;
	private int fillBiome = 0;
	int xOffset = 0;
	int zOffset = 0;

	public GenLayerFromImage(long seed, GenLayer childLayer, BufferedImage map) {
		super(seed);
		this.parent = childLayer;

		// For forge make sure all dimensions are queried since the biome we're looking
		// for may be owned by another dimension
		this.fillBiome = Biome.getIdForBiome(Biomes.DEEP_OCEAN);

		// Read from file
		this.mapWidth = map.getWidth(null);
		this.mapHeight = map.getHeight(null);
		int[] colorMap = new int[this.mapHeight * this.mapWidth];

		map.getRGB(0, 0, this.mapWidth, this.mapHeight, colorMap, 0, this.mapWidth);

		this.biomeMap = new int[colorMap.length];

		for (int nColor = 0; nColor < colorMap.length; nColor++) {
			int color = colorMap[nColor] & 0x00FFFFFF;

			if (RedwallWorldProvider.COLOR_TO_BIOME.containsKey(color))
				this.biomeMap[nColor] = Biome.getIdForBiome(RedwallWorldProvider.COLOR_TO_BIOME.get(color));
			else this.biomeMap[nColor] = fillBiome;
		}
	}

	@Override
	public int[] getInts(int x, int z, int xSize, int zSize) {
		int[] resultBiomes = IntCache.getIntCache(xSize * zSize);

		for (int zi = 0; zi < zSize; zi++) {
			for (int xi = 0; xi < xSize; xi++) {
				int Buffer_x = x + xi - xOffset;
				int Buffer_z = z + zi - zOffset;
				if (Buffer_x < 0 || Buffer_x >= this.mapWidth || Buffer_z < 0 || Buffer_z >= this.mapHeight)
					resultBiomes[(xi + zi * xSize)] = this.fillBiome;
				else resultBiomes[(xi + zi * xSize)] = this.biomeMap[Buffer_x + Buffer_z * this.mapWidth];
			}
		}
		return resultBiomes;
	}

}