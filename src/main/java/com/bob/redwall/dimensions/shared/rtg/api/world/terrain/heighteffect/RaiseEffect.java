package com.bob.redwall.dimensions.shared.rtg.api.world.terrain.heighteffect;

import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;

/**
 * // just adds a constant increase
 *
 * @author Zeno410
 */
public class RaiseEffect extends HeightEffect {

    // just adds a number
    public final float height;

    public RaiseEffect(float height) {

        this.height = height;
    }

    @Override
    public final float added(IRTGWorld rtgWorld, float x, float y) {

        return height;
    }
}
