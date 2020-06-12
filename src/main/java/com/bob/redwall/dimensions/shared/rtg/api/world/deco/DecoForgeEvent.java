package com.bob.redwall.dimensions.shared.rtg.api.world.deco;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.biome.IRealisticBiome;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

/**
 * @author WhichOnesPink
 */
public class DecoForgeEvent extends DecoBase {
    private DecorateBiomeEvent.Decorate.EventType event;

    public DecoForgeEvent(DecorateBiomeEvent.Decorate.EventType event) {
        super();

        this.event = event;
    }

    @Override
    public boolean properlyDefined() {
        try {
            if (this.event != null) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

	@Override
    @SuppressWarnings("deprecation")
    public void generate(IRealisticBiome biome, IRTGWorld rtgWorld, Random rand, int worldX, int worldZ, float strength, float river, boolean hasPlacedVillageBlocks) {
        if (this.allowed) {
            if (TerrainGen.decorate(rtgWorld.world(), rand, new BlockPos(worldX, 0, worldZ), this.event)) { ; }
        }
    }
}
