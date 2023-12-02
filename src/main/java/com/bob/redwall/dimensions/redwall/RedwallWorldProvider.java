package com.bob.redwall.dimensions.redwall;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.dimensions.ModDimensions;
import com.bob.redwall.init.BiomeHandler;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;

public class RedwallWorldProvider extends WorldProvider {
	public static final BlockPos REDWALL_SPAWN_POINT = new BlockPos(53616, 70, 37856);
	public static final long REDWALL_SEED = 1L;
	public static final float REDWALL_DAY_LENGTH = 72000.0F;
	public static final int REDWALL_BIOME_SIZE = 2; // 2 results in a 1:1 ratio between pixels and chunks. That seems
													// pretty reasonable.
	public static final float NPC_SPAWN_CHANCE_WORLDGEN = 0.02F;
	public static final float NPC_SPAWN_CHANCE = 0.02F;
	public static final int SEA_LEVEL = 63;
	public static final String REDWALL_MAP_LOCATION = "map.png";
	public static BufferedImage IMAGE = null;

	public static final Map<Integer, Biome> COLOR_TO_BIOME = Maps.<Integer, Biome>newHashMap();

	static {
		try {
			File file = new File(RedwallWorldProvider.class.getResource("/assets/redwall/biome_color_map.txt").getFile());
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (!StringUtils.isEmpty(line)) {
				if (line.startsWith("#") || !line.contains(":")) {
					line = reader.readLine();
					continue;
				}
				int index = line.indexOf(':');
				String biome_def = line.substring(0, index);
				String color_def = line.substring(index + 1, line.length());

				Biome biome = BiomeHandler.getRegisteredBiome(biome_def);
				int color = Integer.parseInt(color_def, 16);

				RedwallWorldProvider.COLOR_TO_BIOME.put(color, biome);

				line = reader.readLine();
			}
			reader.close();
			RedwallWorldProvider.loadMapImage(REDWALL_MAP_LOCATION);
		} catch (IOException e) {
			Logger.getLogger(RedwallWorldProvider.class.getName()).log(Level.SEVERE, String.format("Can't find %s! This is a bug!", REDWALL_MAP_LOCATION), e);
		} catch (Exception e1) {
			Logger.getLogger(RedwallWorldProvider.class.getName()).log(Level.SEVERE, "Error reading biomes", e1);
		}
	}

	@Override
	public boolean canRespawnHere() {
		return true;
	}

	@Override
	public void updateWeather() {
		if (this.world.provider.hasSkyLight()) {
			if (!this.world.isRemote) {
				boolean flag = this.world.getGameRules().getBoolean("doWeatherCycle");

				if (flag) {
					int i = this.world.getWorldInfo().getCleanWeatherTime();

					if (i > 0) {
						--i;
						this.world.getWorldInfo().setCleanWeatherTime(i);
						this.world.getWorldInfo().setThunderTime(this.world.getWorldInfo().isThundering() ? 1 : 2);
						this.world.getWorldInfo().setRainTime(this.world.getWorldInfo().isRaining() ? 1 : 2);
					}

					int j = this.world.getWorldInfo().getThunderTime();
					int j1 = RedwallUtils.getSeason(this.world).getRainChanceMultiplier();

					if (j <= 0) {
						if (this.world.getWorldInfo().isThundering())
							this.world.getWorldInfo().setThunderTime(this.world.rand.nextInt(12000 * j1) + 3600 * j1);
						else
							this.world.getWorldInfo().setThunderTime(this.world.rand.nextInt(168000 / j1) + 12000 / j1);
					} else {
						--j;
						this.world.getWorldInfo().setThunderTime(j);

						if (j <= 0)
							this.world.getWorldInfo().setThundering(!this.world.getWorldInfo().isThundering());
					}

					int k = this.world.getWorldInfo().getRainTime();

					if (k <= 0) {
						if (this.world.getWorldInfo().isRaining())
							this.world.getWorldInfo().setRainTime(this.world.rand.nextInt(12000 * j1) + 12000 * j1);
						else
							this.world.getWorldInfo().setRainTime(this.world.rand.nextInt(168000 / j1) + 12000 / j1);
					} else {
						--k;
						this.world.getWorldInfo().setRainTime(k);

						if (k <= 0)
							this.world.getWorldInfo().setRaining(!this.world.getWorldInfo().isRaining());
					}
				}

				this.world.prevThunderingStrength = this.world.thunderingStrength;
				
				if (this.world.getWorldInfo().isThundering())
					this.world.thunderingStrength = (float) ((double) this.world.thunderingStrength + 0.01D);
				else
					this.world.thunderingStrength = (float) ((double) this.world.thunderingStrength - 0.01D);

				this.world.thunderingStrength = MathHelper.clamp(this.world.thunderingStrength, 0.0F, 1.0F);
				this.world.prevRainingStrength = this.world.rainingStrength;

				if (this.world.getWorldInfo().isRaining())
					this.world.rainingStrength = (float) ((double) this.world.rainingStrength + 0.01D);
				else
					this.world.rainingStrength = (float) ((double) this.world.rainingStrength - 0.01D);

				this.world.rainingStrength = MathHelper.clamp(this.world.rainingStrength, 0.0F, 1.0F);
			}
		}
	}

	@Override
	public boolean canSnowAt(BlockPos pos, boolean checkLight) {
		Biome biome = this.world.getBiome(pos);
		float f = biome.getTemperature(pos) > 0.8F ? biome.getTemperature(pos) : biome.getTemperature(pos) * RedwallUtils.getSeason(this.world).getTemperatureMultiplier();

		if (f >= 0.15F) {
			return false;
		} else if (!checkLight) {
			return true;
		} else {
			if (pos.getY() >= 0 && pos.getY() < 256 && this.world.getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
				IBlockState iblockstate = this.world.getBlockState(pos);

				if (iblockstate.getBlock().isAir(iblockstate, this.world, pos) && Blocks.SNOW_LAYER.canPlaceBlockAt(this.world, pos))
					return true;
			}

			return false;
		}
	}

	@Override
	public boolean canBlockFreeze(BlockPos pos, boolean byWater) {
		Biome biome = this.world.getBiome(pos);
		float f = biome.getTemperature(pos) > 0.8F ? biome.getTemperature(pos) : biome.getTemperature(pos) * RedwallUtils.getSeason(this.world).getTemperatureMultiplier();

		if (f >= 0.15F) {
			return false;
		} else {
			if (pos.getY() >= 0 && pos.getY() < 256 && this.world.getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
				IBlockState iblockstate = this.world.getBlockState(pos);
				Block block = iblockstate.getBlock();

				if ((block == Blocks.WATER || block == Blocks.FLOWING_WATER) && ((Integer) iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0) {
					if (!byWater)
						return true;

					boolean flag = this.isWater(pos.west()) && this.isWater(pos.east()) && this.isWater(pos.north()) && this.isWater(pos.south());

					if (!flag)
						return true;
				}
			}

			return false;
		}
	}

	private boolean isWater(BlockPos pos) {
		return this.world.getBlockState(pos).getMaterial() == Material.WATER;
	}

	@Override
	public int getRespawnDimension(EntityPlayerMP player) {
		return ModDimensions.DIM_REDWALL_ID;
	}

	@Override
	public boolean isSurfaceWorld() {
		return true;
	}

	@Override
	public DimensionType getDimensionType() {
		return ModDimensions.REDWALL;
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		int i = (int) (worldTime % REDWALL_DAY_LENGTH);
		float f = ((float) i + partialTicks) / REDWALL_DAY_LENGTH - 0.25F;

		if (f < 0.0F)
			++f;

		if (f > 1.0F)
			--f;

		float f1 = 1.0F - (float) ((Math.cos((double) f * Math.PI) + 1.0D) / 2.0D);
		f = f + (f1 - f) / 3.0F;
		return f;
	}

	@Override
	public int getMoonPhase(long worldTime) {
		return (int) (worldTime / REDWALL_DAY_LENGTH % 8L + 8L) % 8;
	}

	@Override
	public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful) {
		super.setAllowedSpawnTypes(false, allowPeaceful);
	}

	public static void loadMapImage(String name) throws IOException {
		IMAGE = ImageIO.read(RedwallWorldProvider.class.getResourceAsStream("/assets/redwall/textures/map/" + name));
		Logger.getLogger(RedwallWorldProvider.class.getName()).log(Level.INFO, "Initialized " + name);
	}
}
