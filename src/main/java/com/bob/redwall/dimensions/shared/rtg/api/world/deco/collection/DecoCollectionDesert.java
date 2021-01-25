package com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoCactus;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoDeadBush;


/**
 * @author WhichOnesPink
 */
public class DecoCollectionDesert extends DecoCollectionBase {
    public DecoCollectionDesert(BiomeConfig config) {
        super(config);

        DecoCactus decoCactus = new DecoCactus();
        decoCactus.setMaxY(90);
        decoCactus.setStrengthFactor(3f);
        decoCactus.setChance(20);
        this.addDeco(decoCactus, config.ALLOW_CACTUS.get());

        DecoDeadBush decoDeadBush = new DecoDeadBush();
        decoDeadBush.setMaxY(128);
        decoDeadBush.setStrengthFactor(2f);
        decoDeadBush.setChance(30);
        this.addDeco(decoDeadBush);
    }
}
