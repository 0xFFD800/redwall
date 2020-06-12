package com.bob.redwall.gui.smithing;

import com.bob.redwall.Ref;
import com.bob.redwall.tileentity.TileEntitySmithingGeneric;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiSmithingGeneric extends GuiContainer {
	private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation(Ref.MODID, "textures/gui/smithing_generic.png");
	private TileEntitySmithingGeneric te;

	public GuiSmithingGeneric(InventoryPlayer playerInv, World worldIn, TileEntitySmithingGeneric te) {
		this(playerInv, worldIn, BlockPos.ORIGIN, te);
	}

	public GuiSmithingGeneric(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition, TileEntitySmithingGeneric te) {
		super(new ContainerSmithingGeneric(playerInv, worldIn, blockPosition, te));
		this.te = te;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(I18n.format("tile.smithing_generic.name", new Object[0]), 28, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(CRAFTING_TABLE_GUI_TEXTURES);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		this.drawTexturedModalRect(i + 85, j + 65, 177, 20, this.te.getFuel() * 4, 4);
		if (this.te.getSmithingTime() != -1) this.drawTexturedModalRect(i + 90, j + 32, 176, 0, 23 - (int) (((double) this.te.getSmithingTime() / TileEntitySmithingGeneric.SMITHING_TIME) * 23.0D), 19);
	}
}