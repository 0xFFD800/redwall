package com.bob.redwall.gui.factions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.Ref;
import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStatType;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.capabilities.factions.IFactionCap;
import com.bob.redwall.factions.Faction;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFactions extends GuiScreen {
	public static final ResourceLocation TEXTURE = new ResourceLocation(Ref.MODID, "textures/gui/factions.png");
    protected int xSize = 126;
    protected int ySize = 166;
    protected int guiLeft;
    protected int guiTop;
    protected int page = 0;
    protected int scrollAmount = 0;
    protected int maxScroll = 55;
    protected boolean scrolling = false;
    protected final EntityPlayer player;
    
    protected Faction faction = Faction.FacList.REDWALL;
    protected List<Faction> factionList = new ArrayList<>();
    protected float loyalty;
    protected float fightSkill;
    protected float smithSkill;
    protected float farmSkill;
    protected float brewSkill;
    protected float cookSkill;
    protected float scoffSkill;
    
    public GuiFactions(EntityPlayer player) {
        this.player = player;
        this.updateStats();
        for(Faction fac : Faction.getAllFactions()) {
        	if(fac.playerHasContact(this.player)) this.factionList.add(fac);
        }
        if(this.factionList.contains(this.faction)) this.factionList.remove(this.faction);
        this.factionList.add(0, this.faction);
    }
    
    private void updateStats() {
        this.loyalty = this.getOffset(FacStatType.LOYALTY);
        this.fightSkill = this.getOffset(FacStatType.FIGHT);
        this.smithSkill = this.getOffset(FacStatType.SMITH);
        this.farmSkill = this.getOffset(FacStatType.FARM);
        this.brewSkill = this.getOffset(FacStatType.BREW);
        this.cookSkill = this.getOffset(FacStatType.COOK);
        this.scoffSkill = this.getOffset(FacStatType.SCOFF);
    }
    
    private float getOffset(FacStatType type) {
        IFactionCap cap = this.player.getCapability(FactionCapProvider.FACTION_CAP, null);
    	float spaceToGoUp = (40.0F * (float)Math.pow(((float)RedwallUtils.getFacStatLevel(this.player, this.faction, type) + 1.0F), 2) - 20.0F) - (40.0F * (float)Math.pow((float)RedwallUtils.getFacStatLevel(this.player, this.faction, type), 2));
    	float spaceToGoDown = (40.0F * (float)Math.pow(((float)RedwallUtils.getFacStatLevel(this.player, this.faction, type) - 1.0F), 2) - 20.0F) - (40.0F * (float)Math.pow((float)RedwallUtils.getFacStatLevel(this.player, this.faction, type), 2));
    	float offsetRaw = cap.get(this.faction, type) - 40.0F * (float)Math.pow((float)RedwallUtils.getFacStatLevel(this.player, this.faction, type), 2);
    	return offsetRaw < 0 ? -(offsetRaw/spaceToGoDown) * 40.0F : (offsetRaw/spaceToGoUp) * 40.0F;
    }
    
    private int level(FacStatType type) {
    	return RedwallUtils.getFacStatLevel(this.player, this.faction, type);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        if(this.factionList.size() > 1) {
            this.buttonList.add(new GuiButton(101, this.guiLeft + 3, this.guiTop - 30, 20, 20, "<"));
            this.buttonList.add(new GuiButton(102, this.guiLeft + this.xSize - 17, this.guiTop - 30, 20, 20, ">"));
        }
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
    	super.actionPerformed(button);
        if (button.id == 101) {
            this.page = this.page + 1 < this.factionList.size() ? this.page + 1 : 0;
        } else if (button.id == 102) {
            this.page = this.page - 1 >= 0 ? this.page - 1 : this.factionList.size() - 1;
        }
        
        this.faction = this.factionList.get(this.page);
        this.updateStats();
    }

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
        this.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiFactions.TEXTURE);
		this.drawTexturedModalRect(this.guiLeft + 3, this.guiTop, 126, 0, this.xSize, this.ySize);
		//Labels
		if(this.scrollAmount < 20) this.drawCenteredString(this.fontRenderer, faction.getLocalizedName(), this.guiLeft + this.xSize/2, this.guiTop + 20 - this.scrollAmount, 4210752);
		if(this.scrollAmount < 45) this.drawCenteredString(this.fontRenderer, I18n.format("facstat.loyalty.name") + " " + I18n.format("facstat.level", this.level(FacStatType.LOYALTY)), this.guiLeft + this.xSize/2, this.guiTop + 45 - this.scrollAmount, 4210752);
		this.drawCenteredString(this.fontRenderer, I18n.format("facstat.fightSkill.name") + " " + I18n.format("facstat.level", this.level(FacStatType.FIGHT)), this.guiLeft + this.xSize/2, this.guiTop + 69 - this.scrollAmount, 4210752);
		this.drawCenteredString(this.fontRenderer, I18n.format("facstat.smithSkill.name") + " " + I18n.format("facstat.level", this.level(FacStatType.SMITH)), this.guiLeft + this.xSize/2, this.guiTop + 69 + 24 - this.scrollAmount, 4210752);
		this.drawCenteredString(this.fontRenderer, I18n.format("facstat.farmSkill.name") + " " + I18n.format("facstat.level", this.level(FacStatType.FARM)), this.guiLeft + this.xSize/2, this.guiTop + 69 + 48 - this.scrollAmount, 4210752);
		this.drawCenteredString(this.fontRenderer, I18n.format("facstat.brewSkill.name") + " " + I18n.format("facstat.level", this.level(FacStatType.BREW)), this.guiLeft + this.xSize/2, this.guiTop + 69 + 72 - this.scrollAmount, 4210752);
		if(this.scrollAmount > 7) this.drawCenteredString(this.fontRenderer, I18n.format("facstat.cookSkill.name") + " " + I18n.format("facstat.level", this.level(FacStatType.COOK)), this.guiLeft + this.xSize/2, this.guiTop + 69 + 96 - this.scrollAmount, 4210752);
		if(this.scrollAmount > 31) this.drawCenteredString(this.fontRenderer, I18n.format("facstat.scoffSkill.name") + " " + I18n.format("facstat.level", this.level(FacStatType.SCOFF)), this.guiLeft + this.xSize/2, this.guiTop + 69 + 120 - this.scrollAmount, 4210752);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//Bars
		this.mc.getTextureManager().bindTexture(GuiFactions.TEXTURE);
		this.drawTexturedModalRect(this.guiLeft + 20, this.guiTop + 60 - this.scrollAmount, 11, 166, 86, 4);
		this.drawTexturedModalRect(this.guiLeft + 61 + this.loyalty, this.guiTop + 59 - this.scrollAmount, 0, 166, 5, 5);
		this.drawTexturedModalRect(this.guiLeft + 20, this.guiTop + 69 + 15 - this.scrollAmount, 11, 166, 86, 4);
		this.drawTexturedModalRect(this.guiLeft + 61 + this.fightSkill, this.guiTop + 69 + 14 - this.scrollAmount, 0, 171, 5, 5);
		this.drawTexturedModalRect(this.guiLeft + 20, this.guiTop + 69 + 24 + 15 - this.scrollAmount, 11, 166, 86, 4);
		this.drawTexturedModalRect(this.guiLeft + 61 + this.smithSkill, this.guiTop + 69 + 24 + 14 - this.scrollAmount, 0, 176, 5, 5);
		this.drawTexturedModalRect(this.guiLeft + 20, this.guiTop + 69 + 48 + 15 - this.scrollAmount, 11, 166, 86, 4);
		this.drawTexturedModalRect(this.guiLeft + 61 + this.farmSkill, this.guiTop + 69 + 48 + 14 - this.scrollAmount, 0, 181, 5, 5);
		this.drawTexturedModalRect(this.guiLeft + 20, this.guiTop + 69 + 72 + 15 - this.scrollAmount, 11, 166, 86, 4);
		this.drawTexturedModalRect(this.guiLeft + 61 + this.brewSkill, this.guiTop + 69 + 72 + 14 - this.scrollAmount, 5, 176, 5, 5);
		if(this.scrollAmount > 22) {
			this.drawTexturedModalRect(this.guiLeft + 20, this.guiTop + 69 + 96 + 15 - this.scrollAmount, 11, 166, 86, 4);
			this.drawTexturedModalRect(this.guiLeft + 61 + this.cookSkill, this.guiTop + 69 + 96 + 14 - this.scrollAmount, 5, 181, 5, 5);
		}
		if(this.scrollAmount > 46) {
			this.drawTexturedModalRect(this.guiLeft + 20, this.guiTop + 69 + 120 + 15 - this.scrollAmount, 11, 166, 86, 4);
			this.drawTexturedModalRect(this.guiLeft + 61 + this.scoffSkill, this.guiTop + 69 + 120 + 14 - this.scrollAmount, 0, 186, 5, 5);
		}
		//Frame
		this.drawTexturedModalRect(this.guiLeft + 3, this.guiTop, 0, 0, this.xSize, this.ySize);
		//Scroll bar
		this.drawTexturedModalRect(this.guiLeft + 114, this.guiTop + 9 + (this.scrollAmount * 138/this.maxScroll), 5, 166, 6, 10);
		
		super.drawScreen(mouseX, mouseY, f);
	}
	
	@Override
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
		String[] s = text.split("<br>");
		for(int i = 0; i < s.length; i++) {
			String ss = s[i];
	        fontRendererIn.drawString(ss, (float)(x - fontRendererIn.getStringWidth(ss) / 2), (float)y + (i * (fontRendererIn.FONT_HEIGHT + 1)), color, false);
		}
    }
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(mouseY > this.guiTop + 9 + (this.scrollAmount * 138/this.maxScroll) && mouseY < this.guiTop + 19 + (this.scrollAmount * 138/this.maxScroll) && mouseX > this.guiLeft + 111 && mouseX < this.guiLeft + 121) this.scrolling = true;
    }

	@Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.scrolling = false;
    }

	@Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
    	super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    	if(this.scrolling) this.scrollAmount = Math.max(0, Math.min(((mouseY - this.guiTop + 19) * this.maxScroll/138) - 10, this.maxScroll));
    }
}