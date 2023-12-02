package com.bob.redwall.gui.brewing;

import com.bob.redwall.Ref;
import com.bob.redwall.tileentity.TileEntityBrewingVerminMossflower;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiBrewingVerminMossflower extends GuiContainer {
	private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation(Ref.MODID, "textures/gui/brewing_vermin_mossflower.png");
	private TileEntityBrewingVerminMossflower te;

	public GuiBrewingVerminMossflower(InventoryPlayer playerInv, World worldIn, TileEntityBrewingVerminMossflower te) {
		this(playerInv, worldIn, BlockPos.ORIGIN, te);
	}

	public GuiBrewingVerminMossflower(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition, TileEntityBrewingVerminMossflower te) {
		super(new ContainerBrewingVerminMossflower(playerInv, worldIn, blockPosition, te));
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
		this.fontRenderer.drawString(I18n.format("tile.brewing_vermin_mossflower.name", new Object[0]), 28, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(CRAFTING_TABLE_GUI_TEXTURES);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		if (this.te.getBrewingTime() != -1) this.drawTexturedModalRect(i + 90, j + 32, 176, 0, 23 - (int) (((double) this.te.getBrewingTime() / TileEntityBrewingVerminMossflower.BREWING_TIME) * 23.0D), 19);
	}
}