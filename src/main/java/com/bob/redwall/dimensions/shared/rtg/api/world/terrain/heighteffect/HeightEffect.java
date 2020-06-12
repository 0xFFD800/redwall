/*
 * Available under the Lesser GPL License 3.0
 */

package com.bob.redwall.dimensions.shared.rtg.api.world.terrain.heighteffect;

import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;

/**
 * @author Zeno410
 */
public abstract class HeightEffect {

    public abstract float added(IRTGWorld rtgWorld, float x, float y);

    public HeightEffect plus(HeightEffect added) {

        return new SummedHeightEffects(this, added);
    }

    private class SummedHeightEffects extends HeightEffect {

        private final HeightEffect one;
        private final HeightEffect two;

        public SummedHeightEffects(HeightEffect one, HeightEffect two) {

            this.one = one;
            this.two = two;
        }

        @Override
        public float added(IRTGWorld rtgWorld, float x, float y) {

            return one.added(rtgWorld, x, y) + two.added(rtgWorld, x, y);
        }
    }
}
