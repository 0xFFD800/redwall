package com.bob.redwall.gui.cooking;

import com.bob.redwall.Ref;
import com.bob.redwall.tileentity.TileEntityCookingGeneric;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiCookingGeneric extends GuiContainer {
	private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation(Ref.MODID, "textures/gui/cooking_generic.png");
	private TileEntityCookingGeneric te;

	public GuiCookingGeneric(InventoryPlayer playerInv, World worldIn, TileEntityCookingGeneric te) {
		this(playerInv, worldIn, BlockPos.ORIGIN, te);
	}

	public GuiCookingGeneric(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition, TileEntityCookingGeneric te) {
		super(new ContainerCookingGeneric(playerInv, worldIn, blockPosition, te));
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
		this.fontRenderer.drawString(I18n.format("tile.cooking_generic.name", new Object[0]), 28, 6, 4210752);
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
		SlotCookingGeneric a = ((SlotCookingGeneric)this.inventorySlots.getSlotFromInventory(((ContainerCookingGeneric)this.inventorySlots).craftResult, 0));
		if (a.recipe != null) {
			if (a.shouldBurn()) {
				this.drawTexturedModalRect(i + 89, j + 35, 198, 0, 22, 16);
				if (this.te.getCookingTime() != -1) {
					this.drawTexturedModalRect(i + 89, j + 35, 220, 0, 22 - (int) (((double) this.te.getCookingTime() / TileEntityCookingGeneric.COOKING_TIME) * 22.0D), 16);
				}
			} else {
				this.drawTexturedModalRect(i + 89, j + 35, 220, 0, 176, 16);
			}
		}
	}
}