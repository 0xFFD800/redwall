package com.bob.redwall.gui.skills;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.bob.redwall.Ref;
import com.bob.redwall.common.MessageSetCap;
import com.bob.redwall.entity.capabilities.agility.AgilityProvider;
import com.bob.redwall.entity.capabilities.agility.IAgility;
import com.bob.redwall.entity.capabilities.speed.ISpeed;
import com.bob.redwall.entity.capabilities.speed.SpeedProvider;
import com.bob.redwall.entity.capabilities.strength.IStrength;
import com.bob.redwall.entity.capabilities.strength.StrengthProvider;
import com.bob.redwall.entity.capabilities.vitality.IVitality;
import com.bob.redwall.entity.capabilities.vitality.VitalityProvider;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSkills extends GuiScreen {
	public static final ResourceLocation TEXTURE = new ResourceLocation(Ref.MODID, "textures/gui/skills.png");
    protected int xSize = 126;
    protected int ySize = 166;
    protected int guiLeft;
    protected int guiTop;
    protected final EntityPlayer player;
    
    protected int strength;
    protected int speed;
    protected int vitality;
    protected int agility;
    
    public GuiSkills(EntityPlayer player) {
        this.player = player;
        this.updateStats();
    }
    
    private void updateStats() {
        this.strength = this.player.getCapability(StrengthProvider.STRENGTH_CAP, null).get();
        this.speed = this.player.getCapability(SpeedProvider.SPEED_CAP, null).get() + 10;
        this.vitality = this.player.getCapability(VitalityProvider.VITALITY_CAP, null).get() + 10;
        this.agility = this.player.getCapability(AgilityProvider.AGILITY_CAP, null).get();
    }
    
    private boolean hasSkillPoints() {
    	return this.player.experienceLevel > this.strength + this.speed + this.vitality + this.agility;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButton(101, this.guiLeft + this.xSize - 30, this.guiTop + 45, 20, 20, "+"));
        this.buttonList.add(new GuiButton(102, this.guiLeft + this.xSize - 30, this.guiTop + 75, 20, 20, "+"));
        this.buttonList.add(new GuiButton(103, this.guiLeft + this.xSize - 30, this.guiTop + 105, 20, 20, "+"));
        this.buttonList.add(new GuiButton(104, this.guiLeft + this.xSize - 30, this.guiTop + 135, 20, 20, "+"));
    	if(!this.hasSkillPoints()) {
    		for(GuiButton b : this.buttonList) {
    			b.enabled = false;
    		}
    	} else {
    		for(GuiButton b : this.buttonList) {
    			b.enabled = true;
    		}
    	}
    }
    
    @Override
    public void updateScreen() {
    	if(!this.hasSkillPoints()) {
    		for(GuiButton b : this.buttonList) {
    			b.enabled = false;
    		}
    	} else {
    		for(GuiButton b : this.buttonList) {
    			b.enabled = true;
    		}
    	}
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
    	super.actionPerformed(button);
    	if(this.hasSkillPoints()) {
	        if (button.id == 101) {
	            IStrength strength = this.player.getCapability(StrengthProvider.STRENGTH_CAP, null);
	            strength.add(1);
	            Ref.NETWORK.sendToServer(new MessageSetCap(strength.get(), 0, MessageSetCap.Mode.STRENGTH));
	        } else if (button.id == 102) {
	            ISpeed speed = this.player.getCapability(SpeedProvider.SPEED_CAP, null);
	            speed.add(1);
	            Ref.NETWORK.sendToServer(new MessageSetCap(speed.get(), 0, MessageSetCap.Mode.SPEED));
	        } else if (button.id == 103) {
	            IVitality vitality = this.player.getCapability(VitalityProvider.VITALITY_CAP, null);
	            vitality.add(1);
	            Ref.NETWORK.sendToServer(new MessageSetCap(vitality.get(), 0, MessageSetCap.Mode.VITALITY));
	        } else if (button.id == 104) {
	            IAgility agility = this.player.getCapability(AgilityProvider.AGILITY_CAP, null);
	            agility.add(1);
	            Ref.NETWORK.sendToServer(new MessageSetCap(agility.get(), 0, MessageSetCap.Mode.AGILITY));
	        }
    	}

        this.updateStats();
    }

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
        this.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiSkills.TEXTURE);
		this.drawTexturedModalRect(this.guiLeft + 3, this.guiTop, 126, 0, this.xSize, this.ySize);
		
		//Symbols
		this.drawTexturedModalRect(this.guiLeft + 15, this.guiTop + 50, 0, 166, 10, 10);
		this.drawTexturedModalRect(this.guiLeft + 15, this.guiTop + 80, 10, 166, 10, 10);
		this.drawTexturedModalRect(this.guiLeft + 15, this.guiTop + 110, 20, 166, 10, 10);
		this.drawTexturedModalRect(this.guiLeft + 15, this.guiTop + 140, 30, 166, 10, 10);
		
		//Labels
		this.drawCenteredString(this.fontRenderer, this.player.getName(), this.guiLeft + this.xSize/2, this.guiTop + 20, 4210752);
		this.drawCenteredString(this.fontRenderer, I18n.format("playerstat.level", this.player.experienceLevel), this.guiLeft + this.xSize/2, this.guiTop + 35, 4210752);
		this.drawString(this.fontRenderer, I18n.format("playerstat.strength.name") + ": " + String.valueOf(this.strength), this.guiLeft + 30, this.guiTop + 50, 4210752);
		this.drawString(this.fontRenderer, I18n.format("playerstat.speed.name") + ": " + String.valueOf(this.speed), this.guiLeft + 30, this.guiTop + 80, 4210752);
		this.drawString(this.fontRenderer, I18n.format("playerstat.vitality.name") + ": " + String.valueOf(this.vitality), this.guiLeft + 30, this.guiTop + 110, 4210752);
		this.drawString(this.fontRenderer, I18n.format("playerstat.agility.name") + ": " + String.valueOf(this.agility), this.guiLeft + 30, this.guiTop + 140, 4210752);

		//Frame
		this.mc.getTextureManager().bindTexture(GuiSkills.TEXTURE);
		this.drawTexturedModalRect(this.guiLeft + 3, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		super.drawScreen(mouseX, mouseY, f);
	}
	
	@Override
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color, false);
    }
	
	@Override
    public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, (float)x, (float)y, color, false);
    }
}