package com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.util.BlockUtil;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBoulder;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoCactus;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoDeadBush;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoShrub;

import net.minecraft.init.Blocks;


/**
 * @author WhichOnesPink
 */
public class DecoCollectionMesa extends DecoCollectionBase {

    public DecoCollectionMesa(BiomeConfig config) {

        super(config);

        DecoBoulder decoBoulder = new DecoBoulder();
        decoBoulder.setBoulderBlock(Blocks.COBBLESTONE.getDefaultState());
        decoBoulder.setMaxY(83);
        this.addDeco(decoBoulder);

        DecoShrub decoShrub = new DecoShrub();
        decoShrub.setLoops(2);
        decoShrub.setChance(4);
        decoShrub.setMaxY(90);
        addDeco(decoShrub);

        DecoDeadBush decoDeadBush = new DecoDeadBush();
        decoDeadBush.setMaxY(100);
        decoDeadBush.setLoops(3);
        this.addDeco(decoDeadBush);

        DecoCactus decoCactus = new DecoCactus();
        decoCactus.setSoilBlock(BlockUtil.getStateSand(1));
        decoCactus.setLoops(18);
        decoCactus.setMaxY(100);
        this.addDeco(decoCactus, config.ALLOW_CACTUS.get());
    }
}
