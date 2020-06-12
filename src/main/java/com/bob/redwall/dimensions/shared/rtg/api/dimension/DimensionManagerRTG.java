package com.bob.redwall.dimensions.shared.rtg.api.dimension;

import java.util.ArrayList;

/**
 * Created by WhichOnesPink on 28/05/2017.
 */
public abstract class DimensionManagerRTG {

    public static final int OVERWORLD = 0;
    private static ArrayList<Integer> rtgDimensions = new ArrayList<Integer>(){

		/**
		 * 
		 */
		private static final long serialVersionUID = -7383264287129295821L;};

    public static void addRTGDimension(int dimId) {

        if (!rtgDimensions.contains(dimId)) {
            rtgDimensions.add(dimId);
        }
    }

    public static boolean isValidDimension(int dimId) {
        return rtgDimensions.contains(dimId);
    }
}
