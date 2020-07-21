package com.bob.redwall.gui.smithing.redwall;

import com.bob.redwall.Ref;
import com.bob.redwall.tileentity.TileEntitySmithingRedwall;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiSmithingRedwall extends GuiContainer {
	private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation(Ref.MODID, "textures/gui/smithing_redwall.png");
	private TileEntitySmithingRedwall te;

	public GuiSmithingRedwall(InventoryPlayer playerInv, World worldIn, TileEntitySmithingRedwall te) {
		this(playerInv, worldIn, BlockPos.ORIGIN, te);
	}

	public GuiSmithingRedwall(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition, TileEntitySmithingRedwall te) {
		super(new ContainerSmithingRedwall(playerInv, worldIn, blockPosition, te));
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
		this.fontRenderer.drawString(I18n.format("tile.smithing_redwall.name", new Object[0]), 28, 6, 0x632F26);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 0x632F26);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(CRAFTING_TABLE_GUI_TEXTURES);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		this.drawTexturedModalRect(i + 85, j + 65, 177, 20, this.te.getFuel() * 4, 4);
		if (this.te.getSmithingTime() != -1) this.drawTexturedModalRect(i + 90, j + 32, 176, 0, 23 - (int) (((double) this.te.getSmithingTime() / TileEntitySmithingRedwall.SMITHING_TIME) * 23.0D), 19);
	}
}