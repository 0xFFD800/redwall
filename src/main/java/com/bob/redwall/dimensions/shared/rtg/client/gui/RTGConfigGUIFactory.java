package com.bob.redwall.dimensions.shared.rtg.client.gui;


import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public class RTGConfigGUIFactory implements IModGuiFactory
{
    public static final String LOCATION = "rtg.client.gui.RTGConfigGUIFactory";
    @Override public void initialize(Minecraft minecraftInstance) {}
    @Override public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {return null;}
	@Override public boolean hasConfigGui() { return true; } 
	@Override public GuiScreen createConfigGui(GuiScreen parentScreen) { return new RTGConfigGUI(parentScreen); }
}
