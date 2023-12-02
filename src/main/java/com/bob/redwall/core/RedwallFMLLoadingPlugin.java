package com.bob.redwall.core;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.TransformerExclusions("com.bob.redwall")
public class RedwallFMLLoadingPlugin implements IFMLLoadingPlugin {
	@Override
	public String[] getASMTransformerClass() {
		System.out.println(this.getClass().getName() + ".getASMTransformerClass() returning " + RedwallClassTransformer.class.getName() + " as class transformer");
		return new String[] { RedwallClassTransformer.class.getName() };
	}

	@Override
	public String getModContainerClass() {
		System.out.println(this.getClass().getName() + ".getModContainerClass() returning " + RedwallDummyContainer.class.getName() + " as dummy mod container");
		return RedwallDummyContainer.class.getName();
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {

	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}