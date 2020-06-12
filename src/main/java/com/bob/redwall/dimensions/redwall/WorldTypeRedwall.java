package com.bob.redwall.dimensions.redwall;

import javax.annotation.Nonnull;

import com.bob.redwall.dimensions.shared.rtg.RTG;
import com.bob.redwall.dimensions.shared.rtg.api.dimension.DimensionManagerRTG;
import com.bob.redwall.dimensions.shared.rtg.api.util.Logger;
import com.bob.redwall.dimensions.shared.rtg.world.gen.ChunkProviderRTG;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldTypeRedwall extends WorldType {
    private static RedwallBiomeProvider biomeProvider;
    public static ChunkProviderRTG chunkProvider;

    public WorldTypeRedwall(String name) {
        super(name);
    }
    
    @Override
    public int getSpawnFuzz(WorldServer world, net.minecraft.server.MinecraftServer server) {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasInfoNotice() {
        return true;
    }

    @Override @Nonnull
    public BiomeProvider getBiomeProvider(@Nonnull World world) {
        /*if (DimensionManagerRTG.isValidDimension(world.provider.getDimension())) {
            if (biomeProvider == null) {
                biomeProvider = new BiomeProviderRTG(world, this);
                RTG.instance.runOnNextServerCloseOnly(clearProvider(biomeProvider));
            }

            Logger.debug("WorldTypeRTG#getBiomeProvider() returning BiomeProviderRTG");

            return biomeProvider;
        } else {
            Logger.debug("WorldTypeRTG#getBiomeProvider() returning vanilla BiomeProvider");

            return new BiomeProvider(world.getWorldInfo());
        }*/
    	
        if (biomeProvider == null) {
            biomeProvider = new RedwallBiomeProvider(world, this);
            RTG.runOnNextServerCloseOnly(clearProvider(biomeProvider));
        }

        Logger.debug("WorldTypeRTG#getBiomeProvider() returning ValourBiomeProvider");

        return biomeProvider;
    }

    @Override @Nonnull
    public IChunkGenerator getChunkGenerator(@Nonnull World world, String generatorOptions) {
        if (DimensionManagerRTG.isValidDimension(world.provider.getDimension())) {
            if (chunkProvider == null) {
                chunkProvider = new ChunkProviderRTG(world, RedwallWorldProvider.VALOUR_SEED);
                RTG.runOnNextServerCloseOnly(clearProvider(chunkProvider));

                // inform the event manager about the ChunkEvent.Load event
                RTG.eventMgr.setDimensionChunkLoadEvent(world.provider.getDimension(), chunkProvider.delayedDecorator);
                RTG.runOnNextServerCloseOnly(chunkProvider.clearOnServerClose());

                Logger.debug("WorldTypeRTG#getChunkGenerator() returning ChunkProviderRTG");

                return chunkProvider;
            }

            // return a "fake" provider that won't decorate for Streams
            ChunkProviderRTG result = new ChunkProviderRTG(world, world.getSeed());
            result.isFakeGenerator();

            return result;

            // no server close because it's not supposed to decorate
            //return chunkProvider;
        } else {
            Logger.debug("Invalid dimension. Serving up ChunkGeneratorOverworld instead of ChunkProviderRTG.");
            return new ChunkGeneratorOverworld(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions);
        }
    }

    @Override
    public float getCloudHeight() {
        return 256F;
    }

    public static Runnable clearProvider(Object provider) {
        if (provider instanceof RedwallBiomeProvider) {
            Logger.debug("WorldTypeRTG#clearProvider() provider instanceof RedwallBiomeProvider (setting to NULL)");
            return () -> biomeProvider = null;
        }
        else if (provider instanceof ChunkProviderRTG) {
            Logger.debug("WorldTypeRTG#clearProvider() provider instanceof ChunkProviderRTG (setting to NULL)");
            return () -> chunkProvider = null;
        }
        else {
            Logger.debug("WorldTypeRTG#clearProvider() returning NULL");
            return null;
        }
    }
}
