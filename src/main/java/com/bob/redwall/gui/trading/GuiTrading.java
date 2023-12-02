package com.bob.redwall.gui.trading;

import org.lwjgl.opengl.GL11;

import com.bob.redwall.Ref;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.ResourceLocation;

public class GuiTrading extends GuiContainer {
	public static final ResourceLocation TEXTURE = new ResourceLocation(Ref.MODID, "textures/gui/trading.png");
	private float oldMouseX;
	private float oldMouseY;
	private final ContainerTrading container;
    
	public GuiTrading(ContainerTrading inventorySlotsIn) {
		super(inventorySlotsIn);
		this.container = inventorySlotsIn;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		oldMouseX = mouseX;
		oldMouseY = mouseY;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        int i = this.guiLeft;
        int j = this.guiTop;
		GuiInventory.drawEntityOnScreen(i + 32, j + 75, 30, (float)(i + 51) - this.oldMouseX, (float)(j + 75 - 50) - this.oldMouseY, this.container.getNPC());
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		float value = 0;
		for (int i = 1; i < 12; i+=2)
			value += this.container.getNPC().getStackValue(this.container.getSlot(i).getStack());
		for(int i = 0; i < 6; i++)
			this.drawString(this.fontRenderer, this.container.getNPC().getStackValue(this.container.getSlot(i * 2).getStack()) < value ? "y" : "n", this.guiLeft - 58 + (i * 18), this.guiTop - 30, 4210752);
	}
}
