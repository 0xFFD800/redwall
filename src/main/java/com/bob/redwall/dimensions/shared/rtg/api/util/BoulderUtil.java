package com.bob.redwall.dimensions.shared.rtg.api.util;

import com.bob.redwall.dimensions.shared.rtg.api.event.SurfaceEvent;

import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.MinecraftForge;

public class BoulderUtil {

    public IBlockState getBoulderBlock(IBlockState defaultBlock, int worldX, int worldY, int worldZ) {

        SurfaceEvent.BoulderBlock event = new SurfaceEvent.BoulderBlock(
            worldX, worldY, worldZ, defaultBlock
        );
        MinecraftForge.TERRAIN_GEN_BUS.post(event);

        return event.getBlock();
    }
}