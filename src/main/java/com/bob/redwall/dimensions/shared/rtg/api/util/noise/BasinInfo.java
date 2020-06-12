
package com.bob.redwall.dimensions.shared.rtg.api.util.noise;

/**
 * An abstracted class for optionally storing a point which I ended up not using
 * @author Zeno410
 */
public interface BasinInfo {
    
    public abstract void setX(float newX);
    public abstract void setZ(float newY);
    
    public static class NotNeeded implements BasinInfo {

        @Override
        public void setX(float newX) {
            // nothing
        }

        @Override
        public void setZ(float newZ) {
            // nothing
        }
        
    }

    @SuppressWarnings("unused")
    public static class Needed implements BasinInfo {
		private float x;
        private float z;

        @Override
        public void setX(float newX) {
            x = newX;
        }

        @Override
        public void setZ(float newZ) {
           z = newZ;
        }
        
    }

}
