package com.bob.redwall.dimensions.redwall;

public enum EnumSeasons {
	EARLY_WINTER(1.5F,   1.2F,   0.5F,   1.4F,   0.625F,0.3F,   false, 0.0F,   0.18F,  4, true),  //December
	WINTER      (1.7F,   1.125F, 0.7F,   1.4F,   0.625F,0.3F,   false, 0.0F,   0.15F,  5, true),  //January
	LATE_WINTER (1.5F,   1.2F,   0.5F,   1.4F,   0.625F,0.3F,   false, 0.0F,   0.2F,   4, true),  //February
	EARLY_SPRING(0.95F,  0.9F,   0.9F,   1.5F,   1.25F, 0.75F,  true,  0.1F,   0.5F,   4, false), //March
	SPRING      (0.75F,  1.0F,   1.25F,  1.25F,  1.0F,  0.5F,   true,  0.15F,  1.0F,   3, false), //April
	LATE_SPRING (0.875F, 1.0F,   1.125F, 1.125F, 1.0F,  0.75F,  true,  0.2F,   1.1F,   2, false), //May
	EARLY_SUMMER(0.9F,   1.0F,   1.1F,   1.1F,   1.0F,  0.825F, true,  0.225F, 1.125F, 1, false), //June
	SUMMER      (1.0F,   1.0F,   1.0F,   1.0F,   1.0F,  1.0F,   true,  0.25F,  1.25F,  1, false), //July
	LATE_SUMMER (1.2F,   1.2F,   1.0F,   1.2F,   0.5F,  0.8F,   true,  0.25F,  1.2F,   1, false), //August
	EARLY_FALL  (1.45F,  0.9F,   0.4F,   1.75F,  0.9F,  0.65F,  true,  0.2F,   0.8F,   2, false), //September
	FALL        (1.5F,   1.0F,   0.5F,   1.9F,   0.8F,  0.5F,   true,  0.2F,   0.75F,  2, false), //October
	LATE_FALL   (1.35F,  1.0F,   0.65F,  1.9F,   0.8F,  0.5F,   true,  0.1F,   0.4F,   3, false); //November
	
	private float redGMul, greenGMul, blueGMul, redFMul, greenFMul, blueFMul, cropGrowthSpeed, tempMod;
	private boolean allowCropGrowth, isWinter;
	private int rainChanceMultiplier;
	private EnumSeasons(float redGMul, float greenGMul, float blueGMul, float redFMul, float greenFMul, float blueFMul, boolean allowCropGrowth, float cropGrowthSpeed, float tempMod, int rainChanceModifier, boolean isWinter) {
		this.redGMul = redGMul;
		this.greenGMul = greenGMul;
		this.blueGMul = blueGMul;
		this.redFMul = redFMul;
		this.greenFMul = greenFMul;
		this.blueFMul = blueGMul;
		this.allowCropGrowth = allowCropGrowth;
		this.cropGrowthSpeed = cropGrowthSpeed;
		this.tempMod = tempMod;
		this.rainChanceMultiplier = rainChanceModifier;
		this.isWinter = isWinter;
	}
	
	public float getGrassColorMultiplierRed() {
		return this.redGMul;
	}
	
	public float getGrassColorMultiplierGreen() {
		return this.greenGMul;
	}
	
	public float getGrassColorMultiplierBlue() {
		return this.blueGMul;
	}
	
	public float getFoliageColorMultiplierRed() {
		return this.redFMul;
	}
	
	public float getFoliageColorMultiplierGreen() {
		return this.greenFMul;
	}
	
	public float getFoliageColorMultiplierBlue() {
		return this.blueFMul;
	}

	public boolean getAllowCropGrowth() {
		return this.allowCropGrowth;
	}
	
	public float getCropGrowthSpeedMultiplier() {
		return this.cropGrowthSpeed;
	}

	public float getTemperatureMultiplier() {
		return this.tempMod;
	}
	
	public int getRainChanceMultiplier() {
		return this.rainChanceMultiplier;
	}
	
	public boolean isWinter() {
		return this.isWinter;
	}
}
