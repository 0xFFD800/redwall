package com.bob.redwall.gui.factions;

import java.io.IOException;
import java.util.List;

import com.bob.redwall.Ref;
import com.bob.redwall.entity.npc.favors.Favor;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFavor extends GuiScreen {
	public static final ResourceLocation TEXTURE = new ResourceLocation(Ref.MODID, "textures/gui/favor.png");
	protected int xSize = 256;
	protected int ySize = 166;
	protected int guiLeft;
	protected int guiTop;
	private float oldMouseX;
	private float oldMouseY;
	protected final EntityPlayer player;
	protected final List<Favor> favors;
	protected int selectedFavor = 0;

	public GuiFavor(EntityPlayer player, List<Favor> favors) {
		this.player = player;
		this.favors = favors;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		if (this.favors.size() > 1) {
			this.buttonList.add(new GuiButton(101, this.guiLeft + 3, this.guiTop - 30, 20, 20, "<"));
			this.buttonList.add(new GuiButton(102, this.guiLeft + this.xSize - 17, this.guiTop - 30, 20, 20, ">"));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		GuiInventory.drawEntityOnScreen(i + 40, j + 82, 30, (float) (i + 51) - this.oldMouseX, (float) (j + 75 - 50) - this.oldMouseY, this.favors.get(this.selectedFavor).getGiver());

		this.drawCenteredString(this.fontRenderer, I18n.format("favor.story"), 111, 20, 4210752);
		this.drawString(this.fontRenderer, this.favors.get(this.selectedFavor).getStory(), 75, 28, 4210752);
		this.drawCenteredString(this.fontRenderer, I18n.format("favor.conditions"), 200, 20, 4210752);
		this.drawString(this.fontRenderer, I18n.format("favor.timeLimit", this.favors.get(this.selectedFavor).getTimeLimit() <= 0 ? "None" : formatTime(this.favors.get(this.selectedFavor).getTimeLimit())), 164, 28, 4210752);
		for (int i1 = 0; i1 < this.favors.get(this.selectedFavor).getConditions().size(); i1++)
			this.drawString(this.fontRenderer, this.favors.get(this.selectedFavor).getConditions().get(i1).getText(), 164, 38 + i1 * 10, 4210752);
		this.drawCenteredString(this.fontRenderer, I18n.format("favor.failure"), 83, 97, 4210752);
		for (int i1 = 0; i1 < this.favors.get(this.selectedFavor).getFailureRewards().size(); i1++)
			this.drawString(this.fontRenderer, this.favors.get(this.selectedFavor).getFailureRewards().get(i1).getText(), 47, 106 + i1 * 10, 4210752);
		this.drawCenteredString(this.fontRenderer, I18n.format("favor.success"), 171, 97, 4210752);
		for (int i1 = 0; i1 < this.favors.get(this.selectedFavor).getSuccessRewards().size(); i1++)
			this.drawString(this.fontRenderer, this.favors.get(this.selectedFavor).getSuccessRewards().get(i1).getText(), 135, 106 + i1 * 10, 4210752);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button.id == 101) this.selectedFavor = this.selectedFavor + 1 < this.favors.size() ? this.selectedFavor + 1 : 0;
		else if (button.id == 102) this.selectedFavor = this.selectedFavor - 1 >= 0 ? this.selectedFavor - 1 : this.favors.size() - 1;
	}

	public static String formatTime(long timeLimit) {
		long seconds = (timeLimit / 20) % 60;
		long minutes = (timeLimit / 120) % 60;
		long hours = timeLimit / 7200;
		return hours > 0 ? I18n.format("time.format.hours", hours, minutes, seconds) : minutes > 0 ? I18n.format("time.format.minutes", minutes, seconds) : I18n.format("time.format.seconds", seconds);
	}
}
