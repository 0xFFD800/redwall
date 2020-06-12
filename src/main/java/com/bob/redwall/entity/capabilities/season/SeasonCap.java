package com.bob.redwall.entity.capabilities.season;

import com.bob.redwall.dimensions.redwall.EnumSeasons;

public class SeasonCap implements ISeasonCap {
	private EnumSeasons season;
	
	@Override
	public void setSeason(EnumSeasons season) {
		this.season = season;
	}
	
	@Override
	public EnumSeasons getSeason() {
		return this.season;
	}
}
