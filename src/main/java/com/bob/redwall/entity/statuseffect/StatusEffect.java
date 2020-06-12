package com.bob.redwall.entity.statuseffect;

import com.bob.redwall.Ref;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StatusEffect extends Potion {
    private int statusIconIndex = -1;
    private String name = "";
    public static Potion POISON;
    
	protected StatusEffect(boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
	}
	
	public StatusEffect setIcon(int p_76399_1_, int p_76399_2_) {
        this.statusIconIndex = p_76399_1_ + p_76399_2_ * 8;
        return this;
    }
	
	@Override
	public StatusEffect setPotionName(String nameIn) {
        this.name = nameIn;
        return this;
    }

	@Override
	public String getName() {
        return this.name;
    }

	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasStatusIcon() {
        return this.statusIconIndex >= 0;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        return this.statusIconIndex;
    }
    
    public static void init() {
    	POISON = new StatusEffect(true, 5149489).setPotionName("effect.poison").setIcon(6, 0).setEffectiveness(0.25D).setRegistryName(Ref.MODID, "fatal_poison");
    }
	
	public static void register(Register<Potion> event) {
		event.getRegistry().register(POISON);
    }
}
